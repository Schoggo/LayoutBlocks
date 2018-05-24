package com.schoggomilch.testmod.proxy;

import com.schoggomilch.testmod.TestMod;
import com.schoggomilch.testmod.init.ModBlocks;
import com.schoggomilch.testmod.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy{

    @Override
    public void registerItemRenderer(Item i, int meta, String id){
        ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(TestMod.MOD_ID + ":" + id, "inventory"));
        System.out.println("----------------------------------ClientProxy.regItemRenderer done-----------------------------------");
    }

}
