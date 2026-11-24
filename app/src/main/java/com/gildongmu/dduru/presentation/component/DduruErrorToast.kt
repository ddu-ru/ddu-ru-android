package com.gildongmu.dduru.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.ImageUploadPolicyException
import com.gildongmu.dduru.domain.entity.ImageUploadPolicyReason
import java.io.IOException
import kotlinx.coroutines.delay

private val ToastShape = RoundedCornerShape(16.dp)
private const val SWIPE_DISMISS_THRESHOLD = -8f
private const val TOAST_DURATION_MILLIS = 3000L

data class DduruToastMessage(
    val title: String,
    val message: String
)

object DduruErrorToasts {
    val UnsupportedImage = DduruToastMessage("지원하지 않는 이미지 형식", "JPG, JPEG, PNG 이미지만 업로드할 수 있어요")
    val ImageTooLarge = DduruToastMessage("이미지 용량 초과", "5MB 이하의 이미지만 업로드할 수 있어요")
    val TooManyImages = DduruToastMessage("이미지 개수 초과", "이미지는 최대 10장까지 보낼 수 있어요")
    val ClosedMeetup = DduruToastMessage("마감된 모임", "다른 동행을 찾아보세요")
    val RequestProcessed = DduruToastMessage("이미 처리된 요청", "잠시 후 다시 시도해 보세요")
    val PermissionDenied = DduruToastMessage("작업 권한 부족", "이 작업을 수행할 권한이 없어요")
    val ServerFailed = DduruToastMessage("서버 작업 실패", "일시적인 오류가 발생했어요")
    val ServiceFailed = DduruToastMessage("서비스 응답 실패", "서비스 연결에 실패했어요")
    val PostUnavailable = DduruToastMessage("게시글을 찾을 수 없어요", "삭제되었거나 존재하지 않는 게시글이에요")
}

fun Throwable.toErrorToast(): DduruToastMessage = when (this) {
    is ImageUploadPolicyException -> when (reason) {
        ImageUploadPolicyReason.UNSUPPORTED_FORMAT -> DduruErrorToasts.UnsupportedImage
        ImageUploadPolicyReason.TOO_LARGE -> DduruErrorToasts.ImageTooLarge
        ImageUploadPolicyReason.TOO_MANY -> DduruErrorToasts.TooManyImages
    }
    is IOException -> DduruErrorToasts.ServiceFailed
    else -> DduruErrorToasts.ServerFailed
}

class DduruErrorToastState {
    var current by mutableStateOf<DduruToastMessage?>(null)
        private set

    fun show(toast: DduruToastMessage) {
        current = toast
    }

    fun dismiss() {
        current = null
    }
}

val LocalDduruErrorToast = staticCompositionLocalOf { DduruErrorToastState() }

@Composable
fun DduruErrorToastHost(content: @Composable () -> Unit) {
    val state = remember { DduruErrorToastState() }

    CompositionLocalProvider(LocalDduruErrorToast provides state) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()

            state.current?.let { toast ->
                LaunchedEffect(toast) {
                    delay(TOAST_DURATION_MILLIS)
                    state.dismiss()
                }

                DduruErrorToast(
                    title = toast.title,
                    message = toast.message,
                    onDismiss = state::dismiss,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
                        .padding(top = 33.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun DduruErrorToast(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .dduruDualShadow(
                firstAlpha = 0.24f,
                secondAlpha = 0.12f,
                shape = ToastShape,
                secondElevation = 8.dp
            )
            .background(DduruTheme.colors.gray928.copy(alpha = 0.5f), ToastShape)
            .border(1.dp, DduruTheme.colors.gray0.copy(alpha = 0.3f), ToastShape)
            .pointerInput(onDismiss) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        if (dragAmount < SWIPE_DISMISS_THRESHOLD) onDismiss()
                    }
                )
            }
            .padding(start = 16.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_toast_error),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )

        Column {
            Text(
                text = title,
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray0
            )
            Text(
                text = message,
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray300
            )
        }
    }
}
