
import java.util.Scanner;
import minadero.Minadero;

public class Consola {
    static char tapa = '·';
    static char cv = ' ';
    
    public static char muestraCasilla(Minadero t, int x, int y) {
        
        if (t.getTapa(x, y)) {
            return tapa;
        } else if (t.charAt(x, y) == '0') {
            return cv;
        } else {
            return t.charAt(x, y);
        }
    }
    
    public static void imprimeTablero(Minadero t) {
        char c = 'A';
        for (int i = 1; i <= t.getAncho(); i++) {
            System.out.print("  " + c + " ");
            c++;
        }
        
        System.out.print("\n┌");
        for (int i = 0; i < t.getAncho()-1; i++) {
            System.out.print("───┬");
        }
        System.out.println("───┐");
        for (int i = 0; i < t.getAlto()-1; i++) {
            for (int j = 0; j < t.getAncho(); j++) {
                System.out.print("│ " + muestraCasilla(t, j, i) + " ");
            }
            System.out.print("│");
            System.out.print(" " + (i+1) + " \n");
            System.out.print("├");
            for (int j = 0; j < t.getAncho()-1; j++) {
                System.out.print("───┼");
            }
            System.out.println("───┤");
        }
        for (int j = 0; j < t.getAncho(); j++) {
            System.out.print("│ " + muestraCasilla(t, j, t.getAlto()-1) + " ");
        }
        System.out.print("│ " + t.getAlto() + "\n└");
        
        for (int j = 0; j < t.getAncho()-1; j++) {
                System.out.print("───┴");
            }
            System.out.println("───┘");
        System.out.println("\n");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el tamaño del tablero en formato ANCHO ALTO [N_MINAS].");
        System.out.print("->");
        String s = sc.nextLine();
        Scanner lin = new Scanner(s);
        int ancho = lin.nextInt();
        int alto = lin.nextInt();
        int nminas;
        Minadero t;
        if (lin.hasNext()) {
            nminas = lin.nextInt();
            t = new Minadero(nminas, ancho, alto);
        } else {
            t = new Minadero(ancho, alto);
        }
        imprimeTablero(t);
        
        /*
        System.out.println("Introduce la casilla que quieres abrir (ej: B 3)");
        do {
            int col, fil;
            imprimeTablero(t);
        } while (!t.completo());
        */
    }
}
