package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.block.data.type.Bamboo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockBukkitExtension.class)
class BambooDataMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private final BambooDataMock bamboo = new BambooDataMock();

	@Test
	void getLeaves()
	{
		assertEquals(Bamboo.Leaves.NONE, bamboo.getLeaves());
	}

	@ParameterizedTest
	@EnumSource(Bamboo.Leaves.class)
	void setLeaves(Bamboo.Leaves leaves)
	{
		bamboo.setLeaves(leaves);
		assertEquals(leaves, bamboo.getLeaves());
	}

	@Test
	void getAge()
	{
		assertEquals(0, bamboo.getAge());
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 1 })
	void setAge(int age)
	{
		bamboo.setAge(age);
		assertEquals(age, bamboo.getAge());
	}

	@Test
	void getMaximumAge()
	{
		assertEquals(1, bamboo.getMaximumAge());
	}

	@Test
	void getStage()
	{
		assertEquals(0, bamboo.getStage());
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 1 })
	void setStage(int age)
	{
		bamboo.setStage(age);
		assertEquals(age, bamboo.getStage());
	}

	@Test
	void getMaximumStage()
	{
		assertEquals(1, bamboo.getMaximumStage());
	}

	@Test
	void validateClone()
	{
		@NotNull BambooDataMock cloned = bamboo.clone();

		assertEquals(bamboo, cloned);
		assertEquals(bamboo.getAge(), cloned.getAge());

		bamboo.setAge(10);

		assertNotEquals(bamboo, cloned);
		assertEquals(10, bamboo.getAge());
		assertEquals(0, cloned.getAge());
	}

}
