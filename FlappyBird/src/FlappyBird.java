import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int boardWidth = 360;
    int boardHeight = 640;

    //images
    Image bgImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

    //bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;


    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img){
            this.img = img;
        }

    }

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }
    }


    // Game Logic
    Bird bird;
    int velocityX = -4; // this is for pipe both top and bottom
    int velocityY = 0; // when the game start the bird is falling downward
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;

    boolean gameOver = false;

    double score = 0;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.BLUE);
        setFocusable(true);
        addKeyListener(this); //this will implements our keyListener inside our flappy bird game

        //load images
        bgImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImage);
        pipes = new ArrayList<Pipe>();

        //place pipes timer;
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipeTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

    }

    public void placePipes(){
        //this will place the position of top pipe 1/4 to 3/4 of pipe height
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
        Pipe toppipe = new Pipe(topPipeImage);
        toppipe.y = randomPipeY;
        pipes.add(toppipe);

        Pipe bottompipe = new Pipe(bottomPipeImage);
        bottompipe.y = toppipe.y + pipeHeight + openingSpace;
        pipes.add(bottompipe);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        //System.out.println("draw"); //just to check if the gameLoop is working
        //draw background
        g.drawImage(bgImage, 0, 0, boardWidth, boardHeight, null);

        //bird - > Class is created so that its easy to access the details paramaters below
        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if(gameOver){
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        }
        else{
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move(){
        //bird movement in y axis up and down
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipe movement
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5; // because there are 2 pipes up and down
            }

            if(collision(bird, pipe)){
                gameOver = true;
            }
        }

        //gameover
        if(bird.y > boardHeight){
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b){
        return  a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;

            //reset the game
            if(gameOver){
                //restart the game by resetting the conditions
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }
    //we are not using this
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
