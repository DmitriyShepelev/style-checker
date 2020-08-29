import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.io.File;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import stylecheck.StyleCheck;

/**
 * Tests line length checker.
 */
public final class LineLengthCheckerTest {
    @Test
    public void testOver100CharacterLimit() {
        StyleCheck.lineLength = 100;
        Map<Integer, Set<String>> errors = StyleCheck.runCheckers(
                StyleCheck.parse(new File("tests/line/length/Over100CharacterLimit.txt")),
                new boolean[] {true, false, false, false});
        assertFalse(errors.isEmpty());
        assertTrue(errors.keySet().iterator().hasNext());
        assertTrue(errors.get(errors.keySet().iterator().next()).iterator().hasNext());
        assertEquals("4 : A line of code must not exceed 100 characters in length.",
                     errors.keySet().iterator().next() + " : " +
                             errors.get(errors.keySet().iterator().next()).iterator().next());
    }

    @Test
    public void test100CharacterLine() {
        StyleCheck.lineLength = 100;
        assertTrue(StyleCheck.runCheckers(
                StyleCheck.parse(new File("tests/line/length/100CharacterLine.txt")),
                new boolean[] {true, false, false, false, false}).isEmpty());
    }
}
