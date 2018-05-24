package com.schoggomilch.testmod.block;

import com.schoggomilch.testmod.TestMod;
import com.schoggomilch.testmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSolidLayoutBlock extends Block {

    public static Item ItemSolidLayoutBlock;

    public BlockSolidLayoutBlock(){
        super(Material.ROCK);

        setHardness((float) .012);
        setResistance(0);
        setLightOpacity(0);


        setUnlocalizedName(TestMod.MOD_ID + ".solid_layout_block");
        setRegistryName("solid_layout_block");

 //       ItemSolidLayoutBlock = new ItemBlock(this).setRegistryName(this.getRegistryName());

    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return false;
    }


    public Item createItemBlock(){
        return new ItemBlock(this).setRegistryName(this.getRegistryName());
    }

    public void registerItemModel(){
        TestMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "solid_layout_block");
    }

}
