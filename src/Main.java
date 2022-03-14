import Hexagons.HexagonController;
import GUIS.SecondMarchScreen;
import GUIS.FirstMarchScreen;
import GUIS.FirstCases;
import GUIS.SecondCases;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
//        for (int i = 0; i < 64; i++) {
//            System.out.println("{}, // "+(i+1));
//        }
        HexagonController.initialize();

//        double[] e = new double[100];
//        for (int i = 0; i < e.length; i++) {
//            e[i] = Hexagons.noise.Hexagons.noise(0.15649426796489,Math.random(),Math.random())+0.5;
//        }
//        System.out.println(-1%2);
//        System.out.println(1%2);
//        System.out.println(Arrays.stream(e).max().getAsDouble());
//        System.out.println(Arrays.stream(e).min().getAsDouble());
//        System.out.println(Arrays.stream(e).average().getAsDouble());

        JFrame jFrame = new JFrame("Launch Panel");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridLayout(0,1));
        jFrame.add(new JButton("New Noise Generation"){{
            setPreferredSize(new Dimension(200,50));
            addActionListener(e -> {
                HexagonController.generatenoise();
            });
        }});
        jFrame.add(new JButton("Show First Set"){{
            setPreferredSize(new Dimension(200,100));
            addActionListener(e -> {
                new FirstCases();
                new FirstMarchScreen();
            });
        }});
        jFrame.add(new JButton("Show Second Set"){{
            setPreferredSize(new Dimension(200,100));
            addActionListener(e -> {
                new SecondCases();
                new SecondMarchScreen();
            });
        }});

        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
