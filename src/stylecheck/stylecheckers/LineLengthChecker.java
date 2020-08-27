package stylecheck.stylecheckers;

import java.util.Map;
import java.util.Set;
import stylecheck.StyleCheck;

/**
 * Checks that no line exceeds a specified length.
 */
public final class LineLengthChecker {
    /**
     * Checks that no line exceeds a specified length.
     *
     * @param errors a map from line numbers to their errors.
     * @param fileContents a map from line numbers to their code.
     * @param lineLength the length no line should exceed.
     */
    public static void check(Map<Integer, Set<String>> errors, Map<Integer, String> fileContents,
                             int lineLength) {
        for (Integer lineNumber : fileContents.keySet()) {
            String line = fileContents.get(lineNumber);
            String[] splitLine = line.trim().split(" ");
            if (!splitLine[0].equals("package") && !splitLine[0].equals("import")
                && line.length() > lineLength) {
                StyleCheck.addError(errors, lineNumber, "A line of code must not " +
                        "exceed " + lineLength + " characters in length.");
            }
        }
    }
}