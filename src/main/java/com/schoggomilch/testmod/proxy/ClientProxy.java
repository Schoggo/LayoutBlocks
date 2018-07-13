package com.schoggomilch.testmod.proxy;

import com.schoggomilch.testmod.LayoutBlocks;
import com.schoggomilch.testmod.gui.KeyInputHandler;
import com.schoggomilch.testmod.init.ModBlocks;
import com.schoggomilch.testmod.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy{

    public static KeyBinding openGuiKey;

    @Override
    public void registerItemRenderer(Item i, int meta, String id){
        ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(LayoutBlocks.MOD_ID + ":" + id, "inventory"));
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());

        openGuiKey = new KeyBinding("key.open_gui_key", Keyboard.KEY_G, "key.categories.layout_blocks");
        ClientRegistry.registerKeyBinding(openGuiKey);
    }

}
