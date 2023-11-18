Adventure
=========

Platform
--------

When using MockBukkit with `the non-native implementation of Adventure <https://github.com/KyoriPowered/adventure-platform>`_, 
you may run into issues with static fields persisting with audiences, even after executing
``MockBukkit.unmock()``. This can cause undesired issues when running consecutive tests 
like chat messages not sending. 

The solution is to include a ``platform.close()`` statement in the ``onDisable()`` method of your plugin.

.. code-block:: java

    BukkitAudiences platform;
    
    public void onEnable() {
        platform = BukkitAudiences.create(pluginInstance);
    }

    public void onDisable() {
        platform.close();
    }
