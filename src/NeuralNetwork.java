public class NeuralNetwork {
    int input_nodes;
    int hidden_nodes;
    int output_nodes;

    Matrix weights_ih;
    Matrix weights_ho;

    Matrix bias_h;
    Matrix bias_o;

    double learning_rate;

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
    }

    public int[] feedforward(int[] input_array) {
        //generating the hidden outputs
        Matrix inputs = new Matrix(input_nodes, 1).fromArray(input_array);
        Matrix hidden = new Matrix(input_nodes, hidden_nodes).multiply(this.weights_ih, inputs);
        hidden.add(this.bias_h);
        // Activation function
        hidden.map(hidden, "sigmoid");

        //generating the output's outputs
        Matrix output = new Matrix(hidden_nodes, output_nodes).multiply(this.weights_ho, hidden);
        output.add(this.bias_o);
        // Activation function
        output.map(output, "sigmoid");

        // sending back
        return output.toArray();
    }

    public void print(Matrix out) {
        System.out.println(out);
    }

    public void train(int[] inputs, int[] targets) {
        Matrix input = new Matrix(input_nodes, 1).fromArray(inputs);
        Matrix hidden = new Matrix(input_nodes, hidden_nodes).multiply(this.weights_ih, input);
        hidden.add(this.bias_h);
        //activation function
        hidden.map(hidden, "sigmoid");

        // generating the output's output
        Matrix output = new Matrix(hidden_nodes, output_nodes).multiply(this.weights_ho, hidden);
        output.add(this.bias_o);
        output.map(output, "sigmoid");

        // convert array to matrix object
        Matrix target = new Matrix(hidden_nodes, output_nodes).fromArray(targets);


        // calculate the error
        Matrix output_errors = Matrix.subtract(target, output);

        // calculate gradient
        Matrix gradients = Matrix.map(output, "dSigmoid");
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
        Matrix hidden_gradient = new Matrix(hidden_nodes, input_nodes).map(hidden, "sigmoid");
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