package stylecheck.stylecheckers;

import java.util.Map;
import java.util.Set;
import stylecheck.StyleCheck;

/**
 * Checks that each line is indented by a user-specified number of spaces.
 */
public final class IndentationChecker {
    /**
     * Checks that each line is indented by a user-specified number of spaces.
     *
     * @param errors a map from line numbers to their errors.
     * @param fileContents a map from line numbers to their code.
     * @param indentation the number of spaces constituting an indentation.
     */
    public static void check(Map<Integer, Set<String>> errors, Map<Integer, String> fileContents,
                             int indentation) {
        int braces = 0;
        boolean casePresent = false;
        boolean defaultPresent = false;
        for (Integer lineNumber : fileContents.keySet()) {
            String line = fileContents.get(lineNumber);
            if (!line.equals("") && line.trim().charAt(0) != '*') {
                if (line.contains("}")) {
                    if (defaultPresent) {
                        braces--;
                        defaultPresent = false;
                    }
                    braces--;
                }
                if (line.contains(" default:")) {
                    braces--;
                    casePresent = false;
                    defaultPresent = true;
                }
                if (line.contains(" case ")) {
                    if (casePresent) {
                        braces--;
                    }
                    casePresent = true;
                }
                for (int i = 0; i < indentation * braces; i++) {
                    if (line.charAt(i) != ' ') {
                        StyleCheck.addError(errors, lineNumber,
                                "Incorrect indentation.");
                        break;
                    }
                }
                if (line.length() > 1 + indentation * braces &&
                    line.charAt(indentation * braces) == ' ' &&
                    line.charAt(1 + indentation * braces) != '*') {
                    StyleCheck.addError(errors, lineNumber, "Incorrect indentation.");
                }
                if (line.contains(" default:") || line.contains("{") || line.contains(" case ")) {
                    braces++;
                }
            }
        }
    }
}