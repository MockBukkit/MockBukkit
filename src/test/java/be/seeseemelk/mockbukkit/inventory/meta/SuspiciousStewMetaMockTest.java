package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SuspiciousStewMetaMockTest
{

    @Before
    public void setUp()
    {
        MockBukkit.mock();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void testEffectsDefaultEmpty()
    {
        SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
        assertFalse(meta.hasCustomEffects());
    }

    @Test
    public void testAddEffect()
    {
        SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

        assertFalse(meta.hasCustomEffect(PotionEffectType.SPEED));

        meta.addCustomEffect(effect, true);

        assertTrue(meta.hasCustomEffects());
        assertTrue(meta.hasCustomEffect(PotionEffectType.SPEED));
    }

    @Test
    public void testOverrideEffect()
    {
        SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);
        PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 60, 2);
        PotionEffect effect3 = new PotionEffect(PotionEffectType.SPEED, 60, 1);

        meta.addCustomEffect(effect, true);
        assertTrue(meta.getCustomEffects().get(0) == effect);

        meta.addCustomEffect(effect2, false);
        assertFalse(meta.getCustomEffects().get(0) == effect2);

        meta.addCustomEffect(effect2, true);
        assertFalse(meta.getCustomEffects().get(0) == effect);

        meta.addCustomEffect(effect3, true);
        assertFalse(meta.getCustomEffects().get(0) == effect3);
    }

    @Test
    public void testClearEffects()
    {
        SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
        assertFalse(meta.clearCustomEffects());

        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

        meta.addCustomEffect(effect, true);
        assertTrue(meta.hasCustomEffects());

        assertTrue(meta.clearCustomEffects());
        assertFalse(meta.hasCustomEffects());
    }

    @Test
    public void testRemoveEffect()
    {
        SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
        assertFalse(meta.clearCustomEffects());

        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);
        PotionEffect effect2 = new PotionEffect(PotionEffectType.BLINDNESS, 40, 1);

        meta.addCustomEffect(effect, true);
        meta.addCustomEffect(effect2, true);

        assertTrue(meta.removeCustomEffect(PotionEffectType.SPEED));
        assertFalse(meta.hasCustomEffect(PotionEffectType.SPEED));
        assertFalse(meta.removeCustomEffect(PotionEffectType.SPEED));
        assertTrue(meta.hasCustomEffects());
    }

    @Test
    public void testEquals()
    {
        SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
        assertEquals(meta, meta);
        assertFalse(meta.equals(new ItemMetaMock()));

        SuspiciousStewMetaMock meta2 = new SuspiciousStewMetaMock();
        assertEquals(meta, meta2);

        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

        meta.addCustomEffect(effect, true);
        assertFalse(meta.equals(meta2));

        meta2.addCustomEffect(effect, true);
        assertEquals(meta, meta2);
    }

    @Test
    public void testClone()
    {
        SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
        SuspiciousStewMetaMock clone = meta.clone();
        assertEquals(meta, clone);
    }
}
