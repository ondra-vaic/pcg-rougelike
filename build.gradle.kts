import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("multiplatform") version "1.3.71"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "cz.cuni.gamedev.nail123"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

kotlin {
    jvm {
        withJava()
    }
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
                implementation("org.hexworks.zircon:zircon.core-jvm:2020.0.2-PREVIEW")
                implementation("org.hexworks.zircon:zircon.jvm.swing:2020.0.2-PREVIEW")
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
    getByName<Jar>("shadowJar") {
        manifest {
            attributes["Main-Class"] = "MainKt"
        }
    }
}
