import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

import static java.awt.Color.black;
import static javafx.scene.transform.Transform.translate;

public class Run extends JFrame {
    NeuralNetwork brain;

    double[][] inputs = {{0.0, 0.0}, {1.0, 0.0}, {0.0, 1.0}, {1.0, 1.0}};
    double[][] targets = {{0.0}, {1.0}, {1.0}, {0.0}};

    int count = 0;
    double op[] = new double[2];

    public Run() {
        super("Drawing Neural Networks");

        getContentPane().setBackground(Color.WHITE);
        setSize(480, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void drawRun(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        brain = new NeuralNetwork(2,4,1);

        brain.learning_rate = 0.1;

        for (int i = 0; i < 50000; i++) {
            Random rd = new Random();
            Random rt = new Random();
            int dataI = Math.abs(rd.nextInt()) % 4;
            int dataT = Math.abs(rt.nextInt()) % 4;

            brain.train(inputs[dataI], targets[dataT]);
        }

        int resolution = 20;
        //double cols = Math.floor(400 / resolution);
        //double rows = Math.floor(400 / resolution);

        //DecimalFormat df = new DecimalFormat("0.0000000");

        double[] l1 = {0};
        double[] l2 = {0};
        double[] l3 = {0};
        double[] l4 = {0};

        //for (int i = 0; i < cols; i++) {
          //  for (int j = 0; j < rows; j++) {
                //double x = i * resolution;
                //double y = j * resolution;
                //double input_1 = i / (cols - 1);
                //double input_2 = j / (rows - 1);
                //double arr[] = {input_1, input_2};
                g2d.setStroke(new BasicStroke(0f));
                //double[] output = brain.feedforward(arr);
                //double col = output[0] * 255;

                //double out1 = output[0];

                /*Circle c = new Circle();

                //Show the classification—no fill for -1, black for +1.
                if (output[0] > 0) {
                    // noFill();
                    repaint();
                } else {
                    g2d.fillOval(input_1, input_2, col, col);
                    repaint();
                }

                c.setCenterX(input_1);
                c.setCenterY(input_2);
                c.setRadius(col);*/

                //System.out.println("output: " + out1 + " Input: " + input_1 + " " + input_2);

                //Circle c = new Circle(out1, out2, col);
                //g2d.fillOval((int) c.getCenterX(), (int) c.getCenterX(), (int) c.getRadius(), (int) c.getRadius());

                double i1[] = {1,0};
                double i2[] = {0,1};
                double i3[] = {1,1};
                double i4[] = {0,0};



                //l1 = output[0];
                l1 = brain.feedforward(i1);
                l2 = brain.feedforward(i2);
                l3 = brain.feedforward(i3);
                l4 = brain.feedforward(i4);
            //}
        //}

        System.out.println(l1[0]);
        System.out.println(l2[0]);
        System.out.println(l3[0]);
        System.out.println(l4[0]);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawRun(g);

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
