package com.misha.util;

import com.misha.objects.RegularTank;
import com.misha.objects.RegularTankRandom;

public class ObjectHolder {
	public final RegularTank REGULAR_TANK;
	public final RegularTankRandom REGULAR_TANK_RANDOM;

	public ObjectHolder(RegularTank regTank, RegularTankRandom regTankRandom) {
		this.REGULAR_TANK = regTank;
		this.REGULAR_TANK_RANDOM = regTankRandom;
	}
	
}
