package com.misha.functions;

import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

import com.misha.objects.BattleField;
import com.misha.objects.RegularTank;
import com.misha.objects.RegularTankRandom;
import com.misha.util.Physics;

public class TankThreadRandom extends Thread {
	public volatile String direction;
	private RegularTank regularTank;
	BlockingQueue<RegularTankRandom> tankList;
	RegularTankRandom reg;
	BattleField bf;

	public TankThreadRandom(RegularTankRandom reg, RegularTank regularTank) {
		this.reg = reg;
		this.regularTank = regularTank;
	}

	@Override
	public void run() {
		while (true) {
			if (!reg.isDead()) {
				if (direction != null && !Physics.collision(regularTank, reg)) {
					reg.moveTank(direction);
				} else {
				}
			} else {
				reg.setDead(direction);
				try {
					Thread.sleep(5000);
					reg.setTankX(900);
					reg.setTankY(900);
					reg.setBarX(900);
					reg.setBarY(900);
					reg.removeAll();
					Thread.interrupted();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			super.run();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
