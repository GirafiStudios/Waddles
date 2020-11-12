package com.girafi.waddles.utils;

import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BiomeDictionaryHelper {

    /**
     * Retrieves a #BiomeDictionary.Type
     * Based on {@link BiomeDictionary.Type#getType(String, BiomeDictionary.Type...)}, but doesn't create a new {@link BiomeDictionary.Type} if the input is not already a {@link BiomeDictionary.Type}
     *
     * @param name The name of this #BiomeDictionary.Type
     * @return An instance of this #BiomeDictionary.Type
     */
    public static BiomeDictionary.Type getType(String name) {
        Map<String, BiomeDictionary.Type> byName = BiomeDictionary.Type.getAll().stream().collect(Collectors.toMap(BiomeDictionary.Type::getName, Function.identity()));
        name = name.toUpperCase();
        return byName.get(name);
    }

    /**
     * Converts a List <? extends String> to a {@link BiomeDictionary.Type} array
     *
     * @param strings string array containing valid #BiomeDictionary.Types
     * @return {@link BiomeDictionary.Type} based on the string input
     */
    public static BiomeDictionary.Type[] toBiomeTypeArray(List<? extends String> strings) {
        BiomeDictionary.Type[] types = new BiomeDictionary.Type[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            types[i] = getType(string);
        }
        return types;
    }
}