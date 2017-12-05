# MockBukkit
MockBukkit is a framework that makes the unit testing of Bukkit plugins a whole lot easier.
It aims to be a complete mock implementation.

## Usage
In order to use MockBukkit it has to be initialised before each test.
A plugin can also be loaded in this initialiser block.

```java
private ServerMock server;
private MyPlugin plugin;

@Before
public void init()
{
	server = MockBukkit.mock();
	plugin = (MyPlugin) MockBukkit.load(MyPlugin.class);
}
```

One can easily populate the server with several fake players by calling ```server.setPlayers(number of players);```.
This will automatically created several mock players.
