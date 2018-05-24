package com.schoggomilch.testmod;

import com.schoggomilch.testmod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TestMod.MOD_ID, name = TestMod.MOD_NAME, useMetadata = true)
public class TestMod {

    public static final String MOD_ID = "layout_blocks";
    public static final String MOD_NAME = "TestMod";

    @Mod.Instance
    public static TestMod instance;

    @SidedProxy(clientSide = "com.schoggomilch.testmod.proxy.ClientProxy", serverSide = "com.schoggomilch.testmod.proxy.CommonProxy")
    public static CommonProxy proxy;

//    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

}
