## Graal 25.1.3.6 -> 25.1.3.7

Using GraalJS 25.1.3

- Fixed: Regex not usable
- New: `graal.dev.on_init_script` env variable key, for running simple test code without complex operation. Scripts will be evaluated in a constrained, pure-JS environment, with no access to Java classes, IO, etc.

## GraalMC 25.1.3.5 -> 25.1.3.6

Using GraalJS 25.1.3

If you are using GraalVM, update **your GraalVM JDK** to 25.1 or newer..

- Add proper error message when running Graal on GraalVM 25.0
