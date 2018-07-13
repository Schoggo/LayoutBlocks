package com.schoggomilch.layout_blocks.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistrationHandler {


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ModItems.blockSolidLayoutBlock,
                ModItems.blockNonsolidLayoutBlock,
                ModItems.itemPlacer
        );
    }

    @SubscribeEvent
    public static void registerItems(ModelRegistryEvent event){
        ModItems.itemPlacer.registerItemModel();
        ModBlocks.blockSolidLayoutBlock.registerItemModel();
        ModBlocks.blockNonsolidLayoutBlock.registerItemModel();
    }



    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(
                ModBlocks.blockSolidLayoutBlock,
                ModBlocks.blockNonsolidLayoutBlock
        );
    }
}
