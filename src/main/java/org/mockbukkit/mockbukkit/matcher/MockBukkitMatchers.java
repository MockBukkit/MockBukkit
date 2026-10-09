package org.mockbukkit.mockbukkit.matcher;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Matcher;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.command.CommandResult;
import org.mockbukkit.mockbukkit.command.MessageTarget;
import org.mockbukkit.mockbukkit.entity.AllayMock;
import org.mockbukkit.mockbukkit.entity.EntityMock;
import org.mockbukkit.mockbukkit.entity.GoatMock;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;
import org.mockbukkit.mockbukkit.entity.MobMock;
import org.mockbukkit.mockbukkit.entity.MockRangedEntity;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.help.HelpMapMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.mockbukkit.matcher.block.BlockMaterialTypeMatcher;
import org.mockbukkit.mockbukkit.matcher.command.CommandResultAnyResponseMatcher;
import org.mockbukkit.mockbukkit.matcher.command.CommandResultResponseMatcher;
import org.mockbukkit.mockbukkit.matcher.command.CommandResultSucceedMatcher;
import org.mockbukkit.mockbukkit.matcher.command.MessageTargetReceivedAnyMessageMatcher;
import org.mockbukkit.mockbukkit.matcher.command.MessageTargetReceivedMessageMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.EntityLocationMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.EntityTeleportationMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.allay.AllayCurrentItemMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.goat.GoatEntityRammedMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.human.HumanEntityInventoryViewItemMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.human.HumanEntityInventoryViewTypeMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.player.PlayerConsumeItemMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher;
import org.mockbukkit.mockbukkit.matcher.help.HelpMapFactoryRegisteredMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.InventoryItemAmountMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.ItemSimilarityMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.holder.InventoryHolderContainsMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaAnyLoreMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaLoreMatcher;
import org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher;
import org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventFilterMatcher;
import org.mockbukkit.mockbukkit.matcher.scheduler.SchedulerOverdueTasksMatcher;
import org.mockbukkit.mockbukkit.matcher.sound.SoundReceiverSoundHeardMatcher;
import org.mockbukkit.mockbukkit.plugin.PluginManagerMock;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;
import org.mockbukkit.mockbukkit.sound.AudioExperience;
import org.mockbukkit.mockbukkit.sound.SoundReceiver;

import java.util.List;
import java.util.function.Predicate;

/**
 * A single, fluent entry point that aggregates every Hamcrest matcher shipped by MockBukkit.
 * <p>
 * This class does not implement any matching logic itself. Each method simply delegates to the
 * corresponding factory method on the dedicated matcher class (for example
 * {@link BlockMaterialTypeMatcher}), so behaviour and descriptions are identical to calling those
 * classes directly.
 * <p>
 * The goal is discoverability: a single static import surfaces all matchers through IDE
 * autocompletion, in the same spirit as {@code org.hamcrest.Matchers} or Mockito's
 * {@code ArgumentMatchers}.
 * <pre>{@code
 * import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.*;
 *
 * assertThat(block, hasMaterial(Material.CHEST));
 * assertThat(entity, isInLocation(target, 1.5));
 * assertThat(inventory, containsAtLeast(Material.DIAMOND, 3));
 * }</pre>
 * Using the individual matcher classes directly remains fully supported; this facade is purely
 * additive and changes no existing behaviour.
 *
 * @see <a href="https://github.com/MockBukkit/MockBukkit/issues/1533">Issue #1533</a>
 */
@NullMarked
public final class MockBukkitMatchers
{

	private MockBukkitMatchers()
	{
		throw new UnsupportedOperationException("MockBukkitMatchers is a utility class and cannot be instantiated");
	}

	// ===================================================================================
	// Block
	// ===================================================================================

	/**
	 * Creates a matcher which matches blocks with the specified material.
	 *
	 * @param material The material that the block should have
	 * @return A matcher which matches blocks with the specified material
	 * @see BlockMaterialTypeMatcher
	 */
	public static BlockMaterialTypeMatcher hasMaterial(Material material)
	{
		return BlockMaterialTypeMatcher.hasMaterial(material);
	}

	/**
	 * Creates a matcher which matches blocks without the specified material.
	 *
	 * @param material The material that the block should not have
	 * @return A matcher which matches blocks without the specified material
	 * @see BlockMaterialTypeMatcher
	 */
	public static Matcher<BlockMock> doesNotHaveMaterial(Material material)
	{
		return BlockMaterialTypeMatcher.doesNotHaveMaterial(material);
	}

	// ===================================================================================
	// Command
	// ===================================================================================

	/**
	 * Creates a matcher which matches command results that produced any response.
	 *
	 * @return A matcher which matches command results that produced any response
	 * @see CommandResultAnyResponseMatcher
	 */
	public static CommandResultAnyResponseMatcher hasAnyResponse()
	{
		return CommandResultAnyResponseMatcher.hasAnyResponse();
	}

	/**
	 * Creates a matcher which matches command results that produced no response.
	 *
	 * @return A matcher which matches command results that produced no response
	 * @see CommandResultAnyResponseMatcher
	 */
	public static Matcher<CommandResult> hasNoResponse()
	{
		return CommandResultAnyResponseMatcher.hasNoResponse();
	}

	/**
	 * Creates a matcher which matches command results with the specified response.
	 *
	 * @param response The response required for a match
	 * @return A matcher which matches command results with the specified response
	 * @see CommandResultResponseMatcher
	 */
	public static CommandResultResponseMatcher hasResponse(String response)
	{
		return CommandResultResponseMatcher.hasResponse(response);
	}

	/**
	 * Creates a matcher which matches command results without the specified response.
	 *
	 * @param response The response required for no match
	 * @return A matcher which matches command results without the specified response
	 * @see CommandResultResponseMatcher
	 */
	public static Matcher<CommandResult> doesNotHaveResponse(String response)
	{
		return CommandResultResponseMatcher.doesNotHaveResponse(response);
	}

	/**
	 * Creates a matcher which matches command results that succeeded.
	 *
	 * @return A matcher which matches command results that succeeded
	 * @see CommandResultSucceedMatcher
	 */
	public static CommandResultSucceedMatcher hasSucceeded()
	{
		return CommandResultSucceedMatcher.hasSucceeded();
	}

	/**
	 * Creates a matcher which matches command results that failed.
	 *
	 * @return A matcher which matches command results that failed
	 * @see CommandResultSucceedMatcher
	 */
	public static Matcher<CommandResult> hasFailed()
	{
		return CommandResultSucceedMatcher.hasFailed();
	}

	/**
	 * Creates a matcher which matches message targets that received any message.
	 *
	 * @return A matcher which matches message targets that received any message
	 * @see MessageTargetReceivedAnyMessageMatcher
	 */
	public static MessageTargetReceivedAnyMessageMatcher hasReceivedAny()
	{
		return MessageTargetReceivedAnyMessageMatcher.hasReceivedAny();
	}

	/**
	 * Creates a matcher which matches message targets that received no message.
	 *
	 * @return A matcher which matches message targets that received no message
	 * @see MessageTargetReceivedAnyMessageMatcher
	 */
	public static Matcher<MessageTarget> hasNotReceivedAny()
	{
		return MessageTargetReceivedAnyMessageMatcher.hasNotReceivedAny();
	}

	/**
	 * Creates a matcher which matches message targets that received the specified message.
	 *
	 * @param expected The message required for a match
	 * @return A matcher which matches message targets that received the specified message
	 * @see MessageTargetReceivedMessageMatcher
	 */
	public static MessageTargetReceivedMessageMatcher hasReceived(Component expected)
	{
		return MessageTargetReceivedMessageMatcher.hasReceived(expected);
	}

	/**
	 * Creates a matcher which matches message targets that received the specified message.
	 *
	 * @param expected The message required for a match
	 * @return A matcher which matches message targets that received the specified message
	 * @see MessageTargetReceivedMessageMatcher
	 */
	public static MessageTargetReceivedMessageMatcher hasReceived(String expected)
	{
		return MessageTargetReceivedMessageMatcher.hasReceived(expected);
	}

	/**
	 * Creates a matcher which matches message targets that did not receive the specified message.
	 *
	 * @param expected The message required for no match
	 * @return A matcher which matches message targets that did not receive the specified message
	 * @see MessageTargetReceivedMessageMatcher
	 */
	public static Matcher<MessageTarget> hasNotReceived(String expected)
	{
		return MessageTargetReceivedMessageMatcher.hasNotReceived(expected);
	}

	// ===================================================================================
	// Entity
	// ===================================================================================

	/**
	 * Creates a matcher which matches with any entity within a radius of specified location.
	 *
	 * @param location    The location required for a match
	 * @param maxDistance The radius away from the location which gives a match
	 * @return A matcher which matches with any entity within a radius of specified location
	 * @see EntityLocationMatcher
	 */
	public static EntityLocationMatcher isInLocation(Location location, double maxDistance)
	{
		return EntityLocationMatcher.isInLocation(location, maxDistance);
	}

	/**
	 * Creates a matcher which matches with any entity outside a radius of specified location.
	 *
	 * @param location    The location required for no match
	 * @param maxDistance The radius away from the location which withdraws a match
	 * @return A matcher which matches with any entity outside a radius of specified location
	 * @see EntityLocationMatcher
	 */
	public static Matcher<EntityMock> isNotInLocation(Location location, double maxDistance)
	{
		return EntityLocationMatcher.isNotInLocation(location, maxDistance);
	}

	/**
	 * Creates a matcher which matches with any entity that has teleported.
	 *
	 * @return A matcher which matches with any entity that has teleported
	 * @see EntityTeleportationMatcher
	 */
	public static EntityTeleportationMatcher hasTeleported()
	{
		return EntityTeleportationMatcher.hasTeleported();
	}

	/**
	 * Creates a matcher which matches with any entity that has teleported to the specified location.
	 *
	 * @param location The location the entity should have teleported to
	 * @return A matcher which matches with any entity that has teleported to the specified location
	 * @see EntityTeleportationMatcher
	 */
	public static EntityTeleportationMatcher hasTeleported(Location location)
	{
		return EntityTeleportationMatcher.hasTeleported(location);
	}

	/**
	 * Creates a matcher which matches with any entity that has teleported near the specified location.
	 *
	 * @param location        The location the entity should have teleported to
	 * @param maximumDistance The maximum distance from the location which gives a match
	 * @return A matcher which matches with any entity that has teleported near the specified location
	 * @see EntityTeleportationMatcher
	 */
	public static EntityTeleportationMatcher hasTeleported(Location location, double maximumDistance)
	{
		return EntityTeleportationMatcher.hasTeleported(location, maximumDistance);
	}

	/**
	 * Creates a matcher which matches with any entity that has not teleported.
	 *
	 * @return A matcher which matches with any entity that has not teleported
	 * @see EntityTeleportationMatcher
	 */
	public static Matcher<EntityMock> hasNotTeleported()
	{
		return EntityTeleportationMatcher.hasNotTeleported();
	}

	/**
	 * Creates a matcher which matches with any entity that has not teleported to the specified location.
	 *
	 * @param location The location the entity should not have teleported to
	 * @return A matcher which matches with any entity that has not teleported to the specified location
	 * @see EntityTeleportationMatcher
	 */
	public static Matcher<EntityMock> hasNotTeleported(Location location)
	{
		return EntityTeleportationMatcher.hasNotTeleported(location);
	}

	/**
	 * Creates a matcher which matches with any entity that has not teleported near the specified location.
	 *
	 * @param location        The location the entity should not have teleported to
	 * @param maximumDistance The maximum distance from the location which withdraws a match
	 * @return A matcher which matches with any entity that has not teleported near the specified location
	 * @see EntityTeleportationMatcher
	 */
	public static Matcher<EntityMock> hasNotTeleported(Location location, double maximumDistance)
	{
		return EntityTeleportationMatcher.hasNotTeleported(location, maximumDistance);
	}

	/**
	 * Creates a matcher which matches allays holding the specified item.
	 *
	 * @param currentItem The material the allay should be holding
	 * @return A matcher which matches allays holding the specified item
	 * @see AllayCurrentItemMatcher
	 */
	public static AllayCurrentItemMatcher hasCurrentItem(Material currentItem)
	{
		return AllayCurrentItemMatcher.hasCurrentItem(currentItem);
	}

	/**
	 * Creates a matcher which matches allays not holding the specified item.
	 *
	 * @param currentItem The material the allay should not be holding
	 * @return A matcher which matches allays not holding the specified item
	 * @see AllayCurrentItemMatcher
	 */
	public static Matcher<AllayMock> doesNotHaveCurrentItem(Material currentItem)
	{
		return AllayCurrentItemMatcher.doesNotHaveCurrentItem(currentItem);
	}

	/**
	 * Creates a matcher which matches goats that have rammed the specified target.
	 *
	 * @param target The entity the goat should have rammed
	 * @return A matcher which matches goats that have rammed the specified target
	 * @see GoatEntityRammedMatcher
	 */
	public static GoatEntityRammedMatcher hasRammed(LivingEntity target)
	{
		return GoatEntityRammedMatcher.hasRammed(target);
	}

	/**
	 * Creates a matcher which matches goats that have not rammed the specified target.
	 *
	 * @param target The entity the goat should not have rammed
	 * @return A matcher which matches goats that have not rammed the specified target
	 * @see GoatEntityRammedMatcher
	 */
	public static Matcher<GoatMock> hasNotRammed(LivingEntity target)
	{
		return GoatEntityRammedMatcher.hasNotRammed(target);
	}

	/**
	 * Creates a matcher which matches human entities with the specified item in their inventory view.
	 *
	 * @param material The material of the item in the inventory view
	 * @return A matcher which matches human entities with the specified item in their inventory view
	 * @see HumanEntityInventoryViewItemMatcher
	 */
	public static HumanEntityInventoryViewItemMatcher hasItemInInventoryView(Material material)
	{
		return HumanEntityInventoryViewItemMatcher.hasItemInInventoryView(material);
	}

	/**
	 * Creates a matcher which matches human entities with the specified item in their inventory view.
	 *
	 * @param item The item in the inventory view
	 * @return A matcher which matches human entities with the specified item in their inventory view
	 * @see HumanEntityInventoryViewItemMatcher
	 */
	public static HumanEntityInventoryViewItemMatcher hasItemInInventoryView(ItemStack item)
	{
		return HumanEntityInventoryViewItemMatcher.hasItemInInventoryView(item);
	}

	/**
	 * Creates a matcher which matches human entities without the specified item in their inventory view.
	 *
	 * @param item The item that should not be in the inventory view
	 * @return A matcher which matches human entities without the specified item in their inventory view
	 * @see HumanEntityInventoryViewItemMatcher
	 */
	public static Matcher<HumanEntityMock> doesNotHaveItemInInventoryView(ItemStack item)
	{
		return HumanEntityInventoryViewItemMatcher.doesNotHaveItemInInventoryView(item);
	}

	/**
	 * Creates a matcher which matches human entities without the specified item in their inventory view.
	 *
	 * @param material The material that should not be in the inventory view
	 * @return A matcher which matches human entities without the specified item in their inventory view
	 * @see HumanEntityInventoryViewItemMatcher
	 */
	public static Matcher<HumanEntityMock> doesNotHaveItemInInventoryView(Material material)
	{
		return HumanEntityInventoryViewItemMatcher.doesNotHaveItemInInventoryView(material);
	}

	/**
	 * Creates a matcher which matches human entities with the specified inventory view type.
	 *
	 * @param inventoryType The inventory view type required for a match
	 * @return A matcher which matches human entities with the specified inventory view type
	 * @see HumanEntityInventoryViewTypeMatcher
	 */
	public static HumanEntityInventoryViewTypeMatcher hasInventoryViewType(InventoryType inventoryType)
	{
		return HumanEntityInventoryViewTypeMatcher.hasInventoryViewType(inventoryType);
	}

	/**
	 * Creates a matcher which matches human entities without the specified inventory view type.
	 *
	 * @param inventoryType The inventory view type required for no match
	 * @return A matcher which matches human entities without the specified inventory view type
	 * @see HumanEntityInventoryViewTypeMatcher
	 */
	public static Matcher<HumanEntityMock> doesNotHaveInventoryViewType(InventoryType inventoryType)
	{
		return HumanEntityInventoryViewTypeMatcher.doesNotHaveInventoryViewType(inventoryType);
	}

	/**
	 * Creates a matcher which matches players that consumed the specified item.
	 *
	 * @param itemStack The item the player should have consumed
	 * @return A matcher which matches players that consumed the specified item
	 * @see PlayerConsumeItemMatcher
	 */
	public static PlayerConsumeItemMatcher hasConsumed(ItemStack itemStack)
	{
		return PlayerConsumeItemMatcher.hasConsumed(itemStack);
	}

	/**
	 * Creates a matcher which matches players that did not consume the specified item.
	 *
	 * @param itemStack The item the player should not have consumed
	 * @return A matcher which matches players that did not consume the specified item
	 * @see PlayerConsumeItemMatcher
	 */
	public static Matcher<PlayerMock> hasNotConsumed(ItemStack itemStack)
	{
		return PlayerConsumeItemMatcher.hasNotConsumed(itemStack);
	}

	/**
	 * Creates a matcher which matches ranged entities that attacked the specified target.
	 *
	 * @param target The entity that should have been attacked
	 * @param charge The charge of the attack
	 * @return A matcher which matches ranged entities that attacked the specified target
	 * @see RangedEntityAttackMatcher
	 */
	public static RangedEntityAttackMatcher hasAttacked(LivingEntity target, float charge)
	{
		return RangedEntityAttackMatcher.hasAttacked(target, charge);
	}

	/**
	 * Creates a matcher which matches ranged entities that attacked the specified target.
	 *
	 * @param target     The entity that should have been attacked
	 * @param charge     The charge of the attack
	 * @param aggressive Whether the attack should have been aggressive
	 * @return A matcher which matches ranged entities that attacked the specified target
	 * @see RangedEntityAttackMatcher
	 */
	public static RangedEntityAttackMatcher hasAttacked(LivingEntity target, float charge, boolean aggressive)
	{
		return RangedEntityAttackMatcher.hasAttacked(target, charge, aggressive);
	}

	/**
	 * Creates a matcher which matches ranged entities that did not attack the specified target.
	 *
	 * @param target The entity that should not have been attacked
	 * @param charge The charge of the attack
	 * @return A matcher which matches ranged entities that did not attack the specified target
	 * @see RangedEntityAttackMatcher
	 */
	public static Matcher<MockRangedEntity<? extends MobMock>> hasNotAttacked(LivingEntity target, float charge)
	{
		return RangedEntityAttackMatcher.hasNotAttacked(target, charge);
	}

	/**
	 * Creates a matcher which matches ranged entities that did not attack the specified target.
	 *
	 * @param target     The entity that should not have been attacked
	 * @param charge     The charge of the attack
	 * @param aggressive Whether the attack should have been aggressive
	 * @return A matcher which matches ranged entities that did not attack the specified target
	 * @see RangedEntityAttackMatcher
	 */
	public static Matcher<MockRangedEntity<? extends MobMock>> hasNotAttacked(LivingEntity target, float charge, boolean aggressive)
	{
		return RangedEntityAttackMatcher.hasNotAttacked(target, charge, aggressive);
	}

	// ===================================================================================
	// Help
	// ===================================================================================

	/**
	 * Creates a matcher which matches help maps with the specified factory registered.
	 *
	 * @param factory The help topic factory that should be registered
	 * @return A matcher which matches help maps with the specified factory registered
	 * @see HelpMapFactoryRegisteredMatcher
	 */
	public static HelpMapFactoryRegisteredMatcher hasFactoryRegistered(HelpTopicFactory<?> factory)
	{
		return HelpMapFactoryRegisteredMatcher.hasFactoryRegistered(factory);
	}

	/**
	 * Creates a matcher which matches help maps without the specified factory registered.
	 *
	 * @param factory The help topic factory that should not be registered
	 * @return A matcher which matches help maps without the specified factory registered
	 * @see HelpMapFactoryRegisteredMatcher
	 */
	public static Matcher<HelpMapMock> doesNotHaveFactoryRegistered(HelpTopicFactory<?> factory)
	{
		return HelpMapFactoryRegisteredMatcher.doesNotHaveFactoryRegistered(factory);
	}

	// ===================================================================================
	// Inventory
	// ===================================================================================

	/**
	 * Creates a matcher which matches with any inventory with at least the required amount of items.
	 *
	 * @param material The material of the items
	 * @param amount   The amount of the items required for a match
	 * @return A matcher which matches with any inventory with at least the required amount of items
	 * @see InventoryItemAmountMatcher
	 */
	public static InventoryItemAmountMatcher containsAtLeast(Material material, int amount)
	{
		return InventoryItemAmountMatcher.containsAtLeast(material, amount);
	}

	/**
	 * Creates a matcher which matches with any inventory with less than the given amount of items.
	 *
	 * @param material The material of the items
	 * @param amount   The amount of the items required for no match
	 * @return A matcher which matches with any inventory with less than the given amount of items
	 * @see InventoryItemAmountMatcher
	 */
	public static Matcher<InventoryMock> containsLessThan(Material material, int amount)
	{
		return InventoryItemAmountMatcher.containsLessThan(material, amount);
	}

	/**
	 * Creates a matcher which matches with any inventory with at least the required amount of items.
	 *
	 * @param targetItem The target item
	 * @param amount     The amount of the items required for a match
	 * @return A matcher which matches with any inventory with at least the required amount of items
	 * @see InventoryItemAmountMatcher
	 */
	public static InventoryItemAmountMatcher containsAtLeast(ItemStack targetItem, int amount)
	{
		return InventoryItemAmountMatcher.containsAtLeast(targetItem, amount);
	}

	/**
	 * Creates a matcher which matches with any inventory with less than the given amount of items.
	 *
	 * @param targetItem The target item
	 * @param amount     The amount of the items required for no match
	 * @return A matcher which matches with any inventory with less than the given amount of items
	 * @see InventoryItemAmountMatcher
	 */
	public static Matcher<InventoryMock> containsLessThan(ItemStack targetItem, int amount)
	{
		return InventoryItemAmountMatcher.containsLessThan(targetItem, amount);
	}

	/**
	 * Creates a matcher which matches when an item stack is similar to the specified item stack.
	 *
	 * @param itemStack The required item stack to be similar to for a match
	 * @return A matcher which matches when an item stack is similar to the specified item stack
	 * @see ItemSimilarityMatcher
	 */
	public static ItemSimilarityMatcher similarTo(ItemStack itemStack)
	{
		return ItemSimilarityMatcher.similarTo(itemStack);
	}

	/**
	 * Creates a matcher which matches when an item stack is similar to the specified item stack.
	 *
	 * @param itemMaterial The required material of the item stack to be similar to for a match
	 * @return A matcher which matches when an item stack is similar to the specified item stack
	 * @see ItemSimilarityMatcher
	 */
	public static ItemSimilarityMatcher similarTo(Material itemMaterial)
	{
		return ItemSimilarityMatcher.similarTo(itemMaterial);
	}

	/**
	 * Creates a matcher which matches with any inventory holder containing the specified item stack.
	 *
	 * @param itemStack The item stack required for there to be a match
	 * @return A matcher which matches with any inventory holder containing the specified item stack
	 * @see InventoryHolderContainsMatcher
	 */
	public static InventoryHolderContainsMatcher hasItemInInventory(ItemStack itemStack)
	{
		return InventoryHolderContainsMatcher.hasItemInInventory(itemStack);
	}

	/**
	 * Creates a matcher which matches with any inventory holder without the specified item stack.
	 *
	 * @param itemStack The item stack required for there to be no match
	 * @return A matcher which matches with any inventory holder without the specified item stack
	 * @see InventoryHolderContainsMatcher
	 */
	public static Matcher<InventoryHolder> doesNotHaveItemInInventory(ItemStack itemStack)
	{
		return InventoryHolderContainsMatcher.doesNotHaveItemInInventory(itemStack);
	}

	/**
	 * Creates a matcher which matches item meta that has any lore.
	 *
	 * @return A matcher which matches item meta that has any lore
	 * @see ItemMetaAnyLoreMatcher
	 */
	public static ItemMetaAnyLoreMatcher hasAnyLore()
	{
		return ItemMetaAnyLoreMatcher.hasAnyLore();
	}

	/**
	 * Creates a matcher which matches item meta that has no lore.
	 *
	 * @return A matcher which matches item meta that has no lore
	 * @see ItemMetaAnyLoreMatcher
	 */
	public static Matcher<ItemMetaMock> hasNoLore()
	{
		return ItemMetaAnyLoreMatcher.hasNoLore();
	}

	/**
	 * Creates a matcher which matches item meta with the specified lore.
	 *
	 * @param legacyLoreItems The legacy formatted lore lines required for a match
	 * @return A matcher which matches item meta with the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static ItemMetaLoreMatcher hasLore(String... legacyLoreItems)
	{
		return ItemMetaLoreMatcher.hasLore(legacyLoreItems);
	}

	/**
	 * Creates a matcher which matches item meta without the specified lore.
	 *
	 * @param legacyLoreItems The legacy formatted lore lines required for no match
	 * @return A matcher which matches item meta without the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static Matcher<ItemMetaMock> doesNotHaveLore(String... legacyLoreItems)
	{
		return ItemMetaLoreMatcher.doesNotHaveLore(legacyLoreItems);
	}

	/**
	 * Creates a matcher which matches item meta with the specified lore.
	 *
	 * @param loreItems The lore components required for a match
	 * @return A matcher which matches item meta with the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static ItemMetaLoreMatcher hasLore(Component... loreItems)
	{
		return ItemMetaLoreMatcher.hasLore(loreItems);
	}

	/**
	 * Creates a matcher which matches item meta without the specified lore.
	 *
	 * @param loreItems The lore components required for no match
	 * @return A matcher which matches item meta without the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static Matcher<ItemMetaMock> doesNotHaveLore(Component... loreItems)
	{
		return ItemMetaLoreMatcher.doesNotHaveLore(loreItems);
	}

	/**
	 * Creates a matcher which matches item meta with the specified lore.
	 *
	 * @param lore The lore components required for a match
	 * @return A matcher which matches item meta with the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static ItemMetaLoreMatcher hasLore(List<Component> lore)
	{
		return ItemMetaLoreMatcher.hasLore(lore);
	}

	/**
	 * Creates a matcher which matches item meta without the specified lore.
	 *
	 * @param lore The lore components required for no match
	 * @return A matcher which matches item meta without the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static Matcher<ItemMetaMock> doesNotHaveLore(List<Component> lore)
	{
		return ItemMetaLoreMatcher.doesNotHaveLore(lore);
	}

	// ===================================================================================
	// Plugin
	// ===================================================================================

	/**
	 * Creates a matcher which matches plugin managers that fired an instance of the specified event.
	 *
	 * @param targetEvent The event class that should have been fired
	 * @return A matcher which matches plugin managers that fired an instance of the specified event
	 * @see PluginManagerFiredEventClassMatcher
	 */
	public static PluginManagerFiredEventClassMatcher hasFiredEventInstance(Class<? extends Event> targetEvent)
	{
		return PluginManagerFiredEventClassMatcher.hasFiredEventInstance(targetEvent);
	}

	/**
	 * Creates a matcher which matches plugin managers that did not fire an instance of the specified event.
	 *
	 * @param targetEvent The event class that should not have been fired
	 * @return A matcher which matches plugin managers that did not fire an instance of the specified event
	 * @see PluginManagerFiredEventClassMatcher
	 */
	public static Matcher<PluginManagerMock> hasNotFiredEventInstance(Class<? extends Event> targetEvent)
	{
		return PluginManagerFiredEventClassMatcher.hasNotFiredEventInstance(targetEvent);
	}

	/**
	 * Creates a matcher which matches plugin managers that fired a matching event.
	 *
	 * @param eventClass The event class that should have been fired
	 * @param filter     The filter the fired event should match
	 * @param <G>        The type of the event
	 * @return A matcher which matches plugin managers that fired a matching event
	 * @see PluginManagerFiredEventFilterMatcher
	 */
	public static <G extends Event> PluginManagerFiredEventFilterMatcher<G> hasFiredFilteredEvent(Class<G> eventClass, Predicate<G> filter)
	{
		return PluginManagerFiredEventFilterMatcher.hasFiredFilteredEvent(eventClass, filter);
	}

	/**
	 * Creates a matcher which matches plugin managers that did not fire a matching event.
	 *
	 * @param eventClass The event class that should not have been fired
	 * @param filter     The filter the fired event should match
	 * @param <G>        The type of the event
	 * @return A matcher which matches plugin managers that did not fire a matching event
	 * @see PluginManagerFiredEventFilterMatcher
	 */
	public static <G extends Event> Matcher<PluginManagerMock> hasNotFiredFilteredEvent(Class<G> eventClass, Predicate<G> filter)
	{
		return PluginManagerFiredEventFilterMatcher.hasNotFiredFilteredEvent(eventClass, filter);
	}

	// ===================================================================================
	// Scheduler
	// ===================================================================================

	/**
	 * Creates a matcher which matches schedulers with no overdue tasks.
	 *
	 * @return A matcher which matches schedulers with no overdue tasks
	 * @see SchedulerOverdueTasksMatcher
	 */
	public static SchedulerOverdueTasksMatcher hasNoOverdueTasks()
	{
		return SchedulerOverdueTasksMatcher.hasNoOverdueTasks();
	}

	/**
	 * Creates a matcher which matches schedulers with overdue tasks.
	 *
	 * @return A matcher which matches schedulers with overdue tasks
	 * @see SchedulerOverdueTasksMatcher
	 */
	public static Matcher<BukkitSchedulerMock> hasOverdueTasks()
	{
		return SchedulerOverdueTasksMatcher.hasOverdueTasks();
	}

	// ===================================================================================
	// Sound
	// ===================================================================================

	/**
	 * Creates a matcher which matches sound receivers that heard the specified sound.
	 *
	 * @param sound  The sound that should have been heard
	 * @param filter The filter the audio experience should match
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static SoundReceiverSoundHeardMatcher hasHeard(Sound sound, Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound, filter);
	}

	/**
	 * Creates a matcher which matches sound receivers that heard the specified sound.
	 *
	 * @param sound  The sound that should have been heard
	 * @param filter The filter the audio experience should match
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static SoundReceiverSoundHeardMatcher hasHeard(org.bukkit.Sound sound, Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound, filter);
	}

	/**
	 * Creates a matcher which matches sound receivers that did not hear the specified sound.
	 *
	 * @param sound  The sound that should not have been heard
	 * @param filter The filter the audio experience should match
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static Matcher<SoundReceiver> hasNotHeard(org.bukkit.Sound sound, Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(sound, filter);
	}

	/**
	 * Creates a matcher which matches sound receivers that heard the specified sound.
	 *
	 * @param soundKey The key of the sound that should have been heard
	 * @param filter   The filter the audio experience should match
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static SoundReceiverSoundHeardMatcher hasHeard(String soundKey, Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(soundKey, filter);
	}

	/**
	 * Creates a matcher which matches sound receivers that did not hear the specified sound.
	 *
	 * @param soundKey The key of the sound that should not have been heard
	 * @param filter   The filter the audio experience should match
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static Matcher<SoundReceiver> hasNotHeard(String soundKey, Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(soundKey, filter);
	}

	/**
	 * Creates a matcher which matches sound receivers that heard the specified sound.
	 *
	 * @param sound The key of the sound that should have been heard
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static SoundReceiverSoundHeardMatcher hasHeard(String sound)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound);
	}

	/**
	 * Creates a matcher which matches sound receivers that did not hear the specified sound.
	 *
	 * @param sound The key of the sound that should not have been heard
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static Matcher<SoundReceiver> hasNotHeard(String sound)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(sound);
	}

	/**
	 * Creates a matcher which matches sound receivers that heard the specified sound.
	 *
	 * @param sound The sound that should have been heard
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static SoundReceiverSoundHeardMatcher hasHeard(Sound sound)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound);
	}

	/**
	 * Creates a matcher which matches sound receivers that did not hear the specified sound.
	 *
	 * @param sound The sound that should not have been heard
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static Matcher<SoundReceiver> hasNotHeard(Sound sound)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(sound);
	}

	/**
	 * Creates a matcher which matches sound receivers that heard the specified sound.
	 *
	 * @param sound The sound that should have been heard
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static SoundReceiverSoundHeardMatcher hasHeard(org.bukkit.Sound sound)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound);
	}

	/**
	 * Creates a matcher which matches sound receivers that did not hear the specified sound.
	 *
	 * @param sound The sound that should not have been heard
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static Matcher<SoundReceiver> hasNotHeard(org.bukkit.Sound sound)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(sound);
	}

}
