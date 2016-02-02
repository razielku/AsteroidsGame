package asteroidsgame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AsteroidsGame extends JPanel {
//declaro los ArrayList de los distintos tipos que necesito
    ArrayList <Asteroid> asteroid= new ArrayList();
//si bien solo habrá un ovni en pantalla, me resultaba más comodo ponerlo en un arraylist para poder sacarlo de pantalla
    ArrayList <Ovni> ovni= new ArrayList();
    ArrayList <Items> item = new ArrayList();
    Reproductor rpr = new Reproductor();
    Ship ship;
    
    int level;
    int puntos;
    JFrame frame;
    boolean over;
    boolean next;
    boolean ovn;
    boolean inmortal;
    boolean starting;
    int contar=0;
    int contador=0;
    long time1;
    long time2;

    AsteroidsGame(JFrame frame){
        //todos los booleanos a false
        starting=false;
        inmortal=false;
        ovn=false;
        over = false;
        next=false;
        ship = new Ship(this,getWidth()/2,getWidth()/2); //se crea la nave al medio de la pantalla
        addKeyListener(ship);//se declara el key listener
        this.frame=frame;
        setFocusable(true);//se declara la pantalla como activa o algo asi
    }
    @Override
    public void paint(Graphics g){
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
        if (!over&&!next) { //si over y next son false, se pintara lo siguiente en pantalla
            
//            Image img = null;
//            try {                
//            img=ImageIO.read(new File((System.getProperty("user.dir")+"/src/asteroidsgame/Fx/jupit4.png")));
//            } catch (IOException ex) {}
//            g2d.drawImage((BufferedImage) img, null, -200, 0);
            
            ship.paint(g2d); // la nave
            if (starting) {
                g2d.setColor(Color.GREEN);
                g2d.setFont(new Font("",Font.BOLD,25));
                g2d.drawString("↶↑↷",(float)ship.x-36,(float)ship.y-20);
                g2d.setFont(new Font("",Font.BOLD,15));
                g2d.drawString("Ctrl to shoot",(float)ship.x-40,(float)ship.y+25);
            }
            
            
            if (inmortal){
                g2d.setColor(Color.yellow);
                g2d.drawOval((int)ship.x-13, (int)ship.y-13, 24, 24); //si se es inmortal un ovalo alrededor de la nave
            }
            for (int i = 0; i < item.size(); i++) { //los items
                item.get(i).paint(g2d);
            }
            //el texto con puntaje y nivel
            g2d.setColor(Color.cyan);
            g2d.setFont(new Font("",Font.PLAIN,10));
            g2d.drawString("Level " + level+"  Puntos: "+puntos,20,20);
            //los poligonos que representa la cantidad de vidas restante
            int[] xLife={0,3,0,-3}; // las coordenadas de los puntos X (figurando un plano cartesiano)
            int[] yLife={-4,4,2,4}; // las coordenadas de los puntos Y (figurando un plano cartesiano)
            int[] xL = new int[4]; 
            int[] yL = new int[4];
            for (int i = 0; i < ship.vida-1; i++) { //por cada (vida-1) se pinta un poligono
                for (int j = 0; j < 4; j++) {
                    xL[j]=(xLife[j]*2)+(25*(i+1)); // suma a los puntos X la posición X en pantalla donde quiero que aparezca (y lo multiplica en caso de haber más de una)
                    yL[j]=(yLife[j]*2)+(40); //suma los puntos Y la posicion Y en pantalla donde quiero que aparezca el poligono
                }
                g2d.drawPolygon(xL,yL,4); //finalmente dibujo el poligono en pantalla dandole un arreglo X e Y y le indico que son 4 puntos
            }
            if (ovn) { // dibuja al ovni si hay
                for (int i =0; i<ovni.size();i++) {
                    ovni.get(i).paint(g2d);
                }
            }
            
            for (int i = 0; i < this.asteroid.size(); i++) { // dibuja los asteroides si hay
                this.asteroid.get(i).paint(g2d);
            }
        }else if (over){//en caso de ser OVER, dibuja en pantalla el Game Over
            g2d.setColor(Color.white);
            g2d.setFont(new Font("",Font.BOLD,30));
            g2d.drawString("Game Over", (float)(getWidth()/2),(float)(getHeight()/2)-80);
            g2d.setFont(new Font("",Font.BOLD,14));
            g2d.drawString(puntos+" Points", (float)(getWidth()/2),(float)(getHeight()/2));
            g2d.setFont(new Font("",Font.BOLD,15));
            g2d.drawString("press 'Enter' to try again", (float)(getWidth()/2-15),(float)(getHeight()/2-40));
        }else if(next){ // en caso de NEXT, indica el nivel en pantalla
            g2d.setColor(Color.white);
            g2d.setFont(new Font("",Font.BOLD,30));
            g2d.drawString("Level "+level, (float)(getWidth()/2-10),(float)(getHeight()/2) );
        }
    }
    long timer1;
    long timer2;
    void setearLevel() throws InterruptedException{ // seteo el nivel
        Thread.sleep(25);
        
        if (level==0) {
            starting=true;
            timer1=System.currentTimeMillis();
        }
        if (starting){
            timer2=System.currentTimeMillis();
            
            if (timer2-timer1>=10000) {
                starting=false;
            }
        }
        
        if (!inmortal) {
            time1=System.currentTimeMillis();
        }
        if (inmortal) {
            time2=System.currentTimeMillis();
            
            if (time2-time1>=20000) {
                inmortal=false;
            }
        }
        
        
        if (ship.acelerar) { //si la nave acelera suena el sonido
            rpr.playAvanza();
        }
        if(!inmortal){contar=0;} //setea a 0 el contador si no se es inmortal
        if (inmortal) { // siendo inmortal
            if (contar==0||contar%32==0) { //ejecuta el sonido si contar es 0 o multiplo de 32
                rpr.playSmallOvni();    //asi evito un sonido sobre otro
            }
            contar++;
        }
        int ran= (int) (Math.random()*1000); //random de 0 a 999
        if (ran<asteroid.size()){ // si el numero random es menor a la cantidad de asteroides
            ovn=true;  // aparece un ovni
        }
        if (ovn&&ovni.isEmpty()){ // si ovn true y el arreglo ovni se encuentra vacío
            ovni.add(new Ovni(this,-10,Math.random()*getHeight())); // agrega un ovni al arreglo
        }
        
        if (ship.vida>0){ // si la nave sigue viva
            if (asteroid.isEmpty()&&ovni.isEmpty()){ // y no hay mas asteroides u ovnis
                level++; // sube el nivel
                System.gc(); //el garbage colector del sistema, le 'sugiere' que borre todo objeto no usado. lo agregué por problemas con los sonidos
                inmortal=false; //vuelve mortal la nave
                ship.s.clear(); //borra las balas
                
                for (int i = 0; i < level; i++) { //segun el nivel
                    double xx=Math.random()*frame.getWidth();  //genera dos randoms
                    double yy=Math.random()*frame.getHeight(); 
                    if (!(xx>=((getWidth()/2)-35)&&yy>=((getHeight()/2)-35) && //y si esos randoms
                            xx<=((getWidth()/2)+10)&&yy<=((getHeight()/2)+10))) { // no se encuentran en el centro de la pantalla
                        asteroid.add(new Asteroid(this,3,xx,yy)); //se agrega un asteroide con esas coordenadas
                    }else{
                        i--; // si no, se tiran de nuevo
                    }
                }
                ship.relocate(getWidth()/2, getHeight()/2); // la nave es recolocada al centro de la pantalla
                next=true; // next es true
                if (next) {// si next es true
                    frame.repaint(); // se repinta (ocacionando que pinte las palabras next del paint)
                    repaint();
                    
                    Thread.sleep(1000); //el juego se pausa por un segundo
                    next=false; // y next vuelve a aser false
                }
            }
        }
        if (ship.vida<=0&&!over) { // si la vida de la  nave es igual o menor a 0 y over falso
            level=0;   // se setea el nivel a 0
            puntos=0; // puntos a 0
            ship.vida=2; // y vida de nave a 2
        }
    }
    void controlMasas(){ // limpia todos los arraylist y repinta la pantalla
        
        if (over){//solo si over es true
            asteroid.clear();
            ship.s.clear();
            ovni.clear();
            item.clear();
            item.removeAll(item);
            repaint();
            frame.repaint();
        }
    }
    public void run() throws InterruptedException{ // donde el juego corre
        for (;;) { // 'for' infinito
            controlMasas(); //se ejecuta control de masas solo si over es true
            if (!over) {  // de lo contrario
                setearLevel(); // setealevel
                if (!next) {  // si next ya es falso
                    //todo se mueve
                    ship.move();
                    moverAsteroid();
                    moverOvni();
                    moverItem();
                    
                }
            }
            // y se repinta
            repaint();
            frame.repaint();
        }
    }
    void moverItem(){
        try{ // en mi caso las colisiones me arrojaban errores, asi que les puse try catch
        for (int i = 0; i < item.size(); i++) { // mueve todos los items en el arraylist
            item.get(i).move(getWidth(), getHeight());
        }
        for (int i = 0; i < item.size(); i++) { //pregunta a cada item si colisiono con la nave
            if (item.get(i).naveColision(ship)) { //de ser asi
                item.get(i).dar(ship, this); //ejecuta que cosa le regala
                item.remove(i); // y se quita del arreglo
            }

        }}catch(Exception e){}
    }
    
    void moverOvni(){
        if (ovni.isEmpty()) {
            ovn=false;
        }
        
        
        try{
        for (int i = 0; i < ovni.size(); i++) { // mueve el ovni
            ovni.get(i).move(getWidth(), getHeight());
            
            for (int j = 0; j < ship.s.size(); j++) { //pregunta por cada bala disparada si colisiono con ovni
                if (ovni.get(i).balaColision(ship.s.get(j))) { // de ser asi
                    ovni.get(i).vida--; // le resta la vida del ovni
                    puntos+=100; // y agrega 100 puntos
                }
            }
            for (int j = 0; j < asteroid.size(); j++) { // ve si el ovni colision co un asteroide
                
                if (ovni.get(i).asteroidColision(asteroid.get(j))) { // restandole vida al aasteroide ser ser asi
                    ovni.get(i).vida--;
                }
            }
            if (ovni.get(i).naveColision(ship)) { // ve si colisiona con la nave
                if (!inmortal) { // si la nave es mortal
                    ship.vida--; // se le resta una vida a la nave
                }
                ovni.get(i).vida--; // y se le resta vida al ovni
                if (ship.vida<=0) { // caso de nave vida menor o igual a 0
                    over=true; //se temrina la partida
                }
                if(ship.vida>0&&!inmortal){ // en caso de ser mortal y aun mantener vidas
                    ship.relocate(getWidth()/2, getHeight()/2); // recoloc al anave en el centro
                }
            }
            
            if (ship.colisionBala(ovni.get(i).shots)&&!inmortal) { //pregunta si lan ave colisiona con los disparos del ovni
                ship.vida--; // quitandole vida
                
                if (ship.vida<=0) {
                    over=true;
                }
                if(ship.vida>0){
                    ship.relocate(getWidth()/2, getHeight()/2);
                }

            }
            
            
        }}catch(Exception e){}
        for (int i = 0; i < ovni.size(); i++){ 
            if (ovni.get(i).vida<=0||ship.vida<=0) { // si la nave o el ovni tienen vida 0
                ovni.remove(i); //el ovni se quita del arraylist
                ovn=false;
            }
        }
            
    }
    
    void moverAsteroid(){
        if(asteroid.isEmpty()){contador=0;} //igual que el sonido inmortal en setearlevel
        if (!asteroid.isEmpty()) {
            if (contador==0||contador%40==0) {
                rpr.playAsteroidAvan1(); // solo que ejecuta el avanzar de los asteroides
            }
            contador++;
        }
        

        for (int i = 0; i < asteroid.size(); i++) { // se mueven los asteroides
            asteroid.get(i).move();
            
            try{
            if (asteroid.get(i).naveColision(ship)&&!inmortal) { // ve si colisionan con la nave
                ship.vida--;
                if (ship.vida<=0&&!over) {
                    over=true;
                }
                if(ship.vida>0){
                    ship.relocate(getWidth()/2, getHeight()/2);
                }
            }
            for (int j = 0; j < ship.s.size(); j++){ // si les alcanza una bala
                    if(asteroid.get(i).balaColision(ship.s.get(j))){
                        
                        int x1=0,y1=0,x2=0,y2=0;
                        int n=17;
                        int n2=5;
                        if (((ship.s.get(j).x>asteroid.get(i).x||ship.s.get(j).x<asteroid.get(i).x)&&(ship.s.get(j).y-asteroid.get(i).y<=n2&&ship.s.get(j).y-asteroid.get(i).y>=-n2))) {
                            x1=0;x2=0;
                            y1=17;y2=-17;
                        }
                        else if (((ship.s.get(j).y>asteroid.get(i).y||ship.s.get(j).y<asteroid.get(i).y)&&(ship.s.get(j).x-asteroid.get(i).x<=n2&&ship.s.get(j).x-asteroid.get(i).x>=-n2))) {
                            y1=0;y2=0;
                            x1=17;x2=-17;
                        }
                        else if ((ship.s.get(j).x<asteroid.get(i).x&&ship.s.get(j).y<asteroid.get(i).y)||(ship.s.get(j).x>asteroid.get(i).x&&ship.s.get(j).y>asteroid.get(i).y)) {
                            x1=n;
                            y1=n;
                            x2=-n;
                            y2=-n;
                        }
                        else if ((ship.s.get(j).x<asteroid.get(i).x&&ship.s.get(j).y>asteroid.get(i).y)||(ship.s.get(j).x>asteroid.get(i).x&&ship.s.get(j).y<asteroid.get(i).y)) {
                            x1=n;
                            y1=-n;
                            x2=-n;
                            y2=n;
                        }
//                        
//                        if (ship.s.get(j).x>asteroid.get(i).x) {
//                            System.out.println("bala por la Izquierda");
//                            
//                        }
//                        if (ship.s.get(j).x<asteroid.get(i).x) {
//                            
//                            System.out.println("bala por la derecha");
//                        }
//                        if (ship.s.get(j).y<asteroid.get(i).y) {
//                            
//                            System.out.println("bala por arriba");
//                        }
//                        if (ship.s.get(j).y>asteroid.get(i).y) {
//                            
//                            System.out.println("bala por debajo");
//                        }
//                        
                        
                        puntos+=20*asteroid.get(i).n;
                        ship.s.get(j).vida=0;
                        asteroid.get(i).vida--;
                        if (asteroid.get(i).vida>0){ // si el asteroide aun tiene vida
                            Asteroid a= asteroid.get(i); //se entrega a otro asteroide
                            //el cual se "divide" entregando sus valores a dos asteroides mas chicos
                        asteroid.add(asteroid.get(i).split(this, a.vida,(int)a.x+(x1),(int)a.y+(y1),1,a.n+0.6));
                        asteroid.add(asteroid.get(i).split(this, a.vida,(int)a.x+(x2),(int)a.y+(y2),-1,a.n+0.6));
                        int rando= (int)(Math.random()*5);
                            if (rando<=level) { //en caso que el random sea menor al nivel
                                item.add(aparecer(a)); // al dividir aparecera un item
                            }
                        }
                        if (asteroid.get(i).vida>0) { //si aun tiene vida
                            asteroid.remove(i); // se quita el asteroide grande
                        }
                    }
                }
            /// casi lo mismo que arriba, pero si choca con la nave y esta es inmortal
                if (asteroid.get(i).naveColision(ship)&&inmortal) {
                    puntos+=20*asteroid.get(i).n;
                        asteroid.get(i).vida--;
                        if (asteroid.get(i).vida>0){
                            Asteroid a= asteroid.get(i);
                        asteroid.add(asteroid.get(i).split(this, a.vida,(int)a.x+17,(int)a.y,1,a.n+0.6));
                        asteroid.add(asteroid.get(i).split(this, a.vida,(int)a.x-17,(int)a.y,-1,a.n+0.6));
                        int rando= (int)(Math.random()*50);
                            if (rando==15) {
                                item.add(aparecer(a));
                            }
                        }
                        if (asteroid.get(i).vida>0) {
                            asteroid.remove(i);
                        }
                }
                //si el asteroide choca con un balazo de ovni
            for (int j = 0; j < ovni.size(); j++){
                for (int k = 0; k < ovni.get(j).shots.size(); k++) {
                    if(asteroid.get(i).balaColision(ovni.get(j).shots.get(k))){
                        ovni.get(j).shots.get(k).vida=0;
                        asteroid.get(i).vida--;
                        if (asteroid.get(i).vida>0){
                            Asteroid a= asteroid.get(i);
                            asteroid.add(asteroid.get(i).split(this, a.vida,(int)a.x+17,(int)a.y,1,a.n+0.6));
                            asteroid.add(asteroid.get(i).split(this, a.vida,(int)a.x-17,(int)a.y,-1,a.n+0.6));
                            
                            asteroid.remove(i);
                        }
                    }
                }
            }
            }catch(NullPointerException e){}
            
            
        }//remueve todos los asteroides con vida 0 o menos
        for (int i = 0; i < asteroid.size(); i++) {
            if(asteroid.get(i).vida<=0)
                asteroid.remove(i);
        }
        
    }
    Items aparecer(Asteroid a){ //el metodo que agrega items al arraylist
        return new Items((int)a.x,(int)a.y,this);
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        JFrame frame = new JFrame("Asteroids by Roberto Morales");
        AsteroidsGame game = new AsteroidsGame(frame);
        frame.setSize(600, 600);
        frame.setBackground(Color.BLACK);
        
        frame.add(game);
        Image icon= Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/src/asteroidsgame/Fx/icon.gif");// el System.getProperty("user.dir") retorna nu string con la ruta del proyecto
        frame.setIconImage(icon);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game.run();
        
    }
    
}
