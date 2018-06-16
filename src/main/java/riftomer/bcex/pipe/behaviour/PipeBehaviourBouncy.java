package riftomer.bcex.pipe.behaviour;

import buildcraft.api.transport.pipe.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

public class PipeBehaviourBouncy extends PipeBehaviour {
    @SideOnly(Side.CLIENT)
    public boolean isOn = true;

    public PipeBehaviourBouncy(IPipe pipe) {
        super(pipe);
    }

    public PipeBehaviourBouncy(IPipe pipe, NBTTagCompound nbt) {
        super(pipe, nbt);
    }

    @PipeEventHandler
    public void onCheckSides(PipeEventItem.SideCheck sideCheck) {
        for (EnumFacing facing : EnumFacing.values()) {
            if (pipe.getHolder().getRedstoneInput(facing) > 0) {
                return;
            }
        }
        sideCheck.disallowAll();
    }


    @Override
    public void writePayload(PacketBuffer buffer, Side side) {
        super.writePayload(buffer, side);
        boolean flag = true;
        for (EnumFacing facing : EnumFacing.values()) {
            if (pipe.getHolder().getRedstoneInput(facing) > 0) {
                flag = false;
                break;
            }
        }
        buffer.writeBoolean(flag);
    }

    @Override
    public void readPayload(PacketBuffer buffer, Side side, MessageContext ctx) throws IOException {
        super.readPayload(buffer, side, ctx);
        isOn = buffer.readBoolean();
    }

    @Override
    public PipeFaceTex getTextureData(EnumFacing face) {
        return PipeFaceTex.get(isOn ? 1 : 0);
    }

    @PipeEventHandler
    public void onTryBounce(PipeEventItem.TryBounce tryBounce) {
        tryBounce.canBounce = true;
    }
}
