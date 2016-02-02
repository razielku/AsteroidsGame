
package asteroidsgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;

public class Items {
    
    int[] pX1={-4,4,8,4,4,-4,-8,-4};//8
    int[] pY1={-8,-4,-4,4,8,4,4,-4};
    int[] pX2={-1,5,1,-5};//4
    int[] pY2={-5,-1,5,1};
    double x;
    double y;
    double xa;
    double ya;
    Polygon p1;
    Polygon p2;
    AsteroidsGame game;
    Items(int x, int y,AsteroidsGame gam){
        game=gam;
        gam.contar=0;
        this.x=x;
        this.y=y;
        Random r = new Random();
        xa=((r.nextInt(2)==0?-1:1)*1);
        ya=((r.nextInt(2)==0?-1:1)*1);
    }
    
    void move(int wid, int hei){
        x+=xa;
        y+=ya;
        
        if(this.x<0) 
            this.x+=wid;
        if(this.x>wid)
            this.x-=wid;
        if(this.y<0)
            this.y+=hei;
        if(this.y>hei)
            this.y-=hei;
    }
    void paint(Graphics2D g2){
        int[] xP1= new int[8];
        int[] yP1= new int[8];
        int[] xP2=new int[4];
        int[] yP2=new int[4];
        
        for(int i=0;i<8;i++){
        xP1[i]= (int)(pX1[i]+x);
        yP1[i]=(int)(pY1[i]+y);
        }
        for(int i=0;i<4;i++){
        xP2[i]= (int)(pX2[i]+x);
        yP2[i]=(int)(pY2[i]+y);
        }
        
        p1=new Polygon(xP1,yP1,8);
        p2=new Polygon(xP2,yP2,4);
        g2.setColor(Color.WHITE);
        g2.drawPolygon(p1);
        g2.drawPolygon(p2);
    }
    void dar(Ship sh, AsteroidsGame ga){
        int ran = (int)(Math.random()*3);
        if (ran==0) {
            sh.vida++;
            ga.rpr.playExtraLife();
        }
        if(ran==1){
            ga.puntos+=300;
        }
        if(ran==2){
            ga.inmortal=true;
        }
        
    }
    boolean naveColision(Ship sh){
        
        return p2.intersects(sh.p.getBounds2D());
    }
    
    
}
