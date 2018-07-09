package com.schoggomilch.testmod.config;


import com.schoggomilch.testmod.LayoutBlocks;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.common.config.Config(modid = LayoutBlocks.MOD_ID, type = net.minecraftforge.common.config.Config.Type.INSTANCE, name = "LayoutBlocks com.schoggomilch.testmod.config")
public class Config {

    @net.minecraftforge.common.config.Config.Comment({"By default you can open the placer gui only using the hotkey,",
                                                        "if you enable this you can open it with right click."})
    @net.minecraftforge.common.config.Config.Name("Open Gui on Rightclick")
    public static boolean rClickOpensGui = false;


    @net.minecraftforge.common.config.Config.Comment("Disable if you don't want the placer crafting recipe")
    @net.minecraftforge.common.config.Config.RequiresMcRestart
    @net.minecraftforge.common.config.Config.Name("Enable the crafting recipe for the placer")
    public static boolean usesCraftingRecipe = true;

    @net.minecraftforge.common.config.Config.Comment({"How many blocks the placer's alternative breaking mode can break at once.",
                                                        "Set to 0 to disable limit, set to a negative value to disable alternative breaking mode."})
    @net.minecraftforge.common.config.Config.Name("Max broken blocks at once")
    public static int maxBlocksBroken = 200;

    @net.minecraftforge.common.config.Config.Comment({"The alternative breaking mode pauses for that many ms between breaking blocks.",
    "Only for debugging or if you want to play domino."})
    @net.minecraftforge.common.config.Config.Name("[Debug] ms between block breaks")
    public static int millisBetweenBlockBreaks = 0;





    @Mod.EventBusSubscriber
    private static class EventHandler{

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
            if(event.getModID().equals(LayoutBlocks.MOD_ID)){
                System.out.println("changing com.schoggomilch.testmod.config");
                ConfigManager.sync(LayoutBlocks.MOD_ID, net.minecraftforge.common.config.Config.Type.INSTANCE);
            }
        }
    }
}
