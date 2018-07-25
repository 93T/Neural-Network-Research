import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

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

        brain = new NeuralNetwork(2,2,1);

        brain.learning_rate = 0.1;

        for (int i = 0; i < 5000; i++) {
            Random r = new Random();
            int data = Math.abs(r.nextInt()) % 4;

            brain.train(inputs[data], targets[data]);
        }

        int resolution = 10;
        int cols = (int) Math.floor(550 / resolution);
        int rows = (int) Math.floor(550 / resolution);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                /*
                double x = i * resolution;
                double y = j * resolution;
                double input_1 = i / (cols - 1);
                double input_2 = j / (rows - 1);
                double arr[] = {input_1, input_2};
                double[] output = brain.feedforward(arr);
                double col = output[0] * 255;
                */

                Random rand = new Random();

                float re = rand.nextFloat();
                float gr = rand.nextFloat();
                float bl = rand.nextFloat();

                Rectangle rect = new Rectangle(i*resolution, j*resolution, resolution, resolution);
                Color randCol = new Color (re, gr, bl);
                g2d.setPaint(randCol);
                g2d.fill(rect);

               // Circle c = new Circle((i*resolution), (j*resolution), resolution);
                //g.fillOval((int) c.getCenterX(), (int) c.getCenterY(), (int) c.getRadius(), (int) c.getRadius());
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
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
