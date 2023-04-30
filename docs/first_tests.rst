First Tests
===========

The First Test
--------------
In order to start testing, you will need a directory to contain your tests.
This directory will most often be in ``src/test``, next to ``src/main``.

For instance, if you have a class at ``src/main/com/github/username/MyPlugin.java``,
you would probably put unit tests for this class at
``src/test/com/github/username/MyPluginTests.java``.

.. note::
	The name of the test class does not matter, but it is common to name it
	``<ClassName>Tests`` or ``<ClassName>Test``.

Your Main Class can't be ``final``. If you use Kotlin, you main class needs to be an ``open`` class.

Creating the Test Class
^^^^^^^^^^^^^^^^^^^^^^^
Once your directories are set up, you can create unit tests like this

.. code-block:: java
    :linenos:

    public class MyPluginTests {
        private ServerMock server;
        private MyPlugin plugin;

        @BeforeEach
        public void setUp() {
            // Start the mock server
            server = MockBukkit.mock();
            // Load your plugin
            plugin = MockBukkit.load(MyPlugin.class);
        }

        @AfterEach
        public void tearDown() {
            // Stop the mock server
            MockBukkit.unmock();
        }

        @Test
        public void thisTestWillFail() {
            // Perform your test
        }
    }

UnimplementedOperationException
----------------------------------
Sometimes your code may use a method that is not yet implemented in MockBukkit.
When this happens MockBukkit will, instead of returning placeholder values, throw
an ``UnimplementedOperationException``.
These exception extends ``TestAbortedException`` and will cause the test to be skipped.

These exceptions should just be ignored, though pull requests that add functionality
to MockBukkit are always welcome!

As an alternative you can always extend ``ServerMock`` and implement those missing methods.
Simply pass your custom implementation of ServerMock to the `MockBukkit.mock(...)` method.

.. code-block:: java

    MyCustomServerMock server = MockBukkit.mock(new MyCustomServerMock());
