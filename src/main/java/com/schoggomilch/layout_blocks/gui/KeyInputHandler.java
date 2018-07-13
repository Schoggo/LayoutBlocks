package com.schoggomilch.layout_blocks.gui;

import com.schoggomilch.layout_blocks.LayoutBlocks;
import com.schoggomilch.layout_blocks.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent event){
        if(ClientProxy.openGuiKey.isPressed()){
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            player.openGui(LayoutBlocks.instance, RenderGuiHandler.PLACER, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
    }
}
