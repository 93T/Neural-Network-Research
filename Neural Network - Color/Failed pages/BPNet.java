
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

public class BPNet extends NN {
    // variables
    Vector neuronLayerVector;
    NeuronLayer[] neuronLayerArray;
    WMatrix[] weightMatrixArray;
    Pattern[] inputPatternArray;
    Pattern[] targetPatternArray;
    String[] outputPatternArray;
    double minimumError;
    double error;
    double accuracy;
    float[][] layerOutputError;
    String[][] conversionTable;
    int lastLayer;
    int lastMatrix;
    int lastPattern;
    int multiplier;

    public BPNet() {
        super.learningCycle = 0;
        super.maxLearningCycles = -1;
        this.minimumError = 5.0E-4D;
        super.learningRate = 0.24D;
        this.error = 1000.0D;
        this.multiplier = 0;
        this.accuracy = 0.2D;
        this.neuronLayerVector = new Vector();
        super.stopLearning = false;
        this.resetTime();
    }

    void addNeuronLayer(int var1) {this.neuronLayerVector.addElement(new NeuronLayer(var1 * multiplier));}

    void connectLayers() {
        this.weightMatrixArray = new WMatrix[this.neuronLayerVector.size() - 1];
        this.neuronLayerArray = new NeuronLayer[this.neuronLayerVector.size()];
        int var1 = 0;

        for(Enumeration var2 = this.neuronLayerVector.elements(); var2.hasMoreElements(); this.neuronLayerArray[var1++] = (NeuronLayer)var2.nextElement()) {
            ;
        }

        this.neuronLayerVector = null;

        for (var1 = 0; var1 < this.weightMatrixArray.length; ++var1) {
            this.weightMatrixArray[var1] = new WMatrix(this.neuronLayerArray[var1].size(), this.neuronLayerArray[var1 + 1].size(), true);
            this.weightMatrixArray[var1].init();
        }

        this.lastLayer = this.neuronLayerArray.length - 1;
        this.lastMatrix = this.weightMatrixArray.length - 1;
    }

    void setMinimumError(double var1) {this.minimumError = var1;}

    double getMinimumError() {return this.minimumError;}
    void setAccuracy(double var1) {this.accuracy = var1;}
    double getAccuracy() {return this.accuracy;}
    float[][] getWeightValues (int var1) {return this.weightMatrixArray[var1].getWeights();}
    float[] getNeuronOutputs (int var1) {return this.neuronLayerArray[var1].getOutput();}
    int getNumberOfLayers() {return this.neuronLayerArray.length;}
    int getNumberOfNeurons(int var1) {return this.neuronLayerArray[var1].size();}
    int getNumberOfWeights() {
        int var1 = 0;

        for (int var2 = 0; var2 <= this.lastMatrix; ++var2) {
            var1 += this.weightMatrixArray[var2].size();
        }

        return var1;
    }
    int getNumberOfWeights(int var1) {return this.weightMatrixArray[var1].size();}
    int getNumberOfPatterns() {return this.inputPatternArray.length;}
    String getInputPattern(int var1) {return this.inputPatternArray[var1].getPatternString();}
    String getTargetPattern(int var1) {return this.targetPatternArray[var1].getPatternString();}
    String getOutputPattern(int var1) {
        new String();
        String var3 = new String();
        float var4 = 0.0F;

        for (int var5 = 0; var5< this.layerOutputError[0].length; ++var5) {
            var4 = this.targetPatternArray[var1].getValue(var5) - this.layerOutputError[var1][var5];
            var3 = var3 + ((double)var4 < this.accuracy / 10.0D ? "0" : "1");
        }

        String var2 = "";

        for(int var6 = 0; var6 < var3.length(); var6 += this.multiplier) {
            var2 = var2 + this.getAsciiValue(var3.substring(var6, var6 + this.multiplier));
        }

        return var2;
     }

    float getPatternError(int var1) {
        float var2 = 0.0F;

        for (int var3 = 0; var3 < this.layerOutputError[0].length; ++var3) {
            var2 += Math.abs(this.layerOutputError[var1][var3]);
        }

        return var2;
    }

    double getError() {return this.error;}

    void learn() {
        if (this.error <= this.minimumError || super.learningCycle >= super.maxLearningCycles && super.maxLearningCycles != -1) {
            super.stopLearning = true;
        } else {
            ++super.learningCycle;

            for (int var1 = 0; var1 <= this.lastPattern; ++var1) {
                this.neuronLayerArray[0].setInput(this.inputPatternArray[var1]);

                for (int var2 = 1; var2 <= this.lastLayer; ++var2) {
                    this.neuronLayerArray[var2].computeInput(this.neuronLayerArray[var2 - 1], this.weightMatrixArray[var2 - 1]);
                    this.neuronLayerArray[var2].computeOutput();
                }

                this.neuronLayerArray[this.lastLayer].computeLayerError(this.targetPatternArray[var1]);
                this.layerOutputError[var1] = this.neuronLayerArray[this.lastLayer].getLayerError();

                for (int var3 = this.lastMatrix; var3 >= 0; --var3) {
                    this.neuronLayerArray[var3].computeLayerError(this.neuronLayerArray[var3 + 1], this.weightMatrixArray[var3]);
                }
            }
        }

        double var6 = 0.0D;

        for(int var4 = 0; var4 < this.layerOutputError.length; ++var4) {
            for(int var5 = 0; var5 < this.layerOutputError[0].length; ++var5) {
                var6 += this.square((double)this.layerOutputError[var4][var5]);
            }
        }

        this.error = Math.abs(var6 * 0.5D);
    }

    String recall(String var1) {
        Pattern var2 = new Pattern(var1, this.conversionTable);
        float[] var3 = new float[this.targetPatternArray[0].size()];
        this.neuronLayerArray[0].setInput(var2);

        for (int var6 = 1; var6 < this.lastLayer; ++var6) {
            this.neuronLayerArray[var6].computeInput(this.neuronLayerArray[var6 - 1], this.weightMatrixArray[var6 - 1]);
            this.neuronLayerArray[var6].computeOutput();
        }

        var3 = this.neuronLayerArray[this.lastLayer].getOutput();
        String var5 = "";
        String var4 = "";

        for (int var7 = 0; var7 < var3.length; ++var7) {
            var4 = var4 + ((double)var3[var7] < this.accuracy ? "0" : "1");
        }

        for (int var8 = 0; var8 < var4.length(); ++var8) {
            var5 = var5 + this.getAsciiValue(var4.substring(var8, var8 + this.multiplier));
        }

        return var5;
    }

    synchronized void readConversionFile(String var1) {
        String var2 = null;

        try {
            DataInputStream var3 = new DataInputStream(new FileInputStream(var1));
            int var4 = Integer.parseInt(var3.readLine());

            for (int var5 = 0; var5 < var4; ++var5) {
                var2 = var3.readLine();
                this.conversionTable[var5][0] = String.valueOf(var2.charAt(0));
                this.conversionTable[var5][1] = var2.substring(1);
            }

            this.multiplier = this.conversionTable[0][1].length();
        } catch (FileNotFoundException var6) {
            this.error(105);
        } catch (IOException var7) {
            this.error(104);
        }
    }

    String getAsciiValue(String var1) {
        int var2 = 0;
        int var3 = this.conversionTable.length;
        boolean var4 = false;
        boolean var5 = false;

        while(var2 < var3) {
            int var6 = var2 + var3 >> 1;
            int var7 = var1.compareTo(this.conversionTable[var6][1]);
            if (var7 == 0) {
                return this.conversionTable[var6][0];
            }

            if (var7 > 0) {
                var2 = var6;
            } else {
                var3 = var6;
            }
        }

        return "*";
    }
    synchronized void readPatternFile(String var1) {
        String var2 = null;

        try {
            DataInputStream var3 = new DataInputStream(new FileInputStream(var1));

            try {
                int var4 = Integer.parseInt(var3.readLine());
                int var5 = Integer.parseInt(var3.readLine());
                if (var5 * this.multiplier != this.neuronLayerArray[0].size()) {
                    this.error(106);
                }

                int var6 = Integer.parseInt(var3.readLine());
                if (var6 * this.multiplier != this.neuronLayerArray[this.lastLayer].size()) {
                    this.error(107);
                }

                this.inputPatternArray = new Pattern[var4];
                this.targetPatternArray = new Pattern[var4];
                this.outputPatternArray = new String[var4];
                this.lastPattern = this.inputPatternArray.length - 1;
                this.layerOutputError = new float[this.lastPattern + 1][this.neuronLayerArray[this.lastLayer].size()];

                for(int var7 = 0; var7 < var4; ++var7) {
                    var2 = var3.readLine();
                    if (var2 == null) {
                        this.error(102);
                    } else if (var2.length() != var5 + var6 + 1) {
                        this.error(100);
                    } else {
                        this.inputPatternArray[var7] = new Pattern(var2.substring(0, var5), this.conversionTable);
                        this.targetPatternArray[var7] = new Pattern(var2.substring(var5 + 1), this.conversionTable);
                        this.outputPatternArray[var7] = new String();
                    }
                }

            } catch (EOFException var8) {
                this.error(102);
            }
        } catch (FileNotFoundException var9) {
            this.error(105);
        } catch (IOException var10) {
            this.error(104);
        }
    }
}
