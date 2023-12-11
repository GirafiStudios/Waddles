package com.girafi.waddles;

import com.girafi.waddles.init.PenguinRegistry;
import com.girafi.waddles.init.WaddlesSounds;

public class CommonClass {

    public static void init() {
        WaddlesSounds.load();
        PenguinRegistry.load();
    }
}