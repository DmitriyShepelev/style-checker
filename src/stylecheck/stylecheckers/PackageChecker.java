package stylecheck.stylecheckers;

import java.util.Map;
import java.util.Set;
import stylecheck.StyleCheck;

/**
 * Checks that package names are lowercase and do not contain underscores.
 */
public final class PackageChecker {
    /**
     * Checks that package names are lowercase and do not contain underscores.
     *
     * @param errors a map from line numbers to their errors.
     * @param fileContents a map from line numbers to their code.
     */
    public static void check(Map<Integer, Set<String>> errors, Map<Integer, String> fileContents) {
        for (Integer lineNumber : fileContents.keySet()) {
            String[] line = fileContents.get(lineNumber).trim().split(" ");
            if (line[0].equals("package")) {
                if (!line[1].equals(line[1].toLowerCase())) {
                    StyleCheck.addError(errors, lineNumber, "Package names must be " +
                            "all lowercase.");
                }
                if (line[1].contains("_")) {
                    StyleCheck.addError(errors, lineNumber, "Underscores are not " +
                            "allowed in package names.");
                }
                break;
            }
        }
    }
}