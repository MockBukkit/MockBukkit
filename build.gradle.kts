import java.io.ByteArrayOutputStream

plugins {
	id("java-library")
	id("jacoco")
	id("maven-publish")
	id("signing")
	id("net.kyori.blossom") version "2.1.0"
	id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
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
	api("io.papermc.paper:paper-api:${property("paper.api.full-version")}")

	// Dependencies for Unit Tests
	implementation("org.junit.jupiter:junit-jupiter:5.10.1")

	// General utilities for the project
	implementation("net.kyori:adventure-platform-bungeecord:4.3.1")
	implementation("org.jetbrains:annotations:24.1.0")
	implementation("net.bytebuddy:byte-buddy:1.14.10")

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
				links("https://jd.papermc.io/paper/${project.property("paper.api.version")}/")
			}
		}
	}

	publishToMavenLocal {
		doLast {
			println("Published to Maven local with version $version")
		}
	}

	test {
		dependsOn(project(":extra:TestPlugin").tasks.jar)
		useJUnitPlatform()
	}

	check {
		 dependsOn(jacocoTestReport)
	}

	jacocoTestReport {
		dependsOn(test)
		reports {
			xml.required.set(true)
			html.required.set(true)
			html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
		}
	}

	jacoco {
		toolVersion = "0.8.11"
	}
}

sourceSets {
	main {
		blossom {
			javaSources {
				property("paperApiFullVersion", project.property("paper.api.full-version").toString())
			}
		}
	}
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

signing {
	isRequired = !isFork() && isAction()
	sign(publishing.publications)
}

nexusPublishing {
	this.repositories {
		sonatype {
			username.set(findProperty("ossrhUsername") as String?)
			password.set(findProperty("ossrhPassword") as String?)
		}
	}
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			artifactId = "MockBukkit-v${property("paper.api.version")}"
			from(components.getByName("java"))
			pom {
				name.set("MockBukkit-v${property("paper.api.version")}")
				description.set("MockBukkit is a mocking framework for bukkit to allow the easy unit testing of Bukkit plugins.")
				url.set("https://github.com/MockBukkit/MockBukkit")
				scm {
					connection.set("scm:git:git://github.com/MockBukkit/MockBukkit.git")
					developerConnection.set("scm:git:ssh://github.com:MockBukkit/MockBukkit.git")
					url.set("https://github.com/MockBukkit/MockBukkit/tree/v${property("paper.api.version")}")
				}
				licenses {
					license {
						name.set("MIT License")
						url.set("https://github.com/MockBukkit/MockBukkit/blob/v${property("paper.api.version")}/LICENSE")
					}
				}
				developers {
					developer {
						id.set("seeseemelk")
						name.set("Sebastiaan de Schaetzen")
						email.set("sebastiaan.de.schaetzen@gmail.com")
					}
					developer{
						id.set("thebusybiscuit")
						name.set("TheBusyBiscuit")
					}
					developer {
						id.set("insprill")
						name.set("Pierce Thompson")
					}
					developer {
						id.set("thelooter")
						name.set("Eve Kolb")
					}
				}
			}
		}
	}
}

fun isFork(): Boolean {
	return run("git", "config", "--get", "remote.origin.url").contains("MockBukkit/MockBukkit")
}

fun isAction(): Boolean {
	return System.getenv("CI") != null
}

fun getFullVersion(): String {
	return if (!isAction()) {
		"dev-${run("git", "rev-parse", "--verify", "--short", "HEAD")}"
	} else {
		property("mockbukkit.version") as String
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
