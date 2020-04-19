Creating a mock World
=====================
Often times one needs to interact with the minecraft world.
MockBukkit always the creation of superflat worlds on the fly.
Each block in the world is generated the moment it is accessed for the first time, so creation new worlds is a very cheap operation.

The following code will create a flat world::

    WorldMock = server.addSimpleWorld("my_world");

Every time MockBukkit is started a world called "world" is automatically created.
All players are also added to this default world.
