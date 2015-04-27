package com.misha.objects;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.misha.functions.TankThreadRandom;
import com.misha.functions.TankThreadRandomController;

public class BattleField extends JPanel implements Runnable, ActionListener {
	private static final long serialVersionUID = 1L;
	private volatile Image field;
	private RegularTank regularTank;
	BlockingQueue<RegularTankRandom> list;

	public BattleField(RegularTank regularTank,
			BlockingQueue<RegularTankRandom> list) {
		add(addButton());
		this.list = list;
		this.regularTank = regularTank;
		File fieldFile = new File("images/forest.png");
		try {
			field = ImageIO.read(fieldFile);
		} catch (IOException e) {
			System.out.println("Field was not painted");
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(field, 30, 30, this);
		regularTank.paintComponent(g);
		Iterator<RegularTankRandom> it = list.iterator();
		while (it.hasNext())
			it.next().paintComponent(g);
		for (int i = 0; i < regularTank.getList().size(); i++)
			regularTank.getList().get(i).render(g);
	}

	@Override
	public void run() {
		while (true) {
			this.repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addTank(int x, int y) throws InterruptedException {
		list.put(new RegularTankRandom(x, y));
	}

	public JButton addButton() {
		JButton b = new JButton("Add a tank");
		b.setLayout(new BorderLayout());
		b.setFocusable(false);
		b.addActionListener(this);
		b.setLocation(400, 200);
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Random r = new Random();
		RegularTankRandom t = new RegularTankRandom(r.nextInt(400),
				r.nextInt(400));
		t.setRegTank(regularTank);
		TankThreadRandom tankThreadRandom = new TankThreadRandom(t, regularTank);
		try {
			list.put(t);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Thread randomTankController = new TankThreadRandomController(t,
				tankThreadRandom);
		tankThreadRandom.start();
		randomTankController.start();
	}

}
