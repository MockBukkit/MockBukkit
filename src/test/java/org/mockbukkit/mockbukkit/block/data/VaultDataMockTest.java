package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Vault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class VaultDataMockTest
{

	private VaultDataMock vault;

	@BeforeEach
	void setUp()
	{
		this.vault = new VaultDataMock(Material.VAULT);
	}

	@Nested
	class SetVaultState
	{

		@Test
		void givenDefaultAxis()
		{
			assertEquals(Vault.State.INACTIVE, vault.getVaultState());
		}

		@ParameterizedTest
		@EnumSource(Vault.State.class)
		void givenStateChange(Vault.State shape)
		{
			vault.setVaultState(shape);
			assertEquals(shape, vault.getVaultState());
		}

	}

	@Nested
	class SetOminous
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(vault.isOminous());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean ominous)
		{
			vault.setOminous(ominous);
			assertEquals(ominous, vault.isOminous());
		}

	}

	@Nested
	class SetFacing
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(BlockFace.NORTH, vault.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenValidValues(BlockFace face)
		{
			vault.setFacing(face);
			assertEquals(face, vault.getFacing());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = { "NORTH", "SOUTH", "EAST", "WEST" })
		void givenInvalidValues(BlockFace face)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> vault.setFacing(face));
			assertEquals("Invalid face, only cartesian horizontal face are allowed for this property!", e.getMessage());
		}

	}

}
