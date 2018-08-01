import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Run extends JFrame {
    NeuralNetwork brain;

    double[][] inputs = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
    double[][] targets = {{0}, {1}, {1}, {0}};

    public Run() {
        super("Drawing Neural Networks");

        getContentPane().setBackground(Color.WHITE);
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void drawRun(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        brain = new NeuralNetwork(2,4,1);

        brain.learning_rate = 0.1;

        for (int i = 0; i < 5000; i++) {
            Random r = new Random();
            //int data = ThreadLocalRandom.current().nextInt(0, 4);
            //int data = Math.abs(r.nextInt()) * 4;
            //int range = (4 - 0);
            int data = r.nextInt(4);

            brain.train(inputs[data], targets[data]);
        }

        int resolution = 10;
        int cols = 550 / resolution;
        int rows = 550 / resolution;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                //System.out.println(i + " i " + j + " j");

                double x1 = (double) i / (double) cols;
                double x2 = (double) j / (double) rows;
                //System.out.println(x1 + " x1 " + x2 + " x2 " + cols + " C " + rows + " R ");
                double inputs[] = {x1, x2};
                double y = brain.feedforward(inputs);

                /*
                Random rand = new Random();

                float re = rand.nextFloat();
                float gr = rand.nextFloat();
                float bl = rand.nextFloat();
                */

                double input1[] = {0, 0};
                double input2[] = {0, 1};
                double input3[] = {1, 0};
                double input4[] = {1, 1};

                //System.out.print(brain.feedforward(input1)[0] + " [0,0]");
                //System.out.print(brain.feedforward(input2)[0] + " [0,1]");
                //System.out.print(brain.feedforward(input3)[0] + " [1,0]");
                //System.out.print(brain.feedforward(input4)[0] + " [1,1]");
                //System.out.print(y + " [" + x1 + ", " + x2 + "]");

                Rectangle rect = new Rectangle(i*resolution, j*resolution, resolution, resolution);
                Color col = new Color ((int) (y*255), (int) (y*255), (int) (y*255));
                System.out.println(col.toString());
                g2d.setPaint(col);
                g2d.fill(rect);

                /*
                Circle c = new Circle(i*resolution, j*resolution, resolution);
                Color col = new Color ((int) y*255);
                g2d.setPaint(col);
                g.fillOval((int) c.getCenterX(), (int) c.getCenterY(), (int) c.getRadius(), (int) c.getRadius());
                */
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        //drawRun(g);
        while(true) {
            drawRun(g);
        }

        /*
        super.paint(g);
        Random r = new Random();
        int num = 1 + r.nextInt()*200;
        drawRun(g, num); */

        /*
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Neural Network");
        guiFrame.setSize(500,500);
        guiFrame.setLocationRelativeTo(null);
        */




        //guiFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Run().setVisible(true);
            }
        });
    }
}
