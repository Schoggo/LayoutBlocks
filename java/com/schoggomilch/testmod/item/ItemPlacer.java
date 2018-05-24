package com.schoggomilch.testmod.item;

import com.schoggomilch.testmod.TestMod;
import com.schoggomilch.testmod.block.BlockSolidLayoutBlock;
import com.schoggomilch.testmod.init.ModBlocks;
import com.schoggomilch.testmod.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static net.minecraft.item.ItemBlock.setTileEntityNBT;

@Mod.EventBusSubscriber
public class ItemPlacer extends Item {

    List<String> tooltipList = new ArrayList<>();
    BlockSolidLayoutBlock SolidLayoutBlock = ModBlocks.blockSolidLayoutBlock;

    public ItemPlacer(){
        setRegistryName("item_placer");
        setUnlocalizedName(TestMod.MOD_ID + "item_placer");

        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);

        tooltipList.add("Lets you place LayoutBlocks");
        tooltipList.add("and do some fancy stuff in future releases");
        addInformation(new ItemStack(this), null, tooltipList, ITooltipFlag.TooltipFlags.NORMAL);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing);
        }

        ItemStack itemstack = player.getHeldItem(hand);       //später für meta

        if (player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(SolidLayoutBlock, pos, false, facing, (Entity)null))
        {
//            int i = this.getMetadata(itemstack.getMetadata());
            IBlockState iblockstate1 = SolidLayoutBlock.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand);

            if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
            {
                iblockstate1 = worldIn.getBlockState(pos);
                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == SolidLayoutBlock)
        {
            setTileEntityNBT(world, player, pos, stack);
            SolidLayoutBlock.onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }

        return true;
    }



    public void registerItemModel(){
        TestMod.proxy.registerItemRenderer(ModItems.itemPlacer, 0, "item_placer");
    }



}
