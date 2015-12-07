package khumps.firstmod.tile;

import khumps.firstmod.utils.MachineUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class FurnaceSlotSet {
	public final int SLOTNUM;
	public static final int FUELSLOT = 0;
	public static final int INSLOT = 1;
	public static final int OUTSLOT = 2;
	private int fuel;
	private int in;
	private int out;
	protected TileFurnace furnace;
	protected boolean isSmelting;
	private int TIMETOSMELT;
	private int smeltTime;
	public static final int NUMSLOTS = 3;

	public FurnaceSlotSet(int fuel, int in, int out, TileFurnace tileFurnace, int smeltTime, int slotNum) {
		this.fuel = fuel;
		this.in = in;
		this.out = out;
		this.furnace = tileFurnace;
		TIMETOSMELT = smeltTime;
		SLOTNUM = slotNum;
	}

	public void update() {
	}

	public boolean stuffToSmelt() {
		return furnace.inventory[in] != null;
	}

	private void cookItem() {

	}

	public boolean isValidForSlot(int slot, ItemStack stack) {
		if (slot == fuel) {
			return MachineUtils.isFuel(stack);
		}
		if (slot == in) {
			return FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
		}
		if (slot == out) {
			return false;
		}
		return true;
	}

	public boolean isSmelting() {
		return isSmelting;
	}

	public boolean canSmelt() {
		if (!furnace.isBurning && isSmelting) {
			isSmelting = false;
			smeltTime = TIMETOSMELT;
		}
		
		
		
		if (furnace.isBurning)
			return furnace.inventory[in] != null;
		return false;
	}

	private boolean canBeSmelted() {
		return furnace.inventory[in] != null && getSmeltingResult(furnace.inventory[in]) != null;
	}

	private static ItemStack getSmeltingResult(ItemStack item) {
		return FurnaceRecipes.smelting().getSmeltingResult(item);
	}

	public boolean hasSlot(int slot) {
		return slot == fuel || slot == in || slot == out;
	}

	/*
	 * private boolean addToStack(int slot, ItemStack item, int numItems) {
	 * 
	 * }
	 */

	public static boolean sameItem(ItemStack a, ItemStack b) {
		if (a == null) {
			return b == null;
		}
		if (b != null) {
			return a.getItem() == b.getItem();
		}
		return false;
	}

	public int getSmeltTime() {
		return smeltTime;
	}

	public int getMaxSmeltTime() {
		return TIMETOSMELT;
	}

	public void setSmeltTime(int smeltTime) {
		this.smeltTime = smeltTime;
	}

	public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound set = new NBTTagCompound();
		set.setInteger("fuel", fuel);
		set.setInteger("in", in);
		set.setInteger("out", out);
		// set.setTag("furnace", compound);
		set.setBoolean("isSmelting", isSmelting);
		set.setInteger("TIMETOSMELT", TIMETOSMELT);
		set.setInteger("smeltTime", smeltTime);
		compound.setTag(SLOTNUM + "", set);

	}

	public void readFromNBT(NBTTagCompound compound, int slotNum) {
		NBTTagCompound set = compound.getCompoundTag(slotNum + "");
		fuel = set.getInteger("fuel");
		in = set.getInteger("in");
		out = set.getInteger("out");
		// set.setTag("furnace", compound);
		isSmelting = set.getBoolean("isSmelting");
		TIMETOSMELT = set.getInteger("TIMETOSMELT");
		smeltTime = set.getInteger("smeltTime");
	}
}
