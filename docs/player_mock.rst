Adding a player
===============
MockBukkit has several methods to add player to the test server.

This method creates a random new player and lets it join the server.

.. code-block:: java

    PlayerMock player = server.addPlayer();

If you want to customise the object before adding it (such as setting a user name), this can be used

.. code-block:: java

    PlayerMock player = server.addPlayer(player)

One can also add a UUID after the username if needed.

And lastly, if you want to add a whole bunch of players quickily, consider using

.. code-block:: java

    server.setPlayers(20);

This will add 20 players to the server.
After this command you can use ``server.getPlayer(index)`` to reference each player in an easy way.

PlayerMock Methods
^^^^^^^^^^^^^^^^^^
The PlayerMock class adds several methods that makes unit testing player related methods nicer.
In all examples we will assume that your unit test starts with

.. code-block:: java

    PlayerMock player = server.addPlayer();

It's possible to assert that a player is in a specific Gamemode.
If the player is not in that Gamemode, an ``AssertionException`` is thrown.

.. code-block:: java

    player.assertGameMode(GameMode.SURVIVAL);

To simulate a Player disconnecting, use the ``disconnect()`` method.
This will set the Player as offline but keeps him as an OfflinePlayer.

.. code-block:: java

        player.disconnect();

After a Player has been disconnected, it's possible to simulate a reconnection.
This will set the Player as online and restore it's full Functionality.

.. code-block:: java

        player.reconnect();


PlayerMock extends :doc:`/entity_mock` too, so it's possible to use those added methods too.
