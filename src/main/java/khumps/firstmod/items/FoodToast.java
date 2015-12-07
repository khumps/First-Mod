package khumps.firstmod.items;

import khumps.firstmod.Strings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class FoodToast extends ItemFood {
	public FoodToast(int food, float saturation, boolean wolfFood, String name) {
		super(food, saturation, wolfFood);
		setUnlocalizedName(Strings.MODID + "_" + name).setTextureName(Strings.MODID + ":" + name)
				.setCreativeTab(CreativeTabs.tabFood);
	}

}
