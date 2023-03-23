package FlashGameProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

class MusicPlayer1 {
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

class MyPanel1 extends JPanel {
    private Image backgroundImage;
    private JButton button;
    private JLabel label;
    private Timer timer;
    private int count =9;
    private JLabel label1;
    //ImageIcon ic = new ImageIcon("Image/play.jpg");
    public MyPanel1() {
        // 배경 이미지를 로드합니다.
        ImageIcon icon = new ImageIcon("Image/succ2.jpg");
        backgroundImage = icon.getImage();

        // 버튼을 생성합니다.
        button = new JButton("Click");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Window();
            }
        });
        
        //button.setIcon(ic);
        //button.setSize(ic.getIconWidth(),ic.getIconHeight());
        // 라벨을 생성합니다.
        label = new JLabel("9       ");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("DungGeunMo", Font.BOLD, 20));
      ///
        label1 = new JLabel("CONTINUE?");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("DungGeunMo", Font.BOLD, 20));
        
        // 타이머를 생성합니다.
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count--;
                if (count == 0) {
                    timer.stop();
                    // 타이머가 끝나면 버튼을 비활성화합니다.
                    button.setEnabled(false);
                }
                label.setText(Integer.toString(count));
            }
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getSize();
        g.drawImage(backgroundImage, 0, 0, size.width, size.height, null);

        // 버튼과 라벨을 위치시킵니다.
        label1.setBounds(size.width / 2 - 50, size.height * 3 / 4 - 5, 100, 50);
        label.setBounds(size.width / 2 - 50, size.height * 3 / 4 + 15, 100, 50);
        button.setBounds(size.width / 2 - 40, size.height * 3 / 4 + 35, 80, 20);

        // 버튼과 라벨을 패널에 추가합니다.
        add(label1);
        add(button);
        add(label);
    }
}

public class THIRD extends JFrame {
    public THIRD() {
        super("갤러그");
        MyPanel1 panel = new MyPanel1();
        setContentPane(panel);
        
        setSize(700, 630);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        //MusicPlayer.play("C:/Users/ssfu7/OneDrive/바탕 화면/NewJeans_-_Ditto_360media.com.ng.wav");
    }

    public static void main(String[] args) {
        new THIRD();
    }
}