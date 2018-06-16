package riftomer.bcex.pipe.behaviour;

import buildcraft.api.core.EnumPipePart;
import buildcraft.api.transport.pipe.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import riftomer.bcex.init.ModGuis;

import java.io.IOException;

public class PipeBehaviourDrop extends PipeBehaviour {
    public PipeBehaviourDrop(IPipe pipe) {
        super(pipe);
    }

    public PipeBehaviourDrop(IPipe pipe, NBTTagCompound nbt) {
        super(pipe, nbt);
    }

//    @SideOnly(Side.CLIENT)
//    private boolean isOn;

    @PipeEventHandler
    public void onCheckSides(PipeEventItem.SideCheck sideCheck) {
        if (!pipe.getHolder().getPipeWorld().isRemote) {
            sideCheck.disallowAll();
        }
    }

    @Override
    public void writePayload(PacketBuffer buffer, Side side) {
        super.writePayload(buffer, side);
    }

    @Override
    public void readPayload(PacketBuffer buffer, Side side, MessageContext ctx) throws IOException {
        super.readPayload(buffer, side, ctx);
    }

    @Override
    public PipeFaceTex getTextureData(EnumFacing face) {
        return PipeFaceTex.get(getFacing() == null ? 1 : 0);
    }

    @Override
    public boolean onPipeActivate(EntityPlayer player, RayTraceResult trace, float hitX, float hitY, float hitZ, EnumPipePart part) {
        if (!player.world.isRemote) {
            if (player.getGameProfile().equals(pipe.getHolder().getOwner())) {
                ModGuis.DROP_PIPE.openGui(player, pipe.getHolder().getPipePos());
            }
        }
        return true;
    }

    @PipeEventHandler
    public void onDropItem(PipeEventItem.Drop drop) {
        if (!pipe.getHolder().getPipeWorld().isRemote) {
            BlockPos toDrop = this.pipe.getHolder().getPipePos();
            toDrop = toDrop.offset(getFacing() == null ? EnumFacing.DOWN : getFacing());
            drop.getEntity().setPosition(toDrop.getX() + 0.5d, toDrop.getY() + 0.5d, toDrop.getZ() + 0.5d);
            drop.getEntity().setVelocity(0d, 0d, 0d);
        }
    }

    public EnumFacing getFacing() {
        EnumFacing result = null;
        for (EnumFacing facing : EnumFacing.values()) {
            if (pipe.isConnected(facing)) {
                if (result != null) {
                    return null;
                }
                result = facing;
            }
        }
        return result == null ? null : result.getOpposite();
    }
}
