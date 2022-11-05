package be.seeseemelk.mockbukkit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitExtensionTest
{

	private ServerMock fieldServerMock;
	private final ServerMock constructorParameterServerMock;

	public MockBukkitExtensionTest(ServerMock constructorParameterServerMock)
	{
		this.constructorParameterServerMock = constructorParameterServerMock;
	}

	@Test
	void test_FieldServerMock_IsInjected()
	{
		assertNotNull(fieldServerMock);
	}

	@Test
	void test_ConstructorParameterServerMock_IsInjected()
	{
		assertNotNull(constructorParameterServerMock);
	}

	@Test
	void test_MethodParameterServerMock_IsInjected(ServerMock methodParameterServerMock)
	{
		assertNotNull(methodParameterServerMock);
	}

	@Test
	void test_AlreadyMocking_TriesToMockAgain_ThrowsException()
	{
		assertThrowsExactly(IllegalStateException.class, MockBukkit::mock);
	}

	@Test
	void test_EnsureMocking_DoesNotThrowException()
	{
		assertDoesNotThrow(MockBukkit::ensureMocking);
	}

	@Test
	void test_IsMocked_ReturnsTrue()
	{
		assertTrue(MockBukkit.isMocked());
	}

	@Test
	void test_GetMock_ReturnsMock()
	{
		assertNotNull(MockBukkit.getMock());
	}

	@Test
	void test_GetOrCreateMock_ReturnsMock()
	{
		assertNotNull(MockBukkit.getOrCreateMock());
	}

}
