public class WMatrix {
    float[][] weight;
    float[] bias;
    int size;

    // constructor
    public WMatrix(int var1, int var2, boolean var3) {
        this.weight = new float[var1][var2];
        this.bias = new float[var2];

        for (int var4 = 0; var4< var2; ++var4) {
            this.bias[var4] = 1.0F;
        }

        this.size = var1 * var2;
        if (var3) {  // is this checking if var3 exists
            this.size += var2;
        }
    }

    void init() {
        int var2;
        for(int var1 = 0; var1 < this.weight.length; ++var1) {
            for(var2 = 0; var2 < this.weight[0].length; ++var2) {
                this.weight[var1][var2] = (float)Math.random() * 2.0F - 1.0F;
            }
        }

        for(var2 = 0; var2 < this.weight[0].length; ++var2) {
            this.bias[var2] = (float)Math.random() * 2.0F - 1.0F;
        }

    }

    void init(float[][] var1) {
        for(int var2 = 0; var2 < this.weight.length; ++var2) {
            for(int var3 = 0; var3 < this.weight[0].length; ++var3) {
                this.weight[var2][var3] = var1[var2][var3];
            }
        }

    }

    void init(InputVal[] var1, int var2) {
        int var3;
        switch(var2) {
            case 1:
                for(var3 = 0; var3 < this.weight[0].length; ++var3) {
                    this.weight[0][var3] = (float)var1[var3].getX();
                }

                return;
            case 2:
                for(var3 = 0; var3 < this.weight[0].length; ++var3) {
                    this.weight[0][var3] = (float)var1[var3].getX();
                    this.weight[1][var3] = (float)var1[var3].getY();
                }

                return;
            case 3:
                for(var3 = 0; var3 < this.weight[0].length; ++var3) {
                    this.weight[0][var3] = (float)var1[var3].getX();
                    this.weight[1][var3] = (float)var1[var3].getY();
                    this.weight[2][var3] = (float)var1[var3].getZ();
                }

                return;
            default:
        }
    }

    void changeWeights(float[] var1, float[] var2, double var3) {
        int var6;
        float var7;
        for(int var5 = 0; var5 < this.weight.length; ++var5) {
            if (var1[var5] != 0.0F) {
                for(var6 = 0; var6 < this.weight[0].length; ++var6) {
                    if (var2[var6] != 1.0F && var2[var6] != 0.0F) {
                        var7 = this.weight[var5][var6];
                        this.weight[var5][var6] = 0.0F;
                        this.weight[var5][var6] = var7 + var1[var5] * var2[var6] * (1.0F - var2[var6]) * (float)var3;
                    }
                }
            }
        }

        for(var6 = 0; var6 < this.bias.length; ++var6) {
            if (var2[var6] != 1.0F && var2[var6] != 0.0F) {
                var7 = this.bias[var6];
                this.bias[var6] = 0.0F;
                this.bias[var6] = var7 + var2[var6] * (1.0F - var2[var6]) * (float)var3;
            }
        }

    }

    void changeWeightsKFM(float[] var1, float[] var2, double var3) {
        for(int var5 = 0; var5 < this.weight.length; ++var5) {
            for(int var6 = 0; var6 < this.weight[0].length; ++var6) {
                if (var1[var5] != this.weight[var5][var6] && var2[var6] != 0.0F) {
                    float var7 = this.weight[var5][var6];
                    this.weight[var5][var6] = 0.0F;
                    this.weight[var5][var6] = var7 + var2[var6] * (var1[var5] - var7) * (float)var3;
                }
            }
        }

    }

    float[] getInputWeights(int var1) {
        float[] var2 = new float[this.weight.length + 1];

        for(int var3 = 0; var3 < this.weight.length; ++var3) {
            var2[var3] = this.weight[var3][var1];
        }

        var2[this.weight.length] = this.bias[var1];
        return var2;
    }

    float[] getOutputWeights(int var1) {
        float[] var2 = new float[this.weight[0].length];

        for(int var3 = 0; var3 < this.weight[0].length; ++var3) {
            var2[var3] = this.weight[var1][var3];
        }

        return var2;
    }

    int size() {
        return this.size;
    }

    float[][] getWeights() {
        return this.weight;
    }

    float[] getBiases() {
        return this.bias;
    }
}
