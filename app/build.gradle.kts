import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.ktlint)
}

val localProperties = Properties().apply {
    val file = project.rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    } else {
        error("local.properties 파일을 찾을 수 없습니다. 프로젝트 루트에 생성해 주세요.")
    }
}

fun readLocalProperty(key: String): String =
    localProperties.getProperty(key)?.removeSurrounding("\"")
        ?: error("local.properties에 $key 값을 정의해 주세요.")

fun readLocalPropertyQuoted(key: String): String = "\"${readLocalProperty(key)}\""

android {
    namespace = "gildong.dduru"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "gildong.dduru"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Google 웹 클라이언트 ID
        buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", readLocalPropertyQuoted("google.client.id"))

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            readLocalPropertyQuoted("kakao.api.key")
        )

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = readLocalProperty("kakao.api.key")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", readLocalPropertyQuoted("debug.base.url"))
        }

        release {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", readLocalPropertyQuoted("release.base.url"))
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

    // Google 로그인
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    // 카카오 로그인
    implementation("com.kakao.sdk:v2-user:2.20.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
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
