import Hexagons.HexagonController;
import org.w3c.dom.css.CSSFontFaceRule;

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

        new SecondScreen();
        new Screen();
        new ThirdScreen();
        new FourthScreen();
    }
}
