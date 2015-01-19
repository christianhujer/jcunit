package test.de.riedquat;

import de.riedquat.JCReader;
import java.io.StringReader;
import org.junit.Test;

/**
 * Unit Test for {@link JCReader}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
public class JCReaderTest {

    @Test
    public void testReadLine() {
        final JCReader jcReader = new JCReader(new StringReader("foo\nbar__LINE__buzz\nbar __LINE__ buzz\nbar(__LINE__)buzz\n"));
        for (final String line : jcReader) {
        }
    }
}
