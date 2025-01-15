package org.mockbukkit.mockbukkit.art;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Art;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class ArtMockTest
{

	@Nested
	class ValidateArtInformation
	{

		@ParameterizedTest
		@CsvSource({
			"minecraft:alban, 1, 1, §epainting.minecraft.alban.title, §7painting.minecraft.alban.author",
			"minecraft:aztec, 1, 1, §epainting.minecraft.aztec.title, §7painting.minecraft.aztec.author",
			"minecraft:aztec2, 1, 1, §epainting.minecraft.aztec2.title, §7painting.minecraft.aztec2.author",
			"minecraft:backyard, 3, 4, §epainting.minecraft.backyard.title, §7painting.minecraft.backyard.author",
			"minecraft:baroque, 2, 2, §epainting.minecraft.baroque.title, §7painting.minecraft.baroque.author",
			"minecraft:bomb, 1, 1, §epainting.minecraft.bomb.title, §7painting.minecraft.bomb.author",
			"minecraft:bouquet, 3, 3, §epainting.minecraft.bouquet.title, §7painting.minecraft.bouquet.author",
			"minecraft:burning_skull, 4, 4, §epainting.minecraft.burning_skull.title, §7painting.minecraft.burning_skull.author",
			"minecraft:bust, 2, 2, §epainting.minecraft.bust.title, §7painting.minecraft.bust.author",
			"minecraft:cavebird, 3, 3, §epainting.minecraft.cavebird.title, §7painting.minecraft.cavebird.author",
			"minecraft:changing, 4, 2, §epainting.minecraft.changing.title, §7painting.minecraft.changing.author",
			"minecraft:cotan, 3, 3, §epainting.minecraft.cotan.title, §7painting.minecraft.cotan.author",
			"minecraft:courbet, 2, 1, §epainting.minecraft.courbet.title, §7painting.minecraft.courbet.author",
			"minecraft:creebet, 2, 1, §epainting.minecraft.creebet.title, §7painting.minecraft.creebet.author",
			"minecraft:donkey_kong, 4, 3, §epainting.minecraft.donkey_kong.title, §7painting.minecraft.donkey_kong.author",
			"minecraft:earth, 2, 2, §epainting.minecraft.earth.title, ''",
			"minecraft:endboss, 3, 3, §epainting.minecraft.endboss.title, §7painting.minecraft.endboss.author",
			"minecraft:fern, 3, 3, §epainting.minecraft.fern.title, §7painting.minecraft.fern.author",
			"minecraft:fighters, 4, 2, §epainting.minecraft.fighters.title, §7painting.minecraft.fighters.author",
			"minecraft:finding, 4, 2, §epainting.minecraft.finding.title, §7painting.minecraft.finding.author",
			"minecraft:fire, 2, 2, §epainting.minecraft.fire.title, ''",
			"minecraft:graham, 1, 2, §epainting.minecraft.graham.title, §7painting.minecraft.graham.author",
			"minecraft:humble, 2, 2, §epainting.minecraft.humble.title, §7painting.minecraft.humble.author",
			"minecraft:kebab, 1, 1, §epainting.minecraft.kebab.title, §7painting.minecraft.kebab.author",
			"minecraft:lowmist, 4, 2, §epainting.minecraft.lowmist.title, §7painting.minecraft.lowmist.author",
			"minecraft:match, 2, 2, §epainting.minecraft.match.title, §7painting.minecraft.match.author",
			"minecraft:meditative, 1, 1, §epainting.minecraft.meditative.title, §7painting.minecraft.meditative.author",
		})
		void givenOther(String expectedKey, int expectedWidth, int expectedHeight, String expectedTitle, String expectedAuthor)
		{

			NamespacedKey key = NamespacedKey.fromString(expectedKey);
			Art art = RegistryAccess.registryAccess().getRegistry(RegistryKey.PAINTING_VARIANT).get(key);

			assertEquals(expectedKey, art.assetId().asString());
			assertEquals(expectedTitle, LegacyComponentSerializer.legacySection().serialize(art.title()));
			assertEquals(expectedAuthor, LegacyComponentSerializer.legacySection().serialize(art.author()));
			assertEquals(expectedWidth, art.getBlockWidth());
			assertEquals(expectedHeight, art.getBlockHeight());
		}
	}

}
