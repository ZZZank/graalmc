package graal.mod.forge;

import net.minecraftforge.fml.common.Mod;

import graal.mod.GraalMC;

@Mod(GraalMC.MOD_ID)
public final class GraalMCForge {
    public GraalMCForge() {
        // Run our common setup.
        GraalMC.init();
    }
}
