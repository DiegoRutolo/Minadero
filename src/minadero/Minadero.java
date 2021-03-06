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
    private boolean[][]     mapaMarcas;
    private final char      mina = '*';
    
    /**
     * Establece el numero de minas como (Nº celdas) / 8
     * 
     * @param ancho numero de columnas
     * @param alto  numero de filas
     */
    public Minadero(int ancho, int alto) {
        this((ancho*alto)/8, ancho, alto);
    }
    
    /**
     * Constructor predeterminado 
     * @param nMinas    numero total de minas
     * @param ancho     numero de columnas
     * @param alto      numero de filas
     */
    public Minadero(int nMinas, int ancho, int alto) {
        this.nMinas = nMinas;
        this.ancho = ancho;
        this.alto = alto;
        mapaMinas = new char[ancho][alto];
        mapaTapas = new boolean[ancho][alto];
        mapaMarcas = new boolean[ancho][alto];
        
        // inicializamos los mapas
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                mapaTapas[i][j] = true;
                mapaMarcas[i][j] = false;
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
    public final char charAt(int x, int y) {
        char cel = mapaMinas[x][y];
        return cel;
    }
    
    /**
     * @return the mina
     */
    public char getMina() {
        return mina;
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
    public boolean tapado(int x, int y) {
        boolean b = mapaTapas[x][y];
        return b;
    }
    
    /**
     * Quita la tapa de la casilla (x, y) y las que la rodean hasta encontrar
     * un numero
     * 
     * @param x posicion hozirontal
     * @param y posicion vertical
     */
    public void abre(int x, int y) {
        if (x < 0 || x >= ancho || y < 0 || y >= alto) {
            return;
        }
        if (mapaMarcas[x][y]) {
            return;
        }
        if (!mapaTapas[x][y]) {
            return;
        }
        mapaTapas[x][y] = false;
        if (mapaMinas[x][y] != '0') {
            return;
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                abre(x+i, y+j);
            }
        }
    }
    
    /**
     * Comprueba si todas las casillas sin mina están destapadas
     * 
     * @return  verdadero cuando está todo abierto
     */
    public boolean completo() {
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                if (mapaMinas[x][y] != getMina() && mapaTapas[x][y]) {
                    return false;
                }
                if (mapaMinas[x][y] == getMina() && !mapaTapas[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Si no hay marca, pone una. Si hay, la quita.
     * 
     * @param x posicion horizontal
     * @param y posicion vertical
     */
    public void setMarca(int x, int y) {
        mapaMarcas[x][y] = !mapaMarcas[x][y];
    }
    
    /**
     * Consultar el estado de la marca en (x, y)
     * 
     * @param x posicion horizontal
     * @param y posicion vertical
     * @return  verdadero si hay marca
     */
    public boolean getMarca(int x, int y) {
        return mapaMarcas[x][y];
    }

    /**
     * Descubre todo el tablero
     */
    public void abreTodo() {
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                mapaTapas[x][y] = false;
            }
        }
    }

    /**
     * Cuando pulsamos sobre una casilla con número, comprueba las marcas que
     * hay a su alrededor. Si es coinciden con el numero, abre todas las demas
     * casillas
     * 
     * @param x posicion horizontal
     * @param y posicion vertical
     */
    public void numero(int x, int y) {
        int n = (int) this.charAt(x, y) - 48;
        int m = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    if (this.getMarca(x+i, y+j)) {
                        m++;
                    }
                } catch (IndexOutOfBoundsException e) {}
            }
        }
        if (n == m) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    this.abre(x+i, y+j);
                }
            }
        }
    }
}
