package riftomer.bcex.init;

import buildcraft.lib.registry.TagManager;
import buildcraft.transport.BCTransportItems;
import buildcraft.transport.item.ItemPipeHolder;
import riftomer.bcex.BCExtensions;

public class ModItems {
    public static ItemPipeHolder dropPipe;
    public static ItemPipeHolder advIronPipe;
    public static ItemPipeHolder bouncyPipe;
    public static ItemPipeHolder distributionPipe;

    public static void preInit() {
        manageTags();
        dropPipe = BCTransportItems.makePipeItem(ModPipes.drop_item);
        advIronPipe = BCTransportItems.makePipeItem(ModPipes.advancedIron_item);
        bouncyPipe = BCTransportItems.makePipeItem(ModPipes.bouncy_item);
        distributionPipe = BCTransportItems.makePipeItem(ModPipes.distribution_item);
    }

    private static void manageTags() {
        TagManager.startBatch();
        addPipeTag("drop_item", "PipeDrop");
        addPipeTag("advancediron_item", "PipeAdvancedIron");
        addPipeTag("bouncy_item", "PipeBouncy");
        addPipeTag("distribution_item", "PipeDistributionItem");
        TagManager.endBatch(TagManager.setTab("bcex.pipes"));
    }

    private static void addPipeTag(String reg, String locale) {
        TagManager.registerTag("item.pipe." + BCExtensions.MODID + "." + reg).reg(reg).locale(locale);
    }
}
