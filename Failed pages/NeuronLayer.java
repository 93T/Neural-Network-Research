public class NeuronLayer {
    Neuron[] neuron;

    NeuronLayer(int var1) {
        this.neuron = new Neuron[var1];

        for(int var2 = 0; var2 < var1; ++var2) {
            this.neuron[var2] = new Neuron();
        }

    }

    void setInput(Pattern var1) {
        for(int var2 = 0; var2 < this.neuron.length; ++var2) {
            this.neuron[var2].init(var1.getValue(var2));
        }

    }

    void setInput(InputVal var1) {
        float[] var2 = new float[]{(float)var1.getX(), (float)var1.getY(), (float)var1.getZ()};

        for(int var3 = 0; var3 < this.neuron.length; ++var3) {
            this.neuron[var3].init(var2[var3]);
        }

    }

    void computeInput(NeuronLayer var1, WMatrix var2) {
        for(int var3 = 0; var3 < this.neuron.length; ++var3) {
            this.neuron[var3].computeInput(var1.getOutput(), var2.getInputWeights(var3));
        }

    }

    void computeOutput() {
        for(int var1 = 0; var1 < this.neuron.length; ++var1) {
            this.neuron[var1].activateSigmoid();
        }

    }

    void computeLayerError(Pattern var1) {
        for(int var2 = 0; var2 < this.neuron.length; ++var2) {
            this.neuron[var2].computeOutputError(var1.getValue(var2));
        }

    }

    void computeLayerError(NeuronLayer var1, WMatrix var2) {
        for(int var3 = 0; var3 < this.neuron.length; ++var3) {
            this.neuron[var3].computeOutputError(var1.getLayerError(), var2.getOutputWeights(var3));
        }

    }

    float[] getOutput() {
        float[] var1 = new float[this.neuron.length];

        for(int var2 = 0; var2 < this.neuron.length; ++var2) {
            var1[var2] = this.neuron[var2].getOutput();
        }

        return var1;
    }

    float[] getLayerError() {
        float[] var1 = new float[this.neuron.length];

        for(int var2 = 0; var2 < this.neuron.length; ++var2) {
            var1[var2] = this.neuron[var2].getOutputError();
        }

        return var1;
    }

    int size() {
        return this.neuron.length;
    }
}