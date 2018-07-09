package com.schoggomilch.testmod.gui;


import com.schoggomilch.testmod.Network.LBMessage;
import com.schoggomilch.testmod.Network.LBPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class PlacerGui extends GuiScreen {
    GuiButton SolidBtt;
    GuiButton NonsolidBtt;

    GuiButton BreaksNormalBtt;
    GuiButton BreaksAllBtt;

    public final EntityPlayer player;
    public ItemStack stack;
    public NBTTagCompound nbt;
    /**0 = Nonsolid, 1= Solid*/
    byte places;

    /**0 = normal breaking mode, 1 = breaks all connected layoutBlocks*/
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

        System.out.println("width: " + this.width + ", height: " + this.height);


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

//    @Override
//    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        super.drawScreen(mouseX, mouseY, partialTicks);
//
//
//        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/book.png"));
//        int i = (this.width - 192) / 2;
//        int j = 2;
//        this.drawTexturedModalRect(0, 0, 0, 0, 100, 100);
//    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        System.out.println("-------------------------------action performed");
        if(button.enabled){

        System.out.println("button id: " + button.id);
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

    public void sendItemToServer(byte places, byte breaks){
        LBPacketHandler.INSTANCE.sendToServer(new LBMessage(places, breaks));
    }

    @Override
    public void onGuiClosed(){
        sendItemToServer(places, breaks);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
            this.mc.player.closeScreen();
        }
    }
}



























/*
public class PlacerGui extends GuiContainer {

    GuiButton SolidBtt;
    GuiButton NonsolidBtt;

//    public static final ResourceLocation bg = new ResourceLocation(LayoutBlocks.MOD_ID, "textures/gui/placer.png");


 //   public PlacerGui(Minecraft mc){
 //       this.mc = mc;
 //       addButton(new GuiButton(1, 100, 100, "Hey"));
 //   }


    //use drawScreen(), sonst super.drawScreen
   // public void drawGui(){
   //     this.width = mc.displayWidth;
   //     this.height = mc.displayHeight;
   //     drawCenteredString(mc.fontRenderer, "hi", width / 2, (height / 2) - 4, Integer.parseInt("FFAA00", 16));
   // }



        private InventoryPlayer playerInv;

        public PlacerGui(Container container, InventoryPlayer playerInv) {
            super(container);
            this.playerInv = playerInv;
            addButton(new GuiButton(1, width/2, height/2, "Hey"));
        }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
  //      GlStateManager.color(1, 1, 1, 1);
  //      mc.getTextureManager().bindTexture(BG_TEXTURE);
  //      int x = (width - xSize) / 2;
  //      int y = (height - ySize) / 2;
  //      drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        //partialTicks = this.mc.getTickLength();
        drawDefaultBackground();
        }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {


        //addButton(new GuiButton(1, width/2, height/2, "Hey"));
        //drawScreen(100, 100, 0);


    }


}
*/


