package be.seeseemelk.mockbukkit;

import org.jetbrains.annotations.ApiStatus;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Extension that mocks the Bukkit singleton before each test and subsequently unmocks it after each test.
 *
 * Example usage:
 *
 * <pre class="code"><code class="java">
 * <b>&#064;ExtendWith(MockBukkitExtension.class)</b>
 * class ExampleTest
 * {
 *
 * 	&#064;Test
 * 	void aUnitTest()
 *	{
 *	    ServerMock serverMock = MockBukkit.getMock();
 *	    // ...
 *	}
 *
 * }
 * </code></pre>
 */
@ApiStatus.Experimental
public class MockBukkitExtension implements BeforeEachCallback, AfterEachCallback
{

	@Override
	public void beforeEach(ExtensionContext context) throws Exception
	{
		MockBukkit.mock();
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception
	{
		MockBukkit.unmock();
	}

}
