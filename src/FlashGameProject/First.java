package FlashGameProject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

class MusicPlayer {
    public static void play(String filePath) {
        try {
            // 오디오 스트림을 열어 준비합니다.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
            
            // 오디오 포맷을 가져옵니다.
            AudioFormat format = audioIn.getFormat();
            
            // 데이터 라인을 찾습니다.
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            
            // 오디오 스트림을 클립에 로드합니다.
            clip.open(audioIn);
            
            // 오디오 재생을 시작합니다.
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyPanel extends JPanel {
    private Image backgroundImage;
    
    public MyPanel() {
        ImageIcon icon = new ImageIcon("Image/front.png");
        backgroundImage = icon.getImage();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getSize();
        g.drawImage(backgroundImage, 0, 0, size.width, size.height, null);
    }
}

public class First extends JFrame {
    public First() {
        super("갤러그");
        MyPanel panel = new MyPanel();
        setContentPane(panel);
        
        
        setSize(700, 630);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        
        
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
               
               
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    new Window();
                    setVisible(false); // 현재 창 안보이게 하기 
                }
            }
        });
       
        //MusicPlayer1.play("C:/Users/ssfu7/OneDrive/바탕 화면/NewJeans_-_Ditto_360media.com.ng.wav");


    }         

    public static void main(String[] args) {
        new First();
    }
}