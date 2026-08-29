
## GraalMC 25.1.3.4 -> 25.1.3.5

Using GraalJS 25.1.3

- A new class parameter is added to `MemberRemapper` methods, allowing implementations to receive owner class of the member, aka the class where the specified member is found.
- Breaking change: `MemberRemapper` API has been changed.

## GraalMC 25.0.2.3 -> 25.1.3.4

Using GraalJS 25.1.3

- New: `TypeMappingProvider` and `TypeMappingProviderRegistry`, allowing you to dynamically provide type mapping based on target type. For example `string -> enum` type mapping for every enum class without having to register for all enum classes manually.
- New: `MemberRemapper.GLOBAL` replaced by `MemberRemapper.CHAIN`, allowing multiple mods to register their own remappers without conflicts.

## GraalMC 25.0.2.2 -> 25.0.2.3

Using GraalJS 25.0.2

- Fix: crash when running on Java 17

## GraalMC 25.0.2.1 -> 25.0.2.2

Using GraalJS 25.0.2

- MemberRemapper now allows `null` return value, which indicates that such member should be hidden and not accessible in JS
- fields with shadowing can now be remapped more completely

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