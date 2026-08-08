package io.papermc.paper.command.brigadier.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import io.papermc.paper.command.brigadier.argument.predicate.BlockInWorldPredicate;
import io.papermc.paper.command.brigadier.argument.predicate.ItemStackPredicate;
import io.papermc.paper.command.brigadier.argument.range.DoubleRangeProvider;
import io.papermc.paper.command.brigadier.argument.range.IntegerRangeProvider;
import io.papermc.paper.command.brigadier.argument.resolvers.AngleResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.ColumnBlockPositionResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.ColumnFinePositionResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.FinePositionResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.PlayerProfileListResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.RotationResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.EntitySelectorArgumentResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import io.papermc.paper.entity.LookAnchor;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.HeightMap;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.mockbukkit.mockbukkit.command.brigadier.argument.PlayerArgumentTypeMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

public class VanillaArgumentProviderMock implements VanillaArgumentProvider
{

	@Override
	public ArgumentType<Component> component()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<Key> key()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<Integer> time(int min)
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<Style> style()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public <T> ArgumentType<T> resource(RegistryKey<T> registryKey)
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public <T> ArgumentType<TypedKey<T>> resourceKey(RegistryKey<T> registryKey)
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<SignedMessageResolver> signedMessage()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<EntitySelectorArgumentResolver> entity()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<HeightMap> heightMap()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<TextColor> hexColor()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<BlockState> blockState()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<LookAnchor> entityAnchor()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<RotationResolver> rotation()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<PlayerProfileListResolver> playerProfiles()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<Mirror> templateMirror()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<DoubleRangeProvider> doubleRange()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<IntegerRangeProvider> integerRange()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<ColumnFinePositionResolver> columnFinePosition(boolean centerIntegers)
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<StructureRotation> templateRotation()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<BlockPositionResolver> blockPosition()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<FinePositionResolver> finePosition(boolean centerIntegers)
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<ItemStackPredicate> itemStackPredicate()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<Criteria> objectiveCriteria()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<GameMode> gameMode()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<AxisSet> axes()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<EntitySelectorArgumentResolver> entities()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<World> world()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<PlayerSelectorArgumentResolver> player()
	{
		return new PlayerArgumentTypeMock();
	}

	@Override
	public ArgumentType<PlayerSelectorArgumentResolver> players()
	{
		return new PlayerArgumentTypeMock(false);
	}

	@Override
	public ArgumentType<UUID> uuid()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<NamedTextColor> namedColor()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<ItemStack> itemStack()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<AngleResolver> angle()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<DisplaySlot> scoreboardDisplaySlot()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<ColumnBlockPositionResolver> columnBlockPosition()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<BlockInWorldPredicate> blockInWorldPredicate()
	{
		throw UnimplementedOperationException.exception();
	}

	@Override
	public ArgumentType<NamespacedKey> namespacedKey()
	{
		throw UnimplementedOperationException.exception();
	}

}
