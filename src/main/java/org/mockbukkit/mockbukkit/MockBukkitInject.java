package org.mockbukkit.mockbukkit;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.ServerMock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a {@link ServerMock} field or parameter as a mock, which will be reflectively set if the containing class is
 * using the {@link MockBukkitExtension} JUnit extension. See the latter class for example usage.
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MockBukkitInject
{

}
