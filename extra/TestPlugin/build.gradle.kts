plugins {
	id("java")
}

repositories {
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:${rootProject.property("paper.api.full-version")}")
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks {
	jar {
		doLast {
			project.copy {
				from(outputs.files.singleFile)
				into("./")
			}
		}
	}

	register("deploy") {
		dependsOn("jar")
		doLast {
			project.copy {
				from("build/libs/TestPlugin.jar")
				into(File(file(project.property("test-plugin.deploy-dir") as String), "plugins/"))
			}
		}
	}
}
