
package asteroidsgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

public class Ovni {

    double xP[]={8,12,-12,14,20,-20,20,14,-14,-20,-14,-12,-8};
    double yP[]={12,6,6,6,0,0,0,-6,-6,0,6,6,12};
    int pYP[];
    int pXP[];
    final double g = -3.2588;
    double angulo;
    Polygon ovn;
    double x;
    double y;
    double xa;
    double ya;
    int contador;
    int count;
    int vida;
    AsteroidsGame game;
    ArrayList <Shot>shots=new ArrayList();
    Ovni(AsteroidsGame game,double x, double y){
        vida=1;
        angulo=g;
        this.game=game;
        this.x=x;
        this.y=y;
        contador=0;
        pYP= new int[13];
        pXP= new int [13];
    }
    void move(int scrnX,int scrnY){
        angulo+=0.018;
        Random r = new Random();
        if (contador==0||contador%150==0) {
            xa=Math.random()*3*((r.nextInt(2)==0?-1:1)*1);
            ya=Math.random()*3*((r.nextInt(2)==0?-1:1)*1);
        }
        x+=xa;
        y+=ya;
        if(this.x<0) 
            this.x+=scrnX;
        if(this.x>scrnX)
            this.x-=scrnX;
        if(this.y<0)
            this.y+=scrnY;
        if(this.y>scrnY)
            this.y-=scrnY;
        contador++;
        if (this.count==0||this.count%45==0) {
                this.shots.add(shootin(this));
                game.rpr.playBigOvni();
            }
            this.count++;
        for (int i = 0; i < shots.size(); i++) {
            shots.get(i).move(scrnX, scrnY);
        }
        for (int i = 0; i < shots.size(); i++) {
            if (shots.get(i).vida<=0) {
                shots.remove(i);
            }
        }
    }
    Shot shootin(Ovni s){
    return new Shot(s);}
    boolean asteroidColision(Asteroid asteroide){
        if(ovn.intersects(asteroide.p.getBounds2D()))
            game.rpr.playBigCrash();
        return ovn.intersects(asteroide.p.getBounds2D());
    }
    boolean balaColision(Shot shot){
        if (ovn.contains(shot.x, shot.y))
            game.rpr.playBigCrash();
        return ovn.contains(shot.x, shot.y);
    }
    boolean naveColision(Ship sh){
        if (ovn.intersects(sh.p.getBounds2D()))
            game.rpr.playBigCrash();
        return ovn.intersects(sh.p.getBounds2D());
    }
    
    void paint(Graphics2D g2){
        for(int i=0;i<13;i++){
        pXP[i]= (int)(xP[i]*Math.cos(g)-yP[i]*Math.sin(g)+x);
        pYP[i]=(int)(xP[i]*Math.sin(g)+yP[i]*Math.cos(g)+y);
        }
        ovn = new Polygon(pXP,this.pYP,13);
        
        for (int i = 0; i < shots.size(); i++) {
            shots.get(i).paint(g2);
        }
        
        g2.setColor(Color.gray);
        g2.drawPolygon(ovn);
    }
    

}
