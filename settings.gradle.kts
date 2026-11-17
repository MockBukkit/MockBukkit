plugins {
	/*
	  Lets Gradle download the JDK pinned by `java.version` when a contributor doesn't
	  have that exact version installed. Toolchains match on the exact major version,
	  so a newer JDK does not satisfy the requirement.
	*/
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

/*
  This is the name of our MockBukkit artifact, it includes
  the API version of Minecraft we are targeting.
*/
rootProject.name = "MockBukkit-v${extensions.extraProperties.get("paper.api.version")}"

include(":extra:TestPlugin")
include(":metaminer")
