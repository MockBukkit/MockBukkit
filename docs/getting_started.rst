Getting Started
===============

Before MockBukkit can be used, a dependency has to be added to your build tool.
The latest stable version can always be found at https://search.maven.org/search?q=MockBukkit

Gradle
------
If you're using Gradle, add the following to your `build.gradle` file:
::

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation 'junit:junit:4.12'
        testImplementation 'com.github.seeseemelk:MockBukkit-v1.15:0.3.0-SNAPSHOT'
    }
