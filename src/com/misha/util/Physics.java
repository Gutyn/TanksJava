package com.misha.util;

import com.misha.interfaces.EntityA;
import com.misha.interfaces.EntityB;

public class Physics {

	public static boolean collision(EntityA enta, EntityB entb) {
		if (enta.getAura().intersects(entb.getAura()))
			return true;
		return false;
	}

}
