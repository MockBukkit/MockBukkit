package be.seeseemelk.mockbukkit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
public class MockBukkitServerTest
{

	@MockBukkitServer
	private ServerMock fieldServerMock;

	private ServerMock constructorParameterServerMock;

	public MockBukkitServerTest(@MockBukkitServer ServerMock serverMock)
	{
		this.constructorParameterServerMock = serverMock;
	}

	@Test
	void mockBukkitServerAnnotation_InjectsField()
	{
		assertNotNull(fieldServerMock);
	}

	@Test
	void mockBukkitServerAnnotation_InjectsConstructorParameter()
	{
		assertNotNull(constructorParameterServerMock);
	}

	@Test
	void mockBukkitServerAnnotation_InjectsMethodParameter(@MockBukkitServer ServerMock methodParameter)
	{
		assertNotNull(methodParameter);
	}

}
