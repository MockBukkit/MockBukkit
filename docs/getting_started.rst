Getting Started
===============

In order to use MockBukkit, you first have to integrate it into your build tool.
You will also need to know which version of MockBukkit to use.
MockBukkit version numbering can be a little bit confusing.
The most important thing to remember is that each version of MockBukkit is named
after the version of Bukkit it implements, followed by the version number of
MockBukkit itself.

For instance: MockBukkit-v1.20 v3.3.0 is the 3.3.0 release of MockBukkit,
targeting plugins build for Minecraft 1.20.
The latest stable version can always be found on `Maven Central <https://search.maven.org/search?q=MockBukkit>`_.

Gradle
------
If you are using Gradle, you will need to setup your ``build.gradle`` file to enable
unit testing.

Dependencies
^^^^^^^^^^^^
To enable unit testing, all you need to do is add the JUnit dependency and the
correct MockBukkit dependency.
Here is an example which adds support for JUnit 5.9.3 and MockBukkit-v1.20:3.0.0

Groovy DSL
^^^^^^^^^^
This is how you add the dependencies in Groovy DSL

.. code-block:: groovy

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation 'org.junit.jupiter:junit-jupiter:5.9.3'
        testImplementation 'com.github.seeseemelk:MockBukkit-v1.20:3.3.0'
    }

Kotlin DSL
^^^^^^^^^^
This is how you add the dependencies in Kotlin DSL

::

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation('org.junit.jupiter:junit-jupiter:5.9.3')
        testImplementation('com.github.seeseemelk:MockBukkit-v1.20:3.3.0')
    }

Running
^^^^^^^
When this has been added, you can run your tests using the `test` task.
For Windows, running the following command will execute the tests

.. code-block:: bash

    gradlew.bat test

On Linux and macOS, use the following command

.. code-block:: bash

    ./gradlew test

Maven
-----
For people that use Maven instead of Gradle can also use MockBukkit by adding it
to their ``pom.xml``.
To do so, both JUnit and MockBukkit have to be added to your dependencies

.. sourcecode:: xml
    :linenos:

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.github.seeseemelk</groupId>
            <artifactId>MockBukkit-v1.20</artifactId>
            <version>3.3.0</version>
            <scope>test</scope>
        </dependency>
        <!-- Add your other dependencies here -->
    </dependencies>

    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>

Running
^^^^^^^
After having modified your ``pom.xml``, you can run the unit tests as follows

.. code-block:: bash

    mvn test

