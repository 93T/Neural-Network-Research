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

    static Matrix fromArray(int[] arr) {
        Matrix m = new Matrix(arr.length, 1);

        for (int i = 0; i < arr.length; i++) {
            m.data[i][0] = arr[i];
        }
        return m;
    }

    static Matrix subtract(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.cols != b.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B. Subtract function!");
            //return;
        }

        Matrix result = new Matrix(a.rows, a.cols);

        for (int i = 0; i < result.rows; i++) {
            for(int j = 0; j < result.cols; j++) {
                result.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }

        return result;

        //// Return a new Matrix a-b
        //        return new Matrix(a.rows, a.cols).map((_, i, j) => a.data[i][j] - b.data[i][j]);
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
                //this.data[i][j] = Math.random() * 2 - 1;
                this.data[i][j] = -1.0 + (1.0-(-1.0)) * r.nextDouble();
            }
        }

        //return this.map(e => Math.random() * 2 - 1);
    }

    public void add (Matrix n) {
        if (this.rows != n.rows || this.cols != n.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B. Add matrix public function");
            return;
        }

        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.data[i][j] += n.data[i][j];
            }
        }
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
    }

    static Matrix multiply(Matrix a, Matrix b) {
        if (a.cols != b.rows) {
            System.out.println("Columns of A must match rows of B. Multiply static function matrix!");
            // TODO: look up how to write error messages
        }

        Matrix result = new Matrix(a.rows, b.cols);

        NNCalculations newCalc = new NNCalculations(result.rows, result.cols);

        result = newCalc.weightedSummsFormula(a, b);

        return result;

    }

    public void multiply (Matrix n) {
        if (this.rows != n.rows || this.cols != n.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B. Multiply public function matrix!");
            return;
        }

        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.data[i][j] *= n.data[i][j];
            }
        }
    }

    public void multiply(double n) {
        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.data[i][j] *= n;
            }
        }
    }

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
        }

        return result;
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
            // TODO: write error message here;
        }
        return mC;
    } */
}
