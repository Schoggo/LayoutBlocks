package com.schoggomilch.testmod.item;

import com.schoggomilch.testmod.LayoutBlocks;
import com.schoggomilch.testmod.block.BlockSolidLayoutBlock;
import com.schoggomilch.testmod.gui.RenderGuiHandler;
import com.schoggomilch.testmod.init.ModBlocks;
import com.schoggomilch.testmod.init.ModItems;
import com.schoggomilch.testmod.config.Config;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.schoggomilch.testmod.block.BlockSolidLayoutBlock.COLOR;
import static net.minecraft.item.ItemBlock.setTileEntityNBT;

@Mod.EventBusSubscriber
public class ItemPlacer extends Item {

    BlockSolidLayoutBlock SolidLayoutBlock = ModBlocks.blockSolidLayoutBlock;


    public ItemPlacer(){
        setRegistryName("item_placer");
        setUnlocalizedName(LayoutBlocks.MOD_ID + ".item_placer");

        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);


    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(Config.rClickOpensGui) {
            if (worldIn.isRemote) {
                playerIn.openGui(LayoutBlocks.instance, RenderGuiHandler.PLACER, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }



    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt;
        if (stack.hasTagCompound()){
            nbt = stack.getTagCompound();
            if (nbt.hasKey("places")){
                tooltip.add("Places: " + nbt.getByte("places"));
            }
            else {
                tooltip.add("does have nbt but not places");
            }
        }
        else {
            tooltip.add("doesnt have nbt");
        }
    }

    public void registerItemModel(){
        LayoutBlocks.proxy.registerItemRenderer(ModItems.itemPlacer, 0, "item_placer");
    }


    //<editor-fold desc="Alternative breaking mode">
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if(state.getBlock() == ModBlocks.blockNonsolidLayoutBlock || state.getBlock() == ModBlocks.blockSolidLayoutBlock){
            NBTTagCompound nbt;
            if (stack.hasTagCompound()){
                nbt = stack.getTagCompound();
                if (nbt.hasKey("breaks")){
                    byte breaks = nbt.getByte("breaks");

                    switch (breaks){
                        case 0: //normal
                            break;

                        case 1: //break all connected layoutblocks
                            if (Config.enableAltBreakMode)
                                new Thread(new BlockBreakerThread(worldIn, entityLiving, pos, stack)).start();
                            break;
                    }
                }
                else {
                    nbt.setByte("breaks", (byte) 0);
                    stack.setTagCompound(nbt);
                    return false;
                }
            }
            else {
                nbt = new NBTTagCompound();
                nbt.setByte("places", (byte) 1);
                nbt.setByte("breaks", (byte) 0);
                stack.setTagCompound(nbt);
                return false;
            }
        }
        return false;
    }
    //</editor-fold>


    //<editor-fold desc="Placing blocks">
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos) || block.equals(ModBlocks.blockNonsolidLayoutBlock))
        {
            pos = pos.offset(facing);
        }

        ItemStack stack = player.getHeldItem(hand);
        NBTTagCompound nbt;
        IBlockState iblockstate1;

        if (player.canPlayerEdit(pos, facing, stack) && worldIn.mayPlace(SolidLayoutBlock, pos, false, facing, (Entity)null))
        {
            if(stack.hasTagCompound()){
                nbt = stack.getTagCompound();
            }
            else {
                nbt = new NBTTagCompound();
            }

            if(nbt.hasKey("places")){
                if (nbt.getByte("places") == 0){
                    iblockstate1 = ModBlocks.blockNonsolidLayoutBlock.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand);
                }
                else {
                    iblockstate1 = ModBlocks.blockSolidLayoutBlock.getStateForPlacement(worldIn,
                            pos, facing, hitX, hitY, hitZ, 0, player, hand).withProperty(COLOR, EnumDyeColor.GRAY );
                }
            }
            else {
                nbt.setByte("places", (byte) 1);
                iblockstate1 = ModBlocks.blockSolidLayoutBlock.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand);
            }


            if (placeBlockAt(stack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
            {
                iblockstate1 = worldIn.getBlockState(pos);
                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F,
                        soundtype.getPitch() * 0.8F);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.SUCCESS;
        }
    }



    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == ModBlocks.blockSolidLayoutBlock)
        {
            setTileEntityNBT(world, player, pos, stack);
            ModBlocks.blockSolidLayoutBlock.onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }
        else{
            if (state.getBlock() == ModBlocks.blockNonsolidLayoutBlock){
                setTileEntityNBT(world, player, pos, stack);
                ModBlocks.blockNonsolidLayoutBlock.onBlockPlacedBy(world, pos, state, player, stack);

                if (player instanceof EntityPlayerMP)
                    CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }}

        return true;
    }
    //</editor-fold>

}
