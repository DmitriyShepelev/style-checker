import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import stylecheck.StyleCheck;

public class NonemptyBlocksCheckerTest {
    private void testNonemptyBlocks(String filePath, String errorMessage) throws
                                                                          FileNotFoundException {
        Map<Integer, Set<String>> errors = StyleCheck
                .runCheckers(StyleCheck.parse(new File(filePath)),
                             new boolean[] {false, false, false, true, false, false});
        assertFalse(errors.isEmpty());
        assertTrue(errors.keySet().iterator().hasNext());
        assertTrue(errors.get(errors.keySet().iterator().next()).iterator().hasNext());
        assertEquals(errorMessage, errors.keySet().iterator().next() + " : " +
                errors.get(errors.keySet().iterator().next()).iterator().next());
    }

    @Test
    public void testLineBreakAfterOpeningBrace() throws FileNotFoundException {
        testNonemptyBlocks("tests/nonempty/blocks/LineBreakAfterOpeningBrace.txt",
                           "6 : Missing line break after the opening brace for this " +
                                   "nonempty block.");
    }

    @Test
    public void testNoLineBreakBeforeOpeningBrace() throws FileNotFoundException {
        testNonemptyBlocks("tests/nonempty/blocks/NoLineBreakBeforeOpeningBrace.txt",
                           "4 : Line breaks are disallowed before the opening brace.");
    }

    @Test
    public void testNoLineBreakBeforeClosingBrace() throws FileNotFoundException {
        testNonemptyBlocks("tests/nonempty/blocks/NoLineBreakBeforeClosingBrace.txt",
                           "5 : Missing line break before the closing brace.");
    }

    @Test
    public void testLineBreakAfterClosingBrace() throws FileNotFoundException {
        testNonemptyBlocks("tests/nonempty/blocks/LineBreakAfterClosingBrace.txt",
                       "5 : Missing line break after the closing brace (only if " +
                                  "the closing brace terminates a statement or the body of a " +
                                  "named class or method).");
    }
}
