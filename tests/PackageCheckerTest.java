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
 * Tests the package checker.
 */
public final class PackageCheckerTest {
    @Test
    public void testAllLowercase() {
        Map<Integer, Set<String>> errors = StyleCheck.runCheckers(
                StyleCheck.parse(new File("tests/package/PackageLowercaseName.txt")),
                new boolean[] {false, false, false, false, false, true});
        assertFalse(errors.isEmpty());
        assertTrue(errors.keySet().iterator().hasNext());
        assertTrue(errors.get(errors.keySet().iterator().next()).iterator().hasNext());
        assertEquals("1 : Package names must be all lowercase.",
                     errors.keySet().iterator().next() + " : " +
                             errors.get(errors.keySet().iterator().next()).iterator().next());
    }

    @Test
    public void testPresenceOfUnderscores() {
        Map<Integer, Set<String>> errors = StyleCheck.runCheckers(
                StyleCheck.parse(new File("tests/package/PackageWithUnderscore.txt")),
                new boolean[] {false, false, false, false, false, true});
        assertFalse(errors.isEmpty());
        assertTrue(errors.keySet().iterator().hasNext());
        assertTrue(errors.get(errors.keySet().iterator().next()).iterator().hasNext());
        assertEquals("1 : Underscores are not allowed in package names.",
                     errors.keySet().iterator().next() + " : " +
                             errors.get(errors.keySet().iterator().next()).iterator().next());
    }

    @Test
    public void testAllLowercaseWithPresenceOfUnderscores() {
        Map<Integer, Set<String>> errors = StyleCheck.runCheckers(StyleCheck
                                                     .parse(new File("tests/package" +
                                                     "/PackageWithUnderscoreAllLowercase" +
                                                     ".txt")), new boolean[] {false, false, false,
                                                     false, false, true});
        assertFalse(errors.isEmpty());
        Iterator<Integer> lineNumberIterator = errors.keySet().iterator();
        assertTrue(lineNumberIterator.hasNext());
        int lineNumber = lineNumberIterator.next();
        Iterator<String> errorIterator = errors.get(lineNumber).iterator();
        assertTrue(errorIterator.hasNext());
        assertEquals("1 : Package names must be all lowercase.",
                     lineNumber + " : " + errorIterator.next());
        assertTrue(errorIterator.hasNext());
        assertEquals("1 : Underscores are not allowed in package names.",
                     lineNumber + " : " + errorIterator.next());
    }
}