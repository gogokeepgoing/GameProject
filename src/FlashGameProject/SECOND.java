package FlashGameProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import FlashGameProject.GamePanel.MonsterThread;

class MyTimer extends Thread {
   JLabel timerLa;
   JLabel scoreLa;
   int score = 0;

   public MyTimer(JLabel timerLa, JLabel scoreLa) {
      this.timerLa = timerLa;
      this.scoreLa = scoreLa;
   }

   @Override
   public void run() {
      int n = 0;
      while (true) {
         timerLa.setText(Integer.toString(n));
         score += 1000;
         scoreLa.setText(Integer.toString(score));

         try {
            sleep(1000);
         } catch (InterruptedException e) {
            return;
         }
         n++;

         if (n == 13) {
            // JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(timerLa);
            // frame.dispose();
            // new FOUR();
         }
      }
   }
}

class GamePanel extends JPanel {

   private Image backgroundImage;

   /////////////////////////////////////////////

   ///////////////////////////////////////
   public GamePanel() {
      try {
         // 이미지 파일을 읽어와서 배경화면으로 사용할 이미지로 설정합니다.
         BufferedImage originalImage = ImageIO.read(new File("Image/space.jpg"));
         backgroundImage = originalImage.getScaledInstance(getPreferredSize().width, getPreferredSize().height,
               Image.SCALE_DEFAULT);
      } catch (IOException e) {
         e.printStackTrace();
      }
      MonsterThread th = new MonsterThread();
      th.start();

   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      // 이미지를 그려서 배경화면으로 사용
      g.drawImage(backgroundImage, 0, 0, null);
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(800, 600);
   }

   class MonsterThread extends Thread {

      JLabel[] mon = new JLabel[9];
      ImageIcon[] icon = new ImageIcon[9];

      JLabel[] mon1 = new JLabel[9];
      ImageIcon[] icon1 = new ImageIcon[9];

      JLabel[] mon2 = new JLabel[9];
      ImageIcon[] icon2 = new ImageIcon[9];

      JLabel[] mon3 = new JLabel[9];
      ImageIcon[] icon3 = new ImageIcon[9];

      JLabel[] mon4 = new JLabel[9];
      ImageIcon[] icon4 = new ImageIcon[9];

      JLabel New = new JLabel();
      ImageIcon newjeans = new ImageIcon("Image/Boss.png");

      JLabel Plane = new JLabel();
      ImageIcon p = new ImageIcon("Image/plane.png");
      private static final long serialVersionUID = 1L;
      private static final int FLYING_UNIT = 10;

      private Timer timer;
      private int dx = 0;
      private int dy = 0;
      ////////////////////////
      public TargetThread targetThread = null;
      BubbleThread th;
      public TargetThread targetThread1 = null;
      public TargetThread targetThread2 = null;
      public TargetThread targetThread3 = null;
      public TargetThread targetThread4 = null;
      public TargetThread targetThread5 = null;
      /////
      private volatile boolean running = true;
      /////////////
      int pp = 0;

      public MonsterThread() {
         New.setIcon(newjeans);
         New.setSize(340, 340);
         New.setLocation(80, -400);
         add(New);
         //////// 비행기
         Plane.setIcon(p);
         Plane.setSize(50, 50);
         Plane.setLocation(180, 530);
         ///////////////////// 총알

         GamePanel.this.repaint();
         ///////////////////////////////
         add(Plane);
         ////////////////////////////////
         // for(int i=0; i<mon.length; i++) {
         // targetThread = new TargetThread(mon[i]);
         // targetThread.start();
         // }
         targetThread = new TargetThread(mon[0]);
         targetThread.start();

         for (int i = 0; i < mon1.length; i++) {
            targetThread1 = new TargetThread(mon1[i]);
            targetThread1.start();
         }
         for (int i = 0; i < mon2.length; i++) {
            targetThread2 = new TargetThread(mon2[i]);
            targetThread2.start();
         }
         for (int i = 0; i < mon3.length; i++) {
            targetThread3 = new TargetThread(mon3[i]);
            targetThread3.start();
         }
         for (int i = 0; i < mon4.length; i++) {
            targetThread4 = new TargetThread(mon4[i]);
            targetThread4.start();
         }
         targetThread5 = new TargetThread(New);
         targetThread5.start();
         //////////////////////////////////
         Plane.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
               // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
               int keyCode = e.getKeyCode();

               switch (keyCode) {
               case KeyEvent.VK_UP:
               case KeyEvent.VK_DOWN:
                  dy = 0;
                  break;
               case KeyEvent.VK_RIGHT:
               case KeyEvent.VK_LEFT:
                  dx = 0;
                  break;
               }
            }

            @Override
            public void keyPressed(KeyEvent e) {
               int keyCode = e.getKeyCode();
               BulletThread bulletThread = null;
               BulletThread bulletThread1 = null;
               BulletThread bulletThread2 = null;
               BulletThread bulletThread3 = null;
               BulletThread bulletThread4 = null;
               BulletThread1 boss = null;
               switch (keyCode) {
               case KeyEvent.VK_UP:
                  dy = -FLYING_UNIT;
                  break;
               case KeyEvent.VK_DOWN:
                  dy = FLYING_UNIT;
                  break;
               case KeyEvent.VK_RIGHT:
                  dx = FLYING_UNIT;
                  break;
               case KeyEvent.VK_LEFT:
                  dx = -FLYING_UNIT;
                  break;
               ////////////////////////
               case KeyEvent.VK_SPACE:
                  BubbleThread th = new BubbleThread(Plane.getX() + 20, Plane.getY());
                  th.start();
                  if (bulletThread == null || !bulletThread.isAlive()) {
                     for (int i = 0; i < mon.length; i++) {
                        bulletThread = new BulletThread(th.bubble, mon[i], targetThread, th);
                        bulletThread.start();
                     }

                  }
                  if (bulletThread1 == null || !bulletThread1.isAlive()) {
                     for (int i = 0; i < mon1.length; i++) {
                        bulletThread1 = new BulletThread(th.bubble, mon1[i], targetThread1, th);
                        bulletThread1.start();
                     }

                  }
                  if (bulletThread2 == null || !bulletThread2.isAlive()) {
                     for (int i = 0; i < mon2.length; i++) {
                        bulletThread2 = new BulletThread(th.bubble, mon2[i], targetThread2, th);
                        bulletThread2.start();
                     }

                  }
                  if (bulletThread3 == null || !bulletThread3.isAlive()) {
                     for (int i = 0; i < mon3.length; i++) {
                        bulletThread3 = new BulletThread(th.bubble, mon3[i], targetThread3, th);
                        bulletThread3.start();
                     }

                  }

                  for (int i = 0; i < mon4.length; i++) {
                     bulletThread4 = new BulletThread(th.bubble, mon4[i], targetThread4, th);
                     bulletThread4.start();
                  }
                  for (int i = 0; i < 2; i++) {
                     boss = new BulletThread1(th.bubble, New, targetThread5, th);
                     // boss.start();
                  }

                  /////////////////////////
               }

            }
         });
         timer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // move the plane
               int x = Plane.getX();
               int y = Plane.getY();
               Plane.setLocation(x + dx, y + dy);
            }
         });
         timer.start();

         requestFocus();
         Plane.setFocusable(true);
         Plane.requestFocusInWindow();

         // Mon113
         for (int i = 0; i < 10; i++) {
            int x = Plane.getX();
            int y = Plane.getY() - 5;
            if (y < 0) {
               GamePanel.this.remove(Plane); // 컴포넌트 제거
               GamePanel.this.repaint(); // 컴포넌트 제거 후 패널 다시 그리기
               return; // thread ends
            }
            Plane.setLocation(x, y); // 컴포넌트 위치 이동
            GamePanel.this.repaint(); // 이동된 컴포넌트 다시 크리기
            try {
               sleep(200);
            } catch (InterruptedException e) {
            }
         }

         /////////////////////////////////////////
         for (int i = 0; i < 9; i++) {
            icon[i] = new ImageIcon("Image/monster113.png");
            mon[i] = new JLabel();
            mon[i].setIcon(icon[i]);
            mon[i].setSize(40, 40);

         }

         for (int i = 0; i < 9; i++) {
            mon[i].setLocation(10 + (i * 50), 20);
            add(mon[i]);
         }
         GamePanel.this.repaint();
         // Mon112
         for (int i = 0; i < 9; i++) {
            icon1[i] = new ImageIcon("Image/monster112.png");
            mon1[i] = new JLabel();
            mon1[i].setIcon(icon1[i]);
            mon1[i].setSize(40, 40);

         }

         for (int i = 0; i < 9; i++) {
            mon1[i].setLocation(10 + (i * 50), 60);
            add(mon1[i]);
         }
         GamePanel.this.repaint();
         // Mon111
         for (int i = 0; i < 9; i++) {
            icon2[i] = new ImageIcon("Image/monster112.png");
            mon2[i] = new JLabel();
            mon2[i].setIcon(icon2[i]);
            mon2[i].setSize(40, 40);

         }

         for (int i = 0; i < 9; i++) {
            mon2[i].setLocation(10 + (i * 50), 100);
            add(mon2[i]);
         }
         GamePanel.this.repaint();

         // Mon111
         for (int i = 0; i < 9; i++) {
            icon3[i] = new ImageIcon("Image/monster111.png");
            mon3[i] = new JLabel();
            mon3[i].setIcon(icon3[i]);
            mon3[i].setSize(40, 40);

         }

         for (int i = 0; i < 9; i++) {
            mon3[i].setLocation(10 + (i * 50), 140);
            add(mon3[i]);
         }
         GamePanel.this.repaint();
         // Mon111
         for (int i = 0; i < 9; i++) {
            icon4[i] = new ImageIcon("Image/monster111.png");
            mon4[i] = new JLabel();
            mon4[i].setIcon(icon4[i]);
            mon4[i].setSize(40, 40);

         }

         for (int i = 0; i < 9; i++) {
            mon4[i].setLocation(10 + (i * 50), 180);
            add(mon4[i]);
         }
         GamePanel.this.repaint();

      }

      ///
      public void stopRunning(boolean str) {
         str = false;

      }

      @Override
      public void run() {

         while (running) {

            for (int i = 0; i < 1; i++) {
               int x = New.getX();
               int y = New.getY() + 100;
               if (y > 340) {
                  GamePanel.this.remove(New);
                  GamePanel.this.repaint();
                  
                  running = false;
                  setVisible(false);
                   JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(GamePanel.this);
                     frame.dispose();
                  new THIRD().setVisible(true);

                  break;
               } else {
                  New.setLocation(x, y);
                  GamePanel.this.repaint();
               }
               
               try {
                  sleep(50);
               } catch (InterruptedException e) {
               }
            }

            for (int i = 0; i < 9; i++) {
               int x = mon[i].getX();
               int y = mon[i].getY() + 15;

               if (y > 540) {
                  GamePanel.this.remove(mon[i]);
                  GamePanel.this.repaint();
               } else {
                  mon[i].setLocation(x, y);
                  GamePanel.this.repaint();
               }

               try {
                  sleep(50);
               } catch (InterruptedException e) {
               }
            }
            for (int i = 0; i < 9; i++) {
               int x = mon1[i].getX();
               int y = mon1[i].getY() + 20;

               if (y > 540) {
                  GamePanel.this.remove(mon1[i]);
                  GamePanel.this.repaint();
               } else {
                  mon1[i].setLocation(x, y);
                  GamePanel.this.repaint();
               }

               try {
                  sleep(50);
               } catch (InterruptedException e) {
               }
            }
            for (int i = 0; i < 9; i++) {
               int x = mon2[i].getX();
               int y = mon2[i].getY() + 24;

               if (y > 540) {
                  GamePanel.this.remove(mon2[i]);
                  GamePanel.this.repaint();
               } else {
                  mon2[i].setLocation(x, y);
                  GamePanel.this.repaint();
               }

               try {
                  sleep(50);
               } catch (InterruptedException e) {
               }
            }
            for (int i = 0; i < 9; i++) {
               int x = mon3[i].getX();
               int y = mon3[i].getY() + 30;

               if (y > 540) {
                  GamePanel.this.remove(mon3[i]);
                  GamePanel.this.repaint();
               } else {
                  mon3[i].setLocation(x, y);
                  GamePanel.this.repaint();
               }

               try {
                  sleep(50);
               } catch (InterruptedException e) {
               }
            }
            for (int i = 0; i < 9; i++) {
               int x = mon4[i].getX();
               int y = mon4[i].getY() + 35;

               if (y > 540) {
                  GamePanel.this.remove(mon4[i]);
                  GamePanel.this.repaint();
               } else {
                  mon4[i].setLocation(x, y);
                  GamePanel.this.repaint();
               }

               try {
                  sleep(50);
               } catch (InterruptedException e) {
               }
            }

         }

      }

      //////////////////////////////
      class BubbleThread extends Thread {
         public JLabel bubble;

         public BubbleThread(int bubbleX, int bubbleY) {
            ImageIcon img = new ImageIcon("Image/bullet.png");
            bubble = new JLabel(img);
            bubble.setSize(img.getIconWidth(), img.getIconWidth());
            bubble.setLocation(bubbleX, bubbleY);
            add(bubble); // GaePanel에 add()
            GamePanel.this.repaint();
         }

         @Override
         public void run() {
            while (true) {
               int x = bubble.getX();
               int y = bubble.getY() - 20;
               if (y < 0) {
                  GamePanel.this.remove(bubble); // 컴포넌트 제거
                  GamePanel.this.repaint(); // 컴포넌트 제거 후 패널 다시 그리기
                  return; // thread ends
               }
               bubble.setLocation(x, y); // 컴포넌트 위치 이동
               GamePanel.this.repaint(); // 이동된 컴포넌트 다시 크리기
               try {
                  sleep(200);
               } catch (InterruptedException e) {
               }
            }

         }
      }

      ///////////////////////////////////
      class TargetThread extends Thread {
         private JComponent target;

         public TargetThread(JComponent target) {
            this.target = target;

            // target.getParent().repaint();
         }

         @Override
         public void run() {
            while (true) {

               // target.getParent().repaint();
               try {
                  sleep(20);
               } catch (InterruptedException e) {
                  // the case of hit by a bullet
                  // target.setLocation(0, 0);
                  // target.getParent().repaint();
                  try {
                     sleep(500); // 0.5초 기다린 후에 계속한다.
                  } catch (InterruptedException e2) {
                  }
               }
            }
         }
      }

      class BulletThread extends Thread {
         private JComponent bullet, target;
         private Thread targetThread, th;

         public BulletThread(JComponent bullet, JComponent target, Thread targetThread, Thread th) {
            this.bullet = bullet;
            this.target = target;
            this.targetThread = targetThread;
            this.th = th;
         }

         @Override
         public void run() {
            while (true) {
               // 명중하였는지 확인
               if (hit()) {
                  targetThread.interrupt();
                  // bullet.setLocation(bullet.getParent().getWidth()/2 - 5,
                  // bullet.getParent().getHeight()-50);
                  return;
               } else {

               }
               try {
                  sleep(20);
               } catch (InterruptedException e) {
               }
            }
         }

         private boolean hit() {
            if (targetContains(bullet.getX(), bullet.getY())
                  || targetContains(bullet.getX() + bullet.getWidth() - 1, bullet.getY())
                  || targetContains(bullet.getX() + bullet.getWidth() - 1, bullet.getY() + bullet.getHeight() - 1)
                  || targetContains(bullet.getX(), bullet.getY() + bullet.getHeight() - 1)) {
               ////////////////////
               targetThread.interrupt();
               th.interrupt();
               target.setVisible(false); // 타겟을 화면에서 지웁니다.
               bullet.setVisible(false);

               // 총알 컴포넌트를 부모 컨테이너에서 제거합니다.

               ////////////////////
               return true;
            } else {
               return false;
            }
         }

         private boolean targetContains(int x, int y) {
            if (((target.getX() <= x) && (target.getX() + target.getWidth() - 1 >= x))
                  && ((target.getY() <= y) && (target.getY() + target.getHeight() - 1 >= y))) {
               return true;
            } else
               return false;

         }
      }

      class BulletThread1 extends Thread {
         private JComponent bullet, target;
         private Thread targetThread, th;
         private int hitCount = 0; // 명중한 횟수를 저장하는 변수

         public BulletThread1(JComponent bullet, JComponent target, Thread targetThread, Thread th) {
            this.bullet = bullet;
            this.target = target;
            this.targetThread = targetThread;
            this.th = th;
         }

         @Override
         public void run() {
            while (true) {
               // 명중하였는지 확인
               if (hit()) {
                  hitCount++;
                  targetThread.interrupt();
                  if (hitCount == 10) { // 명중 횟수가 10일 경우 해당 쓰레드 종료
                     th.interrupt();
                  }
                  target.setVisible(false); // 타겟을 화면에서 지웁니다.
                  bullet.setVisible(false);
                  // 총알 컴포넌트를 부모 컨테이너에서 제거합니다.
                  return;
               } else {
               }
               try {
                  sleep(20);
               } catch (InterruptedException e) {
               }
            }
         }

         private boolean hit() {
            if (targetContains(bullet.getX(), bullet.getY())
                  || targetContains(bullet.getX() + bullet.getWidth() - 1, bullet.getY())
                  || targetContains(bullet.getX() + bullet.getWidth() - 1, bullet.getY() + bullet.getHeight() - 1)
                  || targetContains(bullet.getX(), bullet.getY() + bullet.getHeight() - 1)) {
               return true;
            } else {
               return false;
            }
         }

         private boolean targetContains(int x, int y) {
            if (((target.getX() <= x) && (target.getX() + target.getWidth() - 1 >= x))
                  && ((target.getY() <= y) && (target.getY() + target.getHeight() - 1 >= y))) {
               return true;
            } else
               return false;

         }
      }

      ////////////////////////////////////
   }

   ////////////////////////////////////////////////////

}

class Monster {
   JLabel mon;
   int x;
   int y;

   boolean destroyed;

   public Monster(JLabel mon, int x, int y) throws HeadlessException {
      this.mon = mon;
      this.x = x;
      this.y = y;
   }
}

class Window extends JFrame {
   GamePanel game = new GamePanel();

   MonsterThread bb;
   boolean str = true;
   JLabel pan = new JLabel("<HTML>SURVIVE<br>TIME!<br></HTML>");
   private static final int MONSTER_WIDTH = 40;
   private static final int MONSTER_HEIGHT = 40;
   private static final int NUM_MONSTER = 8;
   MyTimer timerTh;

   Window() {
      setTitle("갤러그");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Container c = getContentPane();
      c.setLayout(new BorderLayout());

      // 게임판
      // JPanel gamepane = new JPanel();
      c.add(BorderLayout.CENTER, game);
      game.setLayout(null);
      game.setOpaque(false);

      //

      JPanel gameScore = new JPanel();
      c.add(BorderLayout.EAST, gameScore);
      gameScore.setLayout(new BoxLayout(gameScore, BoxLayout.Y_AXIS));
      gameScore.setBackground(Color.black);

      pan.setFont(new Font("DungGeunMo", Font.BOLD, 30));
      pan.setForeground(Color.red);

      gameScore.add(pan);

      // 타이머
      JLabel timerLa = new JLabel("0");
      JLabel scoreLa = new JLabel();
      timerLa.setFont(new Font("DungGeunMo", Font.BOLD, 30));
      timerLa.setForeground(Color.WHITE);
      gameScore.add(timerLa);

      JLabel pan1 = new JLabel("HIGH");
      pan1.setFont(new Font("DungGeunMo", Font.BOLD, 30));
      pan1.setForeground(Color.red);
      JLabel pan2 = new JLabel("SCORE!");
      pan2.setFont(new Font("DungGeunMo", Font.BOLD, 30));
      pan2.setForeground(Color.red);

      scoreLa.setFont(new Font("DungGeunMo", Font.BOLD, 30));
      scoreLa.setForeground(Color.WHITE);

      gameScore.add(pan1);
      gameScore.add(pan2);
      gameScore.add(scoreLa);

      timerTh = new MyTimer(timerLa, scoreLa);
      timerTh.start();

      setResizable(false);
      setSize(700, 630);
      setVisible(true);
      setLocationRelativeTo(null);
   }

}

public class SECOND {

   public static void main(String[] args) {
      new Window();
   }
}