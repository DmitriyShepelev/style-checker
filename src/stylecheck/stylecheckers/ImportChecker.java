package stylecheck.stylecheckers;

import java.util.Map;
import java.util.Set;
import stylecheck.StyleCheck;

/**
 * Checks that:
 * <ul>
 *     <li>
 *         Wildcard imports are not used.
 *     </li>
 *     <li>
 *         Within each block (non-static and static), imported names appear in ASCII sort order.
 *     </li>
 *     <li>
 *         Static imports are in a single block, and a single blank line separates static imports
 *         from non-static imports.
 *     </li>
 * </ul>
 */
public final class ImportChecker {
    /**
     * Checks that:
     * <ul>
     *     <li>
     *         Wildcard imports are not used.
     *     </li>
     *     <li>
     *         Within each block (non-static and static), imported names appear in ASCII sort order.
     *     </li>
     *     <li>
     *         Static imports are in a single block, and a single blank line separates static
     *         imports from non-static imports.
     *     </li>
     * </ul>
     * @param errors a map from line numbers to their errors.
     * @param fileContents a map from line numbers to their code.
     */
    public static void check(Map<Integer, Set<String>> errors, Map<Integer, String> fileContents) {
        boolean isStatic = false;
        String previousLine = "";
        for (Integer lineNumber : fileContents.keySet()) {
            String[] line = fileContents.get(lineNumber).trim().split(" ");
            if (line[0].equals("import")) {
                if (line[1].equals("static")) {
                    isStatic = true;
                    if (line[2].contains("*")) {
                        StyleCheck.addError(errors, lineNumber,"Wildcard imports are " +
                                "not allowed.");
                    }
                    if (line[2].compareToIgnoreCase(previousLine) < 0) {
                        StyleCheck.addError(errors, lineNumber, "Within each block, " +
                                "imported names must appear in ASCII sort order.");
                    }
                    previousLine = line[2];
                } else {
                    if (!isStatic) {
                        if (line[1].compareToIgnoreCase(previousLine) < 0) {
                            StyleCheck.addError(errors, lineNumber, "Within each " +
                                    "block, imported names must appear in ASCII sort order.");
                        }
                        if (line[1].contains("*")) {
                            StyleCheck.addError(errors, lineNumber, "Wildcard imports " +
                                                    "are not allowed.");
                        }
                    } else {
                        StyleCheck.addError(errors, lineNumber,
                                 "All static imports must be in a single block, and a" +
                                            " single blank line must separate static imports from" +
                                            " non-static imports.");
                    }
                    previousLine = line[1];
                }
            } else {
                if (line[0].equals("class") || line.length > 1 && line[1].equals("class")) {
                    break; // Nothing left to check.
                } else if (line[0].equals("") && previousLine.equals("")) {
                    StyleCheck.addError(errors, lineNumber, "A single blank line must " +
                            "separate static imports from non-static imports.");
                } else if (line[0].equals("")) {
                    isStatic = false;
                }
                previousLine = line[0];
            }
        }
    }
}