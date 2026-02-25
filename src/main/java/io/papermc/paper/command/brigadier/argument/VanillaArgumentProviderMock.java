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
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<Key> key()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<Integer> time(int min)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<Style> style()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> ArgumentType<T> resource(RegistryKey<T> registryKey)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> ArgumentType<TypedKey<T>> resourceKey(RegistryKey<T> registryKey)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<SignedMessageResolver> signedMessage()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<EntitySelectorArgumentResolver> entity()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<HeightMap> heightMap()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<TextColor> hexColor()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<BlockState> blockState()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<LookAnchor> entityAnchor()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<RotationResolver> rotation()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<PlayerProfileListResolver> playerProfiles()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<Mirror> templateMirror()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<DoubleRangeProvider> doubleRange()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<IntegerRangeProvider> integerRange()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<ColumnFinePositionResolver> columnFinePosition(boolean centerIntegers)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<StructureRotation> templateRotation()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<BlockPositionResolver> blockPosition()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<FinePositionResolver> finePosition(boolean centerIntegers)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<ItemStackPredicate> itemStackPredicate()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<Criteria> objectiveCriteria()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<GameMode> gameMode()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<AxisSet> axes()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<EntitySelectorArgumentResolver> entities()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<World> world()
	{
		throw new UnimplementedOperationException();
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
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<NamedTextColor> namedColor()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<ItemStack> itemStack()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<AngleResolver> angle()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<DisplaySlot> scoreboardDisplaySlot()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<ColumnBlockPositionResolver> columnBlockPosition()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<BlockInWorldPredicate> blockInWorldPredicate()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ArgumentType<NamespacedKey> namespacedKey()
	{
		throw new UnimplementedOperationException();
	}

}
