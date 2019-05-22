package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import minadero.Minadero;

/**
 *
 * @author a16diegoar
 */
class Main extends JFrame {
    
    static int tamCampo = 10;
    
    Minadero campo = new Minadero(tamCampo, tamCampo);
    class MinasListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Boton b  = (Boton) e.getSource();
            campo.abre(b.x, b.y);
            if (campo.charAt(b.x, b.y) == '*') {
            	pierde();
            }
            actualizar();
        }
    }
    MinasListener ml = new MinasListener();
    Boton[][] botones = new Boton[10][10];

    Main() throws HeadlessException {
        super("Minadero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Boton.tam*tamCampo+Boton.tam, Boton.tam*tamCampo+2*Boton.tam);
        setResizable(false);
        setLayout(new FlowLayout());
        
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
         } catch (Exception e) {
                    e.printStackTrace();
         }
        
        for (int y = 0; y < tamCampo; y++) {
            for (int x = 0; x < tamCampo; x++) {
            	botones[x][y] = new Boton(x, y);
            	botones[x][y].addActionListener(ml);
                add(botones[x][y]);
                
            }
        }
        
        setVisible(true);
    }
    
    Color colCorrespond(char c) {
    	switch (c) {
    		case '0':
    			return Color.GRAY;
    		case '1':
    			return new Color(0x4646cf);
    		case '2':
    			return new Color(0x137313);
    		case '3':
    			return new Color(0xba1d0f);
    		case '4':
    			return Color.GREEN.darker();
    		case '5':
    		case '6':
    			return Color.YELLOW;
    		case '7':
    		case '8':
    			return Color.MAGENTA;
    		case '*':
    		default:
    			return Color.RED;
    	}
    }
    
    String getImagen(char c) {
    	String ruta = "/gui/img/";
    	if (c != '*') {
    		ruta += Character.toString(c)+".png";
    	} else {
    		ruta += "mina.png";
    	}
    	return ruta;
    }
    
    void abre(int x, int y) {
    	botones[x][y].setEnabled(false);
		//botones[x][y].setText(Character.toString(campo.charAt(x, y)));
		botones[x][y].setBackground(colCorrespond(campo.charAt(x, y)));
		botones[x][y].setIcon(new ImageIcon(getClass().getResource(getImagen(campo.charAt(x, y)))));
    }
    
    void actualizar() {
    	for (int y = 0; y < tamCampo; y++) {
    		for (int x = 0; x < tamCampo; x++) {
    			if (!campo.tapado(x, y)) {
    				abre(x, y);
    			}
    		}
    	}
    	
    	if (campo.completo()) {
    		gana();
    	}
    }
    
    void gana() {
    	JOptionPane.showMessageDialog(null, "Enhorabuena, evitaste un Carrero Blanco :D");
    	System.exit(0);
    }
    
    void pierde() {
    	for (int y = 0; y < tamCampo; y++) {
    		for (int x = 0; x < tamCampo; x++) {
    			abre(x, y);
    		}
    	}
    	JOptionPane.showMessageDialog(null, "Bin Laden estaría orgulloso :-\\");
    	System.exit(0);
    }
   
    public static void main(String[] args) {
        new Main();
    }
}
