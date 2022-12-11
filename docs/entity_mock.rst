Mock Entities
=============
The EntityMock adds several methods that can be used be on all entities created by MockBukkit.

This will check that the entity is within a certain distance from the location.

.. code-block:: java

    entity.assertLocation(location, distance);

You can also assert that a player was teleported by a plugin.

.. code-block:: java

    entity.assertTeleported(location, distance);
    entity.assertNotTeleported();

If you want to reset the teleported flag you can use

.. code-block:: java

    entity.clearTeleported();

This will useful if you know that a method teleports your entity and you want to check if afterwards a different method *doesn't* teleport it.

To check if the entity was teleported without throwing AssertionExceptions use

.. code-block:: java

    if (entity.hasTeleported()){
        // Player was teleported
    }

In normal bukkit the only way to move an entity is by teleporting it, but this causes a teleported event.
Therefore MockBukkit adds a setter for the location

.. code-block:: java

    entity.setLocation(location);

In MockBukkit an entity can also be renamed if needed

.. code-block:: java

    entity.setName("new-name");

An EntityMock also implements the :doc:`/message_target` interface so one can test received messages.

