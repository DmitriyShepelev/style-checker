import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import stylecheck.StyleCheck;

/**
 * Tests indentation checker.
 */
public final class IndentationCheckerTest {
    private void testIndentation(String filePath) {
        StyleCheck.indentation = 4;
        Map<Integer, Set<String>> errors = StyleCheck.runCheckers(
                StyleCheck.parse(new File(filePath)),
                new boolean[] {false, false, true, false, false, false});
        assertFalse(errors.isEmpty());
        Iterator<Integer> lineNumberIterator = errors.keySet().iterator();
        assertTrue(lineNumberIterator.hasNext());
        int lineNumber = lineNumberIterator.next();
        Iterator<String> errorIterator = errors.get(lineNumber).iterator();
        assertTrue(errorIterator.hasNext());
        assertEquals("4 : Incorrect indentation.",
                     lineNumber + " : " + errorIterator.next());
        assertTrue(lineNumberIterator.hasNext());
        lineNumber = lineNumberIterator.next();
        errorIterator = errors.get(lineNumber).iterator();
        assertTrue(errorIterator.hasNext());
        assertEquals("6 : Incorrect indentation.",
                     lineNumber + " : " + errorIterator.next());
    }
    @Test
    public void lowIndentation() {
        testIndentation("tests/indentation/IndentationLow.txt");
    }

    @Test
    public void highIndentation() {
        testIndentation("tests/indentation/IndentationHigh.txt");
    }

    @Test
    public void testLowAndHighIndentation() {
        testIndentation("tests/indentation/IndentationLowAndHigh.txt");
    }
}