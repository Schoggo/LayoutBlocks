package com.schoggomilch.layout_blocks.Network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class LBMessage implements IMessage {
    public LBMessage(){}

    byte places;
    byte breaks;
    public LBMessage(byte places, byte breaks){
        this.places = places;
        this.breaks = breaks;
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeByte(places);
        buf.writeByte(breaks);
    }

    @Override
    public void fromBytes(ByteBuf buf){
        places = buf.readByte();
        breaks = buf.readByte();
    }
}
