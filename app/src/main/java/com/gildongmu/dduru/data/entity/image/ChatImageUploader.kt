package com.gildongmu.dduru.data.entity.image

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import com.gildongmu.dduru.data.entity.image.api.ImagePresignedUrlApi
import com.gildongmu.dduru.data.entity.image.model.ImageUploadFileRequest
import com.gildongmu.dduru.data.entity.image.model.ImageUploadRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

class ChatImageUploader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imagePresignedUrlApi: ImagePresignedUrlApi,
    @Named("presignedUploadClient") private val uploadClient: OkHttpClient
) {
    suspend fun uploadImages(uris: List<Uri>): Result<List<String>> = withContext(Dispatchers.IO) {
        runCatching {
            require(uris.isNotEmpty()) { "보낼 사진이 없어요." }
            require(uris.size <= MAX_IMAGE_COUNT) { "사진은 한 번에 최대 ${MAX_IMAGE_COUNT}장까지 보낼 수 있어요." }

            val files = uris.map { it.toUploadFile() }
            val presignedList = imagePresignedUrlApi.createChatPresignedUrls(
                ImageUploadRequest(
                    files = files.map { ImageUploadFileRequest(fileName = it.fileName, fileSize = it.size) }
                )
            ).data ?: error("업로드 URL을 받아오지 못했어요.")
            check(presignedList.size == files.size) { "업로드 URL을 받아오지 못했어요." }

            files.zip(presignedList).map { (file, presigned) ->
                upload(presigned.presignedUrl, file)
                presigned.fileUrl
            }
        }
    }

    private data class UploadFile(
        val uri: Uri,
        val fileName: String,
        val mimeType: String,
        val size: Long
    )

    private fun Uri.toUploadFile(): UploadFile {
        val mimeType = context.contentResolver.getType(this)
        val extension = (
            mimeType?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
                ?: MimeTypeMap.getFileExtensionFromUrl(toString())
            )?.lowercase()
        require(extension != null && extension in ALLOWED_EXTENSIONS) { "jpg, jpeg, png 사진만 보낼 수 있어요." }

        val size = resolveFileSize(this)
        require(size in 1..MAX_FILE_SIZE) { "사진은 한 장에 최대 5MB까지 보낼 수 있어요." }

        return UploadFile(
            uri = this,
            fileName = "${UUID.randomUUID()}.$extension",
            mimeType = mimeType ?: if (extension == "png") "image/png" else "image/jpeg",
            size = size
        )
    }

    private fun resolveFileSize(uri: Uri): Long {
        context.contentResolver.openAssetFileDescriptor(uri, "r")?.use { descriptor ->
            if (descriptor.length >= 0) return descriptor.length
        }
        context.contentResolver.query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { cursor ->
            if (cursor.moveToFirst() && !cursor.isNull(0)) return cursor.getLong(0)
        }
        error("사진 크기를 확인할 수 없어요.")
    }

    private fun upload(presignedUrl: String, file: UploadFile) {
        val body = object : RequestBody() {
            override fun contentType() = file.mimeType.toMediaTypeOrNull()

            override fun contentLength() = file.size

            override fun writeTo(sink: BufferedSink) {
                context.contentResolver.openInputStream(file.uri)?.use { input ->
                    sink.writeAll(input.source())
                } ?: error("사진 파일을 읽을 수 없어요.")
            }
        }

        val request = Request.Builder()
            .url(presignedUrl)
            .put(body)
            .build()

        uploadClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) error("사진 업로드에 실패했어요.")
        }
    }

    private companion object {
        const val MAX_IMAGE_COUNT = 10
        const val MAX_FILE_SIZE = 5L * 1024 * 1024
        val ALLOWED_EXTENSIONS = setOf("jpg", "jpeg", "png")
    }
}
