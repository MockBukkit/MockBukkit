plugins {
	id("java")
	id("io.papermc.paperweight.userdev") version "1.7.1"
	id("xyz.jpenilla.run-paper") version "2.3.0"
}

group = "org.mockbukkit"
version = "1.2-SNAPSHOT"

repositories {
	mavenCentral();
	maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
	paperweight.paperDevBundle("${property("paper.api.version")}")
	implementation("io.papermc.paper:paper-api:${property("paper.api.version")}")
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
