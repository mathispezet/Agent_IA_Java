plugins {
    id("application")
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "fr.cours"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.langchain4j:langchain4j:1.7.1")
    implementation("dev.langchain4j:langchain4j-ollama:1.7.1")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("fr.cours.projkilmat.App")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named("build") {
    dependsOn(tasks.named("shadowJar"))
}
