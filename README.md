[![Maintainability](https://api.codeclimate.com/v1/badges/403a4bb837ca47333d33/maintainability)](https://codeclimate.com/github/seeseemelk/MockBukkit/maintainability)
# MockBukkit
MockBukkit is a framework that makes the unit testing of Bukkit plugins a whole lot easier.
It aims to be a complete mock implementation.

## Usage
MockBukkit can easily be included in gradle using jitpack.io
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    testCompile 'junit:junit:4.12'
    testCompile 'com.github.seeseemelk:MockBukkit:master-SNAPSHOT'
}
```

Note: use v1.8.8-SNAPSHOT to test a Bukkit 1.8.8 plugin or any other version if the branch exists.
These branches are considered supported and will get updates backported to them.

In order to use MockBukkit the plugin to be tested needs an extra constructor and it has to be initialised before each test.
The plugin will need both a default constructor and an extra one that will call a super constructor.
Your plugins constructor will look like this if your plugin was called ```MyPlugin```
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
    MockBukkit.unload();
}
```

## Features
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
Using Material.DIRT and 3 as heightUntilAir will create a superflat world with a height of a 128.
At y=0 everything will be Material.BEDROCK, and from 1 until 3 (inclusive) will be Material.DIRT
and everything else will be Material.AIR.
Each block is created the moment it is first accessed, so if only one block is only ever touched only one
block will ever be created in-memory.

## My tests are being skipped!? (UnimplementedOperationException)
Sometimes your code may use a method that is not yet implemented in MockBukkit.
When this happens MockBukkit will, instead of returning placeholder values, throw
an `UnimplementedOperationException`.
These exception extends `AssumationException` and will cause the test to be skipped.

These exceptions should just be ignored, though pull requests that add functionality to MockBukkit are always welcome!
If you don't want to add the required methods yourself you can also request the method on the issues page.
