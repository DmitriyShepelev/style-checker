import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import stylecheck.StyleCheck;

/**
 * Tests optional braces checker.
 */
public class OptionalBracesCheckerTest {
    @Test
    public void testOptionalBraces() throws FileNotFoundException {
        Map<Integer, Set<String>> errors = StyleCheck
                .runCheckers(StyleCheck.parse(new File("tests/optional/braces/" +
                                                               "OptionalBraces.txt")),
                             new boolean[] {false, false, false, false, true, false});
        assertFalse(errors.isEmpty());
        Iterator<Integer> lineNumberIterator = errors.keySet().iterator();
        assertTrue(lineNumberIterator.hasNext());
        int lineNumber = lineNumberIterator.next();
        Iterator<String> errorIterator = errors.get(lineNumber).iterator();
        assertTrue(errorIterator.hasNext());
        assertEquals("5 : Braces must be used with if, else, for, do, and while " +
                             "statements, even if the body is empty or contains a single " +
                             "statement.",
                   lineNumber + " : " + errorIterator.next());
        assertTrue(lineNumberIterator.hasNext());
        lineNumber = lineNumberIterator.next();
        errorIterator = errors.get(lineNumber).iterator();
        assertTrue(errorIterator.hasNext());
        assertEquals("7 : Braces must be used with if, else, for, do, and while " +
                             "statements, even if the body is empty or contains a single " +
                             "statement.",
                                  lineNumber + " : " + errorIterator.next());
        assertTrue(lineNumberIterator.hasNext());
        lineNumber = lineNumberIterator.next();
        errorIterator = errors.get(lineNumber).iterator();
        assertTrue(errorIterator.hasNext());
        assertEquals("9 : Braces must be used with if, else, for, do, and while " +
                             "statements, even if the body is empty or contains a single " +
                             "statement.",
                     lineNumber + " : " + errorIterator.next());
        assertTrue(lineNumberIterator.hasNext());
        lineNumber = lineNumberIterator.next();
        errorIterator = errors.get(lineNumber).iterator();
        assertTrue(errorIterator.hasNext());
        assertEquals("11 : Braces must be used with if, else, for, do, and while " +
                             "statements, even if the body is empty or contains a single " +
                             "statement.",
                     lineNumber + " : " + errorIterator.next());
    }
}
