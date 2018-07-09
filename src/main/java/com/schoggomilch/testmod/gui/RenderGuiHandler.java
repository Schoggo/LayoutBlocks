package com.schoggomilch.testmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderGuiHandler implements IGuiHandler {

    public static final int PLACER =0;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            default:
                return null;
        }
    }


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
//                return new PlacerGui(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
                System.out.println("player name: " + player.getName());
                System.out.println("held item: " + player.getHeldItemMainhand().getItem().getUnlocalizedName());
                return new PlacerGui(player, player.getHeldItemMainhand());
            default:
                return null;
        }
    }


}
