package riftomer.bcex.pipe.behaviour;

import buildcraft.api.transport.pipe.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import riftomer.bcex.BCExtensions;

import java.io.IOException;

public class PipeBehaviourBouncy extends PipeBehaviour {
    @SideOnly(Side.CLIENT)
    public boolean isOn;

    public PipeBehaviourBouncy(IPipe pipe) {
        super(pipe);
    }

    public PipeBehaviourBouncy(IPipe pipe, NBTTagCompound nbt) {
        super(pipe, nbt);
    }

    @PipeEventHandler
    public static void onValidate(PipeEventTileState.Validate event) {
        BCExtensions.logger.info("Validated!");
    }

    @PipeEventHandler
    public static void onInvalidate(PipeEventTileState.Invalidate event) {
        BCExtensions.logger.info("Invalidated!");
    }

    @PipeEventHandler
    public static void onCU(PipeEventTileState.ChunkUnload event) {
        BCExtensions.logger.info("Chunk unloaded!");
    }

    @PipeEventHandler
    public void onCheckSides(PipeEventItem.SideCheck sideCheck) {
        if (!pipe.getHolder().getPipeWorld().isRemote) {
            for (EnumFacing facing : EnumFacing.values()) {
                if (pipe.getHolder().getRedstoneInput(facing) > 0) {
                    return;
                }
            }
            sideCheck.disallowAll();
        }
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
        if (side.isServer())
            buffer.writeBoolean(flag);
    }

    @Override
    public void readPayload(PacketBuffer buffer, Side side, MessageContext ctx) throws IOException {
        super.readPayload(buffer, side, ctx);
        if (side.isClient())
            isOn = buffer.readBoolean();
    }

    @Override
    public PipeFaceTex getTextureData(EnumFacing face) {
        return PipeFaceTex.get(isOn ? 1 : 0);
    }

    @PipeEventHandler
    public void onTryBounce(PipeEventItem.TryBounce tryBounce) {
        if (!pipe.getHolder().getPipeWorld().isRemote) {
            for (EnumFacing facing : EnumFacing.values()) {
                if (pipe.getHolder().getRedstoneInput(facing) > 0) {
                    return;
                }
            }
            tryBounce.canBounce = true;
        }
    }


}
