package khumps.firstmod.tile;

import khumps.firstmod.utils.MachineUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class FurnaceSlotSetBroken {
	public final int SLOTNUM;
	public static final int FUELSLOT = 0;
	public static final int INSLOT = 1;
	public static final int OUTSLOT = 2;
	private int fuel;
	private int in;
	private int out;
	protected TileFurnaceBroken furnace;
	protected boolean isSmelting;
	private int TIMETOSMELT;
	private int smeltTime;
	public static final int NUMSLOTS = 3;

	public FurnaceSlotSetBroken(int fuel, int in, int out, TileFurnaceBroken furnace, int smeltTime, int slotNum) {
		this.fuel = fuel;
		this.in = in;
		this.out = out;
		this.furnace = furnace;
		TIMETOSMELT = smeltTime;
		SLOTNUM = slotNum;
	}

	/*
	 * public void update() { System.out.println("is smelting" + isSmelting); if
	 * (canSmelt()) { if (isSmelting) { System.out.println("hereasdasdsa"); if
	 * (getSmeltTime() == 0) { cookItem(); isSmelting = false; smeltTime =
	 * TIMETOSMELT; } else setSmeltTime(getSmeltTime() - 1); } else { if
	 * (canBeSmelted()) { if (furnace.isBurning) smeltTime = TIMETOSMELT;
	 * isSmelting = true; } } } }
	 */

	public void update() {
		if (canSmelt()) {
			if (isSmelting) {
				if (smeltTime == 0) {
					cookItem();
					// System.out.println("smelted " + furnace.inventory[in]);
				} else {
					// System.out.println(smeltTime);
					smeltTime--;
				}
			} else {
				// System.out.println("start");
				smeltTime = TIMETOSMELT;
				isSmelting = true;
			}

		} else
			isSmelting = false;
	}

	public boolean stuffToSmelt() {
		return furnace.inventory[in] != null;
	}

	private void cookItem() {
		System.out.println("cook");
		if (canBeSmelted()) {

			if (addToStack(out, getCookedItem(furnace.inventory[in], 1), 1))
				// MachineUtils.decStackSize(furnace.inventory[in], 1);
				furnace.decrStackSize(in, 1);

			// inventory[out] = new ItemStack(Items.arrow, 64);
		}
		furnace.markDirty();
		isSmelting = false;
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
		if (furnace.inventory[in] == null)
			return false;
		if (furnace.isBurning) {
			if (stuffToSmelt()/*
								 * && furnace.inventory[in]. stackSize != 0
								 */) {
				if (furnace.inventory[out] == null
						|| sameItem(getSmeltingResult(furnace.inventory[in]), furnace.inventory[out])) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean canBeSmelted() {
		return furnace.inventory[in] != null && getSmeltingResult(furnace.inventory[in]) != null;
	}

	private static ItemStack getSmeltingResult(ItemStack item) {
		return FurnaceRecipes.smelting().getSmeltingResult(item);
	}

	private ItemStack getCookedItem(ItemStack item, int numItems) {
		ItemStack temp = getSmeltingResult(item);
		temp.stackSize = numItems;
		return temp;
	}

	public boolean hasSlot(int slot) {
		return slot == fuel || slot == in || slot == out;
	}

	private boolean addToStack(int slot, ItemStack item, int numItems) {
		if (furnace.inventory[slot] == null) {
			furnace.inventory[slot] = new ItemStack(item.getItem());
			furnace.inventory[slot].stackSize = numItems;
			return true;
		} else if (furnace.inventory[slot].isItemEqual(item)) {
			furnace.inventory[slot].stackSize += numItems;
			return true;
		}
		return false;
	}

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