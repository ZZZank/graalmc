## Graal 25.1.3.7 -> 25.5.0

Using GraalJS 25.5.5.0.3

- Updated GraalJS to 25.5
- Further improve handling for old GraalVM. Graal had already patched version detection to give a better error message, and now it gets even better: instead of crashing, the game can continue running using fallback engine, with error messages print to log
- ICU4J is now bundled in Graal again, to fix crash on dedicated server

## Graal 25.1.3.6 -> 25.1.3.7

Using GraalJS 25.1.3

- Fixed: Regex not usable
- New: `graal.dev.on_init_script` env variable key, for running simple test code without complex operation. Scripts will be evaluated in a constrained, pure-JS environment, with no access to Java classes, IO, etc.
