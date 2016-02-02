
package asteroidsgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;


public class Asteroid {
    int vida;
    double n;
    AsteroidsGame game;
    int lados;
    Polygon p;
    double grados;
    double vira;
    double vel;
    double decre;
    double xa,ya,x,y;
    int[] xPol, yPol;
    double[]oXPol,oYPol;
    boolean mover;
    
    Asteroid(AsteroidsGame j,int vi,double x, double y){
        
        determin((int)(Math.random()*4)+1);
        vida=vi;
        
        int v=(int)(Math.random()*2);
        
        switch(v){
            case 0: v=-1;break;
            case 1: v=1;break;
        }
        n=1;
        vira=Math.random()*0.01*v;
        vel=Math.random()*0.08*v;
        decre=0.9995;
        this.x=x;
        this.y=y;
        this.grados=0;
        this.game=j;
        this.xPol=new int[lados]; 
        this.yPol=new int[lados];
        this.mover= true;
    }
    Asteroid(AsteroidsGame j,int vid,int x, int y,int vi, double n){
        
        determin((int)(Math.random()*4)+1);
        vida=vid;
        for (int i = 0; i < oXPol.length; i++) {
            oXPol[i]/=(n);
            oYPol[i]/=(n);
        }
        this.n=n;
        vira=0.01*vi;
        vel=Math.random()*0.08*vi;
        decre=0.9995;
        xa*=vi;
        ya*=vi;
        this.x=x;
        this.y=y;
        
        this.grados=0;
        this.game=j;
        this.xPol=new int[lados]; 
        this.yPol=new int[lados];
        this.mover= true;
    }
    
    private void  determin(int in){
        
        double[] x1 = {8,12,32,32,40,20,-12,-24,-16,-8}; //10
        double[] y1 = {16,28,32,12,-4,-24,-24,-8,4,28};

        double[] x2 ={0,16,24,32,28,36,16,-16,-28,-24}; //10
        double[] y2 ={36,28,36,28,16,8,-24,-24,-8,24};
        
        double[] x3 = {12,40,40,24,36,24,16,-24,-40,-40,-20,-28};//12
        double[] y3 = {32,20,12,0,-12,-28,-20,-29,-12,20,20,32};
        
        double[] x4 = {12,20,40,28,40,24,-12,-20,-40,-24,-32,-8};//12
        double[] y4 = {24,32,12,0,-12,-32,-20,-28,-12,16,20,32};
        
            if(in==1) {oXPol=x1;oYPol=y1;lados=10;}
            else if(in==2) {oXPol=x2;oYPol=y2;lados=10;}
            else if(in==3) {oXPol=x3;oYPol=y3;lados=12;}
            else if(in==4) {oXPol=x4;oYPol=y4;lados=12;}
             else{oXPol=x2;oYPol=y2;lados=10;}    
        
        
    }
    
    void move(){
        this.xa*=decre;
        this.ya*=decre;
        this.x+=this.xa;
        this.y+=this.ya;
        this.grados+=vira;
        if(this.mover){
            this.xa+=vel*Math.cos(this.grados);
            this.ya+=vel*Math.sin(this.grados);
        }
        if(this.grados>(2*Math.PI))
            this.grados-=2*Math.PI;
        if (this.grados<0)
            this.grados+=2*Math.PI;
        if(this.x<0) 
            this.x+=this.game.getWidth();
        if(this.x>this.game.getWidth())
            this.x-=this.game.getWidth();
        if(this.y<0)
            this.y+=this.game.getHeight();
        if(this.y>this.game.getHeight())
            this.y-=this.game.getHeight();
        
        
    }
    void paint(Graphics2D g1){
        Graphics2D ga= g1;
        for(int i=0;i<lados;i++){
        this.xPol[i]=(int)(this.oXPol[i]*Math.cos(this.grados)-this.oYPol[i]*Math.sin(grados)+this.x);
        this.yPol[i]=(int)(this.oXPol[i]*Math.sin(this.grados)+this.oYPol[i]*Math.cos(grados)+this.y);
        }
        p = new Polygon(this.xPol,this.yPol,lados);

        ga.setColor(Color.gray);
        ga.drawPolygon(p);
        
        
    }
    boolean balaColision(Shot s){
        if (p.contains(s.x, s.y)) {
            if (n==1) {
                game.rpr.playBigCrash();
            }else if(n==1.5){
                game.rpr.playMediumCrash();
            }else if(n==2){
                game.rpr.playSmallCrash();
            }else{game.rpr.playSmallCrash();}
        }
        return p.contains(s.x, s.y);
    }
    boolean naveColision(Ship sh){
        if (p.intersects(sh.p.getBounds2D())) {
            if (n==1) {
                game.rpr.playBigCrash();
            }else if(n==1.5){
                game.rpr.playMediumCrash();
            }else if(n==2){
                game.rpr.playSmallCrash();
            }else{game.rpr.playSmallCrash();}
        }
        return p.intersects(sh.p.getBounds2D());
    }
    Asteroid split(AsteroidsGame j,int v, int x, int y, int vir, double n){
    return new Asteroid(j,v,x ,y,vir,n);}
    

}
