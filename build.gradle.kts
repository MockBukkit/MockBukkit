plugins {
	id("java")
}

group = "org.mockbukkit"
version = "1.1-SNAPSHOT"

repositories {
	mavenCentral();
	maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
	implementation("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks {
	compileJava {
		options.encoding = Charsets.UTF_8.name()
		options.release.set(17)
	}
}
