package Game.view;

import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;
import Game.model.Form.Shape;
import Game.model.Plateau;
import Game.model.EcouteurModel;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements EcouteurModel {

    private Plateau plateau;
    private Shape previewShape; // forme temporaire //
    private static final Color SKY_BLUE = new Color(135, 206, 235);
    private static final Color GRAY = new Color(191, 191, 191);
    private static final Color LIGHT_RED = new Color(255, 100, 100, 100);
    
    public GamePanel(Plateau plateau) {
        this.plateau = plateau;
        plateau.ajoutEcouteur(this);
        setBackground(GRAY);
        setOpaque(true);  
    }

    public void setPreview(Shape s) {
        this.previewShape = s;
        repaint();
    }

    public void clearPreview() {
        this.previewShape = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Test Initialisation //   
        for (Shape s : plateau.getFormePlacees()) {
            drawShape(g, s, SKY_BLUE);   
        }
        
        for (Shape s : plateau.getObstacles()) {
            drawShape(g, s, LIGHT_RED);
        }

        
        //System.out.println(plateau.getObstacles());
        
        
        // Dessine la forme temporaire (preview) //
        if (previewShape != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                         0, new float[]{5}, 0));
            drawShape(g, previewShape, GRAY);
        }
    }

    private void drawShape(Graphics g, Shape s, Color color) {
    	
    	Graphics2D g2 = (Graphics2D) g; 
    	
    	//g2.setStroke(new BasicStroke(2));
    	
        if (s instanceof RectangleShape) {
            RectangleShape r = (RectangleShape) s;
            g2.setColor(color); // couleur du remplissage
            g2.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());

            g2.setColor(Color.black); // couleur du contour
            g2.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());


        } else if (s instanceof CircleShape) {
            CircleShape c = (CircleShape) s;
            g2.setColor(color);
            g2.fillOval((int) c.getX(), (int) c.getY(), (int) c.getWidth(), (int) c.getHeight());

            g2.setColor(Color.black);
            g2.drawOval((int) c.getX(), (int) c.getY(), (int) c.getWidth(), (int) c.getHeight());
        }
    }

	@Override
	public void stateChanged(Object source) {
		repaint();
		
	}
   
}