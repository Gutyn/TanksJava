package com.misha.objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.misha.interfaces.EntityB;
import com.misha.interfaces.Tank;
import com.misha.util.Physics;

public class RegularTankRandom extends JPanel implements Tank, EntityB {

	private static final long serialVersionUID = 1L;
	private Image tankDown, tankUp, tankLeft, tankRight, imgTarget, field;
	private Image bar12, bar11, bar10, bar9, bar8, bar7, bar6, bar5, bar4,
			bar3, bar2, bar1, barDead, barTarget;
	private Image deadUp, deadRight, deadDown, deadLeft;
	private boolean isFired;
	private int tankX, tankY, coreX, coreY;
	private EnemyBullet tempBullet;
	private static String dir = "up";
	private LinkedList<EnemyBullet> list;
	private int barX = tankX, barY = tankY;
	private RegularTankRandom me = this;
	private RegularTank regTank;
	private BlockingQueue<Image> imageList = new ArrayBlockingQueue<Image>(13);

	public RegularTankRandom(int x, int y) {
		tankX = x;
		tankY = y;
		list = new LinkedList<EnemyBullet>();
		isFired = false;
		File tankLeftFile = new File("images/tankLeft.png");
		File tankRightFile = new File("images/tankRight.png");
		File tankUpFile = new File("images/tankUp.png");
		File tankDownFile = new File("images/tankDown.png");
		File fieldFile = new File("images/forest.png");

		File bar12file = new File("images/status_bar/12.png");
		File bar11file = new File("images/status_bar/11.png");
		File bar10file = new File("images/status_bar/10.png");
		File bar9file = new File("images/status_bar/9.png");
		File bar8file = new File("images/status_bar/8.png");
		File bar7file = new File("images/status_bar/7.png");
		File bar6file = new File("images/status_bar/6.png");
		File bar5file = new File("images/status_bar/5.png");
		File bar4file = new File("images/status_bar/4.png");
		File bar3file = new File("images/status_bar/3.png");
		File bar2file = new File("images/status_bar/2.png");
		File bar1file = new File("images/status_bar/1.png");
		File barDeadfile = new File("images/status_bar/dead.png");

		File deadUpFile = new File("images/deadUp.png");
		File deadDownFile = new File("images/deadDown.png");
		File deadRightFile = new File("images/deadRight.png");
		File deadLeftFile = new File("images/deadLeft.png");
		try {
			field = ImageIO.read(fieldFile);
			tankLeft = ImageIO.read(tankLeftFile);
			tankRight = ImageIO.read(tankRightFile);
			tankUp = ImageIO.read(tankUpFile);
			tankDown = ImageIO.read(tankDownFile);
			bar12 = ImageIO.read(bar12file);
			bar11 = ImageIO.read(bar11file);
			bar10 = ImageIO.read(bar10file);
			bar9 = ImageIO.read(bar9file);
			bar8 = ImageIO.read(bar8file);
			bar7 = ImageIO.read(bar7file);
			bar6 = ImageIO.read(bar6file);
			bar5 = ImageIO.read(bar5file);
			bar4 = ImageIO.read(bar4file);
			bar3 = ImageIO.read(bar3file);
			bar2 = ImageIO.read(bar2file);
			bar1 = ImageIO.read(bar1file);
			barDead = ImageIO.read(barDeadfile);

			deadUp = ImageIO.read(deadUpFile);
			deadDown = ImageIO.read(deadDownFile);
			deadRight = ImageIO.read(deadRightFile);
			deadLeft = ImageIO.read(deadLeftFile);

		} catch (IOException e) {
			System.out.println("The image was not uploaded!");
		}
		try {
			imageList.put(bar11);
			imageList.put(bar10);
			imageList.put(bar9);
			imageList.put(bar8);
			imageList.put(bar7);
			imageList.put(bar6);
			imageList.put(bar5);
			imageList.put(bar4);
			imageList.put(bar3);
			imageList.put(bar2);
			imageList.put(bar1);
			imageList.put(barDead);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imgTarget = tankUp;
		barTarget = bar12;
	}

	public void moveTank(String direction) {

		switch (direction) {
		case "up":
			dir = "up";
			if (tankY > 30 + imgTarget.getHeight(me) / 2 + 5) {
				tankY -= 3;
				if (Physics.collision(regTank, this)) {
					tankY += 3;
				}

				repaint();
			}
			barX = tankX - 12;
			barY = tankY - 30;

			break;

		case "down":
			dir = "down";

			if (tankY < 30 + field.getHeight(this) - imgTarget.getHeight(this)
					/ 2 - 5) {
				tankY += 3;
				if (Physics.collision(regTank, this)) {
					tankY -= 3;
				}
				repaint();
			}

			barX = tankX - 12;
			barY = tankY - 30;

			break;

		case "left":
			dir = "left";

			if (tankX > 30 + imgTarget.getWidth(this) / 2 + 5) {
				tankX -= 3;
				if (Physics.collision(regTank, this)) {
					tankX += 3;
				}
				repaint();
			}
			barX = tankX - 12;
			barY = tankY - 20;

			break;

		case "right":
			dir = "right";

			if (tankX < 30 + field.getWidth(this) - imgTarget.getWidth(this)
					/ 2 - 5) {
				tankX += 3;
				if (Physics.collision(regTank, this)) {
					tankX -= 3;
				}
				repaint();
			}
			barX = tankX - 12;
			barY = tankY - 20;
			break;

		default:
			break;
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(imgTarget, tankX - imgTarget.getWidth(this) / 2, tankY
				- imgTarget.getHeight(this) / 2, this);
		if (tempBullet != null) {
			tempBullet.render(g);
		}
		renderBar(g);
	}

	public void fire() {
		tempBullet = new EnemyBullet(this.tankX, this.tankY, this, dir, regTank);
		list.add(tempBullet);
		tempBullet.fire();
	}

	public void changeImage(String choice) {
		switch (choice) {
		case "left":
			imgTarget = tankLeft;
			repaint();
			break;
		case "right":
			imgTarget = tankRight;
			repaint();
			break;
		case "up":
			imgTarget = tankUp;
			repaint();
			break;
		case "down":
			imgTarget = tankDown;
			repaint();
			break;

		default:
			break;
		}
	}

	public boolean isFired() {
		return isFired;
	}

	public void setFired(boolean isFired) {
		this.isFired = isFired;
	}

	public int getTankX() {
		return tankX;
	}

	public void setTankX(int tankX) {
		this.tankX = tankX;
	}

	public int getTankY() {
		return tankY;
	}

	public void setTankY(int tankY) {
		this.tankY = tankY;
	}

	public int getCoreX() {
		return coreX;
	}

	public void setCoreX(int coreX) {
		this.coreX = coreX;
	}

	public int getCoreY() {
		return coreY;
	}

	public void setCoreY(int coreY) {
		this.coreY = coreY;
	}

	public LinkedList<EnemyBullet> getList() {
		return list;
	}

	public void setList(LinkedList<EnemyBullet> list) {
		this.list = list;
	}

	public Image getImgTarget() {
		return imgTarget;
	}

	public void setImgTarget(Image imgTarget) {
		this.imgTarget = imgTarget;
	}

	public RegularTank getRegTank() {
		return regTank;
	}

	public void setRegTank(RegularTank regTank) {
		this.regTank = regTank;
	}

	@Override
	public Rectangle getAura() {
		return new Rectangle(this.tankX - imgTarget.getWidth(this) / 2,
				this.tankY - imgTarget.getHeight(this) / 2,
				imgTarget.getWidth(this), imgTarget.getHeight(this));
	}

	@Override
	public Rectangle getAuraBullet() {
		return new Rectangle(this.tankX - imgTarget.getWidth(this) / 2,
				this.tankY - imgTarget.getHeight(this) / 2,
				imgTarget.getWidth(this), imgTarget.getHeight(this));
	}

	private void renderBar(Graphics g) {
		g.drawImage(barTarget, barX, barY, this);
	}

	public void shot() throws InterruptedException {
		barTarget = imageList.take();
	}

	public boolean isDead() {
		return imageList.isEmpty();
	}

	public void setDead(String dir) {
		if (dir.equals("up")) {
			imgTarget = deadUp;
		} else if (dir.equals("left")) {
			imgTarget = deadLeft;
		} else if (dir.equals("down")) {
			imgTarget = deadDown;
		} else if (dir.equals("right")) {
			imgTarget = deadRight;
		}

	}

	public int getBarX() {
		return barX;
	}

	public void setBarX(int barX) {
		this.barX = barX;
	}

	public int getBarY() {
		return barY;
	}

	public void setBarY(int barY) {
		this.barY = barY;
	}
}
