SOURCE_PATH = src
CLASS_PATH = build/classes
TEST_CLASS_PATH = build/testClasses
JAR_PATH = build/stylechecker.jar
STYLECHECK_PATH = $(SOURCE_PATH)/stylecheck
STYLECHECKERS_PATH = $(STYLECHECK_PATH)/stylecheckers
STYLECHECK_CLASS_PATH = $(CLASS_PATH)/StyleCheck.class
STYLECHECK_SRC_PATH = $(STYLECHECK_PATH)/StyleCheck.java
LINE_LENGTH_CHECKER_SRC_PATH = $(STYLECHECKERS_PATH)/LineLengthChecker.java
IMPORT_CHECKER_SRC_PATH = $(STYLECHECKERS_PATH)/ImportChecker.java
INDENTATION_CHECKER_SRC_PATH = $(STYLECHECKERS_PATH)/IndentationChecker.java
NONEMPTY_BLOCKS_CHECKER_SRC_PATH = $(STYLECHECKERS_PATH)/NonemptyBlocksChecker.java
OPTIONAL_BRACES_CHECKER_SRC_PATH = $(STYLECHECKERS_PATH)/OptionalBracesChecker.java
PACKAGE_CHECKER_SRC_PATH = $(STYLECHECKERS_PATH)/PackageChecker.java
TESTS_PATH = tests
IMPORT_CHECKER_TEST_SRC_PATH = $(TESTS_PATH)/ImportCheckerTest.java
IMPORT_CHECKER_TEST_CLASS_PATH = $(CLASS_PATH)/ImportCheckerTest.class
LINE_LENGTH_CHECKER_TEST_SRC_PATH = $(TESTS_PATH)/LineLengthCheckerTest.java
LINE_LENGTH_CHECKER_TEST_CLASS_PATH = $(CLASS_PATH)/LineLengthCheckerTest.class
INDENTATION_CHECKER_TEST_SRC_PATH = $(TESTS_PATH)/IndentationCheckerTest.java
INDENTATION_CHECKER_TEST_CLASS_PATH = $(CLASS_PATH)/IndentationCheckerTest.class
NONEMPTY_BLOCKS_CHECKER_TEST_SRC_PATH = $(TESTS_PATH)/NonemptyBlocksCheckerTest.java
NONEMPTY_BLOCKS_CHECKER_TEST_CLASS_PATH = $(CLASS_PATH)/NonemptyBlocksCheckerTest.class
OPTIONAL_BRACES_CHECKER_TEST_SRC_PATH = $(TESTS_PATH)/OptionalBracesCheckerTest.java
OPTIONAL_BRACES_CHECKER_TEST_CLASS_PATH = $(CLASS_PATH)/OptionalBracesCheckerTest.class
PACKAGE_CHECKER_TEST_SRC_PATH = $(TESTS_PATH)/PackageCheckerTest.java
PACKAGE_CHECKER_TEST_CLASS_PATH = $(CLASS_PATH)/PackageCheckerTest.class

all: jar run
.PHONY: all

jar: $(STYLECHECK_CLASS_PATH)
	jar cf $(JAR_PATH) -C $(CLASS_PATH) .
.PHONY: jar

run: jar
	java -cp build/stylechecker.jar:build/lib stylecheck.StyleCheck
.PHONY: run

test: jar $(IMPORT_CHECKER_TEST_CLASS_PATH) $(LINE_LENGTH_CHECKER_TEST_CLASS_PATH) \
$(INDENTATION_CHECKER_TEST_CLASS_PATH) $(NONEMPTY_BLOCKS_CHECKER_TEST_CLASS_PATH) \
$(OPTIONAL_BRACES_CHECKER_TEST_CLASS_PATH) $(PACKAGE_CHECKER_TEST_CLASS_PATH)
	java -cp \
	build/stylechecker.jar:build/classes:build/lib/junit-4.13.jar:build/lib/hamcrest-2.2.jar \
	org.junit.runner.JUnitCore ImportCheckerTest LineLengthCheckerTest IndentationCheckerTest \
	NonemptyBlocksCheckerTest OptionalBracesCheckerTest PackageCheckerTest

$(IMPORT_CHECKER_TEST_CLASS_PATH): $(IMPORT_CHECKER_TEST_SRC_PATH)
	javac -d $(TEST_CLASS_PATH) -cp build/stylechecker.jar:build/lib/junit-4.13.jar $<

$(LINE_LENGTH_CHECKER_TEST_CLASS_PATH): $(LINE_LENGTH_CHECKER_TEST_SRC_PATH)
	javac -d $(TEST_CLASS_PATH) -cp build/stylechecker.jar:build/lib/junit-4.13.jar $<

$(INDENTATION_CHECKER_TEST_CLASS_PATH): $(INDENTATION_CHECKER_TEST_SRC_PATH)
	javac -d $(TEST_CLASS_PATH) -cp build/stylechecker.jar:build/lib/junit-4.13.jar $<

$(NONEMPTY_BLOCKS_CHECKER_TEST_CLASS_PATH): $(NONEMPTY_BLOCKS_CHECKER_TEST_SRC_PATH)
	javac -d $(TEST_CLASS_PATH) -cp build/stylechecker.jar:build/lib/junit-4.13.jar $<

$(OPTIONAL_BRACES_CHECKER_TEST_CLASS_PATH): $(OPTIONAL_BRACES_CHECKER_TEST_SRC_PATH)
	javac -d $(TEST_CLASS_PATH) -cp build/stylechecker.jar:build/lib/junit-4.13.jar $<

$(PACKAGE_CHECKER_TEST_CLASS_PATH): $(PACKAGE_CHECKER_TEST_SRC_PATH)
	javac -d $(TEST_CLASS_PATH) -cp build/stylechecker.jar:build/lib/junit-4.13.jar $<

$(STYLECHECK_CLASS_PATH): $(STYLECHECK_SRC_PATH) $(LINE_LENGTH_CHECKER_SRC_PATH) \
                          $(IMPORT_CHECKER_SRC_PATH) $(INDENTATION_CHECKER_SRC_PATH) \
                          $(NONEMPTY_BLOCKS_CHECKER_SRC_PATH) $(OPTIONAL_BRACES_CHECKER_SRC_PATH) \
                          $(PACKAGE_CHECKER_SRC_PATH)
	javac -d $(CLASS_PATH) $< $(LINE_LENGTH_CHECKER_SRC_PATH) $(IMPORT_CHECKER_SRC_PATH) \
	$(INDENTATION_CHECKER_SRC_PATH) $(NONEMPTY_BLOCKS_CHECKER_SRC_PATH) \
	$(OPTIONAL_BRACES_CHECKER_SRC_PATH) $(PACKAGE_CHECKER_SRC_PATH)

clean:
	rm -rf build
.PHONY: clean