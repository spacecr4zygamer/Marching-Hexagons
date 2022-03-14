package GUIS;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static Hexagons.HexagonController.*;

public class FirstCases extends JPanel {



    public JFrame parent;

    public FirstCases() {
        parent = new JFrame("First Cases Screen");
        parent.setContentPane(this);
        parent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1900, 1000));

        parent.pack();
        parent.setLocationRelativeTo(null);
        parent.setVisible(true);
    }

    public Color colorlerp(Color a, Color b, double alpha) {
        int ar = a.getRed(), ag = a.getGreen(), ab = a.getBlue(),
                br = b.getRed(), bg = b.getGreen(), bb = b.getBlue();
        return new Color((int) (ar + (br - ar) * alpha), (int) (ag + (bg - ag) * alpha), (int) (ab + (bb - ab) * alpha));
    }
    public Point lerppoint(Point a, Point b, double alpha) {
        //int ax = a.x, ay = a.y, bx = b.x, by = b.y;
        return new Point((int) (a.x+(b.x-a.x)*alpha),(int) (a.y+(b.y-a.y)*alpha));
    }

    @Override
    public void paint(Graphics g) {


        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setTransform(new AffineTransform() {{
            setToScale(0.9, 0.9);
            //setToTranslation(getWidth() / 2, getHeight() / 2);
        }});
        g2d.setColor(Color.blue);
        int xoff = 0, yoff = 0;

        for (int x = -100; x <= 100; x++) {
            for (int y = -100; y <= 100; y++) {
                Point point1 = converttoHexagongrid(x, y);
                g2d.drawPolygon(getTransformedHexagon(point1.x, point1.y));
                //g2d.drawOva l(point1.x,point1.y,10,10);
//                for (Point connectionPoint : getConnectionPoints(point1)) {
//                    g2d.fillOval(connectionPoint.x-3,connectionPoint.y-3,6,6);
//                }
            }
        }

        /*g2d.setColor(Color.RED);
        for (int x = -20; x <= 20; x++) {
            for (int y = -20; y <= 20; y++) {
                Point point1 = convertVerticeToHexagonGrid(x, y);
                //g2d.fillOval(point1.x - 3, point1.y - 3, 6, 6);
                g2d.drawString(x + "/" + y, point1.x - 10, point1.y + 5);
            }
        }*/
//
//        g2d.setColor(Color.green);
//        for (int x = 0; x < 30; x++) {
//            for (int y = 0; y < 60; y+=2) {
//
//                int phase = x % 2;
//                //int y = 0;
//                int sy = y + phase;
//
//
//
//                Point upleft = convertVerticeToHexagonGrid(x, sy);
//                Point upright = convertVerticeToHexagonGrid(x + 1, sy);
//                Point left = convertVerticeToHexagonGrid(x, sy + 1);
//                Point right = convertVerticeToHexagonGrid(x + 1, sy + 1);
//                Point downleft = convertVerticeToHexagonGrid(x, sy + 2);
//                Point downright = convertVerticeToHexagonGrid(x + 1, sy + 2);
//
//                g2d.setColor(Color.green);
//                Point middle = lerppoint(left,right,0.5);
//                g2d.fillOval(middle.x-5,middle.y-5,10,10);
//
//                Point[] verts = {
//                        upleft,
//                        lerppoint(upleft, upright, 0.5),
//                        upright,
//                        lerppoint(upright, right, 0.5),
//                        right,
//                        lerppoint(right, downright, 0.5),
//                        downright,
//                        lerppoint(downright, downleft, 0.5),
//                        downleft,
//                        lerppoint(downleft, left, 0.5),
//                        left,
//                        lerppoint(left, upleft, 0.5)
//                };
//                upleft = lerppoint(upleft,middle,0.1);
//                upright = lerppoint(upright,middle,0.1);
//                left = lerppoint(left,middle,0.1);
//                right = lerppoint(right,middle,0.1);
//                downleft = lerppoint(downleft,middle,0.1);
//                downright = lerppoint(downright,middle,0.1);
//
//                int[] verticestodraw = data[getIndexNumber(valarr[x][sy],valarr[x + 1][sy],valarr[x+1][sy + 1],valarr[x + 1][sy + 2],valarr[x][sy + 2],valarr[x][sy + 1])];
//
//                for (int i = 1; i <= verticestodraw.length; i+=2) {
//                    //System.out.println(i);
//                    Point a = verts[verticestodraw[i-1]-1], b = verts[verticestodraw[i]-1];
//                    g2d.setColor(Color.CYAN);
//                    g2d.fillOval(a.x-3,a.y-3,6,6);
//                    g2d.fillOval(b.x-3,b.y-3,6,6);
//                    g2d.setColor(Color.yellow);
//                    g2d.drawLine(a.x,a.y,b.x,b.y);
//                }
//
//                //g2d.setColor(colorlerp(Color.MAGENTA, Color.WHITE, valarr[x][sy]));
//                g2d.setColor(valarr[x][sy]==1?Color.WHITE:Color.MAGENTA);
//                g2d.fillOval(upleft.x - 2, upleft.y - 2, 4, 4);
////                g2d.setColor(colorlerp(Color.MAGENTA, Color.WHITE, valarr[x + 1][sy]));
//                g2d.setColor(valarr[x+1][sy]==1?Color.WHITE:Color.MAGENTA);
//                g2d.fillOval(upright.x - 2, upright.y - 2, 4, 4);
////                g2d.setColor(colorlerp(Color.MAGENTA, Color.WHITE, valarr[x][sy + 1]));
//                g2d.setColor(valarr[x][sy+1]==1?Color.WHITE:Color.MAGENTA);
//                g2d.fillOval(left.x - 2, left.y - 2, 4, 4);
////                g2d.setColor(colorlerp(Color.MAGENTA, Color.WHITE, valarr[x + 1][sy + 1]));
//                g2d.setColor(valarr[x+1][sy+1]==1?Color.WHITE:Color.MAGENTA);
//                g2d.fillOval(right.x - 2, right.y - 2, 4, 4);
////                g2d.setColor(colorlerp(Color.MAGENTA, Color.WHITE, valarr[x][sy + 2]));
//                g2d.setColor(valarr[x][sy+2]==1?Color.WHITE:Color.MAGENTA);
//                g2d.fillOval(downleft.x - 2, downleft.y - 2, 4, 4);
////                g2d.setColor(colorlerp(Color.MAGENTA, Color.WHITE, valarr[x + 1][sy + 2]));
//                g2d.setColor(valarr[x+1][sy+2]==1?Color.WHITE:Color.MAGENTA);
//                g2d.fillOval(downright.x - 2, downright.y - 2, 4, 4);
//            }
//        }
        /*for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 13; y++) {
                Point point1 = converttoHexagongrid(x, y);
                g2d.setColor(Color.red);
                g2d.drawPolygon(getTransformedHexagon(point1.x, point1.y));
                int[] values = new int[6];
                Point middlepos = converttoHexagongrid(xoff-1,yoff-1);
                //Point[] points = getVerticePositions(middlepos);
                Point[] connectionPoints = getConnectionPoints(middlepos);
                /*for (int i = 0; i < 6; i++) {
                    values[i] =
                            Math.max(
                                    Math.min(
                                            (int) Math.round(
                                                    Hexagons.noise.Hexagons.noise(0.981614687,connectionPoints[i].x*0.001,connectionPoints[i].y*0.001)+0.5),1),0);
                }*/
        //System.out.println(Arrays.stream(values).max().getAsInt());
        //System.out.println("Min: "+Arrays.stream(values).min().getAsInt());

        //int[] verticestodraw = data[getIndexNumber(values[0],values[1], values[2],values[3],values[4],values[5])];
        //int[] verticestodraw = data[casenumber];
        //System.out.println("Vertices: "+verticestodraw.length);
        //g2d.setColor(Color.blue);
//                //for (int i = 1; i <= verticestodraw.length; i+=2) {
//                    //System.out.println(i);
//                    Point a = connectionPoints[verticestodraw[i-1]-1], b = connectionPoints[verticestodraw[i]-1];
//                    g2d.fillOval(a.x-5,a.y-5,10,10);
//                    g2d.fillOval(b.x-5,b.y-5,10,10);
//                    g2d.drawLine(a.x,a.y,b.x,b.y);
//                }
//                if (xoff%24==0) {yoff+=2;xoff=0;}
//                xoff+=2;

                /*for (Point connectionPoint : getConnectionPoints(point1)) {
                    g2d.setColor(colorlerp(Color.BLACK,Color.WHITE, Math.random()));
                    g2d.fillOval(connectionPoint.x-3,connectionPoint.y-3,6,6);
                }*/
//            }
//        }



        g2d.setColor(Color.RED);
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 13; y++) {
                Point point1 = converttoHexagongrid(x, y);
                g2d.drawPolygon(getTransformedHexagon(point1.x, point1.y));
                for (Point connectionPoint : getConnectionPoints(point1)) {
                    g2d.fillOval(connectionPoint.x-3,connectionPoint.y-3,6,6);
                }
            }
        }

        //g2d.setStroke(new BasicStroke(2));
        for (int casenumber = 0; casenumber < data.length; casenumber++) {

            g2d.setColor(Color.BLUE);
            //int[] verticestodraw = data[getIndexNumber(1,1,1,0,0,0)];
            int[] verticestodraw = data[casenumber];
            //System.out.println("Vertices: "+verticestodraw.length);
            Point middlepos = converttoHexagongrid(xoff-1,yoff-1);
            Point[] points = getVerticePositions(middlepos);
            Point[] connectionPoints = getConnectionPoints(middlepos);
            for (int i = 1; i <= verticestodraw.length; i+=2) {
                //System.out.println(i);
                Point a = connectionPoints[verticestodraw[i-1]-1], b = connectionPoints[verticestodraw[i]-1];
                g2d.fillOval(a.x-5,a.y-5,10,10);
                g2d.fillOval(b.x-5,b.y-5,10,10);
                g2d.drawLine(a.x,a.y,b.x,b.y);
            }

            g2d.setColor(Color.green);
            if ((casenumber&0b1)==0b1) {
                g2d.fillOval(points[0].x-3,points[0].y-3,6,6);
            }
            if ((casenumber&0b10)==0b10) {
                g2d.fillOval(points[1].x-3,points[1].y-3,6,6);
            }
            if ((casenumber&0b100)==0b100) {
                g2d.fillOval(points[2].x-3,points[2].y-3,6,6);
            }
            if ((casenumber&0b1000)==0b1000) {
                g2d.fillOval(points[3].x-3,points[3].y-3,6,6);
            }
            if ((casenumber&0b10000)==0b10000) {
                g2d.fillOval(points[4].x-3,points[4].y-3,6,6);
            }
            if ((casenumber&0b100000)==0b100000) {
                g2d.fillOval(points[5].x-3,points[5].y-3,6,6);
            }

            g2d.setFont(new Font("Tahoma",Font.PLAIN,14));
            g2d.drawString("Case: "+casenumber,middlepos.x+10,middlepos.y-40);

            if (xoff%24==0) {yoff+=2;xoff=0;}
            xoff+=2;
        }



//        for (int i = 1; i < points.length; i++) {
//            Point crp = points[i],lp = points[i-1];
//            g2d.drawLine(lp.x,lp.y,crp.x,crp.y);
//        }
//        g2d.drawLine(points[0].x,points[0].y,points[5].x,points[5].y);

//        {Point point1 = converttoHexagongrid(1,1);
//            g.drawPolygon(getTransformedHexagon(point1.x,point1.y));}
//        {Point point1 = converttoHexagongrid(2,2);
//            g.drawPolygon(getTransformedHexagon(point1.x,point1.y));}
//        {Point point1 = converttoHexagongrid(3,1);
//            g.drawPolygon(getTransformedHexagon(point1.x,point1.y));}

    }
}

