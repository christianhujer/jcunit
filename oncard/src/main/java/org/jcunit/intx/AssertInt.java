package org.jcunit.intx;

import static org.jcunit.Assert.assertTrue;

/**
 * Provides int assertion facilities for Java Card.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings({"UnusedDeclaration", "MethodCanBeVariableArityMethod"})
public class AssertInt {

    /**
     * Utility class - do not instantiate.
     */
    private AssertInt() {
    }

    /**
     * Asserts that the two given int values are equal.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expected
     *         Expected value.
     * @param actual
     *         Actual value.
     * @since 1.0
     */
    public static void assertEquals(final short lineNumber, final int expected, final int actual) {
        assertTrue(lineNumber, expected == actual);
    }

    /**
     * Asserts that two int arrays are equal.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expecteds
     *         int array with expected values.
     * @param actuals
     *         int array with actual values.
     * @since 1.0
     */
    public static void assertArrayEquals(final short lineNumber, final int[] expecteds, final int[] actuals) {
        assertEquals(lineNumber, expecteds.length, actuals.length);
        for (short index = 0; index < expecteds.length; index++) {
            assertEquals(lineNumber, expecteds[index], actuals[index]);
        }
    }
}
