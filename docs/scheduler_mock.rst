Mock Scheduler (Testing Timers and Delays)
==========================================
MockBukkit allows the testing of timers and delays that are normally created using the Bukkit scheduler.
This schedulers is used in the same way as a normal scheduler except that it adds several extra methods.

Executing ticks
---------------
The scheduler can be asked to perform a single tick during tick. ::

    server.getScheduler().performOneTick();

If more ticks need to be executed in quick succession, it's possible to execute many ticks at once.
The following code will perform a hundred ticks. ::

    server.getScheduler().performTicks(100L);

Using this method executes all ticks in order, as if they were executed on a real server.

Getting the current tick.
-------------------------
MockBukkit has an extra method that allows to get the number of ticks since MockBukkit was last started. ::

    long tick = server.getScheduler().getCurrentTick();
