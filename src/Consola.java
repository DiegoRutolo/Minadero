
import java.util.Scanner;
import minadero.Minadero;
import org.apache.commons.lang3.SystemUtils;

public class Consola {
    static char tapa = '·';
    static char cv = ' ';
    static char marca = '?';
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_GREEN = "\u001B[32m";
    
    /**
     * Decide que mostrar en la casilla (x, y)
     * 
     * @param t tablero
     * @param x posicion horizontal
     * @param y posicion vertical
     * @return  cadena a mostrar
     */
    public static String muestraCasilla(Minadero t, int x, int y) {
        if (SystemUtils.IS_OS_LINUX) {
            if (!t.completo()) {
                if (t.getMarca(x, y)) {
                    return ANSI_BLUE + Character.toString(marca) + ANSI_RESET;
                } else if (t.tapado(x, y)) {
                    return Character.toString(tapa);
                } else if (t.charAt(x, y) == '0') {
                    return Character.toString(cv);
                } else if (t.charAt(x, y) == t.getMina()) {
                    return ANSI_RED + Character.toString(t.getMina()) + ANSI_RESET;
                } else {
                    return Character.toString(t.charAt(x, y));
                }
            } else {
                if (t.charAt(x, y) == '0') {
                    return Character.toString(cv);
                } else if (t.charAt(x, y) == t.getMina()) {
                    return ANSI_GREEN + Character.toString(t.getMina()) + ANSI_RESET;
                } else {
                    return Character.toString(t.charAt(x, y));
                }
            }
        } else {
            if (!t.completo()) {
                if (t.getMarca(x, y)) {
                    return Character.toString(marca);
                } else if (t.tapado(x, y)) {
                    return Character.toString(tapa);
                } else if (t.charAt(x, y) == '0') {
                    return Character.toString(cv);
                } else if (t.charAt(x, y) == t.getMina()) {
                    return Character.toString(t.getMina());
                } else {
                    return Character.toString(t.charAt(x, y));
                }
            } else {
                if (t.charAt(x, y) == '0') {
                    return Character.toString(cv);
                } else if (t.charAt(x, y) == t.getMina()) {
                    return Character.toString(t.getMina());
                } else {
                    return Character.toString(t.charAt(x, y));
                }
            }
        }
    }
    
    /**
     * Imprime el estado actual del tablero en forma de cuadricula
     * 
     * @param t el tablero
     */
    public static void imprimeTablero(Minadero t) {
        System.out.println("");
        char c = 'A';
        // letras
        for (int i = 1; i <= t.getAncho(); i++) {
            System.out.print("  " + c + " ");
            c++;
        }
        // borde superior
        System.out.print("\n┌");
        for (int i = 0; i < t.getAncho()-1; i++) {
            System.out.print("───┬");
        }
        // esquina superior derecha
        System.out.println("───┐");
        // las filas excepto la última
        for (int i = 0; i < t.getAlto()-1; i++) {
            // la fila que contine los numeros
            for (int j = 0; j < t.getAncho(); j++) {
                System.out.print("│ " + muestraCasilla(t, j, i) + " ");
            }
            // borde derecho
            System.out.print("│");
            // numero indicador de fila
            System.out.print(" " + (i+1) + " \n");
            // borde izquierdo
            System.out.print("├");
            // borde de casilla intermedio
            for (int j = 0; j < t.getAncho()-1; j++) {
                System.out.print("───┼");
            }
            // borde derecho
            System.out.println("───┤");
        }
        // ultima fila con numeros
        for (int j = 0; j < t.getAncho(); j++) {
            System.out.print("│ " + muestraCasilla(t, j, t.getAlto()-1) + " ");
        }
        // ultimo borde derecho y esquina inferior izquierda
        System.out.print("│ " + t.getAlto() + "\n└");
        // borde inferior
        for (int j = 0; j < t.getAncho()-1; j++) {
                System.out.print("───┴");
            }
        // esquina inferior derecha
            System.out.println("───┘");
        System.out.println("\n");
    }
    
    /**
     * Escribe las instrucciones del juego
     */
    private static void instrucciones() {
        System.out.println("BUSCAMINAS\n");
        System.out.println("Primero se te pedirá que escribas el tamaño del tablero.");
        System.out.println("El formato es ANCHO ALTO [NUM_MINAS] (el numero de minas es opcional)\n");
        System.out.println("Luego debes escribir la casilla que quieres abrir.");
        System.out.println("Si quieres poner una marca, escribe una \"M\" al final.");
        System.out.println("Ej: B 7 M");
        System.out.println("Si tienes el mismo numero de marcas en las casillas adyacentes que el numero,");
        System.out.println("puedes elegir la casilla para abrir todas las casillas excepto las marcadas.\n\n");
    }
    
    /**
     * Calcula la coordenada X a partir de la letra dada
     * 
     * @param x letra de la columna
     * @return  coordenada de la columna
     */
    public static int getX(char x) {
        return (int) x-65;
    }
    
    /**
     * Calcula la coordenada Y a partir del numero de fila
     * 
     * @param y numero de fila
     * @return  coordenada de la fila
     */
    public static int getY(int y) {
        return y-1;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        instrucciones();
        System.out.println("Tamaño del tablero");
        System.out.print("-> ");
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
        
        do {
            System.out.print("-> ");
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
            } else {
                t.numero(x, y);
            }
            imprimeTablero(t);
        } while (!t.completo());
        System.out.println("CAMPO LIMPIO\n");
        t.abreTodo();
        imprimeTablero(t);
    }
}
