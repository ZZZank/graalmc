## GraalMC 25.1.3.4 -> 25.1.3.5

Using GraalJS 25.1.3

- A new class parameter is added to `MemberRemapper` methods, allowing implementations to receive owner class of the member, aka the class where the specified member is found.
- Breaking change: `MemberRemapper` API has been changed.

## GraalMC 25.0.2.3 -> 25.1.3.4

Using GraalJS 25.1.3

- New: `TypeMappingProvider` and `TypeMappingProviderRegistry`, allowing you to dynamically provide type mapping based on target type. For example `string -> enum` type mapping for every enum class without having to register for all enum classes manually.
- New: `MemberRemapper.GLOBAL` replaced by `MemberRemapper.CHAIN`, allowing multiple mods to register their own remappers without conflicts.
