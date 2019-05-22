package gui;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 *
 * @author a16diegoar
 */
public class Boton extends JButton {
    
    static int tam = 50;
    
    int x;
    int y;

    public Boton(int x, int y) {
        super("");
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(tam, tam));
        setOpaque(true);
    }
}
