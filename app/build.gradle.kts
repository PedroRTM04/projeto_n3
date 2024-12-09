plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // Inclua o kapt diretamente
}


android {
    namespace = "com.example.projeton3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projeton3"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Dependências padrão
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit para requisições HTTP
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room para armazenamento local
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("com.github.bumptech.glide:compiler:4.15.1")
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // Biblioteca para gráficos
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Coroutines para requisições assíncronas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}
