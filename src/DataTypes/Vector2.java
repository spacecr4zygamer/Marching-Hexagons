package DataTypes;

import java.awt.*;

public class Vector2 {
    public double x = 0, y = 0;

    public static Vector2 UP = new Vector2(0, 1);

    public static Vector2 RIGHT = new Vector2(1, 0);

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * @param x The X position
     * @param y The Y position
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the Vector2 to this.
     *
     * @param x The X position
     * @param y The Y position
     */

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*public Vector2(String string) {
        Vector2 r = new Vector2(string);
        this.set(r.x, r.y);
    }*/

    public Vector2(Point position) {
        this.set(position.getX(),position.getY());
    }

    public double dot(Vector2 other) {
        return 0;
    }

    public Vector2 clone() {
        return new Vector2(this.x, this.y);
    }

    public Vector2 add(Vector2 b) {
        return new Vector2(this.x + b.x, this.y + b.y);
    }

    public Vector2 add(double xoff, double yoff) {
        return new Vector2(this.x+xoff,this.y+yoff);
    }

    public Vector2 sub(Vector2 b) {
        return new Vector2(this.x - b.x, this.y - b.y);
    }

    public Vector2 sub(double xoff, double yoff) {
        return new Vector2(this.x-xoff,this.y-yoff);
    }

    public Vector2 mul(Vector2 b) {
        return new Vector2(this.x * b.x, this.y * b.y);
    }

    public Vector2 mul(double a) {
        return new Vector2(this.x * a, this.y * a);
    }

    public Vector2 div(Vector2 b) {
        return new Vector2(this.x / b.x, this.y / b.y);
    }

    public Vector2 div(double a) {
        return new Vector2(this.x / a, this.y / a);
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public String toString() {
        return this.x + "," + this.y;
    }

    public Vector2 unit() {
        return this.clone().div(this.magnitude());
    }

    public double angle() {
        return Math.atan2(this.x, this.y);
    }

    public Vector2 lerp(Vector2 vector, double t) {
        return this.add(vector.sub(this).mul(t));
    }

    public Vector2 converttorender() {
        Vector2 E = this.clone();
        E.y *= -1;
        return E;
    }

    public static Vector2 Transform(Vector2 v, double angle) {
        double radian = Math.toRadians(angle);
        double x = v.x * Math.cos(radian) - v.y * Math.sin(radian);
        double y = v.x * Math.sin(radian) + v.y * Math.cos(radian);

        return new Vector2(x, y);
    }

    public static Vector2 fromString(String string) {
        String[] strings = string.split(",");
        return new Vector2(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
    }

}