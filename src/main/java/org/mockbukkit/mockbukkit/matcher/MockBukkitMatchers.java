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
import org.jetbrains.annotations.NotNull;
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
	 * @param material The material that the block should have
	 * @return A matcher which matches blocks with the specified material
	 * @see BlockMaterialTypeMatcher
	 */
	public static @NotNull BlockMaterialTypeMatcher hasMaterial(@NotNull Material material)
	{
		return BlockMaterialTypeMatcher.hasMaterial(material);
	}

	/**
	 * @param material The material that the block should not have
	 * @return A matcher which matches blocks without the specified material
	 * @see BlockMaterialTypeMatcher
	 */
	public static @NotNull Matcher<BlockMock> doesNotHaveMaterial(@NotNull Material material)
	{
		return BlockMaterialTypeMatcher.doesNotHaveMaterial(material);
	}

	// ===================================================================================
	// Command
	// ===================================================================================

	/**
	 * @return A matcher which matches command results that produced any response
	 * @see CommandResultAnyResponseMatcher
	 */
	public static @NotNull CommandResultAnyResponseMatcher hasAnyResponse()
	{
		return CommandResultAnyResponseMatcher.hasAnyResponse();
	}

	/**
	 * @return A matcher which matches command results that produced no response
	 * @see CommandResultAnyResponseMatcher
	 */
	public static @NotNull Matcher<CommandResult> hasNoResponse()
	{
		return CommandResultAnyResponseMatcher.hasNoResponse();
	}

	/**
	 * @param response The response required for a match
	 * @return A matcher which matches command results with the specified response
	 * @see CommandResultResponseMatcher
	 */
	public static @NotNull CommandResultResponseMatcher hasResponse(@NotNull String response)
	{
		return CommandResultResponseMatcher.hasResponse(response);
	}

	/**
	 * @param response The response required for no match
	 * @return A matcher which matches command results without the specified response
	 * @see CommandResultResponseMatcher
	 */
	public static @NotNull Matcher<CommandResult> doesNotHaveResponse(@NotNull String response)
	{
		return CommandResultResponseMatcher.doesNotHaveResponse(response);
	}

	/**
	 * @return A matcher which matches command results that succeeded
	 * @see CommandResultSucceedMatcher
	 */
	public static @NotNull CommandResultSucceedMatcher hasSucceeded()
	{
		return CommandResultSucceedMatcher.hasSucceeded();
	}

	/**
	 * @return A matcher which matches command results that failed
	 * @see CommandResultSucceedMatcher
	 */
	public static @NotNull Matcher<CommandResult> hasFailed()
	{
		return CommandResultSucceedMatcher.hasFailed();
	}

	/**
	 * @return A matcher which matches message targets that received any message
	 * @see MessageTargetReceivedAnyMessageMatcher
	 */
	public static @NotNull MessageTargetReceivedAnyMessageMatcher hasReceivedAny()
	{
		return MessageTargetReceivedAnyMessageMatcher.hasReceivedAny();
	}

	/**
	 * @return A matcher which matches message targets that received no message
	 * @see MessageTargetReceivedAnyMessageMatcher
	 */
	public static @NotNull Matcher<MessageTarget> hasNotReceivedAny()
	{
		return MessageTargetReceivedAnyMessageMatcher.hasNotReceivedAny();
	}

	/**
	 * @param expected The message required for a match
	 * @return A matcher which matches message targets that received the specified message
	 * @see MessageTargetReceivedMessageMatcher
	 */
	public static @NotNull MessageTargetReceivedMessageMatcher hasReceived(@NotNull Component expected)
	{
		return MessageTargetReceivedMessageMatcher.hasReceived(expected);
	}

	/**
	 * @param expected The message required for a match
	 * @return A matcher which matches message targets that received the specified message
	 * @see MessageTargetReceivedMessageMatcher
	 */
	public static @NotNull MessageTargetReceivedMessageMatcher hasReceived(@NotNull String expected)
	{
		return MessageTargetReceivedMessageMatcher.hasReceived(expected);
	}

	/**
	 * @param expected The message required for no match
	 * @return A matcher which matches message targets that did not receive the specified message
	 * @see MessageTargetReceivedMessageMatcher
	 */
	public static @NotNull Matcher<MessageTarget> hasNotReceived(@NotNull String expected)
	{
		return MessageTargetReceivedMessageMatcher.hasNotReceived(expected);
	}

	// ===================================================================================
	// Entity
	// ===================================================================================

	/**
	 * @param location    The location required for a match
	 * @param maxDistance The radius away from the location which gives a match
	 * @return A matcher which matches with any entity within a radius of specified location
	 * @see EntityLocationMatcher
	 */
	public static @NotNull EntityLocationMatcher isInLocation(@NotNull Location location, double maxDistance)
	{
		return EntityLocationMatcher.isInLocation(location, maxDistance);
	}

	/**
	 * @param location    The location required for no match
	 * @param maxDistance The radius away from the location which withdraws a match
	 * @return A matcher which matches with any entity outside a radius of specified location
	 * @see EntityLocationMatcher
	 */
	public static @NotNull Matcher<EntityMock> isNotInLocation(@NotNull Location location, double maxDistance)
	{
		return EntityLocationMatcher.isNotInLocation(location, maxDistance);
	}

	/**
	 * @return A matcher which matches with any entity that has teleported
	 * @see EntityTeleportationMatcher
	 */
	public static EntityTeleportationMatcher hasTeleported()
	{
		return EntityTeleportationMatcher.hasTeleported();
	}

	/**
	 * @param location The location the entity should have teleported to
	 * @return A matcher which matches with any entity that has teleported to the specified location
	 * @see EntityTeleportationMatcher
	 */
	public static @NotNull EntityTeleportationMatcher hasTeleported(@NotNull Location location)
	{
		return EntityTeleportationMatcher.hasTeleported(location);
	}

	/**
	 * @param location        The location the entity should have teleported to
	 * @param maximumDistance The maximum distance from the location which gives a match
	 * @return A matcher which matches with any entity that has teleported near the specified location
	 * @see EntityTeleportationMatcher
	 */
	public static @NotNull EntityTeleportationMatcher hasTeleported(@NotNull Location location, double maximumDistance)
	{
		return EntityTeleportationMatcher.hasTeleported(location, maximumDistance);
	}

	/**
	 * @return A matcher which matches with any entity that has not teleported
	 * @see EntityTeleportationMatcher
	 */
	public static Matcher<EntityMock> hasNotTeleported()
	{
		return EntityTeleportationMatcher.hasNotTeleported();
	}

	/**
	 * @param location The location the entity should not have teleported to
	 * @return A matcher which matches with any entity that has not teleported to the specified location
	 * @see EntityTeleportationMatcher
	 */
	public static @NotNull Matcher<EntityMock> hasNotTeleported(@NotNull Location location)
	{
		return EntityTeleportationMatcher.hasNotTeleported(location);
	}

	/**
	 * @param location        The location the entity should not have teleported to
	 * @param maximumDistance The maximum distance from the location which withdraws a match
	 * @return A matcher which matches with any entity that has not teleported near the specified location
	 * @see EntityTeleportationMatcher
	 */
	public static @NotNull Matcher<EntityMock> hasNotTeleported(@NotNull Location location, double maximumDistance)
	{
		return EntityTeleportationMatcher.hasNotTeleported(location, maximumDistance);
	}

	/**
	 * @param currentItem The material the allay should be holding
	 * @return A matcher which matches allays holding the specified item
	 * @see AllayCurrentItemMatcher
	 */
	public static @NotNull AllayCurrentItemMatcher hasCurrentItem(@NotNull Material currentItem)
	{
		return AllayCurrentItemMatcher.hasCurrentItem(currentItem);
	}

	/**
	 * @param currentItem The material the allay should not be holding
	 * @return A matcher which matches allays not holding the specified item
	 * @see AllayCurrentItemMatcher
	 */
	public static @NotNull Matcher<AllayMock> doesNotHaveCurrentItem(@NotNull Material currentItem)
	{
		return AllayCurrentItemMatcher.doesNotHaveCurrentItem(currentItem);
	}

	/**
	 * @param target The entity the goat should have rammed
	 * @return A matcher which matches goats that have rammed the specified target
	 * @see GoatEntityRammedMatcher
	 */
	public static @NotNull GoatEntityRammedMatcher hasRammed(@NotNull LivingEntity target)
	{
		return GoatEntityRammedMatcher.hasRammed(target);
	}

	/**
	 * @param target The entity the goat should not have rammed
	 * @return A matcher which matches goats that have not rammed the specified target
	 * @see GoatEntityRammedMatcher
	 */
	public static @NotNull Matcher<GoatMock> hasNotRammed(@NotNull LivingEntity target)
	{
		return GoatEntityRammedMatcher.hasNotRammed(target);
	}

	/**
	 * @param material The material of the item in the inventory view
	 * @return A matcher which matches human entities with the specified item in their inventory view
	 * @see HumanEntityInventoryViewItemMatcher
	 */
	public static @NotNull HumanEntityInventoryViewItemMatcher hasItemInInventoryView(Material material)
	{
		return HumanEntityInventoryViewItemMatcher.hasItemInInventoryView(material);
	}

	/**
	 * @param item The item in the inventory view
	 * @return A matcher which matches human entities with the specified item in their inventory view
	 * @see HumanEntityInventoryViewItemMatcher
	 */
	public static @NotNull HumanEntityInventoryViewItemMatcher hasItemInInventoryView(@NotNull ItemStack item)
	{
		return HumanEntityInventoryViewItemMatcher.hasItemInInventoryView(item);
	}

	/**
	 * @param item The item that should not be in the inventory view
	 * @return A matcher which matches human entities without the specified item in their inventory view
	 * @see HumanEntityInventoryViewItemMatcher
	 */
	public static @NotNull Matcher<HumanEntityMock> doesNotHaveItemInInventoryView(@NotNull ItemStack item)
	{
		return HumanEntityInventoryViewItemMatcher.doesNotHaveItemInInventoryView(item);
	}

	/**
	 * @param material The material that should not be in the inventory view
	 * @return A matcher which matches human entities without the specified item in their inventory view
	 * @see HumanEntityInventoryViewItemMatcher
	 */
	public static @NotNull Matcher<HumanEntityMock> doesNotHaveItemInInventoryView(Material material)
	{
		return HumanEntityInventoryViewItemMatcher.doesNotHaveItemInInventoryView(material);
	}

	/**
	 * @param inventoryType The inventory view type required for a match
	 * @return A matcher which matches human entities with the specified inventory view type
	 * @see HumanEntityInventoryViewTypeMatcher
	 */
	public static @NotNull HumanEntityInventoryViewTypeMatcher hasInventoryViewType(@NotNull InventoryType inventoryType)
	{
		return HumanEntityInventoryViewTypeMatcher.hasInventoryViewType(inventoryType);
	}

	/**
	 * @param inventoryType The inventory view type required for no match
	 * @return A matcher which matches human entities without the specified inventory view type
	 * @see HumanEntityInventoryViewTypeMatcher
	 */
	public static @NotNull Matcher<HumanEntityMock> doesNotHaveInventoryViewType(@NotNull InventoryType inventoryType)
	{
		return HumanEntityInventoryViewTypeMatcher.doesNotHaveInventoryViewType(inventoryType);
	}

	/**
	 * @param itemStack The item the player should have consumed
	 * @return A matcher which matches players that consumed the specified item
	 * @see PlayerConsumeItemMatcher
	 */
	public static @NotNull PlayerConsumeItemMatcher hasConsumed(@NotNull ItemStack itemStack)
	{
		return PlayerConsumeItemMatcher.hasConsumed(itemStack);
	}

	/**
	 * @param itemStack The item the player should not have consumed
	 * @return A matcher which matches players that did not consume the specified item
	 * @see PlayerConsumeItemMatcher
	 */
	public static @NotNull Matcher<PlayerMock> hasNotConsumed(@NotNull ItemStack itemStack)
	{
		return PlayerConsumeItemMatcher.hasNotConsumed(itemStack);
	}

	/**
	 * @param target The entity that should have been attacked
	 * @param charge The charge of the attack
	 * @return A matcher which matches ranged entities that attacked the specified target
	 * @see RangedEntityAttackMatcher
	 */
	public static @NotNull RangedEntityAttackMatcher hasAttacked(@NotNull LivingEntity target, float charge)
	{
		return RangedEntityAttackMatcher.hasAttacked(target, charge);
	}

	/**
	 * @param target     The entity that should have been attacked
	 * @param charge     The charge of the attack
	 * @param aggressive Whether the attack should have been aggressive
	 * @return A matcher which matches ranged entities that attacked the specified target
	 * @see RangedEntityAttackMatcher
	 */
	public static @NotNull RangedEntityAttackMatcher hasAttacked(@NotNull LivingEntity target, float charge, boolean aggressive)
	{
		return RangedEntityAttackMatcher.hasAttacked(target, charge, aggressive);
	}

	/**
	 * @param target The entity that should not have been attacked
	 * @param charge The charge of the attack
	 * @return A matcher which matches ranged entities that did not attack the specified target
	 * @see RangedEntityAttackMatcher
	 */
	public static @NotNull Matcher<MockRangedEntity<? extends MobMock>> hasNotAttacked(@NotNull LivingEntity target, float charge)
	{
		return RangedEntityAttackMatcher.hasNotAttacked(target, charge);
	}

	/**
	 * @param target     The entity that should not have been attacked
	 * @param charge     The charge of the attack
	 * @param aggressive Whether the attack should have been aggressive
	 * @return A matcher which matches ranged entities that did not attack the specified target
	 * @see RangedEntityAttackMatcher
	 */
	public static @NotNull Matcher<MockRangedEntity<? extends MobMock>> hasNotAttacked(@NotNull LivingEntity target, float charge, boolean aggressive)
	{
		return RangedEntityAttackMatcher.hasNotAttacked(target, charge, aggressive);
	}

	// ===================================================================================
	// Help
	// ===================================================================================

	/**
	 * @param factory The help topic factory that should be registered
	 * @return A matcher which matches help maps with the specified factory registered
	 * @see HelpMapFactoryRegisteredMatcher
	 */
	public static @NotNull HelpMapFactoryRegisteredMatcher hasFactoryRegistered(@NotNull HelpTopicFactory<?> factory)
	{
		return HelpMapFactoryRegisteredMatcher.hasFactoryRegistered(factory);
	}

	/**
	 * @param factory The help topic factory that should not be registered
	 * @return A matcher which matches help maps without the specified factory registered
	 * @see HelpMapFactoryRegisteredMatcher
	 */
	public static @NotNull Matcher<HelpMapMock> doesNotHaveFactoryRegistered(@NotNull HelpTopicFactory<?> factory)
	{
		return HelpMapFactoryRegisteredMatcher.doesNotHaveFactoryRegistered(factory);
	}

	// ===================================================================================
	// Inventory
	// ===================================================================================

	/**
	 * @param material The material of the items
	 * @param amount   The amount of the items required for a match
	 * @return A matcher which matches with any inventory with at least the required amount of items
	 * @see InventoryItemAmountMatcher
	 */
	public static @NotNull InventoryItemAmountMatcher containsAtLeast(Material material, int amount)
	{
		return InventoryItemAmountMatcher.containsAtLeast(material, amount);
	}

	/**
	 * @param material The material of the items
	 * @param amount   The amount of the items required for no match
	 * @return A matcher which matches with any inventory with less than the given amount of items
	 * @see InventoryItemAmountMatcher
	 */
	public static @NotNull Matcher<InventoryMock> containsLessThan(Material material, int amount)
	{
		return InventoryItemAmountMatcher.containsLessThan(material, amount);
	}

	/**
	 * @param targetItem The target item
	 * @param amount     The amount of the items required for a match
	 * @return A matcher which matches with any inventory with at least the required amount of items
	 * @see InventoryItemAmountMatcher
	 */
	public static @NotNull InventoryItemAmountMatcher containsAtLeast(@NotNull ItemStack targetItem, int amount)
	{
		return InventoryItemAmountMatcher.containsAtLeast(targetItem, amount);
	}

	/**
	 * @param targetItem The target item
	 * @param amount     The amount of the items required for no match
	 * @return A matcher which matches with any inventory with less than the given amount of items
	 * @see InventoryItemAmountMatcher
	 */
	public static @NotNull Matcher<InventoryMock> containsLessThan(@NotNull ItemStack targetItem, int amount)
	{
		return InventoryItemAmountMatcher.containsLessThan(targetItem, amount);
	}

	/**
	 * @param itemStack The required item stack to be similar to for a match
	 * @return A matcher which matches when an item stack is similar to the specified item stack
	 * @see ItemSimilarityMatcher
	 */
	public static @NotNull ItemSimilarityMatcher similarTo(@NotNull ItemStack itemStack)
	{
		return ItemSimilarityMatcher.similarTo(itemStack);
	}

	/**
	 * @param itemMaterial The required material of the item stack to be similar to for a match
	 * @return A matcher which matches when an item stack is similar to the specified item stack
	 * @see ItemSimilarityMatcher
	 */
	public static @NotNull ItemSimilarityMatcher similarTo(@NotNull Material itemMaterial)
	{
		return ItemSimilarityMatcher.similarTo(itemMaterial);
	}

	/**
	 * @param itemStack The item stack required for there to be a match
	 * @return A matcher which matches with any inventory holder containing the specified item stack
	 * @see InventoryHolderContainsMatcher
	 */
	public static @NotNull InventoryHolderContainsMatcher hasItemInInventory(@NotNull ItemStack itemStack)
	{
		return InventoryHolderContainsMatcher.hasItemInInventory(itemStack);
	}

	/**
	 * @param itemStack The item stack required for there to be no match
	 * @return A matcher which matches with any inventory holder without the specified item stack
	 * @see InventoryHolderContainsMatcher
	 */
	public static @NotNull Matcher<InventoryHolder> doesNotHaveItemInInventory(@NotNull ItemStack itemStack)
	{
		return InventoryHolderContainsMatcher.doesNotHaveItemInInventory(itemStack);
	}

	/**
	 * @return A matcher which matches item meta that has any lore
	 * @see ItemMetaAnyLoreMatcher
	 */
	public static @NotNull ItemMetaAnyLoreMatcher hasAnyLore()
	{
		return ItemMetaAnyLoreMatcher.hasAnyLore();
	}

	/**
	 * @return A matcher which matches item meta that has no lore
	 * @see ItemMetaAnyLoreMatcher
	 */
	public static @NotNull Matcher<ItemMetaMock> hasNoLore()
	{
		return ItemMetaAnyLoreMatcher.hasNoLore();
	}

	/**
	 * @param legacyLoreItems The legacy formatted lore lines required for a match
	 * @return A matcher which matches item meta with the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static ItemMetaLoreMatcher hasLore(String... legacyLoreItems)
	{
		return ItemMetaLoreMatcher.hasLore(legacyLoreItems);
	}

	/**
	 * @param legacyLoreItems The legacy formatted lore lines required for no match
	 * @return A matcher which matches item meta without the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static Matcher<ItemMetaMock> doesNotHaveLore(String... legacyLoreItems)
	{
		return ItemMetaLoreMatcher.doesNotHaveLore(legacyLoreItems);
	}

	/**
	 * @param loreItems The lore components required for a match
	 * @return A matcher which matches item meta with the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static ItemMetaLoreMatcher hasLore(Component... loreItems)
	{
		return ItemMetaLoreMatcher.hasLore(loreItems);
	}

	/**
	 * @param loreItems The lore components required for no match
	 * @return A matcher which matches item meta without the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static Matcher<ItemMetaMock> doesNotHaveLore(Component... loreItems)
	{
		return ItemMetaLoreMatcher.doesNotHaveLore(loreItems);
	}

	/**
	 * @param lore The lore components required for a match
	 * @return A matcher which matches item meta with the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static @NotNull ItemMetaLoreMatcher hasLore(List<Component> lore)
	{
		return ItemMetaLoreMatcher.hasLore(lore);
	}

	/**
	 * @param lore The lore components required for no match
	 * @return A matcher which matches item meta without the specified lore
	 * @see ItemMetaLoreMatcher
	 */
	public static @NotNull Matcher<ItemMetaMock> doesNotHaveLore(List<Component> lore)
	{
		return ItemMetaLoreMatcher.doesNotHaveLore(lore);
	}

	// ===================================================================================
	// Plugin
	// ===================================================================================

	/**
	 * @param targetEvent The event class that should have been fired
	 * @return A matcher which matches plugin managers that fired an instance of the specified event
	 * @see PluginManagerFiredEventClassMatcher
	 */
	public static @NotNull PluginManagerFiredEventClassMatcher hasFiredEventInstance(@NotNull Class<? extends Event> targetEvent)
	{
		return PluginManagerFiredEventClassMatcher.hasFiredEventInstance(targetEvent);
	}

	/**
	 * @param targetEvent The event class that should not have been fired
	 * @return A matcher which matches plugin managers that did not fire an instance of the specified event
	 * @see PluginManagerFiredEventClassMatcher
	 */
	public static @NotNull Matcher<PluginManagerMock> hasNotFiredEventInstance(@NotNull Class<? extends Event> targetEvent)
	{
		return PluginManagerFiredEventClassMatcher.hasNotFiredEventInstance(targetEvent);
	}

	/**
	 * @param eventClass The event class that should have been fired
	 * @param filter     The filter the fired event should match
	 * @param <G>        The type of the event
	 * @return A matcher which matches plugin managers that fired a matching event
	 * @see PluginManagerFiredEventFilterMatcher
	 */
	public static <G extends Event> @NotNull PluginManagerFiredEventFilterMatcher<G> hasFiredFilteredEvent(@NotNull Class<G> eventClass, @NotNull Predicate<G> filter)
	{
		return PluginManagerFiredEventFilterMatcher.hasFiredFilteredEvent(eventClass, filter);
	}

	/**
	 * @param eventClass The event class that should not have been fired
	 * @param filter     The filter the fired event should match
	 * @param <G>        The type of the event
	 * @return A matcher which matches plugin managers that did not fire a matching event
	 * @see PluginManagerFiredEventFilterMatcher
	 */
	public static <G extends Event> @NotNull Matcher<PluginManagerMock> hasNotFiredFilteredEvent(@NotNull Class<G> eventClass, @NotNull Predicate<G> filter)
	{
		return PluginManagerFiredEventFilterMatcher.hasNotFiredFilteredEvent(eventClass, filter);
	}

	// ===================================================================================
	// Scheduler
	// ===================================================================================

	/**
	 * @return A matcher which matches schedulers with no overdue tasks
	 * @see SchedulerOverdueTasksMatcher
	 */
	public static @NotNull SchedulerOverdueTasksMatcher hasNoOverdueTasks()
	{
		return SchedulerOverdueTasksMatcher.hasNoOverdueTasks();
	}

	/**
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
	 * @param sound  The sound that should have been heard
	 * @param filter The filter the audio experience should match
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull Sound sound, @NotNull Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound, filter);
	}

	/**
	 * @param sound  The sound that should have been heard
	 * @param filter The filter the audio experience should match
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull org.bukkit.Sound sound, @NotNull Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound, filter);
	}

	/**
	 * @param sound  The sound that should not have been heard
	 * @param filter The filter the audio experience should match
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull org.bukkit.Sound sound, @NotNull Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(sound, filter);
	}

	/**
	 * @param soundKey The key of the sound that should have been heard
	 * @param filter   The filter the audio experience should match
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull String soundKey, @NotNull Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(soundKey, filter);
	}

	/**
	 * @param soundKey The key of the sound that should not have been heard
	 * @param filter   The filter the audio experience should match
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull String soundKey, @NotNull Predicate<AudioExperience> filter)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(soundKey, filter);
	}

	/**
	 * @param sound The key of the sound that should have been heard
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull String sound)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound);
	}

	/**
	 * @param sound The key of the sound that should not have been heard
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull String sound)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(sound);
	}

	/**
	 * @param sound The sound that should have been heard
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull Sound sound)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound);
	}

	/**
	 * @param sound The sound that should not have been heard
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull Sound sound)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(sound);
	}

	/**
	 * @param sound The sound that should have been heard
	 * @return A matcher which matches sound receivers that heard the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull SoundReceiverSoundHeardMatcher hasHeard(@NotNull org.bukkit.Sound sound)
	{
		return SoundReceiverSoundHeardMatcher.hasHeard(sound);
	}

	/**
	 * @param sound The sound that should not have been heard
	 * @return A matcher which matches sound receivers that did not hear the specified sound
	 * @see SoundReceiverSoundHeardMatcher
	 */
	public static @NotNull Matcher<SoundReceiver> hasNotHeard(@NotNull org.bukkit.Sound sound)
	{
		return SoundReceiverSoundHeardMatcher.hasNotHeard(sound);
	}

}
