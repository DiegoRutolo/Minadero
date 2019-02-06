
import minadero.Minadero;

public class Consola {
    
    public static void imprimeTablero(Minadero t) {
        System.out.print("┌");
        for (int i = 0; i < t.getAncho()-1; i++) {
            System.out.print("─┬");
        }
        System.out.println("─┐");
        for (int i = 0; i < t.getAlto()-1; i++) {
            for (int j = 0; j < t.getAncho(); j++) {
                System.out.print("│" + t.charAt(j, i));
            }
            System.out.println("│");
            System.out.print("├");
            for (int j = 0; j < t.getAncho()-1; j++) {
                System.out.print("─┼");
            }
            System.out.println("─┤");
        }
        for (int j = 0; j < t.getAncho(); j++) {
                System.out.print("│" + t.charAt(j, t.getAlto()-1));
            }
        System.out.print("│\n└");
        for (int j = 0; j < t.getAncho()-1; j++) {
                System.out.print("─┴");
            }
            System.out.println("─┘");
        System.out.println("\n");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Minadero t = new Minadero(5, 10, 10);
        imprimeTablero(t);
    }
}
