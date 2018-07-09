package com.schoggomilch.testmod.item;

import com.schoggomilch.testmod.config.Config;
import com.schoggomilch.testmod.init.ModBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
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
            //TODO make an exception for this instead of sout
            System.out.println("not a player");
        }
        this.stack = stack;

        breakLimit = Config.maxBlocksBroken;
    }

    @Override
    public void run() {
        System.out.println("-----------------from thread: run()");
        if(entity != null)
        breakBlocks(pos);
    }


    //TODO find out why it works this way
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
