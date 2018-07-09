package com.schoggomilch.testmod.config;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class ConditionFac implements IConditionFactory {
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        String configSetting = JsonUtils.getString(json, "config_setting");
        System.out.println("config_setting? " + configSetting);

        return () -> Config.usesCraftingRecipe;

    }
}
