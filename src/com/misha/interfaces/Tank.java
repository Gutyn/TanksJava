package com.misha.interfaces;

import java.awt.Rectangle;

public interface Tank {

	public void moveTank(String direction);

	public void fire();

	public void changeImage(String choice);

	public Rectangle getAura();
	
	public Rectangle getAuraBullet();

}
