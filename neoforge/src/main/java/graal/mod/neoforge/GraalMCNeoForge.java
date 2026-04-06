package graal.mod.neoforge;

import net.neoforged.fml.common.Mod;
import graal.mod.GraalMC;

@Mod(GraalMC.MOD_ID)
public final class GraalMCNeoForge {
    public GraalMCNeoForge() {
        // Run our common setup.
        GraalMC.init();
    }
}
