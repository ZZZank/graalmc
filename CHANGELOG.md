## GraalMC 25.0.2.1 -> 25.0.2.2

Using GraalJS 25.0.2

- MemberRemapper now allows `null` return value, which indicates that such member should be hidden and not accessible in JS
- fields with shadowing can now be remapped more completely
