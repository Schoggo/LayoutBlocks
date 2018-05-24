package com.schoggomilch.testmod.init;

import com.schoggomilch.testmod.block.BlockSolidLayoutBlock;
import com.schoggomilch.testmod.item.ItemPlacer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static ItemPlacer itemPlacer = new ItemPlacer();
    public static Item blockSolidLayoutBlock = ModBlocks.blockSolidLayoutBlock.createItemBlock();
}
