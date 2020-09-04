package stylecheck.stylecheckers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import stylecheck.StyleCheck;

/**
 * Checks that braces follow the Kernighan and Ritchie style for nonempty blocks/block-like
 * constructs.
 */
public final class NonemptyBlocksChecker {
    /**
     * Checks that braces follow the Kernighan and Ritchie style for nonempty blocks/block-like
     * constructs. In particular, there is a line break after the opening brace, before the
     * closing brace, and after the closing brace (only if the closing brace terminates a
     * statement or the body of a named class or method).
     *
     * @param errors a map from line numbers to their errors.
     * @param fileContents a map from line numbers to their code.
     */
    public static void check(Map<Integer, Set<String>> errors, Map<Integer, String> fileContents) {
        Set<String> keywords = new HashSet<>();
        keywords.add("catch");
        keywords.add("class");
        keywords.add("do");
        keywords.add("finally");
        keywords.add("for");
        keywords.add("if");
        keywords.add("switch");
        keywords.add("try");
        keywords.add("while");
        Set<String> noLineBreakKeywords = new HashSet<>();
        noLineBreakKeywords.add("else");
        noLineBreakKeywords.add("while");
        noLineBreakKeywords.add("catch");
        noLineBreakKeywords.add("finally");
        int braces = 0;
        for (Integer lineNumber : fileContents.keySet()) {
            String[] line = fileContents.get(lineNumber).trim().split(" ");
            String uncheckedLine = fileContents.get(lineNumber).trim();
            if (uncheckedLine.contains("{")) {
                braces++;
            }
            if (uncheckedLine.contains("}")) {
                braces--;
            }
            String checkedLine = uncheckedLine;
            if (uncheckedLine.contains("//")) { // Account for internal comments.
                checkedLine = uncheckedLine.substring(0, uncheckedLine.indexOf("//"));
            }
            for (String s : line) {
                if (checkedLine.length() > 1 && checkedLine.charAt(0) == '*' || s.equals("while") &&
                        !line[0].equals("while")) {
                    break;
                } else if ((keywords.contains(s) || braces == 2 && checkedLine.contains("(")) &&
                        !checkedLine.contains("{}") && checkedLine.contains("{") &&
                        checkedLine.charAt(checkedLine.length() - 1) != '{') {
                    StyleCheck.addError(errors, lineNumber,
                            "Missing line break after the opening brace for this " +
                                    "nonempty block.");
                } else if (line[0].equals("{")) {
                    StyleCheck.addError(errors, lineNumber,
                             "Line breaks are disallowed before the opening brace.");
                } else if (!keywords.contains(s) && !checkedLine.contains("{}") &&
                            checkedLine.length() > 1 && checkedLine.lastIndexOf('}') ==
                            checkedLine.length() - 1) {
                    StyleCheck.addError(errors, lineNumber,
                             "Missing line break before the closing brace.");
                } else if (line.length > 1 && !noLineBreakKeywords.contains(line[1]) &&
                        checkedLine.contains("}") && checkedLine.indexOf("}") !=
                        checkedLine.length() - 1) {
                    StyleCheck.addError(errors, lineNumber, "Missing line break after" +
                            " the closing brace (only if the closing brace terminates a statement" +
                            " or the body of a named class or method).");
                    break;
                }
            }
        }
    }
}