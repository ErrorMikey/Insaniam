import org.gradle.internal.impldep.org.bouncycastle.cms.RecipientId.password

plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version "1.9.0"
    id("com.gradle.plugin-publish") version "1.2.0"
}

group = "pro.mikey.plugins"
version = "0.1-SNAPSHOT"
description = "Not sure yet"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    testImplementation(kotlin("test:1.8.10"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
}

tasks.withType(JavaCompile::class.java).all {
    options.release = 17
}

gradlePlugin {
    website = "https://github.com/errormikey/insaniam"
    vcsUrl = "https://github.com/errormikey/insaniam"
//    testSourceSet(sourceSets["test"])

    plugins.create("insaniam") {
        id = "pro.mikey.plugins.insaniam"
        implementationClass = "pro.mikey.insaniam.InsaniamPlugin"
        displayName = "Insaniam"
        description = project.description
        version = project.version
        tags = listOf("minecraft", )
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            val isSnapshot = providers.environmentVariable("SNAPSHOT").getOrElse("false").toBoolean()

            url = uri("https://maven.saps.dev/${if (isSnapshot) "snapshots" else "releases"}")
            credentials {
                username = "errormikey"
                password = providers.environmentVariable("SAPS_TOKEN").get()
            }
        }
    }
}
