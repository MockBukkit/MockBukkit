plugins {
	id("java")
}

repositories {
	maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:${rootProject.property("paper.api.full-version")}")
}
java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}
