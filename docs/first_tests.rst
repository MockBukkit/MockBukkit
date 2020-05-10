First Tests
===========

The First Test
--------------
In order to start testing, you will need a directory to contain your tests.
This directory will most often be in ``src/test``, next to ``src/main``.

For instance, if you have a class at ``src/main/com/github/username/MyPlugin.java``,
you would probably put unit tests for this class at
``src/test/com/github/username/MyPluginTests.java``.

Extra Constructor
^^^^^^^^^^^^^^^^^
Before you can start running unit tests, your plugin will need an extra constructor.
This is because the ``JavaPlugin`` class expects that the plugin was loaded by a
special class loader.
However, it is not possible to use this class loader during the unit test phase.

The workaround is easy though, a constructor with a visibility of ``protected``. ::

    public class MyPlugin extends JavaPlugin {
        public MyPlugin() {
            super();
        }

        protected MyPlugin(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) {
            super(loader, descriptionFile, dataFolder, file);
        }

        @Override
        public void onEnable() {
            // Executed when your plugin is enabled.
        }

        @Override
        public void onDisable() {
            // Executed when your plugin is disabled.
        }
    }

Creating the Test Class
^^^^^^^^^^^^^^^^^^^^^^^
Once your directories are set up, you can create unit tests like this::

    public class MyPluginTests {
        private ServerMock server;
        private MyPlugin plugin;

        @Before    
        public void setUp() {
            // Start the mock server
            server = MockBukkit.mock();
            // Load your plugin
            plugin = MockBukkit.load(MyPlugin.class);
        }

        @After
        public void tearDown() {
            // Stop the mock server
            MockBukkit.unmock();
        }

        @Test
        public void thisTestWillFail() {
            // Perform your test
        }
    }

UnimplementationOperationException
---------------------------------- 
Sometimes your code may use a method that is not yet implemented in MockBukkit.
When this happens MockBukkit will, instead of returning placeholder values, throw
an ``UnimplementedOperationException``.
These exception extends ``AssumationException`` and will cause the test to be skipped.

These exceptions should just be ignored, though pull requests that add functionality
to MockBukkit are always welcome!

As an alternative you can always extend `ServerMock` and implement those missing methods.
Simply pass your custom implementation of ServerMock to the `MockBukkit.mock(...)` method.

    MyCustomServerMock server = MockBukkit.mock(new MyCustomServerMock());
