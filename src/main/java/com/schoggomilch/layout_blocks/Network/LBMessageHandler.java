package com.schoggomilch.layout_blocks.Network;

import com.schoggomilch.layout_blocks.LayoutBlocks;
import com.schoggomilch.layout_blocks.init.ModItems;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LBMessageHandler implements IMessageHandler<LBMessage, LBMessage> {
    NBTTagCompound nbt;
    ItemStack stack;

    @Override
    public LBMessage onMessage(LBMessage msg, MessageContext ctx) {
        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        byte places = msg.places;
        byte breaks = msg.breaks;
        /*recieves msg with new item nbt from client (gui) and sets nbt of item on server*/
        if(places >= 0) {
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                if (serverPlayer.getHeldItemMainhand().getItem() == ModItems.itemPlacer) {
                    stack = serverPlayer.getHeldItemMainhand();
                    if(stack.hasTagCompound()){
                       nbt = stack.getTagCompound();
                    }
                    else {
                        nbt = new NBTTagCompound();
                    }

                    nbt.setByte("places", places);
                    nbt.setByte("breaks", breaks);

                    stack.setTagCompound(nbt);

                    serverPlayer.inventory.setInventorySlotContents(serverPlayer.inventory.currentItem, stack);
                } else {
                    LayoutBlocks.logger.error("Held item changed while trying to change nbt of Placer wand on server.");
                }
            });
        }
            return null;
    }
}
