package com.schoggomilch.testmod.Network;

import com.schoggomilch.testmod.LayoutBlocks;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class LBPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LayoutBlocks.MOD_ID);
    static byte id;

    public LBPacketHandler(){
        id = -1;
        INSTANCE.registerMessage(LBMessageHandler.class, LBMessage.class, id++, Side.SERVER);

    }
}
