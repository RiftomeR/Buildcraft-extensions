package riftomer.bcex.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import riftomer.bcex.gui.GuiPipeAdvIron;
import riftomer.bcex.gui.GuiPipeDrop;
import riftomer.bcex.gui.container.ContainerAdvIronPipe;
import riftomer.bcex.gui.container.ContainerDropPipe;
import riftomer.bcex.init.ModGuis;

import javax.annotation.Nullable;

public class ClientProxy extends Proxy {

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ModGuis gui = ModGuis.values()[ID];
        if (gui.equals(ModGuis.DROP_PIPE)) {
            return new GuiPipeDrop((ContainerDropPipe) getServerGuiElement(ID, player, world, x, y, z));
        } else if (gui.equals(ModGuis.ADV_IRON_PIPE)) {
            return new GuiPipeAdvIron((ContainerAdvIronPipe) getServerGuiElement(ID, player, world, x, y, z));
        }
        return null;
    }
}
