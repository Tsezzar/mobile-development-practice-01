plugins {
    kotlin("jvm") version "2.1.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

application {
    // Точка входа: pr1/Main.kt запускает демонстрации всех задач по порядку
    mainClass.set("pr1.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
