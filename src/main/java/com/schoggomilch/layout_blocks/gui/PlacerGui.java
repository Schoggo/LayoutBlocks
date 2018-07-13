package com.schoggomilch.layout_blocks.gui;


import com.schoggomilch.layout_blocks.Network.LBMessage;
import com.schoggomilch.layout_blocks.Network.LBPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

public class PlacerGui extends GuiScreen {
    GuiButton SolidBtt;
    GuiButton NonsolidBtt;

    GuiButton BreaksNormalBtt;
    GuiButton BreaksAllBtt;

    public final EntityPlayer player;
    public ItemStack stack;
    public NBTTagCompound nbt;
    /*0 = Nonsolid, 1= Solid*/
    byte places;
    /*0 = normal breaking mode, 1 = breaks all connected layoutBlocks*/
    byte breaks;
    LBPacketHandler packetHandler;


    public PlacerGui(EntityPlayer player, ItemStack placer) {
        packetHandler = new LBPacketHandler();

        this.player = player;
        this.stack = placer;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {

        this.buttonList.clear();

       if(stack.hasTagCompound()){
           nbt = stack.getTagCompound();
       }
       else {
           nbt = new NBTTagCompound();
       }

       if(!nbt.hasKey("places")){
           nbt.setByte("places", (byte) 1);
       }
       places = nbt.getByte("places");

        if(!nbt.hasKey("breaks")){
            nbt.setByte("breaks", (byte) 0);
        }
        breaks = nbt.getByte("breaks");

        this.NonsolidBtt = this.addButton(new GuiButton(0, width / 2 - 105, height / 2 + 25, 120, 20,I18n.format("gui.nonsolid")));
        this.SolidBtt = this.addButton(new GuiButton(1, width / 2 - 105, height / 2 - 5, 120, 20,I18n.format("gui.solid")));

        this.BreaksNormalBtt = this.addButton(new GuiButton(2, width/2 + 105, height / 2 + -5, 120, 20, I18n.format("gui.break_one")));
        this.BreaksAllBtt = this.addButton(new GuiButton(3, width/2 + 105, height / 2 + 25, 120, 20, I18n.format("gui.break_all")));

        updateButtons();
    }

    public void updateButtons(){
        SolidBtt.enabled = places == 0;
        NonsolidBtt.enabled = !SolidBtt.enabled;

        BreaksNormalBtt.enabled = breaks == 1;
        BreaksAllBtt.enabled = !BreaksNormalBtt.enabled;
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        if(button.enabled){

            switch (button.id){
                case 0:
                    places = 0;
                    break;

                case 1:
                    places = 1;
                    break;

                case 2:
                    breaks = 0;
                    break;

                case 3:
                    breaks = 1;
                    break;
            }

            updateButtons();
        }
    }

    public void sendItemToServer(){
        LBPacketHandler.INSTANCE.sendToServer(new LBMessage(places, breaks));
    }

    @Override
    public void onGuiClosed(){
        sendItemToServer();
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
            this.mc.player.closeScreen();
        }
    }
}
