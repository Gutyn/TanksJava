package com.misha.functions;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.misha.listeners.TankKeyListener;
import com.misha.objects.BattleField;
import com.misha.objects.RegularTank;
import com.misha.objects.RegularTankRandom;

public class Main {
	private static JFrame frame;
	private static RegularTank regularTank;
	private static RegularTankRandom regularTankRandom1;
	private static RegularTankRandom regularTankRandom2;
	private static RegularTankRandom regularTankRandom3;
	private static TankThread tankThread;
	private static BattleField field;

	public static void main(String[] args) {
		frame = new JFrame();
		regularTankRandom1 = new RegularTankRandom(80, 120);
		regularTankRandom2 = new RegularTankRandom(100, 90);
		regularTankRandom3 = new RegularTankRandom(90, 80);

		BlockingQueue<RegularTankRandom> bq = new ArrayBlockingQueue<>(10);
		try {
			bq.put(regularTankRandom1);
			bq.put(regularTankRandom2);
			bq.put(regularTankRandom3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		regularTank = new RegularTank(bq);
		field = new BattleField(regularTank, bq);
		Thread start = new Thread(field);
		start.start();
		ExecutorService executor = Executors.newFixedThreadPool(6);
		Iterator<RegularTankRandom> i = bq.iterator();
		while (i.hasNext()) {
			RegularTankRandom t = i.next();
			t.setRegTank(regularTank);
			TankThreadRandom tankThreadRandom = new TankThreadRandom(t,
					regularTank);
			Thread randomTankController = new TankThreadRandomController(t,
					tankThreadRandom);
			executor.execute(tankThreadRandom);
			executor.execute(randomTankController);
		}
		tankThread = new TankThread(regularTank, bq);
		tankThread.start();
		executor.shutdown();
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(field);
		frame.setVisible(true);
		frame.addKeyListener(new TankKeyListener(regularTank));
	}

}
