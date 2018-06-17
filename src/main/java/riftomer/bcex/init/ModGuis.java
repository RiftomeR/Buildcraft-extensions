package riftomer.bcex.init;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import riftomer.bcex.BCExtensions;

public enum ModGuis {
    DROP_PIPE, ADV_IRON_PIPE, DISTRIBUTION_PIPE;

    public void openGui(EntityPlayer player, BlockPos pos) {
        player.openGui(BCExtensions.INSTANCE, this.ordinal(), player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ());
    }
}
