import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("multiplatform") version "1.3.71"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "cz.cuni.gamedev.nail123"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }

        js().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
    }
}

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
    }
}

// Final result in JVM form - runnable JAR
tasks.register<ShadowJar>("shadowJar") {
    group = "shadow"
    destinationDirectory.set(file("out/jvm"))

    manifest {
        attributes["Manifest-Version"] = "1.0"
        attributes["Main-Class"] = "MainKt"
    }
    archiveClassifier.set("all")
    from(kotlin.jvm().compilations.getByName("main").output)
    configurations = mutableListOf(kotlin.jvm().compilations.getByName("main").compileDependencyFiles as Configuration)
}

// Final result in JS form - working webpage
tasks.register<Copy>("buildWeb") {
    group = "build"
    dependsOn("jsBrowserProductionWebpack")

    from("build/distributions")
    into("out/js")
}