package khumps.firstmod;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import khumps.firstmod.tile.TileRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		System.out.println("test");
		TileRegistry.init();
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}
}
