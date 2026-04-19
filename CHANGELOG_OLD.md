## GraalMC 25.0.2.0 -> 25.0.2.1

Using GraalJS 25.0.2

- fix: MemberRemapper not working

## GraalMC 25.0.1.1 -> 25.0.2.0

Using GraalJS 25.0.2

- Fix crash when running on GraalVM JVM
- Some classes are now moved to `graal.` package
    - `org.graalvm` -> `graal.graalvm`
    - `zank.mods.graalmc` -> `graal.mod`
    - `com.oracle.svm` -> `graal.svm`
    - However, other `com.oracle` classes are not moved
- Some enterprise-only feature removed

## GraalMC 25.0.1 -> 25.0.1.1

Using GraalJS 25.0.1

- Fix mod version in mod metadata file

## GraalMC 1.0.0

Using GraalJS 25.0.1

- Full GraalJS feature
- Duplicated libraries trimmed, making the total mod size ~50% smaller
- MemberRemapper