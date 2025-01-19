/*
  This is the name of our MockBukkit artifact, it includes
  the API version of Minecraft we are targeting.
*/
rootProject.name = "MockBukkit-v${extensions.extraProperties.get("paper.api.version")}"

include(":extra:TestPlugin")
include(":metaminer")
