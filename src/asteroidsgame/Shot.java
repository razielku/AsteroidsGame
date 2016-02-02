
package asteroidsgame;

import java.awt.Color;
import java.awt.Graphics2D;

public class Shot {
    Ship ship;
    double x,y,ya,xa,angulo;
    int vida;
    final double velocidad=20;
    Shot (Ship s){
        vida=30;
        this.ship=s;
        angulo = s.angulo;
        x=s.x;
        y=s.y;
        xa=s.xa;
        ya=s.ya;
    }
    Shot (Ovni s){
        vida=10;
        angulo = s.angulo;
        x=s.x;
        y=s.y;
        xa=s.xa;
        ya=s.ya;
    }
    
    void move(int scrnWidth, int scrnHeight){
        vida--;
        x+=velocidad*Math.cos(angulo)+xa;
        y+=velocidad*Math.sin(angulo)+ya;;
        if(x<0)
            x+=scrnWidth;
        if(x>scrnWidth)
            x-=scrnWidth;
        if(y<0)
            y+=scrnHeight;
        if(y>scrnHeight)
            y-=scrnHeight;
    }
    void paint(Graphics2D g0){
        
        Graphics2D g= g0;
        g.setColor(Color.yellow);
        g.fillOval((int)x, (int)y, 3, 3);
    }
    
    
    
    
}
