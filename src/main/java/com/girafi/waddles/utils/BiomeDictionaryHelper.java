package com.girafi.waddles.utils;

import net.minecraftforge.common.BiomeDictionary;

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
     * Converts a #BiomeDictionary.Type array to a String array
     * Useful for config options
     *
     * @param types array of {@link BiomeDictionary.Type}
     * @return String array based on the input
     */
    public static String[] toStringArray(BiomeDictionary.Type[] types) {
        String[] strings = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            BiomeDictionary.Type type = types[i];
            strings[i] = type.getName();
        }
        return strings;
    }

    /**
     * Converts a String array to a {@link BiomeDictionary.Type} array
     *
     * @param strings string array containing valid #BiomeDictionary.Types
     * @return {@link BiomeDictionary.Type} based on the string input
     */
    public static BiomeDictionary.Type[] toBiomeTypeArray(String[] strings) {
        BiomeDictionary.Type[] types = new BiomeDictionary.Type[strings.length];
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            types[i] = getType(string);
        }
        return types;
    }
}