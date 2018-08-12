import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.incremental.standaloneCacheVersion

plugins {
    java
    application
    kotlin("jvm") version "1.2.60"
}

group = "org.opdebeeck"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "NamesKt"
}

val run by tasks.getting(JavaExec::class) {
    standardInput = System.`in`
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.opencsv", "opencsv", "4.2")
    testCompile("org.junit.jupiter", "junit-jupiter-api", "5.2.0")
    testCompile(group = "org.assertj", name = "assertj-core", version = "3.10.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
