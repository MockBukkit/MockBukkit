package be.seeseemelk.mockbukkit;

import org.bukkit.GameEvent;
import org.bukkit.Keyed;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistryMockTest
{

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void get_Structure()
	{
		RegistryMock<Structure> structureRegistryMock = new RegistryMock<>(Structure.class);
		assertNotNull(structureRegistryMock.get(Structure.MANSION.getKey()));
	}

	@ParameterizedTest
	@MethodSource("getStructures")
	void getStructureType_NonNull(Structure structure)
	{
		assertNotNull(structure.getStructureType());
	}

	@ParameterizedTest
	@MethodSource("getValues")
	void stream(Class<? extends Keyed> tClass)
	{
		assertNotEquals(0, new RegistryMock<>(tClass).stream().count());
	}

	@ParameterizedTest
	@MethodSource("getValues")
	void iterator(Class<? extends Keyed> tClass)
	{
		assertTrue(new RegistryMock<>(tClass).iterator().hasNext());
	}

	@Test
	void key_NotNull()
	{
		RegistryMock<Structure> structureRegistryMock = new RegistryMock<>(Structure.class);
		assertThrows(NullPointerException.class, () -> structureRegistryMock.get(null));
	}

	@Test
	void key_Invalid()
	{
		assertNull(new RegistryMock<>(Structure.class).get(NamespacedKey.minecraft("invalid")));
	}

	static Stream<Class<? extends Keyed>> getValues()
	{
		return Stream.of(Structure.class, StructureType.class, TrimMaterial.class, TrimPattern.class,
				MusicInstrument.class, GameEvent.class);
	}

	static Stream<Structure> getStructures()
	{
		return Registry.STRUCTURE.stream();
	}

}
