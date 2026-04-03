package zank.mods.graalmc.forge;

import net.minecraftforge.fml.common.Mod;

import zank.mods.graalmc.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModForge {
    public ExampleModForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
