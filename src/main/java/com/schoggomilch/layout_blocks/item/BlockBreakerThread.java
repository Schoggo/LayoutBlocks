package com.schoggomilch.layout_blocks.item;

import com.schoggomilch.layout_blocks.LayoutBlocks;
import com.schoggomilch.layout_blocks.config.Config;
import com.schoggomilch.layout_blocks.init.ModBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBreakerThread implements Runnable {

    World world;
    EntityPlayer entity;
    BlockPos pos;
    ItemStack stack;
    int breakLimit;
    int breakCount = 1;

    public BlockBreakerThread(World world, EntityLivingBase entity, BlockPos pos, ItemStack stack){
        this.world = world;
        this.pos = pos;
        if (entity instanceof EntityPlayer){
            this.entity = (EntityPlayer) entity;
        }
        else{
            LayoutBlocks.logger.error("Placer wand's alternative breaking mode used by something else than player.");
        }
        this.stack = stack;

        breakLimit = Config.maxBlocksBroken;
    }

    @Override
    public void run() {
        if(entity != null)
        breakBlocks(pos);
    }


    private void breakBlocks(BlockPos pos1) {
        if (breakCount < breakLimit || breakLimit == 0) {
            if (entity.canPlayerEdit(pos1, EnumFacing.UP, ItemStack.EMPTY) &&
                    (world.getBlockState(pos1).getBlock() == ModBlocks.blockSolidLayoutBlock ||
                            world.getBlockState(pos1).getBlock() == ModBlocks.blockNonsolidLayoutBlock)) {
                world.destroyBlock(pos1, false);
                breakCount++;
            } else {
            }

            if(Config.millisBetweenBlockBreaks > 0) {
                try {
                    Thread.sleep(Config.millisBetweenBlockBreaks);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (EnumFacing facing : EnumFacing.values()) {
                if (entity.canPlayerEdit(pos1.offset(facing), facing, ItemStack.EMPTY) &&
                        (world.getBlockState(pos1.offset(facing)).getBlock() == ModBlocks.blockSolidLayoutBlock ||
                                world.getBlockState(pos1.offset(facing)).getBlock() == ModBlocks.blockNonsolidLayoutBlock)) {
                    try {
                        breakBlocks(pos1.offset(facing));
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
