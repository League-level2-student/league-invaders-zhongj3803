import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	int currentState = MENU;
	Font titleFont;
	Font smallFont;
	Timer frameDraw;
	Timer alienSpawn;
	Rocketship rocketship = new Rocketship(250, 700, 50, 50);
	ObjectManager objectManager = new ObjectManager(rocketship);

	@Override
	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		}
	}

	GamePanel() {
		titleFont = new Font("Arial", Font.PLAIN, 48);
		smallFont = new Font("Arial", Font.PLAIN, 20);
		frameDraw = new Timer(1000 / 60, this);
		frameDraw.start();
		if (needImage) {
			loadImage("space.png");
		}
	}

	void updateMenuState() {

	}

	void updateGameState() {
		objectManager.update();
		if (!rocketship.isActive) {
			currentState=END;
		}
	}

	void updateEndState() {

	}

	void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.GREEN);
		g.drawString("LEAGUE INVADERS", 20, 200);
		g.setFont(smallFont);
		g.drawString("Press ENTER to start", 150, 400);
		g.drawString("Press SPACE for instructions", 110, 500);
	}

	void drawGameState(Graphics g) {
		if (gotImage) {
			g.drawImage(image, 0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT, null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		}
		objectManager.draw(g);
	}

	void drawEndState(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.red);
		g.drawString("GAME OVER", 100, 200);
		g.setFont(smallFont);
		g.drawString("You killed  enemies", 150, 400);
		g.drawString("Press ENTER to restart", 110, 500);
	}

	void startGame() {
		alienSpawn = new Timer(1000, objectManager);
		alienSpawn.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();
		} else if (currentState == END) {
			updateEndState();
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
			} else {
				currentState++;
			}
		}
		if (currentState == GAME) {
			startGame();
			if (e.getKeyCode() == KeyEvent.VK_UP && rocketship.y > 0) {
				rocketship.up();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN && rocketship.y < 750) {
				rocketship.down();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT && rocketship.x > 0) {
				rocketship.left();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT && rocketship.x < 450) {
				rocketship.right();
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				objectManager.addProjectile(rocketship.getProjectile());
			}

		}
		if (currentState == END) {
			alienSpawn.stop();
		}
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}