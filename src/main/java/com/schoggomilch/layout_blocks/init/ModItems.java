package com.schoggomilch.layout_blocks.init;

import com.schoggomilch.layout_blocks.item.ItemPlacer;
import net.minecraft.item.Item;

public class ModItems {
    public static ItemPlacer itemPlacer = new ItemPlacer();
    public static Item blockSolidLayoutBlock = ModBlocks.blockSolidLayoutBlock.createItemBlock();
    public static Item blockNonsolidLayoutBlock = ModBlocks.blockNonsolidLayoutBlock.createItemBlock();
}
