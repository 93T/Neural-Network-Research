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

    static Matrix fromArray(int[] arr) {
        Matrix m = new Matrix(arr.length, 1);

        for (int i = 0; i < arr.length; i++) {
            m.data[i][0] = arr[i];
        }
        return m;
    }

    static Matrix subtract(Matrix a, Matrix b) {
        Matrix result = new Matrix(a.rows, a.cols);

        for (int i = 0; i < result.rows; i++) {
            for(int j = 0; j < result.cols; j++) {
                result.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }

        return result;
    }

    public int[] toArray() {
        int arr[] = new int[this.data.length];

        int place = 0;

        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                arr[place] = (int) this.data[i][j];
            }
        }

        return arr;
    }

    public void randomize() {
        for (int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.data[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    public void add (Matrix n) {
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
                result.data[i][j] = matrix.data[i][j];
            }
        }

        return result;
    }

    static Matrix multiply(Matrix a, Matrix b) {
        if (a.cols != b.cols) {
            System.out.println("Columns of A must match rows of B.");
            // TODO: look up how to write error messages
        }

        Matrix result = new Matrix(a.rows, b.cols);

        NNCalculations newCalc = new NNCalculations(result.rows, result.cols);

        newCalc.weightedSummsFormula(a, b);

        return result;
    }

    public void multiply (Matrix n) {
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
        NNCalculations newCalc = new NNCalculations(this.rows, this.cols);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; i < this.cols; j++) {
                double val = this.data[i][j];
                if (func == "sigmoid") {
                    this.data[i][j] = newCalc.sigmoidFormula(val);
                } else {
                    this.data[i][j] = newCalc.dSigmoidFormula(val);
                }
            }
        }
    }

    static Matrix map(Matrix m, String func) {
        NNCalculations newCalc = new NNCalculations(m.rows, m.cols);

        Matrix result = new Matrix(m.rows, m.cols);

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; i < m.cols; j++) {
                System.out.print("rows: " + m.rows + " cols: " + m.cols);
                double val = m.data[i][j];
                if (func == "sigmoid") {
                    result.data[i][j] = newCalc.sigmoidFormula(val);
                } else {
                    result.data[i][j] = newCalc.dSigmoidFormula(val);
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
