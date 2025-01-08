import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.github.chizhanov"
version = "0.0.1"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    // TODO: didn't tested
    //iosX64()
    //iosArm64()
    //iosSimulatorArm64()
    //linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.kotlinx.datetime)
            }
        }
    }
}

android {
    namespace = "com.github.chizhanov.ecoengine"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    //publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    //signAllPublications()

    coordinates(group.toString(), "ecoengine", version.toString())

    pom {
        name = "EcoEngine"
        description = "A Compose Multiplatform based game engine."
        inceptionYear = "2024"
        url = "https://github.com/chizhanov/EcoEngine"
        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/licenses/MIT"
            }
        }
        developers {
            developer {
                id = "chizhanov"
                name = "Ilia Chizhanov"
                url = "https://github.com/chizhanov"
            }
        }
        scm {
            url = "https://github.com/chizhanov/EcoEngine"
            connection = "scm:git:git://github.com/chizhanov/EcoEngine.git"
            developerConnection = "scm:git:ssh://github.com/chizhanov/EcoEngine.git"
        }
    }
}
