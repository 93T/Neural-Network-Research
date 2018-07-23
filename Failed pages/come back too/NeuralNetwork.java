public class NeuralNetwork {
    int input_nodes;
    int hidden_nodes;
    int output_nodes;

    Matrix weights_ih;
    Matrix weights_ho;

    Matrix bias_h;
    Matrix bias_o;

    double learning_rate;
    double activation_function;

    String func;
    String dfunc;

    /*
    NeuralNetwork(int numI, int numH, int numO) {
        this.input_nodes = numI;
        this.hidden_nodes = numH;
        this.output_nodes = numO;

        this.weights_ih = new Matrix(this.hidden_nodes, this.input_nodes);
        weights_ih.randomize();

        weights_ho = new Matrix(output_nodes, hidden_nodes);
        weights_ho.randomize();

        bias_h = new Matrix(hidden_nodes, 1);
        bias_h.randomize();

        bias_o = new Matrix(output_nodes, 1);
        bias_o.randomize();

        learning_rate = 0.1;
    } */

    NeuralNetwork(Object in_nodes, Object hid_nodes, Object out_nodes) {
        if (in_nodes instanceof NeuralNetwork) {
            NeuralNetwork a = (NeuralNetwork) in_nodes;
            this.input_nodes = a.input_nodes;
            this.hidden_nodes = a.hidden_nodes;
            this.output_nodes = a.output_nodes;

            this.weights_ih = a.weights_ih.copy();
            this.weights_ho = a.weights_ho.copy();

            this.bias_h = a.bias_h.copy();
            this.bias_o = a.bias_o.copy();
        } else {
            this.input_nodes = (int) in_nodes;
            this.hidden_nodes = (int) hid_nodes;
            this.output_nodes = (int) out_nodes;

            this.weights_ih = new Matrix(this.hidden_nodes, this.input_nodes);
            this.weights_ho = new Matrix(this.output_nodes, this.hidden_nodes);
            this.weights_ih.randomize();
            this.weights_ho.randomize();

            this.bias_h = new Matrix(this.hidden_nodes, 1);
            this.bias_o = new Matrix(this.output_nodes, 1);
            this.bias_h.randomize();
            this.bias_o.randomize();
        }

        // TODO: copy these as well
        this.setLearningRate(0.1);
        this.setActivationFunction();


    }

    public void ActivationFunction(String s) {
        this.func = s;
        this.dfunc = s;
        this.activation_function = s;
    }

    /*
    public double[] feedforward(double[] input_array) {
        //generating the hidden outputs
        Matrix inputs = new Matrix(input_nodes, 1).fromArray(input_array);
        //System.out.println("Input: " + inputs.data[0][0] + " " + inputs.data[1][0]);
        Matrix hidden = new Matrix(input_nodes, hidden_nodes).multiply(this.weights_ih, inputs);
        //System.out.println("Hidden: " + hidden.data[0][0]);
        hidden.add(this.bias_h);
        //System.out.println("Hidden 2: " + hidden.data[0][0] + " bias_h: " + this.bias_h.data[0][0]);
        // Activation function
        hidden.map(hidden, "sigmoid");
        //System.out.println("Hidden 3: " + hidden.data[0][0]);

        //generating the output's outputs
        Matrix output = new Matrix(hidden_nodes, output_nodes).multiply(this.weights_ho, hidden);
        //System.out.println("Output: " + output.data[0][0] + " weight: " + this.weights_ho.data[0][0] + " " + this.weights_ho.data[0][1] + " hidden: " + hidden.data[0][0]);
        output.add(this.bias_o);
        // Activation function
        output.map(output, "sigmoid");
        //System.out.println("Given: " + output.data[0][0]);

        //double o[] = output.toArray();

        //System.out.println("output: " + o[0] + " hidden: " + hidden.data[0][0] +  " Input: " + input_array[0] + " " + input_array[1]);

        // sending back
        return output.toArray();
    } */

    public double[] predict(double[] input_array) {

        // Generating the Hidden Outputs
        Matrix inputs = new Matrix(input_nodes, 1).fromArray(input_array);
        //System.out.println("Input: " + inputs.data[0][0] + " " + inputs.data[1][0]);
        Matrix hidden = new Matrix(input_nodes, hidden_nodes).multiply(this.weights_ih, inputs);
        //System.out.println("Hidden: " + hidden.data[0][0]);
        hidden.add(this.bias_h);
        //System.out.println("Hidden 2: " + hidden.data[0][0] + " bias_h: " + this.bias_h.data[0][0]);
        hidden.map(activation_function);

        // Generating the output's output!
        Matrix output = new Matrix(hidden_nodes, output_nodes).multiply(this.weights_ho, hidden);
        //System.out.println("Output: " + output.data[0][0] + " weight: " + this.weights_ho.data[0][0] + " " + this.weights_ho.data[0][1] + " hidden: " + hidden.data[0][0]);
        output.add(this.bias_o);
        output.map(activation_function.func);

        // Sending back to the caller!
        return output.toArray();
    }

    public void setLearningRate(double learning_rate) {
        this.learning_rate = learning_rate;
    }

    public void setActivationFunction(String sig) {
        if (sig == "sigmoid") {
            this.activation_function = sig;
        } else {
            this.activation_function = ...;
        }

    }

    public void train(double[] inputs, double[] targets) {
        Matrix m1 = new Matrix(input_nodes, 1);
        Matrix input = m1.fromArray(inputs);
        Matrix m2 = new Matrix(input_nodes, hidden_nodes);
        Matrix hidden = m2.multiply(this.weights_ih, input);
        //System.out.println(this.bias_h.cols + " " + this.bias_h.rows);
        hidden.add(this.bias_h);
        //activation function

        hidden.map(hidden, ...);

        // generating the output's output
        Matrix output = new Matrix(hidden_nodes, output_nodes).multiply(this.weights_ho, hidden);
        output.add(this.bias_o);
        output.map(output, ...);

        // convert array to matrix object
        Matrix target = new Matrix(hidden_nodes, output_nodes).fromArray(targets);

        // calculate the error
        Matrix output_errors = Matrix.subtract(target, output);

        // calculate gradient
        Matrix gradients = Matrix.map(output, ...");
        gradients.multiply(output_errors);
        gradients.multiply(this.learning_rate);

        // Calculae deltas
        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix weight_ho_deltas = Matrix.multiply(gradients, hidden_T);

        // Adjust the weights by deltas
        this.weights_ho.add(weight_ho_deltas);
        // Adjust the bias by its deltas
        this.bias_o.add(gradients);

        // calculate the hidden layer errors
        Matrix who_t = new Matrix(hidden_nodes, output_nodes).transpose(this.weights_ho);
        Matrix hidden_errors = new Matrix(hidden_nodes, output_nodes).multiply(who_t, output_errors);

        // Calculate hidden gradient
        Matrix hidden_gradient = new Matrix(hidden_nodes, input_nodes).map(hidden, ...);
        hidden_gradient.multiply(hidden_errors);
        hidden_gradient.multiply(this.learning_rate);

        // Calculate input->hidden deltas
        Matrix inputs_T = new Matrix(input_nodes, hidden_nodes).transpose(input);
        Matrix weight_ih_deltas = new Matrix(input_nodes, hidden_nodes).multiply(hidden_gradient, inputs_T);

        this.weights_ih.add(weight_ih_deltas);
        // Adjust the bias by its deltas
        this.bias_h.add(hidden_gradient);
    }
}