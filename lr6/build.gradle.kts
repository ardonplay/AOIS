plugins {
    kotlin("jvm") version "1.8.0"
    `maven-publish`
    application
}

group = "org.ardonplay.aois"
version = "1.0"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}



publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.ardonplay.aois.lr6"
            artifactId = "lr6"
            version = "1.0"
            from(components["java"])
        }
    }
}

