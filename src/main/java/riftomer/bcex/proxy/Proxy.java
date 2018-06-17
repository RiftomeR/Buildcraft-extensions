package riftomer.bcex.proxy;

import buildcraft.api.transport.pipe.IPipeHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.IGuiHandler;
import riftomer.bcex.BCExtensions;
import riftomer.bcex.gui.container.*;
import riftomer.bcex.init.ModGuis;

import javax.annotation.Nullable;

public class Proxy implements IGuiHandler {
    @SidedProxy(modId = BCExtensions.MODID, clientSide = "riftomer.bcex.proxy.ClientProxy", serverSide = "riftomer.bcex.proxy.ServerProxy")
    private static Proxy PROXY;

    public static Proxy getProxy() {
        return PROXY;
    }


    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ModGuis gui = ModGuis.values()[ID];
        if (gui.equals(ModGuis.ADV_IRON_PIPE)) {
            return new ContainerAdvIronPipe(player, (IPipeHolder) world.getTileEntity(new BlockPos(x, y, z)));
        } else if (gui.equals(ModGuis.DISTRIBUTION_PIPE)) {
            return new ContainerDistributionPipe(player, (IPipeHolder) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }


}
