import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import stylecheck.StyleCheck;

/**
 * Tests import checker.
 */
public final class ImportCheckerTest {
    private void testImport(String filePath, String errorMessage) {
        Map<Integer, Set<String>> errors = StyleCheck.runCheckers(StyleCheck
                                                      .parse(new File(filePath)),
                                                new boolean[] {false, true, false, false, false});
        assertFalse(errors.isEmpty());
        assertTrue(errors.keySet().iterator().hasNext());
        assertTrue(errors.get(errors.keySet().iterator().next()).iterator().hasNext());
        assertEquals(errorMessage, errors.keySet().iterator().next() + " : " +
                errors.get(errors.keySet().iterator().next()).iterator().next());
    }

    @Test
    public void testWildcardImports() {
        testImport("tests/import/WildcardImports.txt", "3 : Wildcard imports " +
                          "are not allowed.");
    }

    @Test
    public void testASCIISortOrder() {
        testImport("tests/import/ASCIIOrder.txt",
               "3 : Within each block, imported names must appear in ASCII " +
                          "sort order.");
    }

    @Test
    public void testSingleBlankLineExists() {
        testImport("tests/import/SingleBlankLineExists.txt", "3 : All " +
                    "static imports must be in a single block, and a single blank line must " +
                    "separate static imports from non-static imports.");
    }

    @Test
    public void testSingleBlankLineOnlyExists() {
        testImport("tests/import/SingleBlankLineOnlyExists.txt", "3 : A single " +
                "blank line must separate static imports from non-static imports.");
    }
}