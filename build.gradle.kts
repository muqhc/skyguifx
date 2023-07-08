plugins {
    kotlin("jvm") version "1.8.20"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        implementation(kotlin("stdlib"))

        compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

        api("io.github.muqhc:skygui:0.3.3")
    }
}

tasks.create("debugJar") {
    setOf("plugin", "debug").map {
        project("${rootProject.name}-$it")
    }.let {
        it.forEach { dependsOn(":${it.name}:debugJar") }
        doLast {
            it.forEach {
                copy {
                    from(File("${it.buildDir.path}\\libs\\"))
                    include("**/*.jar")
                    into(File(rootDir,".debug/plugins/"))
                }
            }
        }
    }
}
