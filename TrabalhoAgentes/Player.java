package TrabalhoAgentes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class Player extends SwingWorker<Object, Object> {
    private pnPrincipal panel;
    private boolean executar;
    private boolean canTurn;
    private boolean collidingCar;
    private double positionX;
    private double positionY;
    private double maxVelocity;
    private double velocity;
    private double directionX;
    private double directionY;
    private double width;
    private double height;
    private double aceleration;
    private Color color;
    private Boundary lastWall;
    private boolean lastTLightG;
    private boolean brakes;

    private int ang;

    public Player(pnPrincipal panel, double x, double y, double w, double h){
        this.panel=panel;
        this.positionX=x;
        this.positionY=y;
        this.width=w;
        this.height=h;
        this.directionX = 1;
        this.directionY = 0;
        this.canTurn = true;
        this.maxVelocity = ThreadLocalRandom.current().nextDouble(1,3);
        this.velocity = this.maxVelocity;
        this.executar = true;
        this.color = new Color( (int) (255 * ((this.maxVelocity - 1) / 2.0)), (int) (255 * ((3 - this.maxVelocity) / 2.0)),50);
        this.lastTLightG = false;
        this.ang = 0;
        this.aceleration=0.2*this.maxVelocity;
        this.collidingCar=false;
        this.brakes = false;
    }

    @Override
    public String doInBackground() {        
        while (this.executar) {
			try {
                // if(this.canTurn) this.color = Color.GREEN;
                // else this.color = Color.RED;
                if(this.checkColision(panel.getWalls('w'), true)){ //Colisão com paredes
                    this.turn(); 
                }

                if(this.checkColision(panel.getWalls('0'), true)){ //Colisão com trigger de curva 
                    this.lastTLightG = false;
                    this.canTurn=true;
                }

                if(this.checkTransit(panel.getBots())){
                    this.collidingCar=true;
                } else {
                    this.collidingCar=false;
                    if(this.checkColision(panel.getWalls('s'), false)){ //Colisão com semáforo (contínua)
                        if(lastWall.getColor()==Color.GREEN){
                            this.collidingCar=false;
                            this.lastTLightG = true;
                        } else {
                            if(lastTLightG&&lastWall.getColor()==Color.YELLOW)
                                this.collidingCar=false;
                            else
                                this.collidingCar=true;
                        }   
                    }
                }
                

                char[] gates = {'r','d','l','u'}; //Colisão com curvas
                for (char gate : gates) { // lógica para virar em cruzamentos (50% de chance)
                    if(this.checkColision(panel.getWalls(gate),true)&&ThreadLocalRandom.current().nextBoolean()&&this.canTurn){
                        //char randomDir = gate.charAt(new Random().nextInt(gate.length())); // Pra mais de uma direção
                        newDirection(gate);
                    } 
                }
              
                if(this.collidingCar){
                    this.slowDown();
                } else {
                    this.speedUp();
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
    
    public void drawBot(Graphics2D g2d){
        AffineTransform old = g2d.getTransform();
        double CarW = pnPrincipal.stWidth*2.5;
        double CarH = pnPrincipal.stHeight*1.5;
        int LightsW = (int) (CarW * 0.15);
        int LightsH = (int) (CarH * 0.4);
        int frontGlasssW = (int) (CarW * 0.2);
        int backGlasssW = (int) (CarW * 0.15);
        int GlasssH = (int) (CarH * 0.9);
        g2d.rotate(Math.toRadians(this.ang),this.getPositionX() + CarW/2 ,this.getPositionY() + CarH/2);
        g2d.setColor(this.getColor());
        g2d.fillRect((int) this.getPositionX(), (int) this.getPositionY(), (int)CarW, (int)CarH);
        g2d.setColor(Color.YELLOW);
        g2d.fillRect((int)(this.getPositionX() + CarW - LightsW), (int)(this.getPositionY()), LightsW, LightsH);
        g2d.fillRect((int)(this.getPositionX() + CarW - LightsW), (int)(this.getPositionY() + CarH - LightsH*1), LightsW, LightsH);
        Color bks = new Color(136, 0, 21);
        if(this.brakes)
            bks = Color.RED;
        g2d.setColor(bks);
        g2d.fillRect((int)(this.getPositionX()), (int)(this.getPositionY()), LightsW, LightsH);
        g2d.fillRect((int)(this.getPositionX()), (int)(this.getPositionY() + CarH - LightsH), LightsW, LightsH);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect((int)(this.getPositionX()+CarW*0.6), (int)(this.getPositionY() + CarH/2 - GlasssH/2), frontGlasssW, GlasssH);
        g2d.fillRect((int)(this.getPositionX()+CarW*0.2), (int)(this.getPositionY() + CarH/2 - GlasssH/2), backGlasssW, GlasssH);
        g2d.setTransform(old);

    }

    public void newDirection(char d){
        canTurn=false;
        int pathWidth = pnPrincipal.stWidth * 3;
        int pathHeight = pnPrincipal.stHeight * 3;
        switch(d){
            case 'u':
            this.ang=-90;
            if(this.directionX < 0){
                this.positionX += pathWidth / 2 - this.height / 2;
                this.positionY += this.height - this.width;
            }else{
                this.positionX += this.width - pathWidth / 2 - this.height / 2;
                this.positionY += this.height - this.width;
            }
                turnXtoY();
                this.directionX = 0;
                this.directionY = -1;
            break;
            case 'd':
            this.ang=90;
            if(this.directionX > 0){
                this.positionX += this.width - pathWidth / 2 - this.height / 2;
                this.positionY += 0;
            }else{
                this.positionX += pathWidth/2 -this.height / 2;
                this.positionY +=0;
            }
                turnXtoY();
                this.directionX = 0;
                this.directionY = 1;
            break;
            case 'l':
            this.ang=180;
            if(this.directionY > 0){
                this.positionX += this.width - this.height;
                this.positionY += this.height - pathHeight / 2 - this.width / 2;
            }else{
                this.positionX += this.width - this.height;
                this.positionY += pathHeight / 2 - this.width / 2;
            }
                turnXtoY();
                this.directionX = -1;
                this.directionY = 0;
            break;
            case 'r':
            this.ang=0;
            if(this.directionY < 0){
                this.positionX += 0;
                this.positionY += pathHeight / 2 - this.width / 2;
            }else{
                this.positionX += 0;
                this.positionY += this.height - pathHeight / 2 -this.width / 2;
            }
                turnXtoY();
                this.directionX = 1;
                this.directionY = 0;
            break;
        }
    }

    private void slowDown(){
        this.brakes = true;
        if(this.velocity>0){
            this.velocity-=this.aceleration;
            if(this.velocity<0)
                this.velocity=0;
        }
    }

    private void speedUp(){
        this.brakes = false;
        if(this.velocity<this.maxVelocity){
            this.velocity+=this.aceleration;
            if(this.velocity>3)
                this.velocity=3;
        }
    }

    private double thisRightSideX;
    private double thisLeftSideX;
    private double botRightSideX;
    private double botLeftSideX;
    private double thisTopSideY;
    private double thisBotSideY;
    private double botTopSideY;
    private double botBotSideY;

    private boolean checkTransit(ArrayList<Player> bots) {
        for (Player bot : bots) {
            if(bot!=this){
                double carsDistance = 20;
                double sumRight     = 0;
                double sumTop       = 0;
                double sumLeft      = 0;
                double sumBotton    = 0;

                if(directionX>0)
                    sumRight =carsDistance;
                if(directionX<0)
                    sumLeft =carsDistance;
                if(directionY>0)
                    sumBotton =carsDistance;
                if(directionY<0)
                    sumTop =carsDistance;

                thisRightSideX = this.positionX + this.width + sumRight;
                thisLeftSideX = this.positionX - sumLeft;
                thisTopSideY = this.positionY - sumTop;
                thisBotSideY = this.positionY + this.height + sumBotton;
                botRightSideX = bot.getPositionX();
                botLeftSideX = bot.getPositionX() + bot.getWidth();
                botTopSideY = bot.getPositionY();
                botBotSideY = bot.getPositionY() + bot.getHeight();
                //this.collidingCar = false;
                if(thisRightSideX >= botRightSideX && thisLeftSideX <= botLeftSideX &&
                thisBotSideY > botTopSideY && thisTopSideY <= botBotSideY){
                    //this.color = Color.RED;
                    this.collidingCar = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkColision(ArrayList<Boundary> walls, boolean first) {
        for (Boundary wall : walls) {
            thisRightSideX = this.positionX + this.width;
                thisLeftSideX = this.positionX;
                botRightSideX = wall.getPositionX();
                botLeftSideX = wall.getPositionX() + wall.getWidth();
                thisTopSideY = this.positionY;
                thisBotSideY = this.positionY + this.height;
                botTopSideY = wall.getPositionY();
                botBotSideY = wall.getPositionY() + wall.getHeight();
                if(!(lastWall==wall)||!first)
                    if(thisRightSideX >= botRightSideX && thisLeftSideX <= botLeftSideX &&
                        thisBotSideY > botTopSideY && thisTopSideY <= botBotSideY){
                        lastWall = wall;
                        return true;
                    }
        }
        return false;
    }

    public void turnXtoY(){
        double tempWidth = this.width;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public double getHeight() {
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
