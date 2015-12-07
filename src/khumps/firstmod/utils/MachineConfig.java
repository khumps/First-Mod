package khumps.firstmod.utils;

import khumps.firstmod.tile.TileBasicMachine;

public class MachineConfig {
	public enum MachineType {
		SMELTING, CRUSHING
	};

	public enum MachineTier {
		BASIC, ADVANCED, ELITE, ULTIMATE
	};

	public static void setMachineType(TileBasicMachine m, MachineType type) {
		m.NUMSLOTS = 2;
		switch (type) {
		case SMELTING: {
			m.EXTRASLOTS = 1;
			m.TOTALPROCESSTIME = 40;
		}
		case CRUSHING: {
			m.EXTRASLOTS = 2;
			m.TOTALPROCESSTIME = 60;
		}
		default:
			break;
		}
	}

	public static void setMachineTier(TileBasicMachine m, MachineTier tier) {
		switch (tier) {
		case BASIC: {
			m.NUMSETS = 1;
		}

		case ADVANCED: {
			m.NUMSETS = 3;
		}

		case ELITE: {
			m.NUMSETS = 5;
		}

		case ULTIMATE: {
			m.NUMSETS = 7;
		}
		}
	}

}
