plugins {
    kotlin("jvm") version "1.9.10"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.vpnrelay"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("RelayServerKt")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "RelayServerKt"
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("vpn-relay")
        archiveClassifier.set("all")
        archiveVersion.set("")
    }
}
