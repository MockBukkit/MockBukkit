Events
======

Asserting that Events were fired
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Mockbukkit also provides a way to check if Events are fired. Events are Classes that extend ``org.bukkit.event.Event``
and are fired by Bukkit.

To check if an event is fired, you can use the``PluginManagerMock#assertEventFired(Class<? extends Event> eventClass)``
method.This method takes a class that extends``org.bukkit.event.Event``.
It will then check if an event of that type was fired.

.. code-block:: java
    :linenos:

    public class MyPluginTests {
        private ServerMock server;

        @BeforeEach
        public void setUp() {
            server = MockBukkit.mock();
        }

        @AfterEach
        public void tearDown() {
            MockBukkit.unmock();
        }

        @Test
        public void testEvent() {
            Player player = server.addPlayer();
            player.setGameMode(GameMode.CREATIVE);

            server.getPluginManager().assertEventFired(PlayerGameModeChangeEvent.class);
        }
    }

If you want to check if an event was fired with a specific value, you can use the
``PluginManagerMock#assertEventFired(Class<? extends Event> eventClass, Predicate<Event> predicate)`` method.

.. code-block:: java
    :linenos:

    public class MyPluginTests {
        private ServerMock server;

        @BeforeEach
        public void setUp() {
            server = MockBukkit.mock();
        }

        @AfterEach
        public void tearDown() {
            MockBukkit.unmock();
        }

        @Test
        public void testEvent() {
            Player player = server.addPlayer();
            player.setGameMode(GameMode.CREATIVE);

            server.getPluginManager().assertEventFired(PlayerGameModeChangeEvent.class, event -> {
                event.getNewGameMode() == GameMode.CREATIVE);
            });
        }
    }

You can also set a custom failure message for the assertion by using the
``PluginManagerMock#assertEventFired(Class<? extends Event> eventClass, String message)`` method.

.. code-block:: java
    :linenos:

    public class MyPluginTests {
        private ServerMock server;

        @BeforeEach
        public void setUp() {
            server = MockBukkit.mock();
        }

        @AfterEach
        public void tearDown() {
            MockBukkit.unmock();
        }

        @Test
        public void testEvent() {
            Player player = server.addPlayer();
            player.setGameMode(GameMode.CREATIVE);

            server.getPluginManager().assertEventFired(PlayerGameModeChangeEvent.class, "The event was not fired!");
        }
    }

Asserting that Events were not fired
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

You can also check if an event was not fired by using the
``PluginManagerMock#assertEventNotFired(Class<? extends Event> eventClass)`` method.

.. code-block:: java
    :linenos:

    public class MyPluginTests {
        private ServerMock server;

        @BeforeEach
        public void setUp() {
            server = MockBukkit.mock();
        }

        @AfterEach
        public void tearDown() {
            MockBukkit.unmock();
        }

        @Test
        public void testEvent() {
            Player player = server.addPlayer();
            player.setGameMode(GameMode.CREATIVE);

            server.getPluginManager().assertEventNotFired(PlayerMoveEvent.class);
        }
    }

You can also set a custom failure message for the assertion by using the
``PluginManagerMock#assertEventNotFired(Class<? extends Event> eventClass, String message)`` method.

.. code-block:: java
    :linenos:

    public class MyPluginTests {
        private ServerMock server;

        @BeforeEach
        public void setUp() {
            server = MockBukkit.mock();
        }

        @AfterEach
        public void tearDown() {
            MockBukkit.unmock();
        }

        @Test
        public void testEvent() {
            Player player = server.addPlayer();
            player.setGameMode(GameMode.CREATIVE);

            server.getPluginManager().assertEventNotFired(PlayerMoveEvent.class, "The event was fired!");
        }
    }

