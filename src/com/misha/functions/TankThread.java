package com.misha.functions;

import java.util.concurrent.BlockingQueue;

import com.misha.objects.RegularTank;
import com.misha.objects.RegularTankRandom;

public class TankThread extends Thread {
	public static volatile String direction;
	public String dir;
	private RegularTank regularTank;
	private BlockingQueue<RegularTankRandom> list;

	public TankThread(RegularTank regularTank,
			BlockingQueue<RegularTankRandom> list) {
		this.regularTank = regularTank;
		this.list = list;
	}

	@Override
	public void run() {
		while (true) {
			if (!regularTank.isDead()) {
				if (direction != null) {
					dir = direction;
					regularTank.moveTank(direction);
				}

			} else {
				System.out.println(dir);
				regularTank.setDead(dir);
				try {
					Thread.sleep(5000);
					regularTank.setTankX(900);
					regularTank.setTankY(900);
					regularTank.setBarX(900);
					regularTank.setBarY(900);
					regularTank.removeAll();
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
}
