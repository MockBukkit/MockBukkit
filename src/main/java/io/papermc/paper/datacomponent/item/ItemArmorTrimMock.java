package io.papermc.paper.datacomponent.item;

import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record ItemArmorTrimMock(ArmorTrim armorTrim) implements ItemArmorTrim
{

	static class BuilderMock implements Builder
	{
		private ArmorTrim armorTrim;

		BuilderMock(ArmorTrim armorTrim)
		{
			this.armorTrim = armorTrim;
		}

		@Override
		public Builder armorTrim(ArmorTrim armorTrim)
		{
			this.armorTrim = armorTrim;
			return this;
		}

		@Override
		public ItemArmorTrim build()
		{
			return new ItemArmorTrimMock(armorTrim);
		}

	}

}
