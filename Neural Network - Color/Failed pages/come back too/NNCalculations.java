import java.util.concurrent.Callable;
import java.util.function.Function;

class ActivationFunction {
    public double func;
    public double dfunc;

    ActivationFunction(double func, double dfunc) {
        this.func = func;
        this.dfunc = dfunc;
    }
}

public class NNCalculations extends Matrix {
    double sigmoid = new ActivationFunction((x) -> 1 / (1 + Math.exp(-x)), (y) -> (y * (1 - y)));
    double tanh = new ActivationFunction((x) -> {Math.tanh(x), (y) -> 1 - (y * y)));

    NNCalculations(int row, int col) {
        super(row, col);
    }

    // a is for connection input value
    // w is for connection weight
    // calculates the weighted sum
    // for input related NN stuff
    public Matrix weightedSummsFormula(Matrix a, Matrix w) {
        Matrix result = new Matrix(this.rows, this.cols);

        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                int sum = 0;

                for (int k = 0; k < a.cols; k++) {
                    sum += (a.data[i][k] * w.data[k][j]);
                }

                result.data[i][j] = sum;
            }
        }

        return result;
    }

    /*public double sigmoidFormula (double x) {
        return 1/(1+Math.exp(-x));
    }
    public double dSigmoidFormula (double y) {
        return y*(1-y);
    }*/

    public double meanSquaredErrorFormula (double n, double[] expected, double[] actual) {
        double sumMeanSquaredError = 0.0;


        for (int i = 0; i < expected.length && i < actual.length; i++) {
            sumMeanSquaredError = Math.pow((expected[i] - actual[i]), 2);
        }

        return ((1 / n) * sumMeanSquaredError);
    }
}
