Using a custom ServerMock implementation
========================================
Sometimes it may be needed to use a custom implementation of the `ServerMock` class.
This could be if you want to implement some of the unimplemented methods or simply provide your own mocks for certain methods.

To do that you can simply pass your custom mock that extends `ServerMock` to the `MockBukkit.mock(yourServerMock)` method.

    MyCustomServerMock server = MockBukkit.mock(new MyCustomServerMock());

Note that `MockBukkit.getMock()` will return a reference to your instance.
