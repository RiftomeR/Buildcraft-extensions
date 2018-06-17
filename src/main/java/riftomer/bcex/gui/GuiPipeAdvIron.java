package riftomer.bcex.gui;

import buildcraft.lib.gui.*;
import buildcraft.lib.gui.button.GuiButtonDrawable;
import buildcraft.lib.gui.button.StandardSpriteButtons;
import buildcraft.lib.gui.elem.GuiElementDrawable;
import buildcraft.lib.gui.pos.*;
import buildcraft.transport.BCTransportSprites;
import net.minecraft.util.ResourceLocation;
import riftomer.bcex.gui.container.ContainerAdvIronPipe;

public class GuiPipeAdvIron extends GuiBC8<ContainerAdvIronPipe> {
    private static final ResourceLocation BG_TEX = new ResourceLocation("bcex:textures/gui/adviron.png");
    private static final GuiIcon BG_ICON = new GuiIcon(BG_TEX, 0, 0, 60, 190);

    public GuiPipeAdvIron(ContainerAdvIronPipe container) {
        super(container);
        xSize = 60;
        ySize = 190;
    }

    @Override
    public void initGui() {
        super.initGui();
        int yPos = 5;
        for (ContainerAdvIronPipe.ColorWidget widget : container.widgets) {
            addWidgetButton(widget, 5, yPos);
            yPos += 30;
        }
    }

    private void addWidgetButton(ContainerAdvIronPipe.ColorWidget widget, int x, int y) {
        IGuiPosition pos = mainGui.rootElement.offset(x, y);
        GuiButtonDrawable button = new GuiButtonDrawable(mainGui, widget.fromColor.name(), pos.offset(20, 0), StandardSpriteButtons.TINY_BUTTON_DRAWABLE);
        button.registerListener((b, u) -> widget.nextColor());
        mainGui.shownElements.add(button);
        mainGui.shownElements.add(button.createTextElement(">"));
        IGuiArea fromArea = new GuiRectangle(20, 20).offset(pos);
        IGuiArea toArea = new GuiRectangle(20, 20).offset(pos).offset(30, 0);
        ISimpleDrawable fromIcon = (_x, _y) -> GuiIcon.drawAt(BCTransportSprites.ACTION_PIPE_COLOUR[widget.fromColor.color.ordinal()], _x + 2, _y + 2, 16);
        ISimpleDrawable toIcon = (_x, _y) -> GuiIcon.drawAt(BCTransportSprites.ACTION_PIPE_COLOUR[widget.toColor.color.ordinal()], _x + 2, _y + 2, 16);
        mainGui.shownElements.add(new GuiElementDrawable(mainGui, fromArea, fromIcon, false));
        mainGui.shownElements.add(new GuiElementDrawable(mainGui, toArea, toIcon, false));
    }

    @Override
    protected void drawBackgroundLayer(float partialTicks) {
        BG_ICON.drawAt(mainGui.rootElement);
        super.drawBackgroundLayer(partialTicks);
    }
}
