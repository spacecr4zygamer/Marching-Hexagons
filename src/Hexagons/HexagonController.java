package Hexagons;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.EventListener;

public class HexagonController {


    private static final int hexagonsidelength = 50;


    // The Outer Array is a keeper for the Different case
    // The Inside Array Describes which 2 Points to Connect
    // The Number are according to the path the Polygon goes (Top Left Clockwise)
    // All Numbers are +1 (As in like the Array starts from 1) because I want to use this in Lua, probably
    // I added the edges so there is 12 Connection Points now
    public static int[][] data = {
            {}, // 0
            {12, 2}, // 1
            {2, 4}, // 2
            {12, 4}, // 3
            {4, 6}, // 4
            {12, 2, 4, 6}, // 5
            {2, 6}, // 6
            {12, 6}, // 7
            {6, 8}, // 8
            {12, 2, 6, 8}, // 9
            {2, 4, 6, 8}, // 10
            {12, 4, 6, 8}, // 11
            {4, 8}, // 12
            {12, 2, 4, 8}, // 13
            {2, 8}, // 14
            {12, 8}, // 15
            {8, 10}, // 16
            {12, 2, 8, 10}, // 17
            {2, 4, 8, 10}, // 18
            {12, 4, 8, 10}, // 19
            {4, 6, 8, 10}, // 20
            {12, 2, 4, 6, 8, 10}, // 21
            {2, 6, 8, 10}, // 22
            {12, 6, 8, 10}, // 23
            {6, 10}, // 24
            {12, 2, 6, 10}, // 25
            {2, 4, 6, 10}, // 26
            {12, 4, 6, 10}, // 27
            {4, 10}, // 28
            {12, 2, 4, 10}, // 29
            {2,10}, // 30
            {12,10}, // 31
            {10, 12}, // 32
            {10, 2}, // 33
            {2, 4, 10, 12}, // 34
            {10, 4}, // 35
            {4, 6, 10, 12}, // 36
            {10, 2, 4, 6}, // 37
            {2, 6, 10, 12}, // 38
            {10, 6}, // 39
            {6, 8, 10, 12}, // 40
            {10, 2, 6, 8}, // 41
            {2, 4, 6, 8, 10, 12}, // 42
            {10, 4, 6, 8}, // 43
            {4, 8, 10, 12}, // 44
            {10, 2, 4, 8}, // 45
            {2, 8, 10, 12}, // 46
            {10, 8}, // 47
            {12, 8}, // 48
            {8, 2}, // 49
            {2, 4, 8, 12}, // 50
            {8, 4}, // 51
            {4, 6, 8, 12}, // 52
            {8, 2, 4, 6}, // 53
            {2, 6, 8, 12}, // 54
            {6, 8}, // 55
            {6, 12}, // 56
            {6, 2}, // 57
            {2, 4, 6, 12}, // 58
            {4, 6}, // 59
            {4, 12}, // 60
            {4, 2}, // 61
            {12, 2}, // 62
            {}, // 63
    };

    public static int[][] data2 = {
            {}, // 0
            {1,2,1,6}, // 1
            {2,1,2,3}, // 2
            {1,6,2,3}, // 3
            {3,2,3,4}, // 4
            {1,2,1,6,3,2,3,4}, // 5
            {2,1,3,4}, // 6
            {1,6,3,4}, // 7
            {4,3,4,5}, // 8
            {1,2,1,6,4,3,4,5}, // 9
            {2,1,2,3,4,3,4,5}, // 10
            {1,6,2,3,4,3,4,5}, // 11
            {3,2,4,5}, // 12
            {1,2,1,6,3,2,4,5}, // 13
            {2,1,4,5}, // 14
            {1,6,4,5}, // 15
            {5,4,5,6}, // 16
            {1,2,1,6,5,4,5,6}, // 17
            {2,1,2,3,5,4,5,6}, // 18
            {1,6,2,3,5,4,5,6}, // 19
            {3,2,3,4,5,4,5,6}, // 20
            {1,2,1,6,3,2,3,4,5,4,5,6}, // 21
            {2,1,3,4,5,4,5,6}, // 22
            {1,6,3,4,5,4,5,6}, // 23
            {4,3,5,6}, // 24
            {1,6,1,2,4,3,5,6}, // 25
            {2,1,2,3,4,3,5,6}, // 26
            {1,6,2,3,4,3,5,6}, // 27
            {3,2,5,6}, // 28
            {1,6,1,2,3,2,5,6}, // 29
            {2,1,5,6}, // 30
            {1,6,5,6}, // 31
            {6,1,6,5}, // 32
            {1,2,6,5}, // 33
            {2,1,2,3,6,1,6,5}, // 34
            {2,3,6,5}, // 35
            {3,2,3,4,6,1,6,5}, // 36
            {1,2,6,5,3,2,3,4}, // 37
            {6,1,6,5,2,1,3,4}, // 38
            {6,5,3,4}, // 39
            {4,5,4,3,6,1,6,5}, // 40
            {1,2,6,5,4,5,4,3}, // 41
            {2,1,2,3,4,5,4,3,6,1,6,5}, // 42
            {2,3,6,5,4,5,4,3}, // 43
            {3,2,4,5,6,5,6,1}, // 44
            {1,2,6,5,3,2,4,5}, // 45
            {2,1,4,5,6,1,6,5}, // 46
            {4,5,6,5}, // 47
            {5,4,6,1}, // 48
            {1,2,5,4}, // 49
            {2,1,2,3,5,4,6,1}, // 50
            {2,3,5,4}, // 51
            {3,2,3,4,5,4,6,1}, // 52
            {1,2,5,4,3,2,3,4}, // 53
            {2,1,3,4,5,4,6,1}, // 54
            {3,4,5,4}, // 55
            {4,3,6,1}, // 56
            {1,2,4,3}, // 57
            {2,1,2,3,4,3,6,1}, // 58
            {2,3,4,3}, // 59
            {3,2,6,1}, // 60
            {1,2,3,2}, // 61
            {2,1,6,1}, // 62
            {}, // 63
    };

    public static Point converttoHexagongrid(int x, int y) {
        int sx = (int) (x * hexagonsidelength * 1.5) - 25;
        int sy = (y * 86) + ((x - 1) % 2 == 0 ? 43 : 0);
        return new Point(sx, sy);
    }

    public static Point convertVerticeToHexagonGrid(int x, int y) {
        //int sx = (int) (x*100-(x%4==0?25:0));
        //int sy = (int) (y*86+43+((x-1)%4>=2?43:0));
        //int sx = (int) (((x) * 50 - (y%2==1?25:0)) + ((x-(y%2==0?1:0))%3==1?50:0));
        //int sx = (int) (((x) * 50/* - ((y-1)%2==0?25:0)*/) + (int) ((((x/*+((y-1)%2==0?1:0)*/)/2)*50)));
        int direction2 = Math.min(Math.max(x, -1), 1);
        //System.out.println(direction2);
        //int direction = x/Math.abs(x);
        int sx = (x * 50 + direction2 * Math.abs(x + Math.min(direction2, 0) + ((y - 1) % 2 == 0 ? 1 : 0)) / 2 * 50 - ((y - 1) % 2 == 0 ? 25 : 0));
        int sy = y * 43;

        return new Point(sx, sy);
    }

    private static final Polygon hexagon = new Polygon(new int[]{25, 75, 100, 75, 25, 0}, new int[]{0, 0, 43, 86, 86, 43}, 6);

    private static final int[] xconnectionpoints = {25, 50, 75, 87, 100, 87, 75, 50, 25, 12, 0, 12};
    private static final int[] yconnectionpoints = {0, 0, 0, 21, 43, 64, 86, 86, 86, 64, 43, 21};

    public static Point[] getConnectionPoints(Point position) {
        Point[] vertices = new Point[12];
        for (int i = 0; i < 12; i++) {
            vertices[i] = new Point(xconnectionpoints[i] + position.x, yconnectionpoints[i] + position.y);
        }
        return vertices;
    }

    public static Point[] getVerticePositions(Point position) {
        Point[] vertices = new Point[6];
        for (int i = 0; i < 6; i++) {
            vertices[i] = new Point(hexagon.xpoints[i] + position.x, hexagon.ypoints[i] + position.y);
        }
        return vertices;
    }

    // The Points Names are according to the direction of how I setup the Polygon
    // aka top left going clockwise
    public static int getIndexNumber(int a, int b, int c, int d, int e, int f) {
        return (a) + (b << 1) + (c << 2) + (d << 3) + (e << 4) + (f << 5);
    }


    public static Polygon getTransformedHexagon(int x, int y) {
        int[] newxpoints = new int[6];
        int[] newypoints = new int[6];
        for (int i = 0; i < 6; i++) {
            newxpoints[i] = hexagon.xpoints[i] + x;
            newypoints[i] = hexagon.ypoints[i] + y;
        }
        return new Polygon(newxpoints, newypoints, 6);
    }

    public static double[][] valarr = new double[1000][1000];
    private static ArrayList<ChangeListener> changeListeners = new ArrayList<>();

    public static void addChangeListener(ChangeListener changeListener) {
        changeListeners.add(changeListener);
    }

    public static void generatenoise() {
        for (ChangeListener changeListener : changeListeners) {
            changeListener.stateChanged(new ChangeEvent("NoiseTable"));
        }
        double seed = Math.random();
        System.out.println(seed);
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                //valarr[x][y] = Math.random();
                valarr[x][y] = Math.abs(noise.noise(x/6d,y/6d,seed));
                //valarr[x][y] = (int) Math.round(Math.abs(noise.noise(0.15489926789618,x/10,y/10)));
            }
        }
    }

    public static void initialize() {
        /*for (int[] ints : data) {
            for (int anInt : ints) {
                if (anInt%2==1){
                    System.out.println("IT HIT A CORNER NOOOOOOOOO");
                }
            }
        }*/
        generatenoise();
    }
}
