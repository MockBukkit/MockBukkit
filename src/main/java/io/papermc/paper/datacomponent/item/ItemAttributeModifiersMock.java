package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.ArrayList;
import java.util.List;

public record ItemAttributeModifiersMock(List<Entry> modifiers) implements ItemAttributeModifiers
{

	record EntryMock(Attribute attribute, AttributeModifier modifier) implements Entry
	{

	}

	static class BuilderMock implements Builder
	{

		List<Entry> entries = new ArrayList<>();

		@Override
		public Builder addModifier(Attribute attribute, AttributeModifier modifier)
		{
			Preconditions.checkArgument(
					this.entries.stream().noneMatch(e ->
							e.modifier().getKey().equals(modifier.getKey()) && e.attribute().getKey().equals(attribute.getKey())
					),
					"Cannot add 2 modifiers with identical keys on the same attribute (modifier %s for attribute %s)",
					modifier.getKey(), attribute.getKey()
			);
			entries.add(new EntryMock(attribute, modifier));
			return this;
		}

		@Override
		public Builder addModifier(Attribute attribute, AttributeModifier modifier, EquipmentSlotGroup equipmentSlotGroup)
		{
			Preconditions.checkArgument(
					this.entries.stream().noneMatch(e ->
							e.modifier().getKey().equals(modifier.getKey()) && e.attribute().getKey().equals(attribute.getKey())
					),
					"Cannot add 2 modifiers with identical keys on the same attribute (modifier %s for attribute %s)",
					modifier.getKey(), attribute.getKey()
			);
			AttributeModifier newModifier = new AttributeModifier(modifier.getKey(), modifier.getAmount(), modifier.getOperation(), equipmentSlotGroup);
			entries.add(new EntryMock(attribute, newModifier));
			return this;
		}

		@Override
		public ItemAttributeModifiers build()
		{
			return new ItemAttributeModifiersMock(List.copyOf(entries));
		}

	}

}
