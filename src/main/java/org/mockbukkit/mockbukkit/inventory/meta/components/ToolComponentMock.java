package org.mockbukkit.mockbukkit.inventory.meta.components;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.meta.components.ToolComponent;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Builder
@NotNullByDefault
@EqualsAndHashCode
@SerializableAs("Tool")
@SuppressWarnings("UnstableApiUsage")
public class ToolComponentMock implements ToolComponent
{
	private final List<ToolRule> toolRules = new ArrayList<>();

	private float defaultMiningSpeed;
	private int damagePerBlock;

	@Override
	public float getDefaultMiningSpeed()
	{
		return this.defaultMiningSpeed;
	}

	@Override
	public void setDefaultMiningSpeed(float speed)
	{
		this.defaultMiningSpeed = speed;
	}

	@Override
	public int getDamagePerBlock()
	{
		return this.damagePerBlock;
	}

	@Override
	public void setDamagePerBlock(int damage)
	{
		Preconditions.checkArgument(damage >= 0, "damage must be >= 0, was %d", damage);
		this.damagePerBlock = damage;
	}

	@Override
	public List<ToolRule> getRules()
	{
		return Collections.unmodifiableList(toolRules);
	}

	@Override
	public void setRules(List<ToolRule> rules)
	{
		Preconditions.checkArgument(rules != null, "rules must not be null");
		this.toolRules.clear();
		this.toolRules.addAll(rules);
	}

	@Override
	public ToolRule addRule(Material block, @Nullable Float speed, @Nullable Boolean correctForDrops)
	{
		Preconditions.checkArgument(block != null, "block must not be null");
		Preconditions.checkArgument(block.isBlock(), "block must be a block type, given %s", block.getKey());
		Preconditions.checkArgument(speed == null || speed > 0.0F, "speed must be positive");

		return this.addRule(List.of(block), speed, correctForDrops);
	}

	@Override
	public ToolRule addRule(Collection<Material> blocks, @Nullable Float speed, @Nullable Boolean correctForDrops)
	{
		Preconditions.checkArgument(speed == null || speed > 0.0F, "speed must be positive");

		for(Material material : blocks)
		{
			Preconditions.checkArgument(material.isBlock(), "blocks contains non-block type: %s", material.getKey());
		}

		return ToolRuleMock.builder()
				.blocks(blocks)
				.speed(speed)
				.isCorrectForDrops(correctForDrops)
				.build();
	}

	@Override
	public ToolRule addRule(Tag<Material> tag, @Nullable Float speed, @Nullable Boolean correctForDrops)
	{
		return this.addRule(tag.getValues(), speed, correctForDrops);
	}

	@Override
	public boolean removeRule(ToolRule rule)
	{
		return false;
	}

	@Override
	public Map<String, Object> serialize()
	{
		// TODO: Implement serialize
		return Map.of();
	}

	public static ToolComponentMock deserialize(Map<String, Object> map)
	{
		// TODO: Not implemented yet
		throw new UnimplementedOperationException();
	}

	public static ToolComponent useDefault()
	{
		return builder().build();
	}

	@Builder
	@NotNullByDefault
	@EqualsAndHashCode
	@SerializableAs("ToolRule")
	@SuppressWarnings("UnstableApiUsage")
	public static class ToolRuleMock implements ToolComponent.ToolRule
	{
		private Collection<Material> blocks;

		private @Nullable Float speed;
		private @Nullable Boolean isCorrectForDrops;

		@Override
		public Collection<Material> getBlocks()
		{
			return Collections.unmodifiableCollection(this.blocks);
		}

		@Override
		public void setBlocks(Material block)
		{
			Preconditions.checkArgument(block != null, "block must not be null");
			Preconditions.checkArgument(block.isBlock(), "block must be a block type, given %s", block.getKey());

			this.blocks = List.of(block);
		}

		@Override
		public void setBlocks(Collection<Material> blocks)
		{
			Preconditions.checkArgument(blocks != null, "blocks must not be null");

			for(Material material : blocks)
			{
				Preconditions.checkArgument(material.isBlock(), "blocks contains non-block type: %s", material.getKey());
			}

			this.blocks = List.copyOf(blocks);
		}

		@Override
		public void setBlocks(Tag<Material> tag)
		{
			this.setBlocks(tag.getValues());
		}

		@Override
		public @Nullable Float getSpeed()
		{
			return this.speed;
		}

		@Override
		public void setSpeed(@Nullable Float speed)
		{
			this.speed = speed;
		}

		@Override
		public @Nullable Boolean isCorrectForDrops()
		{
			return this.isCorrectForDrops;
		}

		@Override
		public void setCorrectForDrops(@Nullable Boolean correct)
		{
			this.isCorrectForDrops = correct;
		}

		@Override
		public Map<String, Object> serialize()
		{
			Map<String, Object> result = new LinkedHashMap<>();

			var blockKeys = this.blocks.stream()
					.map(Material::getKey)
					.map(NamespacedKey::asString)
					.toList();
			result.put("blocks", blockKeys);
			Float speed = this.getSpeed();
			if (speed != null)
			{
				result.put("speed", speed);
			}

			Boolean correct = this.isCorrectForDrops();
			if (correct != null)
			{
				result.put("correct-for-drops", correct);
			}

			return result;
		}

		public static ToolRuleMock deserialize(Map<String, Object> map)
		{
			// TODO: Not implemented yet
			throw new UnimplementedOperationException();
		}

	}

}
