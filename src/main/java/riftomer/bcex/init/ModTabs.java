package riftomer.bcex.init;

import buildcraft.lib.registry.CreativeTabManager;

public class ModTabs {
    public static CreativeTabManager.CreativeTabBC pipes;

    public static void preInit() {
        pipes = CreativeTabManager.createTab("bcex.pipes");
    }

    public static void setTabItems() {
        pipes.setItem(ModItems.dropPipe);

    }
}
