package zank.mods.graalmc.forge;

import net.neoforged.fml.common.Mod;
import zank.mods.graalmc.GraalMC;

@Mod(GraalMC.MOD_ID)
public final class GraalMCNeoForge {
    public GraalMCNeoForge() {
        // Run our common setup.
        GraalMC.init();
    }
}
