package TrabalhoAgentes;

import java.awt.Color;

public class Boundary {
    private int positionX;
    private int positionY;
    private Color color;
    private int width;
    private int height;
    private String type;

    public Boundary(String t, int x, int y, Color c, int h, int w ){
        this.color= c;
        this.positionX = x;
        this.positionY = y;
        this.type = t;
        this.height = h;
        this.width = w; 
    }

    public String getType() {
        return type;
    }
    
    public Color getColor() {
        return color;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
