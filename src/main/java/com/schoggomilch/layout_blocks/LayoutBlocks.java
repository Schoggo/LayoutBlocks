package com.schoggomilch.layout_blocks;

import com.schoggomilch.layout_blocks.gui.RenderGuiHandler;
import com.schoggomilch.layout_blocks.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = LayoutBlocks.MOD_ID, name = LayoutBlocks.MOD_NAME, useMetadata = true)
public class LayoutBlocks {

    public static final String MOD_ID = "layout_blocks";
    public static final String MOD_NAME = "LayoutBlocks";

    @Mod.Instance
    public static LayoutBlocks instance;

    @SidedProxy(clientSide = "com.schoggomilch.layout_blocks.proxy.ClientProxy", serverSide = "com.schoggomilch.layout_blocks.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new RenderGuiHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
 //       MinecraftForge.EVENT_BUS.register(new RenderGuiHandler());

        proxy.postInit(e);
    }


}
