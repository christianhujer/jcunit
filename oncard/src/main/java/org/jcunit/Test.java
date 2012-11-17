package org.jcunit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation for test cases.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("UnusedDeclaration")
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Test {
}
