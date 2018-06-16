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


public class PipeBehaviourAdvancedIron extends PipeBehaviour {
    public byte[] faces;

    public PipeBehaviourAdvancedIron(IPipe pipe) {
        super(pipe);
        faces = new byte[6];
        for (EnumFacing facing : EnumFacing.values()) {
            faces[facing.ordinal()] = (byte) facing.getOpposite().ordinal();
        }
    }

    public PipeBehaviourAdvancedIron(IPipe pipe, NBTTagCompound nbt) {
        super(pipe, nbt);
        readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNbt() {
        NBTTagCompound nbt = super.writeToNbt();
        nbt.setByteArray("faces", faces);
        return nbt;
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
            try {
                readFromNBT(buffer.readCompoundTag());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if (!player.world.isRemote) {
            ModGuis.ADV_IRON_PIPE.openGui(player, pipe.getHolder().getPipePos());
        }
        return true;
    }

    private void readFromNBT(NBTTagCompound nbt) {
        faces = nbt.getByteArray("faces");

    }

    @PipeEventHandler
    public void onSideCheck(PipeEventItem.SideCheck event) {
        event.disallowAllExcept(EnumFacing.values()[faces[event.from.ordinal()]]);
    }
}
