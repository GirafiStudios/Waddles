package com.girafi.waddles.client;

import com.girafi.waddles.Constants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ClientHelper {
    public static ModelLayerLocation PENGUIN_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "penguin"), "penguin");
    public static ModelLayerLocation PENGUIN_LAYER_BABY = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "penguin_baby"), "penguin_baby");
}
