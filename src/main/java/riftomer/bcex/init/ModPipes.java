package riftomer.bcex.init;

import buildcraft.api.transport.pipe.PipeApi;
import buildcraft.api.transport.pipe.PipeDefinition;
import net.minecraft.util.EnumFacing;
import riftomer.bcex.pipe.behaviour.*;

public class ModPipes {
    public static PipeDefinition drop_item;
    public static PipeDefinition advancedIron_item;
    public static PipeDefinition bouncy_item;
    public static PipeDefinition distribution_item;

    public static void preInit() {
        PipeDefinition.PipeDefinitionBuilder builder = new PipeDefinition.PipeDefinitionBuilder();
        //Drop
        drop_item = builder.id("drop_item").flow(PipeApi.flowItems).tex("drop", "_on", "_error").logic(PipeBehaviourDrop::new, PipeBehaviourDrop::new).define();

        //Advanced iron
        builder.id("advancediron_item").flow(PipeApi.flowItems).logic(PipeBehaviourAdvancedIron::new, PipeBehaviourAdvancedIron::new).texPrefix("adviron");
        String[] suffixes = new String[7];
        suffixes[0] = "_side";
        for (EnumFacing face : EnumFacing.values()) {
            suffixes[face.ordinal() + 1] = "_" + face.getName();
        }

        builder.texSuffixes(suffixes);
        advancedIron_item = builder.define();

        //Bouncy
        bouncy_item = builder.id("bouncy_item").flow(PipeApi.flowItems).tex("bouncy", "_on", "_off").logic(PipeBehaviourBouncy::new, PipeBehaviourBouncy::new).define();

        //Distribution
        builder.id("distribution_item").flow(PipeApi.flowItems).logic(PipeBehaviourDistribution::new, PipeBehaviourDistribution::new).texPrefix("distrib");
        suffixes = new String[7];
        suffixes[0] = "_side";
        for (EnumFacing face : EnumFacing.values()) {
            suffixes[face.ordinal() + 1] = "_" + face.getName();
        }
        builder.texSuffixes(suffixes);
        distribution_item = builder.define();
    }
}
