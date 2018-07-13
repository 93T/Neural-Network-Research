public class NeuronMat {
    MapN[] mapNeuron;
    int nr;
    int xSize;
    int ySize;

    public NeuronMat(int var1) {
        this.xSize = var1;
        this.ySize = 1;
        this.mapNeuron = new MapN[this.xSize * this.ySize];

        for(int var2 = 0; var2 < var1; ++var2) {
            this.mapNeuron[var2] = new MapN();
        }

    }

    public NeuronMat(int var1, int var2) {
        this.xSize = var1;
        this.ySize = var2;
        this.mapNeuron = new MapN[var1 * var2];
        int var3 = 0;

        for(int var4 = 0; var4 < var1; ++var4) {
            for(int var5 = 0; var5 < var2; ++var5) {
                this.mapNeuron[var3++] = new MapN();
            }
        }

    }

    void init(InputVal[] var1) {
        int var2 = 0;

        for(int var3 = 0; var3 < this.xSize; ++var3) {
            for(int var4 = 0; var4 < this.ySize; ++var4) {
                this.mapNeuron[var2].init(var3, var4);
                ++var2;
            }
        }

    }

    int size() {
        return this.mapNeuron.length;
    }

    int xSize() {
        return this.xSize;
    }

    int ySize() {
        return this.ySize;
    }

    MapN[] getMapNeurons() {
        return this.mapNeuron;
    }

    MapN computeActivationCenter(NeuronLayer var1, WMatrix var2) {
        float var3 = 1.0E20F;
        int var4 = 0;
        float[] var5 = new float[var2.size()];
        float[] var6 = new float[var1.size()];
        var6 = var1.getOutput();

        for(int var7 = 0; var7 < this.mapNeuron.length; ++var7) {
            var5 = var2.getInputWeights(var7);
            float var8 = 0.0F;

            for(int var9 = 0; var9 < var6.length; ++var9) {
                if (var6[var9] != var5[var9]) {
                    var8 += (var6[var9] - var5[var9]) * (var6[var9] - var5[var9]);
                }
            }

            if (var8 < var3) {
                var3 = var8;
                var4 = var7;
            }
        }

        return this.mapNeuron[var4];
    }

    float[] getFeedback(MapN var1, double var2) {
        float[] var4 = new float[this.mapNeuron.length];
        float var5 = (float)(2.0D * var2 * var2);

        for(int var6 = 0; var6 < this.mapNeuron.length; ++var6) {
            var4[var6] = this.mapNeuron[var6].computeFeedback(var1, (double)var5);
        }

        return var4;
    }
}
