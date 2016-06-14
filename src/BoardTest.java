
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
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
import java.util.Scanner;


import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class BoardTest extends JPanel implements ActionListener {

    private final int random = 29;
    private final int speed = 140;
    private final int width = 500;
    private final int height = 500;
    private final int dotSize = 10;
    private final int lotsDots = 900;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean alive = true;
    //ADDED ROUND 3
    private final int x1 = width/10;
    private final int x2 = width - x1;
    private final int y1 = height/10;
    private final int y2 = height - y1;
    private final int size = width/4;
    private final int x[] = new int[lotsDots];
    private final int y[] = new int[lotsDots];
    private int dots;
    private int xfood;
    private int yfood;
    private int score = 0;
    private Timer timer;
    private Image body;
    private Image food;
    private Image head;
    private JButton resetAppButton = null;

    //CONSTRUCTOR
    public Board() {
        addKeyListener(new Adapter());
        setBackground(Color.pink);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        getImages();
        init();
    }

    private void getImages() {
        ImageIcon ibody = new ImageIcon("body.png");
        body = ibody.getImage();
        ImageIcon ihead = new ImageIcon("body.png");
        head = ihead.getImage();
        ImageIcon ifood = new ImageIcon("tiny food.jpg");
        food = ifood.getImage();
    }

    private void init() {
        dots = 3; //basic 
    //    if (x[z] < x1 || x[z] > x2 || ((x[z] > x1+size) && (x[z] < x2-size))
        for (int z = 0; z < dots; z++) {
        	if (x[z] < x1 )
            x[z] = 50 - z * 10;
            y[z] = 50;
            
        }
        findfood();
        timer = new Timer(speed, this);
        timer.start();
        resetAppButton = new JButton("Reset");
        resetAppButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                init();
            }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    private void doDrawing(Graphics g) {     
        if (alive) {
        	//I ADDED SOMETHING HERE
        	g.setColor(Color.yellow);
        	g.drawRect(x1, y1, size, 10);
        	g.drawRect(x1, y2, size, 10);
        	g.drawRect(x2-size, y2, size, 10);
        	g.drawRect(x2-size, y1, size, 10);
        	g.drawRect(x1, height/2-5, size, 10);
        	g.drawRect(x2-size, height/2-5, size, 10);
        	g.fillRect(x1, y1, size, 10);
        	g.fillRect(x1, y2, size, 10);
        	g.fillRect(x2-size, y2, size, 10);
        	g.fillRect(x2-size, y1, size, 10);
        	g.fillRect(x1, height/2-5, size, 10);
        	g.fillRect(x2-size, height/2-5, size, 10);

//       	if ((xfood<x1) || (xfood>x2) || ((x1+size<xfood)&&(xfood<x2-size))) && 
//        	((yfood<y1) || (yfood>y2) || ((yfood>y1+10) && (yfood < height/2-5))) {
//        		break;
//        	}
        	
        	
        	
//        	if ((xfood < x1) || (xfood > x2) || ((x1+size < xfood) && (xfood < x2-size))) && 
//        		((yfood < y1) || (yfood > y2) || ((yfood > y1+10) && (yfood < height/2-5)) 
 //       				|| ((yfood < y2) && (yfood > height/2+5))) {
        			
//        		}

            g.drawImage(food, xfood, yfood, this);
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } 
                else { 	
                    String message = String.valueOf(score);
                    Font small = new Font("Times", Font.BOLD, 14);
                    FontMetrics metr = getFontMetrics(small);
                    g.setColor(Color.white);
                    g.setFont(small);
                    g.drawString("Score = " + message, width - 80, height - 5);
                    g.drawImage(body, x[z], y[z], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else 
            gameOver(g);
        }        
    
   /* private void gameStarts(Graphics g){
    	String message = "Welcome to Snake.";
    	String message1 = "Please select which level you would like to play.";
    	
    	
    	
    	Font small = new Font("Times", Font.BOLD, 14);
    	FontMetrics metr = getFontMetrics(small);
    	
    	g.setColor(Color.white);
    	
    	//Scanner in = console.in(nextLine);
    	
    	
    }*/
    private void gameOver(Graphics g) {
    	//ADDED  BUT EDITED HERE
        String message = "GAME OVER!";
        Font small = new Font("Times", Font.BOLD, 35);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (width - metr.stringWidth(message)) / 2, height / 2);   
    }

    private void foodcheck() {
        if ((x[0] == xfood) && (y[0] == yfood)) {
            dots++;
            score += 10;
            findfood();
        }
    }

    private void move() {
    	/**
    	 *  for (int z = dots; z > 0; z--) {
            	x[z] = x[(z + 1)];
            	y[z] = y[(z + 1)];
        }
    	 */
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if (left) {
            x[0] -= dotSize;
        }
        if (right) {
            x[0] += dotSize;
        }
        if (up) {
            y[0] -= dotSize;
        }
        if (down) {
            y[0] += dotSize;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                alive = false;
            }
        }
        if (y[0] >= height) {
            alive = false;
        }
        if (y[0] < 0) {
            alive = false;
        }
        if (x[0] >= width) {
            alive = false;
        }
        if (x[0] < 0) {
            alive = false;
        }
        if(!alive) {
            timer.stop();
        }
    }

    private void findfood() {
        int r = (int) (Math.random() * random);
        xfood = ((r * dotSize));
        r = (int) (Math.random() * random);
        yfood = ((r * dotSize));
    }

    public void actionPerformed(ActionEvent e) {
        if (alive) {
            foodcheck();
            checkCollision();
            move();
        }
        repaint();
    }
    private class Adapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }
            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            //ADDED
   //  	   if (key == KeyEvent.VK_SPACE) {
    // 	      System.exit(0);
    // 	   }
    //	   if (key == KeyEvent.VK_SHIFT) {
    // 	      init();
    // 	   }
        }        
    }
}
























