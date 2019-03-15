package fr.xgouchet.gradle

object Dependencies {

    object Versions {
        const val ArgParser = "2.0.7"
        const val Kotlin = "1.3.0"
        const val Retrofit = "2.4.0"
        const val RxJava2 = "2.1.8"

        // Tests
        const val JUnit4 = "4.12"
        const val Spek = "1.1.5"
        const val JUnitPlatform = "1.0.0"
        const val MockitoKotlin = "1.5.0"
        const val Mockito = "2.18.3"
        const val Elmyr = "0.6.2"
        const val AssertK = "0.1.1"

        // Plugins
        const val DependencyVersions = "0.17.0"
        const val Detekt = "1.0.0.RC9"
    }

    object Libraries {
        const val ArgParser = "com.xenomachina:kotlin-argparser:${Versions.ArgParser}"

        const val KotlinJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin}"

        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"

        const val Rx = "io.reactivex.rxjava2:rxjava:${Versions.RxJava2}"

        const val JUnit4 = "junit:junit:${Versions.JUnit4}"

        @JvmField val Spek = arrayOf("org.jetbrains.spek:spek-api:${Versions.Spek}",
                "org.jetbrains.spek:spek-junit-platform-engine:${Versions.Spek}",
                "org.jetbrains.kotlin:kotlin-refleAct:${Versions.Kotlin}")
        @JvmField val Mockito = arrayOf("com.nhaarman:mockito-kotlin:${Versions.MockitoKotlin}",
                "org.mockito:mockito-core:${Versions.Mockito}")
        @JvmField val TestTools = arrayOf("com.github.xgouchet:Elmyr:${Versions.Elmyr}",
                "com.github.memoizr:assertk-core:${Versions.AssertK}")

    }

    object Processors {
    }

    object ClassPaths {
        const val Kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin}"
        const val DependencyVersions = "com.github.ben-manes:gradle-versions-plugin:${Versions.DependencyVersions}"
        const val JUnitPlatform = "org.junit.platform:junit-platform-gradle-plugin:${Versions.JUnitPlatform}"
    }

    object Repositories {
        const val Spek = "http://dl.bintray.com/jetbrains/spek"
        const val JitPack = "https://jitpack.io"
        const val Gradle = "https://plugins.gradle.org/m2/"
        const val Google = "https://maven.google.com"
    }
}
