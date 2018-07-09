package com.schoggomilch.testmod.Network;

import com.schoggomilch.testmod.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.concurrent.TimeUnit;

public class LBMessageHandler implements IMessageHandler<LBMessage, LBMessage> {
    NBTTagCompound nbt;
    ItemStack stack;

    @Override
    public LBMessage onMessage(LBMessage msg, MessageContext ctx) {
        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        byte places = msg.places;
        byte breaks = msg.breaks;
        /**recieves msg with new item nbt from client (gui) and sets nbt of item on server*/
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

                    System.out.println("nbt set");
                    serverPlayer.inventory.setInventorySlotContents(serverPlayer.inventory.currentItem, stack);
                } else {
                    System.out.println("items unequal");
                }
            });
        }
            return null;
    }
}
