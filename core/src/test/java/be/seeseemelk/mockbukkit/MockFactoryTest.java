package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.proxy.MockFactory;
import be.seeseemelk.mockbukkit.proxy.ProxyTarget;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MockFactoryTest
{
	public static class Implementor extends ProxyTarget
	{
		@Getter
		private int count = 0;

		public void method()
		{
			count++;
		}
	}

	public interface ParentInterface
	{
		void method();

		void notImplemented();
	}

	@RequiredArgsConstructor
	public static abstract class CustomClass implements ParentInterface
	{
		public final Implementor target;
	}

	@Test
	void methodCallReachesProxy()
	{
		Implementor target = new Implementor();
		CustomClass implementation = MockFactory.createProxy(null, CustomClass.class, target);
		implementation.method();
		assertThat(target.getCount(), equalTo(1));
	}

	@Test
	void unimplementedMethodThrowsException()
	{
		Implementor target = new Implementor();
		CustomClass implementation = MockFactory.createProxy(null, CustomClass.class, target);
		assertThrows(UnimplementedOperationException.class, implementation::notImplemented);
	}

	public static class InternalClass
	{
		@Getter
		@Setter
		public int valueA;

		public int valueB;

		@Getter
		@Setter
		public int valueD;
	}

	public static class NativeClass
	{
		@Getter
		@Setter
		private int valueA;

		public int valueB;


		@Getter
		@Setter
		private int valueC;
	}

	@Test
	void copyObject()
	{
		InternalClass internalObject = new InternalClass();
		NativeClass nativeObject = new NativeClass();

		internalObject.setValueA(5);
		internalObject.valueB = 99;
		internalObject.setValueD(23);

		MockFactory.copy(internalObject, nativeObject);
		assertThat(nativeObject.valueA, equalTo(5));
		assertThat(nativeObject.valueB, equalTo(0));
		assertThat(nativeObject.valueC, equalTo(0));
	}

	public enum InternalEnum
	{
		VALUE_A, VALUE_B
	}

	public static class InternalEnumClass extends ProxyTarget
	{
		public InternalEnum getEnum()
		{
			return InternalEnum.VALUE_B;
		}
	}

	public enum NativeEnum
	{
		VALUE_A, VALUE_B, VALUE_C
	}

	@RequiredArgsConstructor
	public static abstract class NativeEnumClass
	{
		public final InternalEnumClass target;

		public abstract NativeEnum getEnum();
	}

	@Test
	void convertEnum()
	{
		InternalEnumClass internalObject = new InternalEnumClass();
		NativeEnumClass nativeObject = MockFactory.createProxy(null, NativeEnumClass.class, internalObject);
		assertThat(internalObject.getEnum(), equalTo(InternalEnum.VALUE_B));
		assertThat(nativeObject.getEnum(), equalTo(NativeEnum.VALUE_B));
	}
}
