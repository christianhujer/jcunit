package de.riedquat;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * LineNumberReader that can replace macros.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class JCReader {

    /**
     * The LineNumberReader to read from.
     */
    private final LineNumberReader reader;

    /**
     * Creates a JCReader.
     *
     * @param in
     *         Reader to read from.
     */
    public JCReader(final Reader in) {
        reader = in instanceof LineNumberReader ? (LineNumberReader) in : new LineNumberReader(in);
    }

    /**
     * Reads a line.
     *
     * @return Line read.
     * @throws IOException
     *         In case of I/O problems.
     */
    public String readLine() throws IOException {
        return replaceMacros(reader.readLine());
    }

    /**
     * Replaces the macros that the JCReader supports with their values.
     *
     * @param text
     *         Text for which to replace the macros.
     * @return The line with all macros replaced.
     */
    private String replaceMacros(final String text) {
        return text.replaceAll("__LINE__", Integer.toString(reader.getLineNumber()));
    }
}
