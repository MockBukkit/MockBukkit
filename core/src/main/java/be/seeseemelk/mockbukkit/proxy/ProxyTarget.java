package be.seeseemelk.mockbukkit.proxy;

import be.seeseemelk.mockbukkit.ObjectProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class ProxyTarget
{
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PACKAGE)
	private Object base;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PACKAGE)
	private ObjectProvider objectProvider;
}
