plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "org.rko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(19)
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

sourceSets {
    main {
        java.srcDirs("src/main/java")
        kotlin.srcDirs("src/main/kotlin")
        resources.srcDirs("src/main/resources")
    }
    test {
        java.srcDirs("src/test/java")
        kotlin.srcDirs("src/test/kotlin")
        resources.srcDirs("src/test/resources")
    }
}

tasks {
    compileJava {
        dependsOn(compileKotlin)
        doFirst {
            options.compilerArgs = listOf("--module-path", classpath.asPath)
        }
    }

    compileKotlin {
        destinationDirectory.set(compileJava.get().destinationDirectory)
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}


application {
    mainModule.set("org.rko.crawler")
    mainClass.set("org.rko.crawler.CrawlerKt")
}