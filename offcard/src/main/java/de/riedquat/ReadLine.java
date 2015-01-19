package de.riedquat;

import com.sun.istack.internal.Nullable;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

/**
 * Provides lines usually read from an {@link java.io.InputStream} or a {@link java.io.Reader}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
public interface ReadLine extends Iterable<String> {
    /**
     * Reads a line.
     *
     * @return Line read.
     * @throws IOException
     *         In case of I/O problems.
     */
    String readLine() throws IOException;

    /**
     * Returns the current line number.
     * @return The current line number.
     */
    int getLineNumber();

    /**
     * A base class that implements the Iterable part.
     *
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     * @version 1.0
     * @since 1.0
     */
    abstract class ReadLineImpl implements ReadLine {

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                private String nextLine;
                private Throwable exception;
                {
                    prefetchNextLine();
                }

                /** Fetches the next line. */
                @Nullable
                private void prefetchNextLine() {
                    try {
                        nextLine = readLine();
                    } catch (final IOException e) {
                        exception = e;
                        nextLine = null;
                    }
                }

                @Override
                public boolean hasNext() {
                    return nextLine != null;
                }

                @Override
                public String next() {
                    if (nextLine == null) {
                        throw new NoSuchElementException();
                    }
                    try {
                        return nextLine;
                    } finally {
                        prefetchNextLine();
                    }
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    /**
     * Provides {@link ReadLine}s.
     *
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     * @version 1.0
     * @since 1.0
     */
    abstract class Factory<T> {

        /**
         * Creates a ReadLine by wrapping the specified object.
         *
         * @param objectToWrap
         *      Object to wrap for creating the ReadLine.
         * @return ReadLine wrapping that object.
         * @throws ClassCastException in case the Factory does not support the type of objectToWrap.
         */
        public abstract ReadLine createReadLineImpl(T objectToWrap) throws ClassCastException;

        /**
         * Returns a Factory that can create a ReadLine from the specified object.
         *
         * @param objectToWrap
         *      Object to wrap for creating the ReadLine.
         * @return Factory for that class.
         */
        public static ReadLine createReadLine(final Object objectToWrap) {
            final ServiceLoader<Factory> serviceLoader = ServiceLoader.load(Factory.class);
            for (final Factory factory : serviceLoader) {
                try {
                    return factory.createReadLineImpl(objectToWrap);
                } catch (final ClassCastException ignore) {
                }
            }
            return null;
        }
    }

    /**
     * Provides {@link ReadLine}s wrapping LineNumberReader.
     *
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     * @version 1.0
     * @since 1.0
     */
    class LineNumberFactory extends Factory<LineNumberReader> {

        @Override
        public ReadLine createReadLineImpl(final LineNumberReader objectToWrap) throws ClassCastException {
            return new ReadLineImpl() {
                private final LineNumberReader reader = objectToWrap;

                @Override
                public String readLine() throws IOException {
                    return reader.readLine();
                }

                @Override
                public int getLineNumber() {
                    return reader.getLineNumber();
                }
            };
        }
    }
}
