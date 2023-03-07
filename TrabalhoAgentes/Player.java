package TrabalhoAgentes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


import javax.swing.*;

public class Player extends SwingWorker<Object, Object> {
    private pnPrincipal panel;
    private boolean executar;
    private double positionX;
    private double positionY;
    private int width;
    private int height;
    private Color color;
    private double velocity;
    private double directionX;
    private double directionY;
    private int ang;

    public Player(pnPrincipal panel, double x, double y, int w, int h, int vx){
        this.panel=panel;
        this.positionX=x;
        this.positionY=y;
        this.width=w;
        this.height=h;
        this.directionX = vx;
        this.directionY = 1 - vx;
        this.velocity = 3;
        this.executar = true;
        this.color = new Color(ThreadLocalRandom.current().nextInt(255),ThreadLocalRandom.current().nextInt(255),ThreadLocalRandom.current().nextInt(255));
        this.ang = 0;
    }

    @Override
    public String doInBackground() {        
        while (this.executar) {
			try {
                if(this.checkColision(panel.getWalls("wall"))){
                    this.turn(); 
                    // turnAnimation();
                }
                String[] gates = {"r","d","l","u"};

                for (String gate : gates) { // lógica para virar em cruzamentos (50% de chance)
                    if(this.checkColision(panel.getWalls(gate))&&ThreadLocalRandom.current().nextBoolean()){
                        char randomDir = gate.charAt(new Random().nextInt(gate.length()));
                        newDirection(randomDir);
                    } 
                }

                this.positionY = this.positionY + (directionY * velocity);
                this.positionX = this.positionX + (directionX * velocity);
                
                panel.repaint();
				synchronized (this) {
					this.wait(10);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "";
    }

    @Override
	protected void done() {
		try {
			//label.setText(get());
		} catch (Exception ignore) {
		}
	}

    private void turn() {
        if(directionX>0){ //canto superior direito
            newDirection('u');
        }else if(directionY>0){ //canto inferior direito
            newDirection('r');
        }else if(directionX<0){ // canto inferior esquerdo
            newDirection('d');
        }else if(directionY<0){ // canto superior esquerdo
            newDirection('l');
        }
    }

    public void newDirection(char d){
        this.directionX = 0;
        this.directionY = 0;
        switch(d){
            case 'u':
                this.positionX += pnPrincipal.stWidth*3/2 - this.height/2;
                this.positionY -= this.width - this.height ;
                turnXtoY();
                this.directionY = -1;
            break;
            case 'd':
                this.positionX += this.width - pnPrincipal.stWidth*3/ 2 - this.height/2;
                this.positionY -= 0;
                turnXtoY();
                this.directionY = 1;
            break;
            case 'l':
                this.positionY += this.height - pnPrincipal.stHeight*3/2 - this.width/2 -2;
                this.positionX += this.width - this.height;
                turnXtoY();
                this.directionX = -1;
            break;
            case 'r':
                this.positionY += pnPrincipal.stHeight*3/2 - this.width/2;
                turnXtoY();
                this.directionX = 1;
            break;
        }
    }


    // public void turnAnimation(){
    //     for(int i=0;i<=2;i++){
    //         try {
    //             ang++;
    //             panel.transform.rotate(Math.toRadians(this.ang), this.positionX, this.positionY);
    //             panel.repaint();
	// 			synchronized (this) {
	// 				this.wait(10);
	// 			}
	// 		} catch (Exception e) {
	// 			e.printStackTrace();
	// 		}
    //     }
    //     ang=0;
    //     this.turnXtoY();
    // }

    Boundary lastWall;
    private boolean checkColision(ArrayList<Boundary> walls) {
        //if(velocity>0)
        for (Boundary wall : walls) {
            if(this.positionX + this.width >= wall.getPositionX() && this.positionX <= wall.getPositionX() + wall.getWidth() &&
               this.positionY + this.height > wall.getPositionY() && this.positionY <= wall.getPositionY() + wall.getHeight()&&
               !(lastWall==wall)){
                System.out.println(wall);
                //this.velocity=0;
                lastWall = wall;
                return true;
            }
        }
        return false;
    }

    public void turnXtoY(){
        int tempWidth = this.width;
        this.width = this.height;
        this.height = tempWidth; 
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double x) {
        this.positionX = x;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double y) {
        this.positionY = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public Color getColor() {
        return color;
    }

    public int getAng() {
        return ang;
    }

    public double getVelocity() {
        return velocity;
    }
}
