MessageTarget introduction
==========================
The MessageTarget interface is a small interface implemented by methods that can receive messages.
Two examples of message targets are ```ConsoleCommandSenderMock``` and ```EntityMock```.

Using MessageTarget
-------------------
Any message that was sent to the target can be read using::

    SimpleEntityMock entity = new SimpleEntityMock();
    entity.sendMessage("Hello world!");
    String message = entity.nextMessage();

It also contains two assert methods to check if a message was or wasn't received. ::

    entity.sendMessage("Hello world!");
    entity.assertSaid("Hello world!");
    entity.assertNoMoreSaid();

