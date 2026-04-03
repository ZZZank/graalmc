package zank.mods.graalmc.forge;

import net.minecraftforge.fml.common.Mod;

import zank.mods.graalmc.GraalMC;

@Mod(GraalMC.MOD_ID)
public final class GraalMCForge {
    public GraalMCForge() {
        // Run our common setup.
        GraalMC.init();
    }
}
