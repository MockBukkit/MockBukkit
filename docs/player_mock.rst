Adding a player
===============
MockBukkit has several methods to add player to the test server.

This method creates a random new player and lets it join the server. ::

    PlayerMock player = server.addPlayer();

If you want to customise the object before adding it (such as setting a user name), this can be used::

    PlayerMock player = server.addPlayer(player)

One can also add a UUID after the username if needed.

And lastly, if you want to add a whole bunch of players quickily, consider using::

    server.setPlayers(20);

This will add 20 players to the server.
After this command you can use ```server.getPlayer(index)``` to reference each player in an easy way.

# PlayerMock methods
The PlayerMock class adds several methods that makes unit testing player related methods nicer.
In all examples we will assume that your unit test starts with::

    PlayerMock player = server.addPlayer();

It's possible to assert that a player is in a specific gamemode.
If the player is not in that gamemode, an AssertionException is thrown. ::

    player.assertGameMode(GameMode.SURVIVAL);

PlayerMock extends [EntityMock](EntityMock.md) since it's possible to use those added methods too.

