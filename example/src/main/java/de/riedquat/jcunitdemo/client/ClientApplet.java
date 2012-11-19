package de.riedquat.jcunitdemo.client;

import de.riedquat.jcunitdemo.server.MemoryAccess;
import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;

/**
 * Accesses the array of another applet.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
public class ClientApplet extends Applet {

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
        new ClientApplet().register();
    }

    @Override
    public void process(final APDU apdu) throws ISOException {
        if (selectingApplet()) {
            return;
        }
        //final MemoryAccess memoryAccess = JCSystem.getAppletShareableInterfaceObject(JCSystem.lookupAID(...));
    }
}
