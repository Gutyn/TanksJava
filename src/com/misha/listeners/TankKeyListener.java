package com.misha.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.misha.functions.TankThread;
import com.misha.objects.RegularTank;

public class TankKeyListener implements KeyListener {
	RegularTank tank;

	public TankKeyListener(RegularTank tank) {
		this.tank = tank;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			TankThread.direction = "up";
			tank.changeImage("up");
			break;
		case KeyEvent.VK_DOWN:
			TankThread.direction = "down";
			tank.changeImage("down");
			break;
		case KeyEvent.VK_LEFT:
			TankThread.direction = "left";
			tank.changeImage("left");
			break;
		case KeyEvent.VK_RIGHT:
			TankThread.direction = "right";
			tank.changeImage("right");
			break;
		case KeyEvent.VK_SPACE:
			synchronized (this) {
				tank.fire();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		TankThread.direction = null;
	}

}
