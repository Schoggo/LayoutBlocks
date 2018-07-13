package com.schoggomilch.testmod.block;

import com.schoggomilch.testmod.LayoutBlocks;
import com.schoggomilch.testmod.LayoutBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockNonsolidLayoutBlock extends Block {


    public BlockNonsolidLayoutBlock(){
        super(LayoutBlocksMaterials.NonsolidMat);



        setHardness(.04f);
        setResistance(0);
        setLightOpacity(0);



        setUnlocalizedName(LayoutBlocks.MOD_ID + ".nonsolid_layout_block");
        setRegistryName("nonsolid_layout_block");
    }




    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    public boolean isSolid()
    {
        return false;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean blocksLight()
    {
        return false;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return false;
    }



    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

    @Nullable
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    public Item createItemBlock(){
        return new ItemBlock(this).setRegistryName(this.getRegistryName());
    }

    @Override
    @SuppressWarnings( "deprecation" )
    public int quantityDropped(Random random) { return 0; }
    @Override
    @SuppressWarnings( "deprecation" )
    protected boolean canSilkHarvest(){return false;}

    public void registerItemModel(){
        LayoutBlocks.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "nonsolid_layout_block");
    }
}
