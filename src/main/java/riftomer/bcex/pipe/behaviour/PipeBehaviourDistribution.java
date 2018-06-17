package riftomer.bcex.pipe.behaviour;

import buildcraft.api.core.EnumPipePart;
import buildcraft.api.transport.pipe.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import riftomer.bcex.init.ModGuis;

import java.io.IOException;

public class PipeBehaviourDistribution extends PipeBehaviour {
    public PipeBehaviourDistribution(IPipe pipe) {
        super(pipe);
    }

    public PipeBehaviourDistribution(IPipe pipe, NBTTagCompound nbt) {
        super(pipe, nbt);
        readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNbt() {
        return super.writeToNbt();
    }

    @Override
    public void writePayload(PacketBuffer buffer, Side side) {
        super.writePayload(buffer, side);
        if (side.isServer()) {
            buffer.writeCompoundTag(writeToNbt());
        }
    }

    @Override
    public void readPayload(PacketBuffer buffer, Side side, MessageContext ctx) throws IOException {
        super.readPayload(buffer, side, ctx);
        if (side.isClient()) {
            readFromNBT(buffer.readCompoundTag());
        }
    }

    @Override
    public PipeFaceTex getTextureData(EnumFacing face) {
        if (pipe.isConnected(face)) {
            return PipeFaceTex.get(face.ordinal() + 1);
        } else {
            return PipeFaceTex.get(0);
        }
    }

    @Override
    public boolean onPipeActivate(EntityPlayer player, RayTraceResult trace, float hitX, float hitY, float hitZ, EnumPipePart part) {
        ModGuis.DISTRIBUTION_PIPE.openGui(player, pipe.getHolder().getPipePos());
        return true;
    }

    public void readFromNBT(NBTTagCompound nbt) {

    }
}
