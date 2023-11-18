package org.mockbukkit.testutils.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Assertions;

public abstract class AbstractMatcherTest
{

	protected abstract Matcher<?> createMatcher();

	public static <T> void assertMatches(Matcher<T> matcher, T arg)
	{
		assertMatches("Expected match, but mismatched", matcher, arg);
	}

	public static <T> void assertMatches(String message, Matcher<T> matcher, Object arg)
	{
		if (!matcher.matches(arg))
		{
			Assertions.fail(message + " because: '" + mismatchDescription(matcher, arg) + "'");
		}
	}

	public static <T> void assertDoesNotMatch(Matcher<? super T> c, T arg)
	{
		assertDoesNotMatch("Unexpected match", c, arg);
	}

	public static <T> void assertDoesNotMatch(String message, Matcher<? super T> c, T arg)
	{
		Assertions.assertFalse(c.matches(arg), message);
	}

	public static void assertDescription(String expected, Matcher<?> matcher)
	{
		Description description = new StringDescription();
		description.appendDescriptionOf(matcher);
		Assertions.assertEquals(expected, description.toString().trim(),"Expected description");
	}

	public static <T> void assertMismatchDescription(String expected, Matcher<? super T> matcher, Object arg)
	{
		Assertions.assertFalse(matcher.matches(arg),"Precondition: Matcher should not match item.");
		Assertions.assertEquals(expected, mismatchDescription(matcher, arg),"Expected mismatch description");
	}

	public static void assertNullSafe(Matcher<?> matcher)
	{
		try
		{
			matcher.matches(null);
		}
		catch (Exception e)
		{
			Assertions.fail("Matcher was not null safe");
		}
	}

	public static void assertUnknownTypeSafe(Matcher<?> matcher)
	{
		try
		{
			matcher.matches(new UnknownType());
		}
		catch (Exception e)
		{
			Assertions.fail("Matcher was not unknown type safe, because: " + e);
		}
	}

	public void testIsNullSafe()
	{
		assertNullSafe(createMatcher());
	}

	public void testCopesWithUnknownTypes()
	{
		createMatcher().matches(new UnknownType());
	}

	private static <T> String mismatchDescription(Matcher<? super T> matcher, Object arg)
	{
		Description description = new StringDescription();
		matcher.describeMismatch(arg, description);
		return description.toString().trim();
	}

	@SuppressWarnings("WeakerAccess")
	public static class UnknownType
	{

	}

}
