package com.schoggomilch.layout_blocks.proxy;

import com.schoggomilch.layout_blocks.LayoutBlocks;
import com.schoggomilch.layout_blocks.gui.KeyInputHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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
