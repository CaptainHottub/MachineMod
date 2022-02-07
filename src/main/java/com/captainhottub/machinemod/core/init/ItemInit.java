package com.captainhottub.machinemod.core.init;

import com.captainhottub.machinemod.MachineMod;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ItemInit {
	private ItemInit() {}
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MachineMod.MODID);
	
	public static final RegistryObject<Item> COMPRESSED_OBSIDIAN = ITEMS.register("compressed_obsidian", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> DIAMOND_CORE = ITEMS.register("diamond_core", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> ENDER_CORE = ITEMS.register("ender_core", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> FAN_BLADE = ITEMS.register("fan_blade", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> HEAVY_CASING = ITEMS.register("heavy_casing", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> HEAVY_GEAR = ITEMS.register("heavy_gear", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> LASER_FOCUS = ITEMS.register("laser_focus", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> LAVA_CORE = ITEMS.register("lava_core", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> OBSIDIAN_BLADE = ITEMS.register("obsidian_blade", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	public static final RegistryObject<Item> OBSIDIAN_GEAR = ITEMS.register("obsidian_gear", 
			() -> new Item(new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	
	
	
	
	// Block Items
	public static final RegistryObject<BlockItem> FARMER_BLOCK_ITEM = ITEMS.register("farmer_block", 
			() -> new BlockItem(BlockInit.FARMER_BLOCK.get(), 
					new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	
	public static final RegistryObject<BlockItem> SPRINKLER_BLOCK_ITEM = ITEMS.register("sprinkler_block", 
			() -> new BlockItem(BlockInit.SPRINKLER_BLOCK.get(), 
					new Item.Properties().tab(MachineMod.MACHINE_TAB)));
	
	
	
	
}
