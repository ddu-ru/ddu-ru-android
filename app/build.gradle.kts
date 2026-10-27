import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.ktlint)
}

val hasGoogleServicesConfig = listOf(
    "google-services.json",
    "src/debug/google-services.json",
    "src/release/google-services.json"
).any { path -> file(path).exists() }

if (hasGoogleServicesConfig) {
    apply(plugin = "com.google.gms.google-services")
}

val localProperties = Properties().apply {
    val file = project.rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

fun readLocalProperty(
    key: String,
    allowDefault: Boolean,
    default: String = "DUMMY"
): String {
    val value = localProperties.getProperty(key)?.removeSurrounding("\"")
    return when {
        value != null -> value
        allowDefault -> default
        else -> error("local.properties에 $key 값을 정의해 주세요.")
    }
}

fun readLocalPropertyQuoted(
    key: String,
    allowDefault: Boolean
): String = "\"${readLocalProperty(key, allowDefault)}\""

android {
    namespace = "com.gildongmu.dduru"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.gildongmu.dduru"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Google 웹 클라이언트 ID
        buildConfigField(
            "String",
            "GOOGLE_WEB_CLIENT_ID",
            readLocalPropertyQuoted("google.client.id", allowDefault = true)
        )

        // Kakao api 키
        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            readLocalPropertyQuoted("kakao.api.key", allowDefault = true)
        )

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] =
            readLocalProperty("kakao.api.key", allowDefault = true)
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", readLocalPropertyQuoted("debug.base.url", allowDefault = true))
        }

        release {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", readLocalPropertyQuoted("release.base.url", allowDefault = true))
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    // buildToolsVersion = "36.1.0"
}

dependencies {
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.test)
    debugImplementation(libs.bundles.debug)
    implementation(libs.bundles.androidx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.kotlinx.immutable)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.coil.compose)
    implementation(libs.timber)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.foundation)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)

    // Google 로그인
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    // 카카오 로그인
    implementation("com.kakao.sdk:v2-user:2.20.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    implementation(libs.haze)
}

ktlint {
    android = true
    debug = true
    coloredOutput = true
    verbose = true
    outputToConsole = true

    filter {
        exclude { element -> element.file.path.contains("test/") }
        exclude { element -> element.file.path.contains("androidTest/") }
    }
}
