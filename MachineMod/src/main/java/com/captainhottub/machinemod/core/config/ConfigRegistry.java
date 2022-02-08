package com.captainhottub.machinemod.core.config;

import com.captainhottub.machinemod.MachineMod;
import com.captainhottub.machinemod.common.entity.SprinklerBlockEntity;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigRegistry {

  private static final ForgeConfigSpec.Builder CFG = new ForgeConfigSpec.Builder();
  private static final ForgeConfigSpec.Builder CFGC = new ForgeConfigSpec.Builder();
  private static ForgeConfigSpec COMMON_CONFIG;
  private static ForgeConfigSpec CLIENT_CONFIG;
  // Defaults
  private static final String WALL = "####################################################################################";
  static {
    initConfig();
    initClientConfig();
  }

 
  private static void initConfig() {
    CFG.comment(WALL, "Features with configurable properties are split into categories", WALL).push(MachineMod.MODID);

    CFG.push("sprinkler");
    SprinklerBlockEntity.COOLDOWN = CFG.comment("Tick rate.  20 will fire one block per second").defineInRange("ticks", 100, 1, 100);
    
    SprinklerBlockEntity.GROWTH_CHANCE = CFG.comment("Growth Chance.  30% chance the block will grow").defineInRange("percent", 0.30, 0.01, 0.30);

    SprinklerBlockEntity.RANGE = CFG.comment("Sprinkler range.  9, it will cover a 9x9 area with sprinkler in the middle").defineInRange("blocks", 9, 1, 9);

    
    CFG.pop(); // sprinkler
   
    CFG.pop(); //blocks
    COMMON_CONFIG = CFG.build();
    initClientConfig();
  }

  private static void initClientConfig() {
	  CFGC.comment(WALL, "Client-side properties", WALL)
      .push(MachineMod.MODID);
	  
	  CFGC.pop();
	  CLIENT_CONFIG = CFGC.build();
  }
 
  public static void setup() {
    final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(MachineMod.MODID + ".toml"))
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();
    configData.load();
    COMMON_CONFIG.setConfig(configData);
  }

  public static void setupClient() {
    final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(MachineMod.MODID + "-client.toml"))
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();
    configData.load();
    CLIENT_CONFIG.setConfig(configData);
  }




}
