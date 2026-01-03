package com.gildongmu.dduru_android.domain.usecase

import com.gildongmu.dduru_android.domain.repository.NicknameRepository
import javax.inject.Inject

class GenerateNicknameUseCase @Inject constructor(
    private val repository: NicknameRepository
) {
    suspend operator fun invoke() =
        repository.generateNickname()
}
