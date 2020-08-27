package stylecheck.stylecheckers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import stylecheck.StyleCheck;

/**
 * Checks that braces are used with do, while, else, String for, and if statements, even if the
 * body
 * contains only a single statement or is empty.
 */
public final class OptionalBracesChecker {
    /**
     * Checks that braces are used with do, while, else, for, and if statements, even if the body
     * contains only a single statement or is empty.
     *
     * @param errors a map from line numbers to their errors.
     * @param fileContents a map from line numbers to their code.
     */
    public static void check(Map<Integer, Set<String>> errors, Map<Integer, String> fileContents) {
        Set<String> optionalBracesStatements = new HashSet<>();
        // Add relevant keywords.
        optionalBracesStatements.add("if");
        optionalBracesStatements.add("else");
        optionalBracesStatements.add("for");
        optionalBracesStatements.add("do");
        optionalBracesStatements.add("while");
        for (Integer lineNumber : fileContents.keySet()) {
            String entireLine = fileContents.get(lineNumber).trim();
            if (entireLine.contains("//")) { // Account for internal comments.
                entireLine = entireLine.substring(0, entireLine.indexOf("//"));
            }
            String[] line = entireLine.split(" ");
            for (String s : line) {
                if (optionalBracesStatements.contains(s) && (entireLine.contains("*") &&
                    entireLine.indexOf("*") < entireLine.indexOf(s) || s.equals("while") &&
                    !line[0].equals("while"))) {
                    break;
                }
                if (optionalBracesStatements.contains(s) &&
                        !line[line.length - 1].equals("{")) {
                    StyleCheck.addError(errors, lineNumber, "Braces must be used with" +
                            " if, else, for, do, and while statements, even if the body is empty " +
                            "or contains a single statement.");
                    break;
                }
            }
        }
    }
}