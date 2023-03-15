package TrabalhoAgentes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Boundary {
    private int positionX;
    private int positionY;
    private Color color;
    private int width;
    private int height;
    private String type;
    private boolean RedStage;

    public Boundary(String t, int x, int y, Color c, int h, int w ){
        this.color= c;
        this.positionX = x;
        this.positionY = y;
        this.type = t;
        this.height = h;
        this.width = w; 
    }

    public Boundary(String t, int x, int y, Color c, int h, int w, boolean r){
        this.color= c;
        this.positionX = x;
        this.positionY = y;
        this.type = t;
        this.height = h;
        this.width = w;
        this.RedStage = r; 
    }

    public void drawWall(Graphics2D g2d){
        // if(true){
        if(this.getType()!="r"&&this.getType()!="l"&&this.getType()!="u"&&this.getType()!="d"&&this.getType()!="0"){
            int ang = 0;
            int tLightHeight = 17;
            int tLightWidth = pnPrincipal.stWidth * 3;
            int lightRadius = 12;

            if(this.getType().charAt(0)=='s'){
                switch(this.getType().charAt(1)){
                    case '0':// top
                    ang=0;
                    break;
                    case '1':// left
                    ang = -90;
                    break;
                    case '2': // bottom
                    ang = 180;
                    break;
                    case '3': // right
                    ang = 90;
                    break;
                }
                AffineTransform old = g2d.getTransform();
                g2d.rotate(Math.toRadians(ang),this.getPositionX() + pnPrincipal.stWidth*3/2 ,this.getPositionY() + pnPrincipal.stHeight*3/2);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRect(this.getPositionX(), this.getPositionY() + pnPrincipal.stHeight*3 - tLightHeight, tLightWidth , tLightHeight);
                Color red = Color.GRAY;
                Color yellow = Color.GRAY;
                Color green = Color.GRAY;
                if(this.getColor()==Color.GREEN){
                    green = Color.GREEN;
                } else if(this.getColor()==Color.YELLOW){
                    yellow = Color.YELLOW;
                } else if(this.getColor()==Color.RED){
                    red = Color.RED;
                }
                g2d.setColor(green);
                g2d.fillOval(this.getPositionX(), this.getPositionY() + pnPrincipal.stHeight * 3 - (tLightHeight + lightRadius) / 2, lightRadius, lightRadius);
                g2d.setColor(yellow);
                g2d.fillOval(this.getPositionX() + lightRadius, this.getPositionY() + pnPrincipal.stHeight * 3 - (tLightHeight + lightRadius) / 2, lightRadius, lightRadius);
                g2d.setColor(red);
                g2d.fillOval(this.getPositionX() + lightRadius * 2, this.getPositionY() + pnPrincipal.stHeight * 3 - (tLightHeight + lightRadius) / 2 , lightRadius, lightRadius);
                
                g2d.setTransform(old);
                } else {
                g2d.setColor(this.getColor());
                g2d.fillRect(this.getPositionX(), this.getPositionY(),
                this.getWidth() , this.getHeight());
            }
        }
    }

    public String getType() {
        return type;
    }

    public void setColor(Color color) {
        this.color = color;
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

    public boolean isRedStage() {
        return RedStage;
    }

    public void setRedStage(boolean redStage) {
        RedStage = redStage;
    }
}
