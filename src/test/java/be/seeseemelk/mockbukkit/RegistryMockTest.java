package be.seeseemelk.mockbukkit;

import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Arrays;
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
		RegistryMock<Structure> structureRegistryMock = new RegistryMock<>(RegistryKey.STRUCTURE);
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
	void stream(RegistryKey<? extends Keyed> key)
	{
		assertNotEquals(0, new RegistryMock<>(key).stream().count());
	}

	@ParameterizedTest
	@MethodSource("getValues")
	void iterator(RegistryKey<? extends Keyed> key)
	{
		assertTrue(new RegistryMock<>(key).iterator().hasNext());
	}

	@Test
	void key_NotNull()
	{
		RegistryMock<Structure> structureRegistryMock = new RegistryMock<>(RegistryKey.STRUCTURE);
		assertThrows(NullPointerException.class, () -> structureRegistryMock.get(null));
	}

	@Test
	void key_Invalid()
	{
		assertNull(new RegistryMock<>(RegistryKey.STRUCTURE).get(NamespacedKey.minecraft("invalid")));
	}

	static Stream<RegistryKey<? extends Keyed>> getValues()
	{
		return Arrays.stream(RegistryKey.class.getFields()).filter(field -> field.getType() == RegistryKey.class)
				.map(RegistryMockTest::getRegistryKey);
	}

	static RegistryKey<? extends Keyed> getRegistryKey(Field field)
	{
		try
		{
			return (RegistryKey<? extends Keyed>) field.get(null);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	static Stream<Structure> getStructures()
	{
		return Registry.STRUCTURE.stream();
	}

}
