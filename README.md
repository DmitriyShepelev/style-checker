# Style Checker

# Instructions
`make` or `make all`: Compiles and runs the Style Checker and creates a `stylechecker.jar` file.

`make jar`: Compiles the Style Checker and creates a `stylechecker.jar` file.

`make run`: Runs the Style Checker.

`make test`: Compiles and runs tests.

`make clean`: Recursively removes the `build` directory.
# About
The Style Checker encompasses six checkers that check Java classes for style:

* **Line Length Checker**: Checks that no line exceeds a user-specified length.

* **Import Checker**: Checks that
  * Wildcard imports are not used.
  * Within each block (static and non-static), imported names appear in ASCII sort order.
  * Static imports appear in a single block, and a single blank line separates static imports
   from non-static imports.

* **Indentation Checker**: Checks that each line is indented by a user-specified number of spaces
. (**Note**: Lines that are wrapped are ignored.)

* **Nonempty Blocks Checker**: Checks that braces follow the 
[Kernighan and Ritchie style](https://en.wikipedia.org/wiki/Indentation_style#K&R_style) for nonempty
 blocks/block-like constructs. In particular, there is a line break after the opening brace, before the closing brace, and after the closing brace (only if the closing brace terminates a
  statement, body of a named class, or method).
  
* **Optional Braces Checker**: Checks that braces are used with `do`, `while`, `else`,`for`, and `if
` statements, even if the body contains only a single statement or is empty.

* **Package Checker**: Checks that package names are lowercase and do not contain underscores.

The Style Checker was largely inspired by the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).