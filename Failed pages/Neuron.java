

public class Neuron {
    float input;
    float output;
    float outputError;

    public Neuron() {
        this.init(0.0F);
    }

    void init(float var1) {
        this.input = var1;
        this.output = var1;
        this.outputError = 0.0F;
    }

    void computeInput(float[] var1, float[] var2) {
        this.input = 0.0F;

        for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3] != 0.0F && var2[var3] != 0.0F) {
                this.input += var1[var3] * var2[var3];
            }
        }

        this.input += var2[var2.length - 1];
    }

    void activateSigmoid() {
        this.output = 1.0F / (1.0F + (float)Math.exp((double)(-this.input)));
    }

    void computeOutputError(float var1) {
        if (this.output != 0.0F && this.output != 1.0F) {
            this.outputError = (var1 - this.output) * this.output * (1.0F - this.output);
        }

    }

    void computeOutputError(float[] var1, float[] var2) {
        this.outputError = 0.0F;

        for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3] != 0.0F && this.output != 0.0F && this.output != 1.0F) {
                this.outputError += var1[var3] * var2[var3] * this.output * (1.0F - this.output);
            }
        }

    }

    float getInput() {
        return this.input;
    }

    float getOutput() {
        return this.output;
    }

    float getOutputError() {
        return this.outputError;
    }
}
