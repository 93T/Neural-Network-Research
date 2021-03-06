import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Run extends JFrame {
    NeuralNetwork brain;

    int[][] inputs = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
    int[][] targets = {{0}, {1}, {1}, {0}};

    public Run() {
        super("Drawing Neural Networks");

        getContentPane().setBackground(Color.WHITE);
        setSize(480, 200);
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

        int resolution = 20;
        int cols = (int) Math.floor(400 / resolution);
        int rows = (int) Math.floor(400 / resolution);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                double x = i * resolution;
                double y = j * resolution;
                int input_1 = i / (cols - 1);
                int input_2 = j / (rows - 1);
                int arr[] = {input_1, input_2};
                int[] output = brain.feedforward(arr);
                int col = output[0] * 255;


                Circle c = new Circle(x, y, col);
                g.fillOval((int) c.getCenterX(), (int) c.getCenterX(), (int) c.getRadius(), (int) c.getRadius());
            }
        }
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
