package de.riedquat;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import org.jetbrains.annotations.Nullable;

/**
 * LineNumberReader that can replace the __LINE__ macro.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class JCReader extends ReadLine.ReadLineImpl {

    // Currently intentionally does not extend Reader.
    // read() etc. are not implemented properly.

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

    @Override
    public String readLine() throws IOException {
        return replaceMacros(reader.readLine());
    }

    @Override
    public int getLineNumber() {
        return reader.getLineNumber();
    }

    /**
     * Replaces the macros that the JCReader supports with their values.
     *
     * @param text
     *         Text for which to replace the macros.
     * @return The line with all macros replaced.
     */
    private String replaceMacros(@Nullable final String text) {
        return text != null ? text.replaceAll("__LINE__", Integer.toString(getLineNumber())) : null;
    }
}
