<p align="center">
    <!-- Badges -->
    <a href="https://travis-ci.org/seeseemelk/MockBukkit">
        <img alt="Build Status" src="https://github.com/seeseemelk/MockBukkit/workflows/Build/badge.svg?event=push" />
    </a>
    <a href="https://mockbukkit.readthedocs.io/en/latest/?badge=latest">
        <img alt="Documentation Status" src="https://readthedocs.org/projects/mockbukkit/badge/?version=latest" />
    </a>
    <a href="https://search.maven.org/search?q=MockBukkit">
        <img alt="Maven Central" src="https://img.shields.io/maven-central/v/com.github.seeseemelk/MockBukkit-v1.17?color=1bcc94&logo=apache-maven" />
    </a>
    <a href="https://codeclimate.com/github/seeseemelk/MockBukkit/maintainability">
        <img alt="Maintainability" src="https://api.codeclimate.com/v1/badges/403a4bb837ca47333d33/maintainability" />
    </a>
    <a href="https://codeclimate.com/github/seeseemelk/MockBukkit/test_coverage">
        <img alt="Test Coverage" src="https://api.codeclimate.com/v1/badges/403a4bb837ca47333d33/test_coverage" />
    </a>
    <!-- Logo -->
    <hr />
        <img alt="MockBukkit logo" src="logo.png"/>
    <hr />
</p>

MockBukkit is a framework that makes the unit testing of Bukkit plugins a whole lot easier.
It aims to be provide complete mock implementation of CraftBukkit that can be completely controlled from a unit test.

## :page_facing_up: Table of contents
1. [Usage](#mag-usage)
    - [Adding MockBukkit via gradle](#adding-mockbukkit-via-gradle)
    - [Adding MockBukkit via Maven](#adding-mockbukkit-via-maven)
    - [Using MockBukkit](#using-mockbukkit)
2. [Features](#sparkles-features)
    - [Mock Plugins](#mock-plugins)
    - [Mock Players](#mock-players)
    - [Mock Worlds](#mock-worlds)
3. [Troubleshooting (My tests are being skipped)](#question-my-tests-are-being-skipped-unimplementedoperationexception)
4. [Discord server](#headphones-discord-server)
5. [Examples (See MockBukkit in action)](#tada-examples-see-mockbukkit-in-action)

## :mag: Usage
MockBukkit can easily be included in your project using either Maven or gradle.

### Adding MockBukkit via gradle
MockBukkit can easily be included in gradle using mavenCentral.

```gradle
repositories {
	mavenCentral()
	maven { url 'https://hub.spigotmc.org/nexus/content/repositories/public/' }
}

dependencies {
	testImplementation 'com.github.seeseemelk:MockBukkit-v1.18:2.0.1'
}
```

Note: use `v1.13-SNAPSHOT` to test a Bukkit 1.13 plugin or any other version if the [branch](https://github.com/MockBukkit/MockBukkit/branches) exists.
These branches will not be receiving patches actively, but any issues will be resolved and any pull requests on them will be accepted.
This is because backporting every single patch on every branch is incredibly time consuming and slows down the development of Mockbukkit.

If you prefer to always have the latest Git version or need a specific commit/branch, you can always use [JitPack](https://jitpack.io/#MockBukkit/MockBukkit) as your maven repository:

```gradle
repositories {
	maven { url 'https://jitpack.io' }
}

dependencies {
	testImplementation 'com.github.seeseemelk:MockBukkit:v1.18-SNAPSHOT'
}
```

### Adding MockBukkit via Maven
MockBukkit can be included by adding the dependency to your `pom.xml`.<br>
You won't need to add any additional repositories since MockBukkit is served via maven-central. Make sure to update the version as necessary.

```xml
<dependencies>
  <dependency>
    <groupId>com.github.seeseemelk</groupId>
    <artifactId>MockBukkit-v1.18</artifactId>
    <version>2.0.1</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

Note: use `v1.13-SNAPSHOT` to test a Bukkit 1.13 plugin or any other version if the [branch](https://github.com/MockBukkit/MockBukkit/branches) exists.
These branches will not be receiving patches actively, but any issues will be resolved and any pull requests on them will be accepted.
This is because backporting every single patch on every branch is incredibly time consuming and slows down the development of Mockbukkit.

The `scope` test is important here since you are likely to only be using MockBukkit during the `test` stage of your Maven lifecycle and not in your final product.

### Using MockBukkit
In order to use MockBukkit the plugin to be tested needs an extra constructor and it has to be initialised before each test.
The plugin will need both a default constructor and an extra one that will call a super constructor.
Your plugins constructor will look like this if your plugin was called ```MyPlugin```.
```java
public class MyPlugin extends JavaPlugin
{
    public MyPlugin()
    {
        super();
    }

    protected MyPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
    {
        super(loader, description, dataFolder, file);
    }
}
```
The plugin is now ready to be tested by MockBukkit.
A plugin can be loaded in this initialiser block.

```java
private ServerMock server;
private MyPlugin plugin;

@Before
public void setUp()
{
    server = MockBukkit.mock();
    plugin = (MyPlugin) MockBukkit.load(MyPlugin.class);
}

@After
public void tearDown()
{
    MockBukkit.unmock();
}
```

## :sparkles: Features
### Mock Plugins
MockBukkit contains several functions that make the unit testing of Bukkit plugins a lot easier.

It is possible to create a mock plugin.
This is useful when the plugin you are testing may be looking at other loaded plugins.
The following piece of code creates a placeholder plugin that extends JavaPlugin.
```java
MockPlugin plugin = MockBukkit.createMockPlugin()
```

### Mock Players
MockBukkit makes it easy to create several mock players to use in unit testing.
By running ```server.setPlayers(int numberOfPlayers)``` one can set the number of online players.
From then on it's possible to get a certain player using ```server.getPlayer(int i)```.

An even easier way to create a player on the fly is by simply using
```java
PlayerMock player = server.addPlayer();
```

A mock player also supports several simulated actions, such as damaging a block or even
breaking it. This will fire all the required events and will remove the block if the
events weren't cancelled.
```java
Block block = ...;
player.simulateBlockBreak(block);
```

### Mock Worlds
Another feature is the easy creation of mock worlds.
One can make a superflat world using one simple command:
```java
World world = new WorldMock(Material material, int heightUntilAir)
```
Using `Material.DIRT` and 3 as heightUntilAir will create a superflat world with a height of a 128.
At y=0 everything will be `Material.BEDROCK`, and from 1 until 3 (inclusive) will be `Material.DIRT`
and everything else will be `Material.AIR`.
Each block is created the moment it is first accessed, so if only one block is only ever touched only one
block will ever be created in-memory.

## :question: My tests are being skipped!? (UnimplementedOperationException)
Sometimes your code may use a method that is not yet implemented in MockBukkit.
When this happens MockBukkit will, instead of returning placeholder values, throw
an `UnimplementedOperationException`.
These exception extends `AssumationException` and will cause the test to be skipped.

These exceptions should just be ignored, though pull requests that add functionality to MockBukkit are always welcome!
If you don't want to add the required methods yourself you can also request the method on the issues page.

## :headphones: Discord Server
You can also find us on discord by the way!
If you need any help with MockBukkit or have a question regarding this project, feel free to join and connect with other members of the community.
<p align="center">
  <a href="https://discord.gg/s4cWYgsFaV">
    <img src="https://discordapp.com/api/guilds/792754410576019477/widget.png?style=banner3" alt="Discord Invite"/>
  </a>
</p>

## :tada: Examples (See MockBukkit in action)
Several projects have utilized MockBukkit for their needs already.
If you want to see some projects that are using MockBukkit right now, feel free to take a peak:
- [Slimefun/Slimefun4](https://github.com/Slimefun/Slimefun4/tree/master/src/test/java/io/github/thebusybiscuit/slimefun4) (400+ Unit Tests)
- [lluiscamino/MultiverseHardcore](https://github.com/lluiscamino/MultiverseHardcore/tree/master/src/test/java/me/lluiscamino/multiversehardcore) (75+ Unit Tests)
- [carelesshippo/SpectatorModeRewrite](https://github.com/carelesshippo/SpectatorModeRewrite/tree/dev/src/test/java/me/ohowe12/spectatormode) (50+ Unit Tests)
- [JacksonChen666/treecapitator](https://github.com/JacksonChen666/treecapitator/tree/master/src/test/java/com/jacksonchen666/treecapitator) (30+ Unit Tests)
- [axelrindle/PocketKnife](https://github.com/axelrindle/PocketKnife/tree/main/api/src/test/kotlin) (30+ Unit Tests)
- *and more! (If you want to see your plugin here, open up an issue and we'll consider adding it)*

You can also have a look at our documentation where we outline various examples and tricks on how to use MockBukkit already:
https://mockbukkit.readthedocs.io/en/latest/index.html
