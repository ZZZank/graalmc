## GraalMC 25.0.1.1 -> 25.0.2.0

Using GraalJS 25.0.2

- Fix crash when running on GraalVM JVM
- Some classes are now moved to `graal.` package
    - `org.graalvm` -> `graal.graalvm`
    - `zank.mods.graalmc` -> `graal.mod`
    - `com.oracle.svm` -> `graal.svm`
    - However, other `com.oracle` classes are not moved
- Some enterprise-only feature removed