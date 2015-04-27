package com.misha.objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import com.misha.interfaces.EntityC;
import com.misha.interfaces.Tank;

public class Bullet implements EntityC {
	private Image core;
	private int x;
	private int y;
	private int flameX = 900, flameY = 900;
	private ImageObserver observer;
	private String dir;
	private Image flame;
	private Bullet bullet = this;
	BlockingQueue<RegularTankRandom> tankList;

	public Bullet(int x, int y, ImageObserver observer, Tank tank, String dir,
			BlockingQueue<RegularTankRandom> tankList) {
		this.tankList = tankList;
		this.observer = observer;
		this.x = x;
		this.y = y;
		this.dir = dir;

		File coreFile = new File("images/core.png");
		File flameFile = new File("images/flame.png");
		try {
			core = ImageIO.read(coreFile);
			flame = ImageIO.read(flameFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		g.drawImage(flame, flameX, flameY, observer);
		switch (dir) {
		case "up":
			g.drawImage(core, x - 2, y - 20, observer);
			break;
		case "down":
			g.drawImage(core, x - 2, y + 20, observer);
			break;
		case "left":
			g.drawImage(core, x - 20, y - 2, observer);
			break;
		case "right":
			g.drawImage(core, x + 20, y - 2, observer);
			break;

		default:
			break;
		}

	}

	public void fire() {
		switch (dir) {
		case "up":
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 400; i++) {
						Iterator<RegularTankRandom> it = tankList.iterator();
						while (it.hasNext()) {
							RegularTankRandom reg = it.next();
							if (!bullet.getAura().intersects(
									reg.getAuraBullet())) {
								y -= 4;
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								try {
									reg.shot();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								int tmp = y;
								y = -900;
								new FlameThread(x, tmp).start();
								break;
							}
						}
					}
				}
			}).start();

			break;
		case "down":
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 400; i++) {
						Iterator<RegularTankRandom> it = tankList.iterator();
						while (it.hasNext()) {
							RegularTankRandom reg = it.next();
							if (!bullet.getAura().intersects(
									reg.getAuraBullet())) {
								y += 4;
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								try {
									reg.shot();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								int tmp = y;
								y = 900;
								new FlameThread(x, tmp).start();
								break;
							}
						}
					}
				}
			}).start();
			break;
		case "left":
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 400; i++) {
						Iterator<RegularTankRandom> it = tankList.iterator();
						while (it.hasNext()) {
							RegularTankRandom reg = it.next();

							if (!bullet.getAura().intersects(
									reg.getAuraBullet())) {

								x -= 4;
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								try {
									reg.shot();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								int tmp = x;
								x = -900;
								new FlameThread(tmp, y).start();

								break;
							}
						}
					}
				}
			}).start();

			break;
		case "right":
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 400; i++) {
						Iterator<RegularTankRandom> it = tankList.iterator();
						while (it.hasNext()) {
							RegularTankRandom reg = it.next();
							if (!bullet.getAura().intersects(
									reg.getAuraBullet())) {
								x += 4;
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								try {
									reg.shot();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								int tmp = x;
								x = 900;
								new FlameThread(tmp, y).start();
								break;
							}
						}
					}
				}
			}).start();
			break;

		default:
			break;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Rectangle getAura() {
		return new Rectangle(this.x, this.y, core.getWidth(observer),
				core.getHeight(observer));
	}

	class FlameThread extends Thread {
		int x;
		int y;

		public FlameThread(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void run() {
			flameX = x;
			flameY = y;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			flameY = 900;
			flameX = 900;
			super.run();
		}
	}
}
