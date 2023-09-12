plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.8.0"
}


apply(plugin = "maven-publish")
apply(plugin = "signing")

val PUBLISH_GROUP_ID = "io.github.danielandroidtt"
val PUBLISH_VERSION = "1.4.0"
val PUBLISH_ARTIFACT_ID = "PerScope"
val PUBLISH_DESCRIPTION = "Introducing \"PerScope\" Library: Simplifying Privacy Policy Event Handling for Android Apps\n" +
        "\n" +
        "\"PerScope\" is a cutting-edge library designed to streamline the processing of privacy policy events within regions where compliance with local legislation is crucial. Specifically crafted for Android applications, this library addresses the intricate task of managing privacy policy-related events while adhering to the legal requirements of the country in which the app is deployed.\n" +
        "\n" +
        "In today's digital landscape, ensuring user privacy and data protection is of paramount importance. Different countries have varying legal frameworks dictating how user data should be handled, necessitating robust mechanisms to accommodate these differences seamlessly. This is where the \"PerScope\" library shines.\n" +
        "\n" +
        "The key feature that sets \"PerScope\" apart is its incredible simplicity. With just a single function call, developers can integrate the library into their Android applications and gain immediate access to a comprehensive suite of tools for managing privacy policy events. Whether it's presenting privacy-related notifications, tracking user consents, or adapting the app's behavior based on regional requirements, \"PerScope\" handles it all efficiently and effectively.\n" +
        "\n" +
        "Here's a glimpse of what \"PerScope\" brings to the table:\n" +
        "\n" +
        "Localized Compliance: \"PerScope\" empowers developers to align their apps with the privacy laws of each region. By intelligently detecting the user's location, the library ensures that the app's behavior remains compliant with the specific privacy regulations of that area.\n" +
        "\n" +
        "Event Handling Made Easy: Instead of grappling with complex event management code, developers can integrate the \"PerScope\" function, drastically reducing development time and effort. The library takes care of the intricate event handling process seamlessly.\n" +
        "\n" +
        "Dynamic Adaptation: With the ability to dynamically adapt the app's features based on the user's consent and the local legal requirements, \"PerScope\" ensures a personalized and compliant user experience.\n" +
        "\n" +
        "Notification Presentation: \"PerScope\" assists in presenting privacy-related notifications to users, making it easier to inform them about data collection practices and obtain necessary consents.\n" +
        "\n" +
        "Smooth Integration: The library is designed to be easily integrated into existing Android applications, minimizing disruptions to the development process.\n" +
        "\n" +
        "In a nutshell, \"PerScope\" is a developer's go-to solution for managing privacy policy events within Android apps. Its single-function approach, combined with its capacity to handle a complex and critical aspect of app development, makes it an indispensable tool for app creators aiming to provide a user-centric, privacy-respecting experience while complying with regional legislation. Stay on the right side of the law and prioritize user privacy with the power of \"PerScope.\""
val PUBLISH_URL = "https://github.com/DanielAndroidTT/PerScope"
val PUBLISH_LICENSE_NAME = "MIT License"
val PUBLISH_LICENSE_URL = "https://github.com/DanielAndroidTT/PerScope/blob/master/SECURITY.md"
val PUBLISH_DEVELOPER_ID = "DanielAndroidTT"
val PUBLISH_DEVELOPER_NAME = "DanielAndroidTT"
val PUBLISH_DEVELOPER_EMAIL = "daniel3553daniel@gmail.com"
val PUBLISH_SCM_CONNECTION = "scm:git:github.com/DanielAndroidTT/PerScope.git"
val PUBLISH_SCM_DEVELOPER_CONNECTION =
        "scm:git:ssh://httpa://github.com/DanielAndroidTT/PerScope.git"
val PUBLISH_SCM_URL = "https://github.com/DanielAndroidTT/PerScope"

group = PUBLISH_GROUP_ID
version = PUBLISH_VERSION

afterEvaluate {
    configure<PublishingExtension> {
        publications.create<MavenPublication>("PerScope") {
            groupId = PUBLISH_GROUP_ID
            artifactId = PUBLISH_ARTIFACT_ID
            version = PUBLISH_VERSION

            afterEvaluate {
                println(components)
                from(components.getByName("release"))
            }

            pom {
                name.set(PUBLISH_ARTIFACT_ID)
                description.set(PUBLISH_DESCRIPTION)
                url.set(PUBLISH_URL)
                licenses {
                    license {
                        name.set(PUBLISH_LICENSE_NAME)
                        url.set(PUBLISH_LICENSE_URL)
                    }
                }
                developers {
                    developer {
                        id.set(PUBLISH_DEVELOPER_ID)
                        name.set(PUBLISH_DEVELOPER_NAME)
                        email.set(PUBLISH_DEVELOPER_EMAIL)
                    }
                }
                scm {
                    connection.set(PUBLISH_SCM_CONNECTION)
                    developerConnection.set(PUBLISH_SCM_DEVELOPER_CONNECTION)
                    url.set(PUBLISH_SCM_URL)
                }
            }
        }
        repositories {
            mavenLocal()
            mavenCentral()
        }

        configure<SigningExtension> {
            sign(publications["PerScope"])
        }
    }
}

android {
    namespace = "com.global.ratio_checker"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.onesignal:OneSignal:4.8.6")
    implementation("com.appsflyer:af-android-sdk:6.12.1")
    implementation("com.facebook.android:facebook-android-sdk:16.1.3")
    implementation("io.ktor:ktor-client-android:2.3.3")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:1.0.0")
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation("com.karumi:dexter:6.2.3")

}