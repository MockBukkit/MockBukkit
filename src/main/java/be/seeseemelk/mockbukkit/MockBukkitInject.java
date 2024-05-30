package be.seeseemelk.mockbukkit;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a {@link ServerMock} field or parameter as a mock, which will be reflectively set if the containing class is
 * using the {@link MockBukkitExtension} JUnit extension. See the latter class for example usage.
 */
@Target(
{ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiStatus.Experimental
public @interface MockBukkitInject
{

}
