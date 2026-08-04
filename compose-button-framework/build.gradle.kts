plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    `maven-publish`
}

android {
    namespace = "com.infinity8.compose_button_framework"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }
    buildFeatures {
        compose = true
    }
    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

}
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.vedprakashsah1998"
            artifactId = "compose-button-framework"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Compose Button Framework")
                description.set("A native button UI framework for Jetpack Compose, built from scratch.")
                // url, licenses, developers, scm — required only if publishing to Maven Central
            }
        }
    }

    repositories {
        // Local repo for testing (publishes into build/repo).
        // JitPack does NOT use this — it builds from your public repo tag.
        maven {
            name = "localRepo"
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
}
dependencies {
    api(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}