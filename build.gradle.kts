import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.10"
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    // apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    group = "com.firestartermc"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
        implementation("com.google.guava:guava:31.1-jre")
        implementation("org.slf4j:slf4j-api:2.0.0")
        implementation("org.mongodb:mongodb-driver-sync:4.4.0")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_16
        targetCompatibility = JavaVersion.VERSION_16

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(17)
        options.compilerArgs.add("-parameters")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-java-parameters")
            jvmTarget = "17"
        }
    }
}