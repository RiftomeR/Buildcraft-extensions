package riftomer.bcex.gui.container;

import buildcraft.api.transport.pipe.IPipeHolder;
import buildcraft.lib.gui.ContainerPipe;
import buildcraft.lib.gui.Widget_Neptune;
import buildcraft.lib.net.PacketBufferBC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import riftomer.bcex.pipe.behaviour.PipeBehaviourDistribution;
import riftomer.bcex.pipe.util.EnumDiamondColor;

import java.util.EnumMap;


public class ContainerDistributionPipe extends ContainerPipe {
    public final PipeBehaviourDistribution behaviour;
    public final EnumMap<EnumDiamondColor,DistribWidget> widgets;

    public ContainerDistributionPipe(EntityPlayer player, IPipeHolder pipeHolder) {
        super(player, pipeHolder);
        behaviour = (PipeBehaviourDistribution) pipeHolder.getPipe().getBehaviour();
        widgets = new EnumMap<>(EnumDiamondColor.class);
        for(EnumDiamondColor color : EnumDiamondColor.values()){
            DistribWidget widget = new DistribWidget(this, color, behaviour.faces.get(color));
            addWidget(widget);
            widgets.put(color,widget);
        }
    }

    public static class DistribWidget extends Widget_Neptune<ContainerDistributionPipe> {
        public final EnumDiamondColor color;
        public int value;

        public DistribWidget(ContainerDistributionPipe container, EnumDiamondColor color, int value) {
            super(container);
            this.color = color;
            this.value = value;
        }

        public void decrease() {
            value--;
            value = Math.max(value, 0);
            sendWidgetData((buffer) -> buffer.writeInt(value));
        }

        public void increase() {
            value++;
            sendWidgetData((buffer) -> buffer.writeInt(value));
        }

        @Override
        public IMessage handleWidgetDataServer(MessageContext ctx, PacketBufferBC buffer) {
            value = buffer.readInt();
            container.behaviour.faces.put(color,value);
            container.pipeHolder.scheduleNetworkUpdate(IPipeHolder.PipeMessageReceiver.BEHAVIOUR);
            return null;
        }
    }
}
