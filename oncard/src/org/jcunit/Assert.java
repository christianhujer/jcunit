package org.jcunit;

import javacard.framework.ISOException;

import static javacard.framework.ISO7816.*;

/**
 * Provides assertion facilities for Java Card.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings({"OverloadedMethodsWithSameNumberOfParameters", "UnusedDeclaration", "MethodCanBeVariableArityMethod"})
public class Assert {

    // TODO:Decide if this ain't responsibility of the caller instead.
    /**
     * The mask for making sure the lineNumber does not overflow the status word.
     */
    private static final short LINE_NUMBER_MASK = 0x3FF;

    /**
     * Utility class - do not instantiate.
     */
    private Assert() {
    }

    /**
     * Makes the test fail.
     *
     * @param lineNumber
     *         Line number in which the test failed.
     */
    public static void fail(final short lineNumber) {
        // Do not throw a new exception class like AssertionException because it consumes memory and causes wear.
        // Status word will be in the range 0x6200 to 0x65FF.
        ISOException.throwIt((short) (SW_WARNING_STATE_UNCHANGED + (short) (lineNumber & LINE_NUMBER_MASK)));
    }

    /**
     * Asserts that the given condition is true.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param condition
     *         Condition to assert true.
     */
    public static void assertTrue(final short lineNumber, final boolean condition) {
        if (!condition) {
            fail(lineNumber);
        }
    }

    /**
     * Asserts that the given condition is false.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param condition
     *         Condition to assert false.
     */
    public static void assertFalse(final short lineNumber, final boolean condition) {
        if (condition) {
            fail(lineNumber);
        }
    }

    /**
     * Asserts that the two given byte values are equal.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expected
     *         Expected value.
     * @param actual
     *         Actual value.
     */
    public static void assertEquals(final short lineNumber, final byte expected, final byte actual) {
        assertTrue(lineNumber, expected == actual);
    }

    /**
     * Asserts that the two given short values are equal.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expected
     *         Expected value.
     * @param actual
     *         Actual value.
     */
    public static void assertEquals(final short lineNumber, final short expected, final short actual) {
        assertTrue(lineNumber, expected == actual);
    }

    /**
     * Asserts that the two given Objects are equal.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expected
     *         Expected value.
     * @param actual
     *         Actual value.
     */
    public static void assertEquals(final short lineNumber, final Object expected, final Object actual) {
        assertTrue(lineNumber, expected == null ? actual == null : expected.equals(actual));
    }

    /**
     * Asserts that the two given references are identical.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expected
     *         Expected value.
     * @param actual
     *         Actual value.
     */
    @SuppressWarnings("ObjectEquality")
    public static void assertSame(final short lineNumber, final Object expected, final Object actual) {
        assertTrue(lineNumber, expected == actual);
    }

    /**
     * Asserts that the two given references are not identical.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param unexpected
     *         Unexpected value.
     * @param actual
     *         Actual value.
     */
    @SuppressWarnings("ObjectEquality")
    public static void assertNotSame(final short lineNumber, final Object unexpected, final Object actual) {
        assertFalse(lineNumber, unexpected == actual);
    }

    /**
     * Asserts that an object is null.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param object
     *         Object to check.
     */
    public static void assertNull(final short lineNumber, final Object object) {
        assertTrue(lineNumber, object == null);
    }

    /**
     * Asserts that an object is not null.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param object
     *         Object to check.
     */
    public static void assertNotNull(final short lineNumber, final Object object) {
        assertTrue(lineNumber, object != null);
    }

    /**
     * Asserts that two byte arrays are equal.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expecteds
     *         byte array with expected values.
     * @param actuals
     *         byte array with actual values.
     */
    public static void assertArrayEquals(final short lineNumber, final byte[] expecteds, final byte[] actuals) {
        assertEquals(lineNumber, expecteds.length, actuals.length);
        for (short index = 0; index < expecteds.length; index++) {
            assertEquals(lineNumber, expecteds[index], actuals[index]);
        }
    }

    /**
     * Asserts that two short arrays are equal.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expecteds
     *         short array with expected values.
     * @param actuals
     *         short array with actual values.
     */
    public static void assertArrayEquals(final short lineNumber, final short[] expecteds, final short[] actuals) {
        assertEquals(lineNumber, expecteds.length, actuals.length);
        for (short index = 0; index < expecteds.length; index++) {
            assertEquals(lineNumber, expecteds[index], actuals[index]);
        }
    }

    /**
     * Asserts that two Object arrays are equal.
     *
     * @param lineNumber
     *         Current line number of the caller.
     * @param expecteds
     *         Object array with expected values.
     * @param actuals
     *         Object array with actual values.
     */
    public static void assertArrayEquals(final short lineNumber, final Object[] expecteds, final Object[] actuals) {
        assertEquals(lineNumber, expecteds.length, actuals.length);
        for (short index = 0; index < expecteds.length; index++) {
            assertEquals(lineNumber, expecteds[index], actuals[index]);
        }
    }
}
