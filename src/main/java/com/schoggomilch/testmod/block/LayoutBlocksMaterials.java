package com.schoggomilch.testmod.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class LayoutBlocksMaterials {
        public static final Material NonsolidMat = new Material(MapColor.GRAY){
        public boolean isSolid()
        {
            return false;
        }
        public boolean blocksLight()
        {
            return false;
        }
        public boolean blocksMovement()
        {
            return false;
        }
        };
}
