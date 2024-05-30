package be.seeseemelk.mockbukkit.enchantments;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import com.google.gson.JsonObject;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class EnchantmentMockTest
{

	private final static String NAMESPACE = "mock_bukkit";
	private NamespacedKey key;
	private EnchantmentTarget target;
	private int maxLevel;
	private int minLevel;
	private String name;
	private Component[] displayNames;
	private int[] minModifiedCost;
	private int[] maxModifiedCost;
	private EnchantmentRarity rarity;
	private EnchantmentMock enchantment;

	public static Stream<Integer> getAvailableLevels()
	{
		return Stream.of(1, 2);
	}

	@BeforeEach
	void setUp()
	{
		this.key = new NamespacedKey(NAMESPACE, "custom_enchantment");
		this.target = EnchantmentTarget.ARMOR_FEET;
		this.maxLevel = 2;
		this.minLevel = 1;
		this.name = "Custom enchantment";
		this.displayNames = new Component[]
		{ Component.text("Level 1"), Component.text("Level 2") };
		this.minModifiedCost = new int[]
		{ 1, 2 };
		this.maxModifiedCost = new int[]
		{ 20, 25 };
		this.rarity = EnchantmentRarity.RARE;
		this.enchantment = new EnchantmentMock(key, target, true, true, maxLevel, minLevel, name, displayNames,
				minModifiedCost, maxModifiedCost, true, true, rarity, Set.of(key));
	}

	@ParameterizedTest
	@MethodSource("getAvailableLevels")
	void displayName(int level)
	{
		assertEquals(displayNames[level - 1], enchantment.displayName(level));
	}

	@Test
	void isTradeable()
	{
		assertTrue(enchantment.isTradeable());
	}

	@Test
	void isDiscoverable()
	{
		assertTrue(enchantment.isDiscoverable());
	}

	@ParameterizedTest
	@MethodSource("getAvailableLevels")
	void getMaxModifiedCost(int level)
	{
		assertEquals(maxModifiedCost[level - 1], enchantment.getMaxModifiedCost(level));
	}

	@ParameterizedTest
	@MethodSource("getAvailableLevels")
	void getMinModifiedCost(int level)
	{
		assertEquals(minModifiedCost[level - 1], enchantment.getMinModifiedCost(level));
	}

	@Test
	void getRarity()
	{
		assertEquals(rarity, enchantment.getRarity());
	}

	@Test
	void getName()
	{
		assertEquals(name, enchantment.getName());
	}

	@Test
	void getMaxLevel()
	{
		assertEquals(maxLevel, enchantment.getMaxLevel());
	}

	@Test
	void setMaxLevel()
	{
		enchantment.setMaxLevel(20);
		assertEquals(20, enchantment.getMaxLevel());
	}

	@Test
	void getStartLevel()
	{
		assertEquals(minLevel, enchantment.getStartLevel());
	}

	@Test
	void setStartLevel()
	{
		enchantment.setStartLevel(20);
		assertEquals(20, enchantment.getStartLevel());
	}

	@Test
	void getItemTarget()
	{
		assertEquals(target, enchantment.getItemTarget());
	}

	@Test
	void setItemTarget()
	{
		enchantment.setItemTarget(EnchantmentTarget.CROSSBOW);
		assertEquals(EnchantmentTarget.CROSSBOW, enchantment.getItemTarget());
	}

	@Test
	void isTreasure()
	{
		assertTrue(enchantment.isTreasure());
	}

	@Test
	void setTreasure()
	{
		enchantment.setTreasure(false);
		assertFalse(enchantment.isTreasure());
	}

	@Test
	void isCursed()
	{
		assertTrue(enchantment.isCursed());
	}

	@Test
	void setCursed()
	{
		enchantment.setCursed(false);
		assertFalse(enchantment.isCursed());
	}

	@Test
	void conflictsWith()
	{
		assertTrue(enchantment.conflictsWith(enchantment));
	}

	@Test
	void getKey()
	{
		assertEquals(key, enchantment.getKey());
	}

	@Test
	void from_invalid()
	{
		JsonObject invalid = new JsonObject();
		assertThrows(IllegalArgumentException.class, () -> EnchantmentMock.from(invalid));
	}

}
