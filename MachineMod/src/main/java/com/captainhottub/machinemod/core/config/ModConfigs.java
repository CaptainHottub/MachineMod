package com.captainhottub.machinemod.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ModConfigs {
    public static final ForgeConfigSpec CLIENT;
    public static final ForgeConfigSpec COMMON;
    
    public static final ForgeConfigSpec.ConfigValue<String> test_string;
    public static final ForgeConfigSpec.ConfigValue<Integer> test_integer;


    // For Client

    // Client
    static {
        final var client = new ForgeConfigSpec.Builder();

        //For animations
        client.comment("General configuration options.").push("General");
        test_string = client
                .comment("Test String")
                .define("testString", "Test String, I hope this works");
        test_integer = client
                .comment("Test Integer")
                .define("testInteger", 3);
        
        client.pop();

        CLIENT = client.build();
    }
    // Block 
    public static final ForgeConfigSpec.IntValue SPRINKLER_RANGE;
    public static final ForgeConfigSpec.IntValue SPRINKLER_COOLDOWN;
    public static final ForgeConfigSpec.IntValue SPRINKLER_GROWTH_CHANCE;

  
    // World
    
    
    
    // Common
    static {
        final var common = new ForgeConfigSpec.Builder();
        	
        // Block related configs
        common.comment("General configuration options.").push("General");
                 
        SPRINKLER_RANGE = common
                .comment("The range of the sprinkler.")
                .defineInRange("sprinklerRange", 9, 1, Integer.MAX_VALUE);
        SPRINKLER_COOLDOWN = common
                .comment("The amount of time in ticks between each Sprinkler growth tick.")
                .defineInRange("sprinklerCooldown", 100, 1, Integer.MAX_VALUE);
        SPRINKLER_GROWTH_CHANCE = common
                .comment("The chance that the block will get a growth tick. It is a percentage(%).")
                .defineInRange("sprinklerGrowthChance", 25, 1, Integer.MAX_VALUE);
        
     
        common.pop();
        
        // World related configs
        common.comment("World generation options.").push("World");
        common.pop();

        COMMON = common.build();
    }
}