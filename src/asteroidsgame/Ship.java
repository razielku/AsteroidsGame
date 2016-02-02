
package asteroidsgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Ship implements KeyListener{
    AsteroidsGame game;
    ArrayList <Shot> s= new ArrayList();
    boolean acelerar,girarD,girarI,dispara;
    Polygon p;
    final double g = -1.574;
    int vida;
    int level;
    int contador;
    double angulo;
    double viraje;
    double xa,ya,x,y;
    final double[] oXP={14,-10,-6,-10},oYP={0,-8,0,8},oFX={-6,-23,-6},oFY={-3,0,3}; 
    int[] xPol, yPol, flamaX, flamaY;
    Ship(AsteroidsGame j,double x, double y){
        
        vida=2;
        level = 1;
        viraje=0.13;
        this.contador=0;
        this.acelerar=false;
        this.girarD=false;
        this.girarI=false;
        this.x=x;
        this.y=y;
        this.angulo=g;
        this.game=j;
        this.xPol=new int[4]; 
        this.yPol=new int[4]; 
        this.flamaX=new int[3]; 
        this.flamaY=new int[3];
    }
    void relocate(double x, double y){
        /*TODO 
        Problema actual.
        Al morir y aun quedar vidas se recoloca al centro, 
        quedando expuesto a perder el resto de vidas
        por culpa de un asteroide en el medio
        */
        xa=0;
        ya=0;
        this.contador=0;
        this.acelerar=false;
        this.girarD=false;
        this.girarI=false;
        this.x=x;
        this.y=y;
        this.angulo=g;
    }
    void move(){
        this.xa*=0.99;
        this.ya*=0.99;
        this.x+=this.xa;
        this.y+=this.ya;
        
        if (this.dispara) {
            if (this.contador==0||this.contador%15==0) {
                this.s.add(shootin(this));
                game.rpr.playShooting();
            }
            this.contador++;
        }else if (!this.dispara){this.contador=0;}
        
        for (int i = 0; i < this.s.size(); i++) {
            this.s.get(i).move(this.game.getWidth(), this.game.getHeight());
        }
        for (int i = 0; i < this.s.size(); i++) {
            if (this.s.get(i).vida<=0) {
                this.s.remove(i);
            }
        }
        double vira=0;
        if(this.girarD){
            vira=viraje;
            this.angulo+=vira;}
        if(this.girarI){
            vira=-viraje;
            this.angulo+=vira;}
        if(!this.girarD&&!this.girarI)
            vira=0;
        if(this.acelerar){
            this.xa+=0.09*Math.cos(this.angulo);
            this.ya+=0.09*Math.sin(this.angulo);
        }
        if(this.angulo>(2*Math.PI))
            this.angulo-=2*Math.PI;
        if (this.angulo<0)
            this.angulo+=2*Math.PI;
        if(this.x<0) 
            this.x+=this.game.getWidth();
        if(this.x>game.getWidth())
            this.x-=this.game.getWidth();
        if(this.y<0)
            this.y+=this.game.getHeight();
        if(this.y>this.game.getHeight())
            this.y-=this.game.getHeight();
        
        
        
    }
    Shot shootin(Ship s){
    return new Shot(s);}
    
    void paint(Graphics2D g2){
        Graphics2D g= g2;
        if(this.acelerar){
            for(int i=0;i<3;i++){ 
            this.flamaX[i]=(int)(this.oFX[i]*Math.cos(this.angulo)-this.oFY[i]*Math.sin(this.angulo)+this.x); 
            this.flamaY[i]=(int)(this.oFX[i]*Math.sin(this.angulo)+this.oFY[i]*Math.cos(this.angulo)+this.y); 
            }
        g.setColor(Color.red);
        g.fillPolygon(this.flamaX,this.flamaY,3);
        }
        for(int i=0;i<4;i++){ 
        this.xPol[i]=(int)(this.oXP[i]*Math.cos(this.angulo)-this.oYP[i]*Math.sin(this.angulo)+this.x);
        this.yPol[i]=(int)(this.oXP[i]*Math.sin(this.angulo)+this.oYP[i]*Math.cos(this.angulo)+this.y);
        }
        g.setColor(Color.white);
        p = new Polygon(this.xPol,this.yPol,4);
        g.fillPolygon(p);
        if (this.s.size()>=1) {
            for (int i = 0; i < this.s.size(); i++) {
            this.s.get(i).paint(g);
            }
        }
        
    }
    boolean colisionBala(ArrayList <Shot> shot){
        for (int i = 0; i < shot.size(); i++) {
            if (p.contains(shot.get(i).x, shot.get(i).y)){
            game.rpr.playBigCrash();
            return true;
            }
        }
        return false;
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_UP){
            this.acelerar=true;}
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
            this.girarI=true;
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            this.girarD=true;
        if(e.getKeyCode()==KeyEvent.VK_CONTROL)
            this.dispara=true;
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
            game.over=false;
    }
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_UP)
            this.acelerar=false;
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
            this.girarI=false;
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            this.girarD=false;
        if(e.getKeyCode()==KeyEvent.VK_CONTROL)
            this.dispara=false;
    }
    @Override
    public void keyTyped(KeyEvent e){}
    

}
