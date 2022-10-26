package be.seeseemelk.mockbukkit;

import org.jetbrains.annotations.ApiStatus;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Extension that mocks the Bukkit singleton before each test and subsequently unmocks it after each test.
 * <p>
 * Example usage:
 *
 * <pre class="code"><code class="java">
 * <b>&#064;ExtendWith(MockBukkitExtension.class)</b>
 * class ExampleTestWithExtension
 * {
 *
 * 	&#064;Test
 * 	void aUnitTest()
 *    {
 * 	    // a test that requires MockBukkit.mock() to have been run
 *    }
 *
 * }
 *
 * </code></pre>
 * <p>
 * The above example replaces the following usage:
 *
 * <pre class="code"><code class="java">
 * class ExampleTestWithoutExtension
 * {
 *
 * 	&#064;BeforeEach
 * 	void setUp()
 *    {
 * 	    MockBukkit.mock();
 *    }
 *
 * 	&#064;AfterEach
 * 	void tearDown()
 *    {
 * 	    MockBukkit.unmock();
 *    }
 *
 * 	&#064;Test
 * 	void aUnitTest()
 *    {
 * 	    // a test that requires MockBukkit.mock() to have been run
 *    }
 *
 * }
 * </code></pre>
 * <p>
 * Note that if you need access to the ServerMock object returned from MockBukkit.mock() you are not currently able to
 * use this extension. Rather, you will need to continue to manually call the mock() and unmock() methods.
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
