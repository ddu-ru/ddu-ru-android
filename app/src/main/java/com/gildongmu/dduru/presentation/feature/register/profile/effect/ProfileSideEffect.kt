package com.gildongmu.dduru.presentation.feature.register.profile.effect

import com.gildongmu.dduru.presentation.component.DduruToastMessage

sealed interface ProfileSideEffect {
    data object NavigateToMain : ProfileSideEffect
    data class ShowError(val toast: DduruToastMessage) : ProfileSideEffect
}
