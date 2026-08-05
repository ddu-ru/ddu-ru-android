package com.gildongmu.dduru.presentation.feature.register.profile.effect

sealed interface ProfileSideEffect {
    data object NavigateToMain : ProfileSideEffect
}
