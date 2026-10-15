package impl;

public abstract class Patch {

    /// [com.oracle.truffle.polyglot.EngineAccessor#isValidLoader(java.lang.ClassLoader)] will probe these classloaders:
    /// 1. Thread.currentThread().getContextClassLoader()
    /// 2. ClassLoader.getSystemClassLoader()
    /// 3. EngineAccessor.class.getClassLoader()
    ///
    /// and it seems, in test environment, context classloader defaults to jdk.internal.loader.ClassLoaders$AppClassLoader
    ///
    /// This results in two parallel classloaders at runtime, `Knot` (from Fabric) and AppClassLoader, causing Graal to
    /// complain:
    ///
    /// ```
    /// [engine] WARNING: The TruffleAttach library at 'example-path\truffleattach.dll' could not be loaded: Native Library example-path\truffleattach.dll already loaded in another classloader. A common cause is that Truffle JARs are loaded by multiple class loaders. Load polyglot languages and their dependencies from a common parent class loader, such as the application class path or module path.
    /// As a result, the optimized Truffle runtime is unavailable, and Truffle cannot provide native access to languages and tools.
    /// ```
    ///
    /// Here we set context classloader to make `Knot` handle all classloading
    public static void setContextClassLoader() {
        Thread.currentThread().setContextClassLoader(Patch.class.getClassLoader());
    }
}
