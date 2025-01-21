plugins {
	id("java")
	id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
	id("xyz.jpenilla.run-paper") version "2.3.0"
}

group = "org.mockbukkit"
version = "1.2-SNAPSHOT"

repositories {
	mavenCentral();
	maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
	paperweight.paperDevBundle("${rootProject.property("paper.api.full-version")}")
	implementation("io.papermc.paper:paper-api:${rootProject.property("paper.api.full-version")}")
}

tasks {
	compileJava {
		options.encoding = Charsets.UTF_8.name()
		options.release.set(21)
	}

	processResources {
		filesMatching("**/plugin.yml") { expand(project.properties) }
	}
}
