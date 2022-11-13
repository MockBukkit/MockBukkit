import java.io.ByteArrayOutputStream
import java.util.Properties

plugins {
    id("java-library")
    id("eclipse")
    id("idea")
    id("jacoco")
    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "com.github.seeseemelk"
version = this.getFullVersion()

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
    maven("https://repo.md-5.net/content/groups/public/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Paper API
    api("io.papermc.paper:paper-api:${property("mockbukkit.api.full-version")}")

    // Dependencies for Unit Tests
    implementation("org.junit.jupiter:junit-jupiter:5.9.1")
    implementation("org.hamcrest:hamcrest-library:2.2")

    // General utilities for the project
    implementation("net.kyori:adventure-platform-bungeecord:4.1.2")
    implementation("org.jetbrains:annotations:23.0.0")

    // LibraryLoader dependencies
    implementation("org.apache.maven:maven-resolver-provider:3.8.5")
    implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.7.3")
    implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.7.3")
}

tasks {
    jar {
        manifest {
            attributes(
                "Automatic-Module-Name" to "be.seeseemelk.mockbukkit"
            )
        }
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }

    javadoc {
        options {
            (options as? StandardJavadocDocletOptions)?.apply {
                encoding = "UTF-8"

                // Custom options
                addBooleanOption("html5", true)
                addStringOption("-release", "17")
                links("https://jd.papermc.io/paper/${property("mockbukkit.api.version")}/")
            }
        }
    }

    processResources {
        doLast {
            file("${buildDir}/resources/main/build.properties").bufferedWriter().use {
                val p = Properties()
                p["full-api-version"] = property("mockbukkit.api.full-version")
                p.store(it, null)
            }
        }
    }

    publishToMavenLocal {
        doLast {
            println("Published to Maven local with version $version")
        }
    }

    test {
        useJUnitPlatform()
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

signing {
    isRequired = !isFork() && System.getenv("CI") != null
    sign(publishing.publications)
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(findProperty("ossrhUsername") as String?)
            password.set(findProperty("ossrhPassword") as String?)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                description.set("MockBukkit is a mocking framework for bukkit to allow the easy unit testing of Bukkit plugins.")
                url.set("https://github.com/MockBukkit/MockBukkit")
                scm {
                    connection.set("scm:git:git://github.com/MockBukkit/MockBukkit.git")
                    developerConnection.set("scm:git:ssh://github.com:MockBukkit/MockBukkit.git")
                    url.set("https://github.com/MockBukkit/MockBukkit/tree/v${property("mockbukkit.api.version")}")
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/MockBukkit/MockBukkit/blob/v${property("mockbukkit.api.version")}/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("seeseemelk")
                        name.set("Sebastiaan de Schaetzen")
                        email.set("sebastiaan.de.schaetzen@gmail.com")
                    }
                }
            }
        }
    }
}

fun isFork(): Boolean {
    return run("git", "config", "--get", "remote.origin.url").contains("MockBukkit/MockBukkit")
}

fun getFullVersion(): String {
    if (System.getenv("CI") == null) {
        return "dev-${run("git", "rev-parse", "--verify", "--short", "HEAD")}"
    } else {
        return property("mockbukkit.version") as String
    }
}

fun run(vararg cmd: String): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine(*cmd)
        standardOutput = stdout
    }
    return stdout.toString().trim()
}
