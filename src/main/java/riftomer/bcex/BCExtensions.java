package riftomer.bcex;

import buildcraft.lib.BCLib;
import buildcraft.transport.BCTransport;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
import riftomer.bcex.init.*;
import riftomer.bcex.proxy.Proxy;

@Mod(
        modid = BCExtensions.MODID,
        dependencies = "required-after:" + BCTransport.MODID + "@[" + BCLib.VERSION + "]",
        useMetadata = true)
public class BCExtensions {
    public static final String MODID = "bcex";
    @Mod.Instance
    public static BCExtensions INSTANCE;

    public static Logger logger;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ModPipes.preInit();
        ModTabs.preInit();
        ModItems.preInit();
        ModTabs.setTabItems();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, Proxy.getProxy());
    }
}