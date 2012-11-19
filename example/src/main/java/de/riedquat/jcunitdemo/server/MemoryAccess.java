package de.riedquat.jcunitdemo.server;

import javacard.framework.Shareable;

/**
 * Grants access to the array of an applet.
 * This should only succeed for global arrays.
 * For non-global arrays, access should be prevented by the firewall.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
public interface MemoryAccess extends Shareable {

    /**
     * Returns the array to play with.
     *
     * @return The array to play with or {@code null} if there currently is no array.
     */
    Object getArray();
}
