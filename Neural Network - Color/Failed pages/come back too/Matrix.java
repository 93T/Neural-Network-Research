import java.util.Random;

public class Matrix {
    int rows;
    int cols;
    double[][] data;

    Matrix(int row, int col) {
        rows = row;
        cols = col;

        data = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = 0;
            }
        }
    }

    public Matrix copy() {
        Matrix m = new Matrix(this.rows, this.cols);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                m.data[i][j] = this.data[i][j];
            }
        }
        return m;
    }

    static Matrix fromArray(double[] arr) {
        Matrix m = new Matrix(arr.length, 1);

        for (int i = 0; i < arr.length; i++) {
            m.map(m.data[i][0] = arr[i]);
        }
        return m;

        //return new Matrix(arr.length, 1).map((e, i) -> arr[i]);
    }

    static Matrix subtract(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.cols != b.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B.");
            //return;
        }

        Matrix result = new Matrix(a.rows, a.cols);

        for (int i = 0; i < result.rows; i++) {
            for(int j = 0; j < result.cols; j++) {
                result.map(result.data[i][j] = a.data[i][j] - b.data[i][j]);
            }
        }

        return result;

        // Return a new Matrix a-b
        //return new Matrix(a.rows, a.cols).map((int i, int j) -> a.data[i][j] - b.data[i][j]);
    }

    public double[] toArray() {
        double arr[] = new double[this.data.length];

        int place = 0;

        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                arr[place] = this.data[i][j];
            }
        }

        return arr;
    }

    public void randomize() {
        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                Random r = new Random();
                //this.data[i][j] = -1.0 + (1.0-(-1.0)) * r.nextDouble();
                this.map(this.data[i][j] = (2.0 - 1.0) * r.nextDouble());
            }
        }

        //return this.map((double e) -> (2.0 - 1.0) * r.nextDouble());
    }

    public void add (Matrix n) {
        /*
        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.data[i][j] += n.data[i][j];
            }
        } */

        if (n instanceof Matrix) {
            if (this.rows != n.rows || this.cols != n.cols) {
                System.out.println("Columns and Rows of A must match Columns and Rows of B.");
                return;
            }

            for (int i = 0; i < this.rows; i++) {
                for(int j = 0; j < this.cols; j++) {
                    this.data[i][j] += n.data[i][j];
                }
            }
        }

        /*
        if (n instanceof Matrix) {
            if (this.rows != n.rows || this.cols != n.cols) {
                System.out.println("Columns and Rows of A must match Columns and Rows of B.");
                return;
            }
            return this.map((e, i, j) -> e + n.data[i][j]);
        } else {
            return this.map(e -> e + n);
        } */
    }

    public void add (double n) {
        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.data[i][j] += n;
            }
        }
    }

    static Matrix transpose(Matrix matrix) {
        Matrix result = new Matrix(matrix.cols, matrix.rows);

        for (int i = 0; i < result.rows; i++) {
            for(int j = 0; j < result.cols; j++) {
                result.data[i][j] = matrix.data[j][i];
            }
        }

        return result;

        //return new Matrix(matrix.cols, matrix.rows).map((e, i, j) -> matrix.data[j][i]);
    }

    static Matrix multiply(Matrix a, Matrix b) {
        if (a.cols != b.rows) {
            System.out.println("Columns of A must match rows of B.");
            // TODO: look up how to write error messages
        }

        Matrix result = new Matrix(a.rows, b.cols);

        NNCalculations newCalc = new NNCalculations(result.rows, result.cols);

        result = newCalc.weightedSummsFormula(a, b);

        return result;

/*
        if (a.cols != b.rows) {
            System.out.println("Columns of A must match rows of B.");
            //return;
        }

        return new Matrix(a.rows, b.cols).map((e, i, j) -> {
            // Dot product of values in col
           double sum = 0;

            for (int k = 0; k < a.cols; k++) {
                sum += a.data[i][k] * b.data[k][j];
            }

            return sum;
        }); */
    }

    public void multiply (Matrix n) {
        if (n instanceof Matrix) {
            if (this.rows != n.rows || this.cols != n.cols) {
                System.out.println("Columns and Rows of A must match Columns and Rows of B.");
                return;
            }

        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.map(this.data[i][j] *= n.data[i][j]);
                //System.out.println(" place: " + i + " " + j + " data this: " + this.data[i][j] + " data n: " + n.data[i][j]);
            }
        }
            // hadamard product
            //return this.map((double e, int i, int j) -> e * n.data[i][j]);
        }
    }

    public void multiply(double n) {
        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.data[i][j] *= n;
                //System.out.println(" place: " + i + " " + j + " data this: " + this.data[i][j] + " data n: " + n);
            }
        }

        //return this.map(e -> e * n);
    }

    /*
    public void map(String func) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; i < this.cols; j++) {
                double val = this.data[i][j];
                if (func == "sigmoid") {
                    NNCalculations newCalc = new NNCalculations(this.rows, this.cols);
                    this.data[i][j] = newCalc.sigmoidFormula(val);
                } else {
                    NNCalculations newCalc = new NNCalculations(this.rows, this.cols);
                    this.data[i][j] = newCalc.dSigmoidFormula(val);
                }
            }
        }
    }

    static Matrix map(Matrix m, String func) {
        Matrix result = new Matrix(m.rows, m.cols);

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                //System.out.print("rows: " + m.rows + " cols: " + m.cols);
                double val = m.data[i][j];
                if (func == "sigmoid") {
                    //System.out.println("S");
                    NNCalculations newCalc = new NNCalculations(m.rows, m.cols);
                    result.data[i][j] = newCalc.sigmoidFormula(val);
                    //System.out.println(result.data[i][j]);
                } else {
                    //System.out.println("D");
                    NNCalculations newCalc = new NNCalculations(m.rows, m.cols);
                    result.data[i][j] = newCalc.dSigmoidFormula(val);
                    //System.out.println(result.data[i][j]);
                }
            }
        } */

    public Matrix map(double func) {
        // Apply a function to every element of matrix
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                double val = this.data[i][j];
                this.data[i][j] = func;
            }
        }
        return this;
    }

    static Matrix map(Matrix m, double func) {
        // Apply a function to every element of matrix
        return new Matrix(m.rows, m.cols).map(func);
    }

    static void print(Matrix out) {
        for (int i = 0; i < out.rows; i++) {
            for (int j = 0; j < out.cols; j++) {
                System.out.print(out.data[i][j]);
                System.out.print(" ");
            }
            System.out.println("\n");
        }
    }


    /*

    public double[][] matrix_product(double[][] mA, double[][] mB) {
        double[][] mC = new double[mA[0].length][mB.length];

        if (mA.length == mB[0].length && mA[0].length == mB.length) {
            for (int i = 0; i < mC.length; i++) {
                for(int j = 0; j < mC[i].length; j++) {
                    int colA = i;
                    int rowB = j;
                    double calculate =  0;

                    for (int k = 0; k < mA[i].length && k < mB.length; k++) {
                        calculate += mA[i][k] * mB[k][j];
                        colA++;
                        rowB++;
                    }

                    mC[i][j] = calculate;
                }
            }
        } else {
           // error message
        }
        return mC;
    } */
}
