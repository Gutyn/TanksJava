package com.misha.functions;

import java.util.Random;

import com.misha.objects.RegularTankRandom;

public class TankThreadRandomController extends Thread {
	private TankThreadRandom tankThreadRandom;
	private RegularTankRandom regularTankRandom;

	public TankThreadRandomController(RegularTankRandom regularTankRandom,
			TankThreadRandom tankThreadRandom) {
		this.regularTankRandom = regularTankRandom;
		this.tankThreadRandom = tankThreadRandom;
	}

	@Override
	public void run() {

		while (!regularTankRandom.isDead()) {
			Random random = new Random();
			int dir = random.nextInt(4);
			switch (dir) {
			case 0:
				tankThreadRandom.setDirection("right");
				regularTankRandom.changeImage("right");
				break;
			case 1:
				tankThreadRandom.setDirection("left");
				regularTankRandom.changeImage("left");
				break;
			case 2:
				tankThreadRandom.setDirection("up");
				regularTankRandom.changeImage("up");
				break;
			case 3:
				tankThreadRandom.setDirection("down");
				regularTankRandom.changeImage("down");
				break;

			default:
				break;
			}

			super.run();
			try {
				Random time = new Random();
				Thread.sleep(time.nextInt(2000));
				regularTankRandom.fire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
