package riftomer.bcex.gui;

import buildcraft.lib.gui.GuiBC8;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import riftomer.bcex.gui.container.ContainerDropPipe;
import riftomer.bcex.pipe.behaviour.PipeBehaviourDrop;

public class GuiPipeDrop extends GuiBC8<ContainerDropPipe> {
    private PipeBehaviourDrop pipe;

    public GuiPipeDrop(ContainerDropPipe container) {
        super(container);
        pipe = (PipeBehaviourDrop) container.pipeHolder.getPipe().getBehaviour();
    }

    @Override
    protected void drawForegroundLayer() {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        drawCenteredString(fontRenderer, pipe.getFacing() == null ? "error" : pipe.getFacing().getName(), resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2, pipe.getFacing() == null ? 0xFF0000 : 0x00FF00);
    }
}
