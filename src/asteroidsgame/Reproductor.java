
package asteroidsgame;
import javax.sound.sampled.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Reproductor{
    private Clip bigCrash;
    private Clip mediumCrash;
    private Clip smallCrash;
    private Clip asteroidAvan1;
    private Clip asteroidAvan2;
    private Clip extraLife;
    private Clip shooting;
    private Clip bigOvni;
    private Clip smallOvni;
    private Clip avanza;
    BufferedInputStream bis1;
    AudioInputStream ais1;
    BufferedInputStream bis2;
    AudioInputStream ais2;
    BufferedInputStream bis3;
    AudioInputStream ais3;
    BufferedInputStream bis4;
    AudioInputStream ais4;
    BufferedInputStream bis5;
    AudioInputStream ais5;
    BufferedInputStream bis6;
    AudioInputStream ais6;
    BufferedInputStream bis7;
    AudioInputStream ais7;
    BufferedInputStream bis8;
    AudioInputStream ais8;
    BufferedInputStream bis9;
    AudioInputStream ais9;
    BufferedInputStream bis;
    AudioInputStream ais;
    Reproductor(){
        try {
            
            bis3 = new BufferedInputStream(getClass().getResourceAsStream("Fx/thrust.wav"));
            ais3 = AudioSystem.getAudioInputStream(bis3);
            avanza = AudioSystem.getClip();
            avanza.open(ais3);
            
            bis4 = new BufferedInputStream(getClass().getResourceAsStream("Fx/bangLarge.wav"));
            ais4 = AudioSystem.getAudioInputStream(bis4);
            bigCrash = AudioSystem.getClip();
            bigCrash.open(ais4);

            bis5 = new BufferedInputStream(getClass().getResourceAsStream("Fx/bangMedium.wav"));
            ais5 = AudioSystem.getAudioInputStream(bis5);
            mediumCrash = AudioSystem.getClip();
            mediumCrash.open(ais5);
            
            bis6 = new BufferedInputStream(getClass().getResourceAsStream("Fx/bangSmall.wav"));
            ais6 = AudioSystem.getAudioInputStream(bis6);
            smallCrash = AudioSystem.getClip();
            smallCrash.open(ais6);
            
            bis7 = new BufferedInputStream(getClass().getResourceAsStream("Fx/beat1.wav"));
            ais7 = AudioSystem.getAudioInputStream(bis7);
            asteroidAvan1 = AudioSystem.getClip();
            asteroidAvan1.open(ais7);
            
            bis8 = new BufferedInputStream(getClass().getResourceAsStream("Fx/beat2.wav"));
            ais8 = AudioSystem.getAudioInputStream(bis8);
            asteroidAvan2 = AudioSystem.getClip();
            asteroidAvan2.open(ais8);
            
            bis9 = new BufferedInputStream(getClass().getResourceAsStream("Fx/extraShip.wav"));
            ais9 = AudioSystem.getAudioInputStream(bis9);
            extraLife = AudioSystem.getClip();
            extraLife.open(ais9);
            
            bis1 = new BufferedInputStream(getClass().getResourceAsStream("Fx/saucerBig.wav"));
            ais1 = AudioSystem.getAudioInputStream(bis1);
            bigOvni = AudioSystem.getClip();
            bigOvni.open(ais1);

            bis = new BufferedInputStream(getClass().getResourceAsStream("Fx/saucerSmall.wav"));
            ais = AudioSystem.getAudioInputStream(bis);
            smallOvni = AudioSystem.getClip();
            smallOvni.open(ais);    
            
            bis2= new BufferedInputStream(getClass().getResourceAsStream("Fx/fire.wav"));
            ais2 = AudioSystem.getAudioInputStream(bis2);
            shooting = AudioSystem.getClip();
            shooting.open(ais2);
            
        } catch (LineUnavailableException | 
                UnsupportedAudioFileException | 
                IOException ex) {}
    }
    void playShooting(){
//        try {
        if(shooting.isRunning()){
            shooting.stop();}
            shooting.setMicrosecondPosition(0);
//            else 
            shooting.start();
//        } catch (IOException ex) {} catch (LineUnavailableException ex) {}
    }
    
    void playAvanza(){
        if(avanza.isRunning())
            avanza.loop(1);
        if(!avanza.isRunning())
            avanza.setMicrosecondPosition(0);
        avanza.loop(1);
    }
    void playBigCrash(){
        
        if(!bigCrash.isRunning())
            bigCrash.setMicrosecondPosition(0);
        bigCrash.loop(0);
        
    }
    void playMediumCrash(){
        
        if(!mediumCrash.isRunning())
            mediumCrash.setMicrosecondPosition(0);
        mediumCrash.loop(0);
        
        
    }
    void playSmallCrash(){
        
        if(!smallCrash.isRunning())
            smallCrash.setMicrosecondPosition(0);
        smallCrash.loop(0);
        
    }
    void playAsteroidAvan1(){
        
        if(!asteroidAvan1.isRunning())
            asteroidAvan1.setMicrosecondPosition(0);
        asteroidAvan1.loop(0);
        
    }
    void playAsteroidAvan2(){
        
        if(!asteroidAvan2.isRunning())
            asteroidAvan2.setMicrosecondPosition(0);
        asteroidAvan2.loop(0);
        
    }
    void playExtraLife(){
        
        if(!extraLife.isRunning())
            extraLife.setMicrosecondPosition(0);
        extraLife.loop(0);
        
    }
    
    void playBigOvni(){
        
        if(!bigOvni.isRunning())
            bigOvni.setMicrosecondPosition(0);
        bigOvni.loop(0);
        
    }
    void playSmallOvni(){
        
        if(!smallOvni.isRunning())
            smallOvni.setMicrosecondPosition(0);
        smallOvni.loop(0);
        
    }
    
}
