import java.io.ByteArrayOutputStream
import com.vanniktech.maven.publish.SonatypeHost

plugins {
	id("checkstyle")
	id("java-library")
	id("jacoco")
	id("com.vanniktech.maven.publish") version "0.30.0"
	id("net.kyori.blossom") version "2.1.0"
}

group = "org.mockbukkit.mockbukkit"
version = getFullVersion()

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
	implementation("org.junit.jupiter:junit-jupiter:5.11.4")

	// General utilities for the project
	implementation("net.kyori:adventure-platform-bungeecord:4.3.4")
	implementation("org.jetbrains:annotations:26.0.1")
	implementation("net.bytebuddy:byte-buddy:1.16.1")
	implementation("org.hamcrest:hamcrest:3.0")

	// LibraryLoader dependencies
	implementation("org.apache.maven:maven-resolver-provider:3.8.5")
	implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.9.18")
	implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.9.18")
}

tasks {
	jar {
		manifest {
			attributes(
				"Automatic-Module-Name" to "org.mockbukkit.mockbukkit"
			)
		}
	}

	java {
		withSourcesJar()
	}

	javadoc {
		options {
			(options as? StandardJavadocDocletOptions)?.apply {
				encoding = "UTF-8"
				tags(
					"apiNote:a:API Note:",
					"implSpec:a:Implementation Requirements:",
					"implNote:a:Implementation Note:"
				)
				// Custom options
				addBooleanOption("Xdoclint:all,-missing", true)
				links("https://jd.papermc.io/paper/${(project.property("paper.api.full-version") as String).split('-')[0]}/")
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
		toolVersion = "0.8.12"
	}
}

sourceSets {
	main {
		blossom {
			javaSources {
				property("mockBukkitVersion", getFullVersion())
				property("paperApiFullVersion", project.property("paper.api.full-version").toString())
				property("buildTime", System.currentTimeMillis().toString())
				property("branch", run("git", "rev-parse", "--abbrev-ref", "HEAD"))
				property("commit", run("git", "rev-parse", "HEAD"))
				val buildNumber = System.getenv("GITHUB_RUN_NUMBER")
				if (buildNumber != null) {
					property("buildNumber", buildNumber)
				} else {
					property("buildNumber", "")
				}
			}
		}
	}
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(property("java.version").toString().toInt()))
	}
}

mavenPublishing {
	coordinates(project.group.toString(), "mockbukkit-v${property("paper.api.version")}", project.version.toString())

	pom {
		description.set("MockBukkit is a mocking framework for bukkit to allow the easy unit testing of Bukkit plugins.")
		name.set("MockBukkit-v${property("paper.api.version")}")
		url.set("https://github.com/MockBukkit/MockBukkit")
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
			developer {
				id.set("thebusybiscuit")
				name.set("TheBusyBiscuit")
			}
			developer {
				id.set("insprill")
				name.set("Pierce Thompson")
				email.set("pierce@insprill.net")
			}
			developer {
				id.set("thelooter")
				name.set("Eve Kolb")
				email.set("me@thelooter.de")
			}
			developer{
				id.set("thorinwasher")
				name.set("Hjalmar Gunnarsson")
				email.set("officialhjalmar.gunnarsson@outlook.com")
			}
			developer{
				id.set("4everTheOne")
				name.set("Afonso Oliveira")
			}
		}
		scm {
			connection.set("scm:git:git://github.com/MockBukkit/MockBukkit.git")
			developerConnection.set("scm:git:ssh://github.com:MockBukkit/MockBukkit.git")
			url.set("https://github.com/MockBukkit/MockBukkit/tree/v${property("paper.api.version")}")
		}
	}
	publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
	// No key available to sign with for maven local
	if (!project.gradle.startParameter.taskNames.any { it.contains("publishToMavenLocal") }) {
		signAllPublications()
	}
}

fun isFork(): Boolean {
	return run("git", "config", "--get", "remote.origin.url").contains("MockBukkit/MockBukkit")
}

fun isAction(): Boolean {
	return System.getenv("GITHUB_ACTIONS") == "true" && System.getenv("GITHUB_REPOSITORY") == "MockBukkit/MockBukkit"
}

fun getFullVersion(): String {
	return if (isAction()) {
		property("mockbukkit.version") as String
	} else {
		"dev-${run("git", "rev-parse", "--verify", "--short", "HEAD")}"
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
