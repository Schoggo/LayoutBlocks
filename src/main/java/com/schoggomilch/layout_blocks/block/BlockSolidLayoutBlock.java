package com.schoggomilch.layout_blocks.block;

import com.schoggomilch.layout_blocks.LayoutBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.DyeUtils;

import java.util.Random;

public class BlockSolidLayoutBlock extends Block {

    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

    public BlockSolidLayoutBlock(){
        super(Material.GROUND);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.GRAY));

        setHardness((float) .04);
        setResistance(0);
        setLightOpacity(0);

        setUnlocalizedName(LayoutBlocks.MOD_ID + ".solid_layout_block");
        setRegistryName("solid_layout_block");
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
        LayoutBlocks.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "solid_layout_block");
    }




    //<editor-fold desc="Coloring stuff">
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side,
                                    float hitX, float hitY, float hitZ){
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if(!itemstack.isEmpty()){
            if(DyeUtils.isDye(itemstack)){
                if(DyeUtils.colorFromStack(itemstack) != null){
                    worldIn.setBlockState(pos, state.withProperty(COLOR, DyeUtils.colorFromStack(itemstack).get()));
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @SuppressWarnings( "deprecation" )
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.getBlockColor((EnumDyeColor)state.getValue(COLOR));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @SuppressWarnings( "deprecation" )
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {COLOR});
    }
    //</editor-fold>
}
