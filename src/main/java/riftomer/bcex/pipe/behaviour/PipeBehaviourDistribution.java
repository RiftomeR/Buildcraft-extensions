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
import riftomer.bcex.pipe.util.EnumDiamondColor;

import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;

public class PipeBehaviourDistribution extends PipeBehaviour {
    public EnumMap<EnumDiamondColor, Integer> faces = new EnumMap<>(EnumDiamondColor.class);
    public EnumDiamondColor currentFace = EnumDiamondColor.BLACK;
    public int currentCount = 0;
    public PipeBehaviourDistribution(IPipe pipe) {
        super(pipe);
        for (EnumDiamondColor color : EnumDiamondColor.values()) {
            faces.put(color, 1);
        }
    }

    public PipeBehaviourDistribution(IPipe pipe, NBTTagCompound nbt) {
        super(pipe, nbt);
        readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNbt() {
        NBTTagCompound nbt = super.writeToNbt();
        int[] array = new int[6];
        for (EnumDiamondColor color : EnumDiamondColor.values()) {
            array[color.ordinal()] = faces.get(color);
        }
        nbt.setIntArray("faces", array);
        nbt.setInteger("currentF", currentFace.ordinal());
        nbt.setInteger("currentC", currentCount);
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
        if (!pipe.getHolder().getPipeWorld().isRemote) {
            ModGuis.DISTRIBUTION_PIPE.openGui(player, pipe.getHolder().getPipePos());
        }
        return true;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        int[] array = nbt.getIntArray("faces");
        for (EnumDiamondColor color : EnumDiamondColor.values()) {
            faces.put(color, array[color.ordinal()]);
        }
        currentFace = EnumDiamondColor.values()[nbt.getInteger("currentF")];
        currentCount = nbt.getInteger("currentC");
    }

    @PipeEventHandler
    public void onFindDest(PipeEventItem.FindDest event) {
        if (!pipe.getHolder().getPipeWorld().isRemote) {
            int i = 0;
            for (PipeEventItem.ItemEntry item : event.items) {
                getDest(item, event.orderedDestinations.get(i));
                i++;
            }
        }
    }

    private void getDest(PipeEventItem.ItemEntry item, EnumSet<EnumFacing> destinations) {
        EnumSet<EnumFacing> prevSet = destinations.clone();
        destinations.clear();
        while (currentFace.facing.equals(item.from) || !prevSet.contains(currentFace.facing)) {
            next();
        }
        destinations.add(currentFace.facing);
        next();
    }

    private void next() {
        if (currentCount < faces.get(currentFace)) {
            currentCount++;
        } else {
            currentCount = 1;
            currentFace = currentFace.next();
            while (faces.get(currentFace) <= 0 || (!pipe.isConnected(currentFace.facing))) {
                currentFace = currentFace.next();
            }
        }
    }

    @PipeEventHandler
    public void onSideCheck(PipeEventItem.SideCheck event) {
        for (EnumDiamondColor color : EnumDiamondColor.values()) {
            if (faces.get(color) <= 0) {
                event.disallow(color.facing);
            }
        }
    }
}
