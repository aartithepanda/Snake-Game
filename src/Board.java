import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 500;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
//    private final int RAND_POS = 29;
    private final int DELAY = 140;
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    private int dots;
    private int apple_x;
    private int apple_y;
    private int score = 0;
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private Image maze;
    private Image aarti1;
    private Image dimple1;

private ArrayList<Image> pics = new ArrayList<Image>();

private int count = pics.size()-1;
    public Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.pink);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }
    
    private void loadImages() {

    ImageIcon aarti= new ImageIcon(this.getClass().getResource("Aarti.jpg"));

    aarti1=aarti.getImage();

    pics.add(aarti1);

    

    ImageIcon dimple= new ImageIcon(this.getClass().getResource("dimple.jpg"));

    dimple1=aarti.getImage();

    pics.add(dimple1);

   
        ImageIcon iid = new ImageIcon("ball.png");
        ball = iid.getImage();
        ImageIcon iia = new ImageIcon("tiny food.jpg");
        apple = iia.getImage();
        ImageIcon iih = new ImageIcon("ball.png");
        head = iih.getImage();
        ImageIcon mazes = new ImageIcon ("maze.png");
        maze= mazes.getImage();
    }

    private void initGame() {
        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(maze, B_WIDTH/2, B_HEIGHT/2, this);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
       if (inGame) {
        //if (count>=0){

        //  g.drawImage(pics.get(count), apple_x, apple_y, this);

        	 

        // count--;

        //}else {
    	   
    	   Random r = new Random();
    	   int num = r.nextInt(pics.size());
        g.drawImage(pics.get(num), apple_x, apple_y, this);
        //}

          

//            
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    String msg = String.valueOf(score);
                    Font small = new Font("Stencil", Font.BOLD, 14);
                    FontMetrics metr = getFontMetrics(small);
                    g.setColor(Color.white);
                    g.setFont(small);
                    g.drawString("Score = " + msg, B_WIDTH/2 , B_HEIGHT/2 );
                    g.drawImage(maze, B_WIDTH/2, B_HEIGHT/2, this);
                    g.drawImage(ball, x[z], y[z], this);
                }
            }
          //  g.drawImage(maze, B_WIDTH/2, B_HEIGHT/2, this);
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        String msg = "GAME OVER";
        Font small = new Font("Stencil", Font.BOLD, 50);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score += 10;
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    
    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }
        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] >= B_WIDTH) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if(!inGame) {
            timer.stop();
        }
    }

    //CHANGED THINGS HERE
    private void locateApple() {
        int r = (int) (Math.random()*45);
        apple_x = ((r * DOT_SIZE));
        r = (int) (Math.random()*45);
        apple_y = ((r * DOT_SIZE));
    }
    
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    private class TAdapter extends KeyAdapter {



        @Override

        public void keyPressed(KeyEvent e) {



            int key = e.getKeyCode();



            if ((key == KeyEvent.VK_LEFT)) {
            	
                leftDirection = true;
                rightDirection = false;
                upDirection = false;

                downDirection = false;

            }



            if ((key == KeyEvent.VK_RIGHT)) {

                rightDirection = true;
                leftDirection = false;
                upDirection = false;

                downDirection = false;

            }



            if ((key == KeyEvent.VK_UP)) {

                upDirection = true;
                downDirection = false;
                rightDirection = false;

                leftDirection = false;

            }



            if ((key == KeyEvent.VK_DOWN)) {

                downDirection = true;
                upDirection = false;
                rightDirection = false;

                leftDirection = false;

            }

        }

    }

    

  

}







