import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * There's nothing for you to do here but run.
 */

public class GUI extends JFrame {
  private static int[][] inputs = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
  private static int[][] targets = {{0}, {1}, {1}, {0}};
  static NeuralNetwork brain;
  // Display panels for images.
  private static ArrayList<Circle> panels = new ArrayList<>();

  static Circle c;

  static {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }

  // bitSelector is the interface element for selecting desired number of bits per channel.
  JSlider bitSelector = new JSlider(SwingConstants.HORIZONTAL, 1, 8, 8) {{
    setBackground(Color.WHITE);
    setPaintTicks(true);
    setPaintLabels(true);
    setSnapToTicks(true);
    setMajorTickSpacing(1);
    setLabelTable(createStandardLabels(1));
    setPreferredSize(new Dimension(200, 60));
    setMaximumSize(new Dimension(200, 60));
    //setToolTipText("Drop lower-order bits from RGB values");
  }};

  // colorBox is the interface element for displaying the most recently selected color
  // from one of the images.
  JLabel colorBox = new JLabel("00 00 00") {{
    setOpaque(true);
    setHorizontalAlignment(SwingConstants.CENTER);
    setBackground(Color.BLACK);
    setToolTipText("Click on an image pixel to change the current color");
  }
  public void setBackground(Color color) {
    super.setBackground(color);
    String text = String.format("%06X", color.getRGB());
    text = text.substring(2, 4) + " " + text.substring(4, 6) + " " + text.substring(6, 8);
    setText(text);
  }};

  /**
   * Constructs a window to display two images and compare them for
   * similarity after quantization.
   */

  public GUI(Circle ci) {
    setTitle("Neural network");
    setBackground(Color.WHITE);

    JPanel main = new JPanel() {{
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
      setBackground(Color.WHITE);
    }};

    // sideBySide is the interface element for displaying the two images side by side.
    Circle A = new Circle(ci);
    JPanel sideBySide = new JPanel() {{
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      setBackground(Color.WHITE);
      setOpaque(true);
      add(A);
    }};

    // controls is the interface element holding the control panel.
    /*
    JPanel controls = new JPanel() {{
      setOpaque(true);
      setBackground(Color.WHITE);
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }}; */
    //JLabel bitLabel = new JLabel(bitSelector.getValue() + " bits Per Channel");

    /*
    JLabel simLabel = new JLabel(String.format("Similarity: %1.2f", similarity()));
    simLabel.setToolTipText("Cosine similarity of the two images (max 1.0)");
    JButton newWindow = new JButton("New");
    JButton close = new JButton("Close");
    */

    /*
    controls.add(Box.createRigidArea(new Dimension(30, 0)));
    controls.add(new JPanel() {{
      setPreferredSize(new Dimension(100, 60));
      setMaximumSize(new Dimension(100, 60));
      setMinimumSize(new Dimension(100, 60));
      setLayout(new GridLayout(0, 1));
      //add(colorBox);
    }});
    controls.add(Box.createRigidArea(new Dimension(30, 0)));
    controls.add(new JPanel() {{
      setBackground(Color.WHITE);
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      //add(bitLabel);
      //bitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      //add(bitSelector);
      //bitSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
    }});
    controls.add(Box.createRigidArea(new Dimension(30, 0)));
    controls.add(simLabel);
    controls.add(Box.createRigidArea(new Dimension(30, 0)));
    controls.add(newWindow);
    controls.add(Box.createRigidArea(new Dimension(30, 0)));
    controls.add(close);
    controls.add(Box.createRigidArea(new Dimension(30, 0)));

    bitSelector.addChangeListener(e -> {
      sideBySide.repaint();
      bitLabel.setText(bitSelector.getValue() + " bits Per Channel");
      simLabel.setText(String.format("Similarity: %1.2f", similarity()));
    }); */

    /*
    newWindow.addActionListener(e -> {
      GUI.main(null);
    });

    close.addActionListener(e -> dispose()); */

    main.add(sideBySide);
    main.add(Box.createRigidArea(new Dimension(0, 30)));;
    //main.add(controls);
    main.add(Box.createRigidArea(new Dimension(0, 30)));;
    setContentPane(main);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  /**
   * An ImagePanel represents one panel displaying one image. The
   * image is quantized depending on the bitSelector value. This
   * component listens for mouse events, and updates the colorBox
   * control accordingly.
   */

  static class Circle extends JPanel {
    // A timer to trigger the xray effect during a long press.
    //private Timer timer;

    public Circle(Circle cir) {
      // Effects on one image affect both, so save all constructed panels in one place.
      panels.add(this);
      setBackground(Color.WHITE);

      panels.add(cir);
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.fillOval(c.getCenterX(), (int) c.getCenterX(), (int) c.getRadius(), (int) c.getRadius());
    }
  }

  /**
   * Fires up the GUI with two randomly selected images.
   */

  public static void main(String[] args) {
    /*
    Random rand = new Random();
    int i = rand.nextInt(n), j = rand.nextInt(n - 1);
    if (j >= i) 
      j++;*/

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
        int x = i * resolution;
        int y = j * resolution;
        int input_1 = i / (cols - 1);
        int input_2 = j / (rows - 1);
        int arr[] = {input_1, input_2};
        int[] output = brain.feedforward(arr);
        int col = output[0] * 255;


        c = new Circle(x, y, col);
      }
    }
    SwingUtilities.invokeLater(() -> new GUI(c));
  }
}
