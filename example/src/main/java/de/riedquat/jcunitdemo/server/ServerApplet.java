package de.riedquat.jcunitdemo.server;

import javacard.framework.AID;
import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;
import javacard.framework.Shareable;
import javacard.framework.SystemException;
import javacard.framework.Util;
import javacardx.framework.util.intx.JCint;

/**
 * Creates an array and provides access to it.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
public class ServerApplet extends Applet implements MemoryAccess {

    /**
     * Instruction byte for running the test.
     */
    private static final byte CREATE_NON_GLOBAL_ARRAY = 0x01;

    /**
     * Instruction byte for creating a global array.
     */
    private static final byte CREATE_GLOBAL_ARRAY = 0x02;

    /**
     * The array that is being tested.
     */
    private Object array;

    /**
     * Installs this applet.
     *
     * @param bArray
     *         the array containing the installation parameters.
     * @param bOffset
     *         the starting offset in bArray.
     * @param bLength
     *         the length in bytes of the parameter data in bArray.
     * @throws ISOException
     *         if the install method failed.
     * @see Applet#install(byte[], short, byte)
     */
    @SuppressWarnings({"UnusedParameters", "UnnecessaryJavaDocLink", "UnusedDeclaration", "OverloadedMethodsWithSameNumberOfParameters"})
    public static void install(final byte[] bArray, final short bOffset, final byte bLength) throws ISOException {
        new ServerApplet().register();
    }

    @Override
    public void process(final APDU apdu) throws ISOException {
        if (selectingApplet()) {
            return;
        }
        switch (apdu.getBuffer()[ISO7816.OFFSET_INS]) {
        case CREATE_NON_GLOBAL_ARRAY:
            createNonGlobalArray(apdu);
            break;
        case CREATE_GLOBAL_ARRAY:
            createGlobalArray(apdu);
            break;
        default:
            ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    public Shareable getShareableInterfaceObject(final AID aid, final byte b) {
        return this;
    }

    /**
     * Creates a non-global array.
     *
     * @param apdu
     *         APDU with the information how the array should be created.
     */
    private void createNonGlobalArray(final APDU apdu) {
        final byte[] buffer = apdu.getBuffer();
        final byte type = buffer[ISO7816.OFFSET_P1];
        final byte event = buffer[ISO7816.OFFSET_P2];
        final short bytesReceived = apdu.setIncomingAndReceive();
        if (bytesReceived != 0x02) {
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        }
        final short length = Util.getShort(buffer, ISO7816.OFFSET_CDATA);
        array = createNonGlobalArray(type, length, event);
    }

    /**
     * Creates a global array.
     *
     * @param apdu
     *         APDU with the information how the array should be created.
     */
    private void createGlobalArray(final APDU apdu) {
        final byte[] buffer = apdu.getBuffer();
        final byte type = buffer[ISO7816.OFFSET_P1];
        if (buffer[ISO7816.OFFSET_P2] != 0x00) {
            ISOException.throwIt((ISO7816.SW_WRONG_P1P2));
        }
        final short bytesReceived = apdu.setIncomingAndReceive();
        if (bytesReceived != 0x02) {
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        }
        final short length = Util.getShort(buffer, ISO7816.OFFSET_CDATA);
        array = JCSystem.makeGlobalArray(type, length);
    }

    /**
     * Creates the specified type of transient array.
     *
     * @param type
     *         Type of array to create.
     *         One of:
     *         <ul>
     *         <li>{@link JCSystem#ARRAY_TYPE_BOOLEAN}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_BYTE}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_INT}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_OBJECT}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_SHORT}</li>
     *         </ul>
     * @param length
     *         Size of array to create.
     * @param event
     *         Event of array, one of {@link JCSystem#MEMORY_TYPE_TRANSIENT_DESELECT}, {@link JCSystem#MEMORY_TYPE_TRANSIENT_RESET} or {@link JCSystem#MEMORY_TYPE_PERSISTENT}.
     * @return The created array.
     */
    private static Object createNonGlobalArray(final byte type, final short length, final byte event) {
        return event == JCSystem.MEMORY_TYPE_PERSISTENT ? createNonGlobalPersistentArray(type, length) : createNonGlobalTransientArray(type, length, event);
    }

    /**
     * Creates a non-global transient array.
     *
     * @param type
     *         Type of the array to create.
     *         One of:
     *         <ul>
     *         <li>{@link JCSystem#ARRAY_TYPE_BOOLEAN}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_BYTE}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_INT}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_OBJECT}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_SHORT}</li>
     *         </ul>
     * @param length
     *         Size of the array to create.
     * @param event
     *         Event on which to clear memory of the transient array.
     *         One of:
     *         <ul>
     *         <li>{@link JCSystem#MEMORY_TYPE_TRANSIENT_DESELECT}</li>
     *         <li>{@link JCSystem#MEMORY_TYPE_TRANSIENT_RESET}</li>
     *         </ul>
     * @return The created non-global transient array.
     */
    private static Object createNonGlobalTransientArray(final byte type, final short length, final byte event) {
        switch (type) {
        case JCSystem.ARRAY_TYPE_BOOLEAN:
            return JCSystem.makeTransientBooleanArray(length, event);
        case JCSystem.ARRAY_TYPE_BYTE:
            return JCSystem.makeTransientByteArray(length, event);
        case JCSystem.ARRAY_TYPE_SHORT:
            return JCSystem.makeTransientShortArray(length, event);
        case JCSystem.ARRAY_TYPE_OBJECT:
            return JCSystem.makeTransientObjectArray(length, event);
        case JCSystem.ARRAY_TYPE_INT:
            return JCint.makeTransientIntArray(length, event);
        }
        SystemException.throwIt(SystemException.ILLEGAL_VALUE);
        //noinspection ReturnOfNull
        return null; // unreachable
    }

    /**
     * Creates a non-global persistent array.
     *
     * @param type
     *         Type of the array to create.
     *         One of:
     *         <ul>
     *         <li>{@link JCSystem#ARRAY_TYPE_BOOLEAN}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_BYTE}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_INT}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_OBJECT}</li>
     *         <li>{@link JCSystem#ARRAY_TYPE_SHORT}</li>
     *         </ul>
     * @param length
     *         Size of the array to create.
     * @return The created non-global persistent array.
     */
    private static Object createNonGlobalPersistentArray(final byte type, final short length) {
        switch (type) {
        case JCSystem.ARRAY_TYPE_BOOLEAN:
            return new boolean[length];
        case JCSystem.ARRAY_TYPE_BYTE:
            return new byte[length];
        case JCSystem.ARRAY_TYPE_SHORT:
            return new short[length];
        case JCSystem.ARRAY_TYPE_OBJECT:
            return new Object[length];
        case JCSystem.ARRAY_TYPE_INT:
            return new int[length];
        }
        SystemException.throwIt(SystemException.ILLEGAL_VALUE);
        //noinspection ReturnOfNull
        return null; // unreachable
    }

    @Override
    public Object getArray() {
        return array;
    }
}
