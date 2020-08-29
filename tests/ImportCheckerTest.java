import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import stylecheck.StyleCheck;

/**
 * Tests import checker.
 */
public class ImportCheckerTest {
    private void testImport(String filePath, String errorMessage) throws FileNotFoundException {
                            Map<Integer, Set<String>> errors = StyleCheck
                .runCheckers(StyleCheck.parse(new File(filePath)),
                             new boolean[] {false, true, false, false, false});
        assertFalse(errors.isEmpty());
        assertTrue(errors.keySet().iterator().hasNext());
        assertTrue(errors.get(errors.keySet().iterator().next()).iterator().hasNext());
        assertEquals(errorMessage, errors.keySet().iterator().next() + " : " +
                errors.get(errors.keySet().iterator().next()).iterator().next());
    }

    @Test
    public void testWildcardImports() throws FileNotFoundException {
        testImport("tests/import/WildcardImports.txt", "3 : Wildcard imports " +
                "are not allowed.");
    }

    @Test
    public void testASCIISortOrder() throws FileNotFoundException {
        testImport("tests/import/ASCIIOrder.txt",
                   "3 : Within each block, imported names must appear in ASCII " +
                           "sort order.");
    }

    @Test
    public void testSingleBlankLineExists() throws FileNotFoundException {
        testImport("tests/import/SingleBlankLineExists.txt", "3 : All " +
                "static imports must be in a single block, and a single blank line must separate" +
                " static imports from non-static imports.");
    }

    @Test
    public void testSingleBlankLineOnlyExists() throws FileNotFoundException {
        testImport("tests/import/SingleBlankLineOnlyExists.txt", "3 : A single " +
                "blank line must separate static imports from non-static imports.");
    }
}