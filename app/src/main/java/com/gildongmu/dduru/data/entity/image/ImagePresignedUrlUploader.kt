package com.gildongmu.dduru.data.entity.image

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.gildongmu.dduru.data.entity.image.api.ImagePresignedUrlApi
import com.gildongmu.dduru.data.entity.image.model.ImagePresignedUrlRequest
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

class ImagePresignedUrlUploader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imagePresignedUrlApi: ImagePresignedUrlApi,
    @Named("presignedUploadClient") private val uploadClient: OkHttpClient
) {
    suspend fun uploadImage(uri: Uri): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val extension = resolveExtension(uri)
            validateExtension(extension)

            val fileName = "${UUID.randomUUID()}.$extension"
            val presigned = imagePresignedUrlApi.createPresignedUrl(
                ImagePresignedUrlRequest(fileNames = listOf(fileName))
            ).data?.firstOrNull() ?: error("업로드 URL을 받아오지 못했어요.")

            val mimeType = context.contentResolver.getType(uri) ?: defaultMimeType(extension)

            val request = Request.Builder()
                .url(presigned.presignedUrl)
                .header("Content-Type", mimeType)
                .put(uri.asRequestBody(mimeType))
                .build()

            uploadClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    error("이미지 업로드에 실패했어요.")
                }
            }

            presigned.fileUrl
        }
    }

    private fun resolveExtension(uri: Uri): String {
        val mimeType = context.contentResolver.getType(uri)
        val mimeExtension = mimeType?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
        val pathExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())

        return (mimeExtension ?: pathExtension)
            ?.lowercase()
            ?.takeIf { it.isNotBlank() }
            ?: error("지원하지 않는 이미지 형식이에요.")
    }

    private fun validateExtension(extension: String) {
        val allowedExtensions = setOf("jpg", "jpeg", "png", "gif")
        require(extension in allowedExtensions) { "jpg, jpeg, png, gif 파일만 업로드할 수 있어요." }
    }

    private fun defaultMimeType(extension: String): String {
        return when (extension) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            else -> "application/octet-stream"
        }
    }

    private fun Uri.asRequestBody(mimeType: String): RequestBody {
        val contentResolver = context.contentResolver
        return object : RequestBody() {
            override fun contentType() = mimeType.toMediaTypeOrNull()

            override fun contentLength(): Long {
                return contentResolver.openAssetFileDescriptor(this@asRequestBody, "r")
                    ?.use { descriptor ->
                        descriptor.length.takeIf { it >= 0 } ?: -1L
                    }
                    ?: -1L
            }

            override fun writeTo(sink: BufferedSink) {
                contentResolver.openInputStream(this@asRequestBody)?.use { input ->
                    sink.writeAll(input.source())
                } ?: error("이미지 파일을 읽을 수 없어요.")
            }
        }
    }
}
