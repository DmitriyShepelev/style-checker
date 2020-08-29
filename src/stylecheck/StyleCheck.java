package stylecheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import stylecheck.stylecheckers.ImportChecker;
import stylecheck.stylecheckers.IndentationChecker;
import stylecheck.stylecheckers.LineLengthChecker;
import stylecheck.stylecheckers.NonemptyBlocksChecker;
import stylecheck.stylecheckers.OptionalBracesChecker;
import stylecheck.stylecheckers.PackageChecker;

/**
 * Stylistically checks Java classes.
 */
public final class StyleCheck {
    private static final int NUM_CHECKERS = 6;

    public static int indentation;

    public static int lineLength;

    /**
     * Introduces user to application.
     *
     * @return a File representing the file to check.
     */
    private static File introduction() {
        File f;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Welcome to the style checker!");
            System.out.print("To begin, please type the java file you'd like to check: ");
            String file = scanner.nextLine();
            f = new File(file);
        } while (!validateFile(f));
        return f;
    }

    /**
     * Checks if the user's file is an actual file.
     *
     * @param file the file to check for actuality.
     * @return true iff the file is an actual file.
     */
    private static boolean validateFile(File file) {
        if (!file.isFile()) {
            System.err.println(file + " is not a file.");
            System.out.println();
            return false;
        }
        return true;
    }

    /**
     * Prompts user to select checkers.
     *
     * @return boolean[] representing the selected checkers.
     */
    private static boolean[] selectCheckers() {
        Scanner scanner = new Scanner(System.in);
        boolean[] selectedCheckers = new boolean[NUM_CHECKERS];
        char response;
        do {
            do {
                System.out.println();
                System.out.println("Which checkers would you like to run? Please select one " +
                                           "checker at a time by inputting the corresponding " +
                                           "number:");
                System.out.println();
                System.out.println("0 : Line Length Checker: Checks that no line exceeds a " +
                                           "user-specified length.");
                System.out.println();
                System.out.println("1 : Import Checker: Checks that:");
                System.out.println("    • Wildcard imports are not used.");
                System.out.println("    • Within each block (static and non-static), imported " +
                                           "names appear in ASCII sort order.");
                System.out.println("    • Static imports appear in a single block, and a single " +
                                           "blank line separates static imports from non-static " +
                                           "imports.");
                System.out.println();
                System.out.println("2 : Indentation Checker: Checks that each line is indented by" +
                                           " a user-specified number of spaces.");
                System.out.println();
                System.out.println("3 : Nonempty Blocks Checker: Checks that braces follow the " +
                                           "Kernighan and Ritchie style for nonempty " +
                                           "blocks/block-like constructs.");
                System.out.println("    In particular, there is a line break after the opening " +
                                           "brace, before the closing brace, and after the " +
                                           "closing brace (only if the closing brace terminates a" +
                                           " statement or the body of a named class or method).");
                System.out.println();
                System.out.println("4 : Optional Braces Checker: Checks that braces are used " +
                                           "with do, while, else, for, and if statements, even if" +
                                           " the body contains only a single statement or is " +
                                           "empty.");
                System.out.println("5 : Package Checker: Checks that package names are lowercase " +
                                           "and do not contain underscores.");
                response = scanner.nextLine().toLowerCase().charAt(0);
            } while (!validateSelection(response));
            selectedCheckers[Character.getNumericValue(response)] = true;
            if (Character.getNumericValue(response) == 0) {
                String userInput;
                do {
                    System.out.println("What would you like the length of each line to be? " +
                                               "(please enter non-negative numeric input)");
                    userInput = scanner.nextLine();
                } while (!validateUserInput(userInput));
                lineLength = Integer.parseInt(userInput);
            } else if (Character.getNumericValue(response) == 2) {
                String userInput;
                do {
                    System.out.println("By how many spaces should each indent increase? (Please" +
                                               " enter non-negative numeric input)");
                    userInput = scanner.nextLine();
                } while (!validateUserInput(userInput));
                indentation = Integer.parseInt(userInput);
            }
            System.out.println("Would you like to select an additional checker? (y/n)");
            response = scanner.nextLine().toLowerCase().charAt(0);
        } while (response == 'y');
        return selectedCheckers;
    }

    /**
     * Validates user's input.
     *
     * @param userInput the user's input.
     * @return true iff the user's input is numeric and non-negative.
     */
    private static boolean validateUserInput(String userInput) {
        if (!userInput.matches("^\\d+$")) {
            System.err.println("Your input must be numeric and non-negative.");
            return false;
        }
        return true;
    }

    /**
     * Validates user's checker selection.
     *
     * @param selection the user's checker selection.
     * @return true iff the user's checker selection is a number between 0 and 5 (inclusive).
     */
    private static boolean validateSelection(char selection) {
        if (!Character.isDigit(selection) || Character.getNumericValue(selection) > 5 ||
                Character.getNumericValue(selection) < 0) {
            System.err.println("Please enter a number between 0 and 5 (inclusive).");
            return false;
        }
        return true;
    }

    /**
     * Parses a file.
     *
     * @param file the file to parse.
     * @return a Map&lt;Integer, String&gt; mapping line numbers to their code, if the file can
     * be successfully opened; otherwise, return null.
     */
    public static Map<Integer, String> parse(File file) {
        Map<Integer, String> fileContents = new TreeMap<>();
        try (Scanner input = new Scanner(file)) {
            int lineNumber = 1;
            while (input.hasNextLine()) {
                fileContents.put(lineNumber, input.nextLine());
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return fileContents;
    }

    /**
     * Adds an error.
     *
     * @param errors a map from line numbers to their errors.
     * @param lineNumber the error's line number.
     * @param errorMessage the error message.
     */
    public static void addError(Map<Integer, Set<String>> errors, int lineNumber,
                                String errorMessage) {
        if (!errors.containsKey(lineNumber)) {
            errors.put(lineNumber, new HashSet<>());
        }
        errors.get(lineNumber).add(errorMessage);
    }

    /**
     * Runs the selected checkers.
     *
     * @param fileContents a map from line numbers to their code.
     * @param selectedCheckers the selected checkers.
     * @return a Map&lt;Integer, Set&lt;String&gt;&gt; mapping line numbers to their errors.
     */
    public static Map<Integer, Set<String>> runCheckers(Map<Integer, String> fileContents,
                                                         boolean[] selectedCheckers) {
        Map<Integer, Set<String>> errors = new TreeMap<>();
        for (int i = 0; i < selectedCheckers.length; i++) {
            if (selectedCheckers[i]) {
                switch (i) {
                    case 0:
                        LineLengthChecker.check(errors, fileContents, lineLength);
                        break;
                    case 1:
                        ImportChecker.check(errors, fileContents);
                        break;
                    case 2:
                        IndentationChecker.check(errors, fileContents, indentation);
                        break;
                    case 3:
                        NonemptyBlocksChecker.check(errors, fileContents);
                        break;
                    case 4:
                        OptionalBracesChecker.check(errors, fileContents);
                        break;
                    case 5:
                        PackageChecker.check(errors, fileContents);
                        break;
                    default:
                        break;
                }
            }
        }
        return errors;
    }

    /**
     * Displays errors and the line numbers on which they occur to the user.
     *
     * @param errors a map from line numbers to their errors.
     */
    private static void displayErrors(Map<Integer, Set<String>> errors) {
        if (errors.isEmpty()) {
            System.out.println("No errors were found.");
        } else {
            System.out.println("The following errors were found:");
            for (Integer lineNumber : errors.keySet()) {
                for (String s : errors.get(lineNumber)) {
                    System.err.println(lineNumber + " : " + s);
                }
            }
        }
    }

    /**
     * Prompts user to rerun the Style Checker.
     *
     * @return true iff the user elects to rerun the Style Checker.
     */
    private static boolean rerunCheckers() {
        System.out.print("Would you like to check another file? (y/n) ");
        Scanner input = new Scanner(System.in);
        return input.nextLine().toLowerCase().charAt(0) == 'y';
    }

    /**
     * Runs the command line application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        do {
            Map<Integer, String> fileContents;
            do {
                fileContents = parse(introduction());
            } while (fileContents == null);
            displayErrors(runCheckers(fileContents, selectCheckers()));
        } while (rerunCheckers());
    }
}