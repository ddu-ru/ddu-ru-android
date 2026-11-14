package com.gildongmu.dduru.data.entity.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
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
import okhttp3.RequestBody.Companion.toRequestBody

class ImagePresignedUrlUploader @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val imagePresignedUrlApi: ImagePresignedUrlApi,
    @param:Named("presignedUploadClient") private val uploadClient: OkHttpClient
) {
    suspend fun uploadImage(uri: Uri, target: ImageUploadTarget): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val processed = processImage(uri)
            val extension = processed.extension
            val fileName = processed.fileName
            val fileSize = processed.bytes.size.toLong()
            val presignedUrlRequest = ImageUploadRequest(
                files = listOf(ImageUploadFileRequest(fileName = fileName, fileSize = fileSize))
            )
            val presigned = when (target) {
                ImageUploadTarget.PROFILE -> imagePresignedUrlApi.createProfilePresignedUrl(presignedUrlRequest)
                ImageUploadTarget.JOURNEY -> imagePresignedUrlApi.createJourneyPresignedUrl(presignedUrlRequest)
                ImageUploadTarget.CHAT -> imagePresignedUrlApi.createChatPresignedUrls(presignedUrlRequest)
                ImageUploadTarget.POST -> imagePresignedUrlApi.createPostPresignedUrls(presignedUrlRequest)
            }.data?.firstOrNull() ?: error("업로드 URL을 받아오지 못했어요.")
            val mimeType = if (extension == "png") "image/png" else "image/jpeg"

            val request = Request.Builder()
                .url(presigned.presignedUrl)
                .header("Content-Type", mimeType)
                .put(processed.bytes.toRequestBody(mimeType.toMediaTypeOrNull()))
                .build()

            uploadClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    error("이미지 업로드에 실패했어요.")
                }
            }

            presigned.fileUrl
        }
    }

    private class ProcessedImage(val fileName: String, val extension: String, val bytes: ByteArray)

    private fun processImage(uri: Uri): ProcessedImage {
        val mimeType = context.contentResolver.getType(uri)
        val mimeExtension = mimeType?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
        val pathExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        val extension = (mimeExtension ?: pathExtension)
            ?.lowercase()
            ?.takeIf { it.isNotBlank() }
            ?: error("지원하지 않는 이미지 형식이에요.")
        require(extension in setOf("jpg", "jpeg", "png")) { "jpg, jpeg, png 파일만 업로드할 수 있어요." }
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) }
        require(bounds.outWidth > 0 && bounds.outHeight > 0) { "이미지를 읽을 수 없어요." }
        val sample = (maxOf(bounds.outWidth, bounds.outHeight) / 1920).coerceAtLeast(1)
        val bitmap = context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, BitmapFactory.Options().apply { inSampleSize = sample })
        } ?: error("이미지를 읽을 수 없어요.")
        val oriented = orient(bitmap, uri)
        val scale = minOf(1f, 1920f / maxOf(oriented.width, oriented.height))
        val resized = if (scale < 1f) {
            oriented.scale(
                (oriented.width * scale).toInt(),
                (oriented.height * scale).toInt()
            )
        } else {
            oriented
        }
        val format = if (extension == "png") Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG
        var quality = 85
        var bytes = java.io.ByteArrayOutputStream().also { resized.compress(format, quality, it) }.toByteArray()
        while (extension != "png" && bytes.size > MAX_SIZE && quality > 40) {
            quality -= 5
            bytes = java.io.ByteArrayOutputStream().also { resized.compress(format, quality, it) }.toByteArray()
        }
        require(bytes.size <= MAX_SIZE) { "이미지는 5MB 이하여야 해요." }
        val sourceName = context.contentResolver.query(
            uri,
            arrayOf(OpenableColumns.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) cursor.getString(0) else null
        }
        resized.recycle()
        if (oriented !== bitmap) bitmap.recycle()
        bitmap.recycle()
        val baseName = sourceName?.substringBeforeLast('.', "photo") ?: "photo"
        return ProcessedImage("${baseName}_${UUID.randomUUID()}.$extension", extension, bytes)
    }

    private fun orient(bitmap: Bitmap, uri: Uri): Bitmap {
        val orientation = context.contentResolver.openInputStream(uri)?.use {
            ExifInterface(it).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        } ?: ExifInterface.ORIENTATION_NORMAL
        val matrix = Matrix().apply {
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> postRotate(270f)
            }
        }
        return if (matrix.isIdentity) {
            bitmap
        } else {
            Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
        }
    }

    private companion object { const val MAX_SIZE = 5L * 1024 * 1024 }
}

enum class ImageUploadTarget {
    PROFILE, JOURNEY, CHAT, POST
}
