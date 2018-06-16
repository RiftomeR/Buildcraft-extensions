package riftomer.bcex.gui.container;

import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.lib.gui.ContainerPipe;
import buildcraft.lib.gui.Widget_Neptune;
import buildcraft.lib.misc.MessageUtil;
import buildcraft.lib.net.PacketBufferBC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import riftomer.bcex.pipe.behaviour.PipeBehaviourAdvancedIron;
import riftomer.bcex.pipe.util.EnumDiamondColor;

import java.io.IOException;

public class ContainerAdvIronPipe extends ContainerPipe {
    public final PipeBehaviourAdvancedIron behaviour;
    public ColorWidget[] widgets = new ColorWidget[6];

    public ContainerAdvIronPipe(EntityPlayer player, IPipeHolder pipeHolder) {
        super(player, pipeHolder);
        behaviour = (PipeBehaviourAdvancedIron) pipeHolder.getPipe().getBehaviour();
        int i = 0;
        for (EnumDiamondColor color : EnumDiamondColor.values()) {
            widgets[i] = new ColorWidget(this, color, EnumDiamondColor.fromFacing(EnumFacing.values()[behaviour.faces[color.facing.ordinal()]]));
            addWidget(widgets[i]);
            i++;
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        pipeHolder.onPlayerClose(playerIn);
    }

    public static class ColorWidget extends Widget_Neptune<ContainerAdvIronPipe> {
        public final EnumDiamondColor fromColor;
        public EnumDiamondColor toColor;

        public ColorWidget(ContainerAdvIronPipe container, EnumDiamondColor firstColor, EnumDiamondColor toColor) {
            super(container);
            this.fromColor = firstColor;
            this.toColor = toColor;
        }

        public void nextColor() {
            toColor = toColor.next();
            sendWidgetData((buffer) -> MessageUtil.writeEnumOrNull(buffer, toColor));
        }

        @Override
        public IMessage handleWidgetDataServer(MessageContext ctx, PacketBufferBC buffer) throws IOException {
            toColor = MessageUtil.readEnumOrNull(buffer, EnumDiamondColor.class);
            container.behaviour.faces[fromColor.facing.ordinal()] = (byte) toColor.facing.ordinal();
            container.pipeHolder.scheduleNetworkUpdate(IPipeHolder.PipeMessageReceiver.BEHAVIOUR);
            return null;
        }
    }
}
