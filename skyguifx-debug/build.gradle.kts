repositories {
    mavenLocal()
    mavenCentral()
}

val projectMain = project(":${rootProject.name}-main")

dependencies {
    implementation(projectMain)
    compileOnly("xyz.icetang.lib:icemmand-api:1.21.1+1.0.0")
}

val pluginName = rootProject.name.split('-').joinToString("") { it.capitalize() }
val packageName = rootProject.name.replace("-", "")
extra.set("pluginName", pluginName)
extra.set("packageName", packageName)

tasks {
    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
            expand(extra.properties)
        }
    }

    create<Jar>("debugJar") {
        archiveBaseName.set(pluginName+"Debug")

        from(project.sourceSets["main"].output)
        from(projectMain.sourceSets["main"].output)
    }
}
