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
	private ItemStack currentSmelting;
	private ItemStack[] inventory;

	public FurnaceSlotSet(int fuel, int in, int out, TileFurnace tileFurnace, int smeltTime, int slotNum) {
		this.fuel = fuel;
		this.in = in;
		this.out = out;
		this.furnace = tileFurnace;
		TIMETOSMELT = smeltTime;
		SLOTNUM = slotNum;
		inventory = furnace.inventory;
	}

	public void update() {
		System.out.println(smeltTime);
		if (canKeepSmelting()) {
			if (smeltTime == 0)
				cookItem();
			else
				smeltTime--;
		} else {
			if (grabItemForSmelting()) {
				isSmelting = true;
				System.out.println("HERE");
				smeltTime = TIMETOSMELT;
			}
		}

	}

	/**
	 * pulls item from in slot and stores it while it is being smelted
	 * 
	 * @return
	 */
	private boolean grabItemForSmelting() {
		System.out.println(stuffToSmelt());
		if (stuffToSmelt() && sameItem(getSmeltingResult(inventory[in]), inventory[out])) {
			System.out.println("grab");
			currentSmelting = new ItemStack(inventory[in].getItem());
			furnace.decrStackSize(in, 1);
			return true;
		}
		return false;
	}

	/**
	 * checks to make sure that there is still an item to smelt and that the
	 * furnace is still burning
	 * 
	 * @return
	 */
	public boolean canKeepSmelting() {
		System.out.println(currentSmelting);
		return currentSmelting != null && furnace.isBurning;
	}

	/**
	 * Checks that there is still stuff to smelt
	 * 
	 * @return inventory[in] != null
	 */
	public boolean stuffToSmelt() {
		return inventory[in] != null;
	}

	private void cookItem() {
		addToStack(out, getSmeltingResult(currentSmelting), 1);
		currentSmelting = null;
		isSmelting = false;
	}

	private boolean addToStack(int slot, ItemStack item, int numItems) {
		if (furnace.inventory[slot] == null) {
			furnace.inventory[slot] = new ItemStack(item.getItem());
			furnace.inventory[slot].stackSize = numItems;
			furnace.markDirty();
			return true;
		} else if (furnace.inventory[slot].isItemEqual(item)) {
			furnace.inventory[slot].stackSize += numItems;
			furnace.markDirty();
			return true;
		}
		return false;
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
		return b == null;
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
