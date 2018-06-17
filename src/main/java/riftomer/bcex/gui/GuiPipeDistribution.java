package riftomer.bcex.gui;

import buildcraft.lib.gui.*;
import buildcraft.lib.gui.button.GuiButtonDrawable;
import buildcraft.lib.gui.button.StandardSpriteButtons;
import buildcraft.lib.gui.elem.GuiElementDrawable;
import buildcraft.lib.gui.elem.GuiElementText;
import buildcraft.lib.gui.pos.*;
import buildcraft.transport.BCTransportSprites;
import net.minecraft.util.ResourceLocation;
import riftomer.bcex.gui.container.ContainerDistributionPipe;

public class GuiPipeDistribution extends GuiBC8<ContainerDistributionPipe> {
    private static final ResourceLocation BG_TEX = new ResourceLocation("bcex:textures/gui/distrib.png");
    private static final GuiIcon BG_ICON = new GuiIcon(BG_TEX, 0, 0, 100, 190);

    public GuiPipeDistribution(ContainerDistributionPipe container) {
        super(container);
        int yPos = 5;
        for (ContainerDistributionPipe.DistribWidget widget : container.widgets.values()) {
            addWidget(widget, 5, yPos);
            yPos += 30;
        }
        ySize = yPos + 5;
        xSize = 100;
    }

    public void addWidget(ContainerDistributionPipe.DistribWidget widget, int x, int y){
        IGuiPosition pos = mainGui.rootElement.offset(x, y);
        ISimpleDrawable color = (_x, _y) -> GuiIcon.drawAt(BCTransportSprites.ACTION_PIPE_COLOUR[widget.color.color.ordinal()], _x + 2, _y + 2, 16);
        IGuiArea colorArea = new GuiRectangle(20,20).offset(pos);
        mainGui.shownElements.add(new GuiElementDrawable(mainGui,colorArea,color,false));
        IGuiPosition posBD = pos.offset(20,0);
        GuiButtonDrawable decrement = new GuiButtonDrawable(mainGui, "-", posBD, StandardSpriteButtons.TINY_BUTTON_DRAWABLE);
        decrement.registerListener((b,u)-> widget.decrease());
        mainGui.shownElements.add(decrement);
        mainGui.shownElements.add(decrement.createTextElement("-"));
        IGuiPosition posTXT = posBD.offset(15, 0);
        GuiElementText num = new GuiElementText(mainGui, posTXT, () -> widget.value + "", 0);
        mainGui.shownElements.add(num);
        IGuiPosition posBI = posTXT.offset(()->num.getWidth() + 5,0);
        GuiButtonDrawable increment = new GuiButtonDrawable(mainGui, "+", posBI, StandardSpriteButtons.TINY_BUTTON_DRAWABLE);
        increment.registerListener((b,u)->widget.increase());
        mainGui.shownElements.add(increment);
        mainGui.shownElements.add(increment.createTextElement("+"));
    }

    @Override
    protected void drawBackgroundLayer(float partialTicks) {
        BG_ICON.drawAt(mainGui.rootElement);
        super.drawBackgroundLayer(partialTicks);
    }
}
