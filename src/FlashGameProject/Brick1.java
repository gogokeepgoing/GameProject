package FlashGameProject;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Box extends JFrame {
	Container c = getContentPane();

	class Paddle1 {
		private int x;
		private int y;
		private int width;
		private int height;
		private int speed;
		private boolean movingLeft;
		private boolean movingRight;

		public Paddle1(int x, int y, int width, int height, int speed) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.speed = speed;
		}

		public void moveLeft() {
			x -= speed;
			if (x < 0) {
				x = 0;
			}
		}

		public void moveRight() {
			x += speed;
			if (x > BrickBreaker.WIDTH - width) {
				x = BrickBreaker.WIDTH - width;
			}
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public boolean isMovingLeft() {
			return movingLeft;
		}

		public boolean isMovingRight() {
			return movingRight;
		}

		public void setMovingLeft(boolean movingLeft) {
			this.movingLeft = movingLeft;
		}

		public void setMovingRight(boolean movingRight) {
			this.movingRight = movingRight;
		}
	}

	class Ball {
		private double x;
		private double y;
		private double dx;
		private double dy;
		private int size;

		public Ball(double x, double y, double dx, double dy, int size) {
			this.x = x;
			this.y = y;
			this.dx = dx;
			this.dy = dy;
			this.size = size;
		}

		public void move() {
			x += dx;
			y += dy;

			if (x < 0) {
				x = 0;
				dx = -dx;
			}
			if (x > BrickBreaker.WIDTH - size) {
				x = BrickBreaker.WIDTH - size;
				dx = -dx;
			}
			if (y < 0) {
				y = 0;
				dy = -dy;
			}
		}

		public void reverseY() {
			dy = -dy;
		}

		public void reverseX() {
			dx = -dx;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public int getSize() {
			return size;
		}

		public Rectangle getBounds() {
			return new Rectangle((int) x, (int) y, size, size);
		}

		public boolean isColliding(Rectangle other) {
			return getBounds().intersects(other);
		}

	}

	class Brick {
		private int x;
		private int y;
		private int width;
		private int height;
		private boolean destroyed;

		public Brick(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.destroyed = false;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public boolean isDestroyed() {
			return destroyed;
		}

		public void setDestroyed(boolean destroyed) {
			this.destroyed = destroyed;
		}

		public Rectangle getBounds() {
			return new Rectangle(x, y, width, height);
		}

	}

	class BrickBreaker extends JPanel implements KeyListener {

		// 게임 상수
		static final int WIDTH = 400;
		private static final int HEIGHT = 600;
		private static final int PADDLE_WIDTH = 60;
		private static final int PADDLE_HEIGHT = 10;
		private static final int PADDLE_SPEED = 5;
		private static final int BALL_SIZE = 10;
		private static final int BRICK_WIDTH = 40;
		private static final int BRICK_HEIGHT = 20;
		private static final int NUM_BRICKS = 48;

		// 게임 객체
		private Paddle1 paddle;
		private Ball ball;
		private Brick[] bricks;

		/////
		private Image backgroundImage;

		public BrickBreaker() {
			// 패널 설정
			setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
			setFocusable(true);
			requestFocus();
			addKeyListener(this);
			System.out.println("BBBBB");

			/////
			ImageIcon icon = new ImageIcon("Image/bg1.jpg");
			backgroundImage = icon.getImage();

			// 게임 객체 초기화
			paddle = new Paddle1(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_HEIGHT - 10, PADDLE_WIDTH, PADDLE_HEIGHT,
					PADDLE_SPEED);
			ball = new Ball(WIDTH / 2, HEIGHT / 2, 2, -2, BALL_SIZE);
			bricks = new Brick[NUM_BRICKS];
			int index = 0;
			
			for (int row = 0; row < 4; row++) {
				for (int col = 0; col < 12; col++) {
					bricks[index] = new Brick(col * (BRICK_WIDTH + 2) + 30, row * (BRICK_HEIGHT + 2) + 50, BRICK_WIDTH,
							BRICK_HEIGHT);
					index++;
					
				}
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//

			Dimension size = getSize();
			g.drawImage(backgroundImage, 0, 0, size.width, size.height, null);
			// 패들 그리기
			g.setColor(Color.red);
			g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

			// 공 그리기
			g.setColor(Color.magenta);
			g.fillOval((int) ball.getX(), (int) ball.getY(), ball.getSize(), ball.getSize());

			// 벽돌 그리기
			for (int i = 0; i < bricks.length; i++) {
				if (!bricks[i].isDestroyed()) {
					g.setColor(Color.ORANGE);
					g.fillRect(bricks[i].getX(), bricks[i].getY(), bricks[i].getWidth(), bricks[i].getHeight());
				}
			}
		}

		public void update() {
			// 패들 이동
			if (paddle.isMovingLeft()) {
				paddle.moveLeft();
			}
			if (paddle.isMovingRight()) {
				paddle.moveRight();
			}

			// 공 이동
			ball.move();

			// 공과 패들 충돌 검사
			if (ball.isColliding(new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight()))) {
				ball.reverseY();
			}

			// 공과 벽돌 충돌 검사
			for (int i = 0; i < bricks.length; i++) {
				if (!bricks[i].isDestroyed() && ball.isColliding(bricks[i].getBounds())) {
					// 벽돌과 공의 충돌 감지
					if (ball.isColliding(bricks[i].getBounds())) {
						ball.reverseY();
						bricks[i].setDestroyed(true);
					}
				}
			}
			// 화면 갱신
			repaint();
		}

		// 키보드 이벤트 처리
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				paddle.setMovingLeft(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				paddle.setMovingRight(true);
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				paddle.setMovingLeft(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				paddle.setMovingRight(false);
			}
		}

		public void keyTyped(KeyEvent e) {
		}
	}

	Box() {

		// 게임 윈도우 생성
		setTitle("벽돌깨기");
		// JFrame frame = new JFrame("Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setResizable(false);
		System.out.println("CCCCC");

		// 게임 패널 생성
		BrickBreaker panel = new BrickBreaker();
		setContentPane(panel);
		pack();
		setLocationRelativeTo(null);

		// 게임 시작
		setVisible(true);
		
		while (true) {
			panel.update();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
		
	

	}
}

public class Brick1 {

	public Brick1() {
		System.out.println("AAAAA");
		Box box = new Box();
	}

	public static void main(String[] args) {
		// System.out.println("GGG");
		// Box box = new Box();
		new Brick1();
	}

}