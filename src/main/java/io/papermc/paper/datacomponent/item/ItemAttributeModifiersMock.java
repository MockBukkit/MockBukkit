package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.item.attribute.AttributeModifierDisplay;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record ItemAttributeModifiersMock(List<Entry> modifiers) implements ItemAttributeModifiers
{

	record EntryMock(Attribute attribute, AttributeModifier modifier, AttributeModifierDisplay display) implements Entry
	{
	}

	static class BuilderMock implements Builder
	{

		private final List<Entry> entries = new ObjectArrayList<>();

		@Override
		public Builder addModifier(Attribute attribute, AttributeModifier modifier, EquipmentSlotGroup equipmentSlotGroup, AttributeModifierDisplay display)
		{
			Preconditions.checkArgument(
					this.entries.stream().noneMatch(e ->
							e.modifier().getKey().equals(modifier.getKey()) && e.attribute().getKey().equals(attribute.getKey())
					),
					"Cannot add 2 modifiers with identical keys on the same attribute (modifier %s for attribute %s)",
					modifier.getKey(), attribute.getKey()
			);
			AttributeModifier newModifier = new AttributeModifier(modifier.getKey(), modifier.getAmount(), modifier.getOperation(), equipmentSlotGroup);
			entries.add(new EntryMock(attribute, newModifier, display));
			return this;
		}

		@Override
		public ItemAttributeModifiers build()
		{
			return new ItemAttributeModifiersMock(List.copyOf(entries));
		}

	}

}
