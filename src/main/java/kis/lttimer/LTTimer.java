package kis.lttimer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simple implementation
 */
public class LTTimer {

    public static void main(String[] args) {
        var defaultTime = Duration.ofMinutes(5).getSeconds();
        
        // ウィンドウ
        var frame = new JFrame("LT Timer");
        var lblTimer = new JLabel("00:00");
        lblTimer.setFont(new Font(Font.DIALOG, Font.PLAIN, 440));
        frame.add(lblTimer);
        
        var btnStart = new JButton("Start");
        var btnReset = new JButton("Reset");
        var panel = new JPanel();
        panel.add(btnStart);
        panel.add(btnReset);
        frame.add(BorderLayout.SOUTH, panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 500);
        frame.setVisible(true);
        
        var fn = new Object() {
            
            boolean running = false;
            long timer = defaultTime;

            void paint() {
                var s = (timer < 0) ? "-" : " ";
                var at = Math.abs(timer);
                lblTimer.setText(String.format("%s%02d:%02d", s, at / 60, at % 60));
                lblTimer.setForeground(timer < 0 ? Color.RED : Color.BLACK);
                btnStart.setText(running ? "Stop" : "Start");   
            }
            
            void start(ActionEvent ae) {
                running = !running;
                paint();
            }
            
            void reset(ActionEvent ae) {
                timer = defaultTime;
                paint();
            }
            
            void tick() {
                if (running) {
                    --timer;
                }
                paint();
            }
        };
        
        // イベント登録
        btnStart.addActionListener(fn::start);
        btnReset.addActionListener(fn::reset);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                fn::tick, 0, 1, TimeUnit.SECONDS);      
    }
    
}
