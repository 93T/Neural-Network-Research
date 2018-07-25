import java.util.LinkedList;

// used the following source for the base:    https://codereview.stackexchange.com/questions/24218/simple-neural-network-in-java

public class NN {
    // variables
    final int PATTERN_LENGTH = 100;
    final int PATTERN_VALUE = 101;
    final int PATTERNFILE_LENGTH = 102;
    final int GENERAL_IO = 104;
    final int FILE_NOT_FOUND = 105;
    final int NUMBER_OF_IVALUES = 106;
    final int NUMBER_OF_TVALUES = 107;
    final int TOO_FEW_LEARNINGCYCLES = 108;
    double learningRate;
    int learningCycle;
    int maxLearningCycles = -1;
    String maxLearningCyclesString;
    int displayStep;
    long startTime;
    boolean learnInfinitely = false;
    boolean stopLearning = false;

    boolean finishedLearning() {
        return this.stopLearning;
    }

    void error(int var1) {
        String var2 = null;
        String var3 = null;
        switch(var1) {
            case 100:
                var2 = "A pattern in the specified pattern file has the wrong number of values.";
                var3 = "The amount of values in one row must be: value in second line + value in third line + 1.";
                break;
            case 101:
                var2 = "A pattern value in the specified pattern file was wrong.";
                var3 = "Use pattern values that are 0 or 1.";
                break;
            case 102:
                var2 = "The number of patterns in the specified pattern file does not match the given number.";
                var3 = "Correct the number in the first line of your pattern file.";
            case 103:
            default:
                break;
            case 104:
                var2 = "A general IO error occurred while reading a file.";
                var3 = "Check the accessed file, maybe it's corrupted.";
                break;
            case 105:
                var2 = "The specified pattern file couldn't be found.";
                var3 = "Check path and filename of your pattern file.";
                break;
            case 106:
                var2 = "The number of input values in the specified pattern file is not the same as the number of neurons in the input neuron layer.";
                var3 = "Set the value in the second line of your pattern file to the number of input neurons or change the number of neurons in the input layer.";
                break;
            case 107:
                var2 = "The number of target values in the specified pattern file is not the same as the number of neurons in the output neuron layer.";
                var3 = "Set the value in the third line of your pattern file to the number of output neurons.";
                break;
            case 108:
                var2 = "There are too few maximum learning cycles defined.";
                var3 = "Increase the value of the maximum learning cycles or set it to -1 if the net should learn infinitely.";
        }

        System.out.println("Error [" + var1 + "]:\n\r" + var2);
        System.out.println("Try this: " + var3 + "\n\r");
        System.exit(0);
    }

    public double square(double var1) {return var1 * var1;}
    void setLearningRate(double var1) {this.learningRate = var1;}
    double getLearningRate() {return this.learningRate;}
    void setDisplayStep(int var1) {this.displayStep = var1;}
    boolean displayNow() {return this.learningCycle % this.displayStep == 0;}
    void resetTime() {this.startTime = System.currentTimeMillis();}

    String getElapsedTime() {
        return String.valueOf((float) ((double) (System.currentTimeMillis() - this.startTime) * 0.001D));
    }

    void setMaxLearningCycles(int var1) {
        if (var1 == -1) {
            this.learnInfinitely = true;
            this.maxLearningCycles = -1;
            this.maxLearningCyclesString = "infinite";
        } else if (var1 > 0) {
            this.learnInfinitely = false;
            this.maxLearningCycles = var1;
            this.maxLearningCyclesString = String.valueOf(var1);
        } else {
            this.error(108);
        }
    }

    int getMaxLearningCycles() {return this.maxLearningCycles;}
    void incLearningCycle() { ++this.learningCycle;}
    int getLearningCycle() {return this.learningCycle;};

    NN() {

    }
}
