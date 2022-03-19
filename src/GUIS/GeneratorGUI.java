package GUIS;

import DataTypes.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GeneratorGUI extends JPanel{

    public JFrame parent;
    public JPanel explorer;

    public GeneratorGUI() {
        parent = new JFrame("Connection Array");
        parent.setLayout(new BorderLayout());
        parent.add(this,BorderLayout.CENTER);
        explorer = new JPanel();
        parent.add(explorer,BorderLayout.EAST);
        setPreferredSize(new Dimension(400,400));
        explorer.setPreferredSize(new Dimension(200,400));
        explorer.setBackground(Color.GRAY);
        setBackground(Color.BLACK);

        parent.pack();
        parent.setLocationRelativeTo(null);
        parent.setVisible(true);
        setupPoints(8);
        explorer.setLayout(new GridLayout(0,1));
        explorer.add(new JTextField(""));
    }

    private int numberoffset = 0;

    private ArrayList<Vector2> cornerpoints = new ArrayList<>();
    private ArrayList<Vector2> connectionpoints = new ArrayList<>();


    public void setupPoints(int Edges) {
        double anglebetween = 360/(double) Edges;
        for (int i = 0; i < 360; i+=anglebetween) {
            cornerpoints.add(Vector2.Transform(new Vector2(0,150),i));
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g2d);
        Vector2 test = new Vector2();
        g2d.setColor(Color.white);
        g2d.setTransform(new AffineTransform(){{
            setToTranslation(getWidth()/2d,getHeight()/2d);
        }});
        g2d.drawOval(-150,-150,300,300);
        for (Vector2 cornerpoint : cornerpoints) {
            g2d.setTransform(new AffineTransform(){{
                setToTranslation(getWidth()/2d+cornerpoint.x,getHeight()/2d+cornerpoint.y);
            }});
            g2d.fillOval(-5,-5,10,10);
        }

        repaint();
    }
}
