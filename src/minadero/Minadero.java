package minadero;

import java.util.Random;

/**
 *
 * @author a16diegoar
 */
public class Minadero {
    private final int   nMinas;
    private final int   ancho;
    private final int   alto;
    private char[][]    mapaMinas;
    private boolean[][] mapaTapas;
    private char        mina = '*';
    private char        cv = ' ';
    
    public Minadero(int nMinas, int ancho, int alto) {
        this.nMinas = nMinas;
        this.ancho = ancho;
        this.alto = alto;
        mapaMinas = new char[ancho][alto];
        mapaTapas = new boolean[ancho][alto];
        
        // inicializamos los mapas
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                mapaMinas[i][j] = cv;
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
    
    public char charAt(int x, int y) {
        char cel = mapaMinas[x][y];
        return cel == '0' ? cv : cel;
    }
    
    public int getAncho() {
        return this.ancho;
    }
    
    public int getAlto() {
        return this.alto;
    }
}
