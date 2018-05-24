package com.schoggomilch.testmod.init;

import com.schoggomilch.testmod.block.BlockSolidLayoutBlock;
import com.schoggomilch.testmod.item.ItemPlacer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistrationHandler {


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ModBlocks.blockSolidLayoutBlock.createItemBlock(),
                ModItems.itemPlacer
        );
        System.out.println("----------------------------------CommonProxy.registerItems done-----------------------------------");
    }

    @SubscribeEvent
    public static void registerItems(ModelRegistryEvent event){
        ModItems.itemPlacer.registerItemModel();
        ModBlocks.blockSolidLayoutBlock.registerItemModel();
        System.out.println("-------------------------------itemPlacer.regModel called---------------------------");
    }



    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(
                ModBlocks.blockSolidLayoutBlock
        );
    }
}
