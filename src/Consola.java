
import java.util.Scanner;
import minadero.Minadero;

public class Consola {
    static char tapa = '·';
    static char cv = ' ';
    
    public static char muestraCasilla(Minadero t, int x, int y) {
        
        if (t.getMarca(x, y)) {
            return '?';
        } else if (t.tapado(x, y)) {
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
    
    public static int getX(char x) {
        return (int) x-65;
    }
    
    public static int getY(int y) {
        return y-1;
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
        
        System.out.println("Introduce la casilla que quieres abrir (ej: B 3)");
        do {
            String orden = sc.nextLine();
            Scanner sl = new Scanner(orden);
            int x = getX(sl.next().toUpperCase().charAt(0));
            int y = getY(sl.nextInt());
            
            if (t.tapado(x, y)) {
                if (sl.hasNext() && sl.next().toUpperCase().charAt(0) == 'M') {
                    t.setMarca(x, y);
                } else {
                    t.abre(x, y);
                    if (t.charAt(x, y) == t.getMina()) {
                        System.out.println("BOOOOOM\n");
                        t.abreTodo();
                        imprimeTablero(t);
                        return;
                    }
                }
            }
            imprimeTablero(t);
        } while (!t.completo());
        System.out.println("CAMPO LIMPIO\n");
        t.abreTodo();
        imprimeTablero(t);
    }
}
