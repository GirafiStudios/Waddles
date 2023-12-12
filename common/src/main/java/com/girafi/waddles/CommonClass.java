package com.girafi.waddles;

import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;
import com.girafi.waddles.utils.WaddlesTags;

public class CommonClass {

    public static void init() {
        WaddlesTags.load();
        WaddlesSounds.load();
        PenguinRegistry.load();
    }
}