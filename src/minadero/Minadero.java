package minadero;

import java.util.Random;

/**
 *
 * @author a16diegoar
 */
public class Minadero {
    private final int       nMinas;
    private final int       ancho;
    private final int       alto;
    private final char[][]  mapaMinas;
    private boolean[][]     mapaTapas;
    private final char      mina = '*';
    
    public Minadero(int nMinas, int ancho, int alto) {
        this.nMinas = nMinas;
        this.ancho = ancho;
        this.alto = alto;
        mapaMinas = new char[ancho][alto];
        mapaTapas = new boolean[ancho][alto];
        
        // inicializamos los mapas
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                mapaTapas[i][j] = false;
            }
        }
        
        // colocamos las minas
        for (int i = 0; i < nMinas; i++) {
            int x, y;
            do {
                Random rnd = new Random();
                x = rnd.nextInt(ancho);
                y = rnd.nextInt(alto);
            } while (this.charAt(x, y) == mina);
            mapaMinas[x][y] = mina;
        }
        
        // colocamos los numeros
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                if (this.charAt(x, y) != mina) {
                    char cont = '0';
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            try {
                                if (this.charAt(x+i, y+j) == mina) {
                                    cont++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {}
                        }
                    }
                    mapaMinas[x][y] = cont;
                }
            }
        }
    }
    
    /**
     * Devuelve el caracter en la posición (x, y). Siempre será un numero
     * entre 0 y 9 o un asterisco para indicar la mina
     * 
     * @param x posición horizontal
     * @param y posición vertical
     * @return caracter en (x, y)
     */
    public char charAt(int x, int y) {
        char cel = mapaMinas[x][y];
        return cel;
    }
    
    /**
     * Devuelve el numero total de celdas horizontales
     * @return ancho
     */
    public int getAncho() {
        return this.ancho;
    }
    
    /**
     * Devuelve el numero total de celdas verticales
     * @return alto
     */
    public int getAlto() {
        return this.alto;
    }
    
    /**
     * Indica si hay una tapa sobre la posición (x, y)
     * 
     * @param x posicion horizontal
     * @param y posicion vertical
     * @return verdadero si hay tapa, falso si no
     */
    public boolean getTapa(int x, int y) {
        boolean b = mapaTapas[x][y];
        return b;
    }
}
