package gildong.dduru.data.remote.datasource

interface KakaoAuthDataSource {
    suspend fun getIdToken(): String?
}
