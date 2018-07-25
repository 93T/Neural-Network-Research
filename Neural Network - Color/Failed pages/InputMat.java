public class InputMat {
    InputVal[] value;
    int dimension;

    public InputMat(int var1, int var2) {
        this.value = new InputVal[var1];
        this.dimension = var2 > 0 && var2 < 4 ? var2 : 3;

        for(int var3 = 0; var3 < var1; ++var3) {
            this.value[var3] = new InputVal();
        }
    }

    public void setInputX(int[] var1) {
        if (var1.length <= this.value.length) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                this.value[var2].setX(var1[var2]);
            }
        }
    }

    public void setInputY(int[] var1) {
        if (var1.length <= this.value.length) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                this.value[var2].setY(var1[var2]);
            }
        }
    }

    public void setInputZ(int[] var1) {
        if (var1.length <= this.value.length) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                this.value[var2].setZ(var1[var2]);
            }
        }
    }

    public void setInputValues(int[] var1, int[] var2, int[] var3) {
        this.setInputX(var1);
        this.setInputY(var2);
        this.setInputZ(var3);
    }

    int getDimension() {return this.dimension;}

    InputVal[] getInputValues() {return this.value;}

    InputVal getRandomInput() {return this.value[(int)(Math.random() * (double)this.value.length)];}

    int size() {return this.value.length;}
}
