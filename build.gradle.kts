plugins {
    application
    kotlin("jvm") version "1.9.10"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("RelayServerKt") // matches your Kotlin filename
}

dependencies {
    implementation(kotlin("stdlib"))
}
