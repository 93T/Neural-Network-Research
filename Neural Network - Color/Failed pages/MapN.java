public class MapN {
    int x;
    int y;
    float feedback;

    public MapN() {this.init(0,0);}

    public MapN(int var1, int var2) {this.init(var1, var2);}

    void init(int var1, int var2) {
        this.x = var1;
        this.y = var2;
        this.feedback = 0.0F;
    }

    int getXPos() {return this.x;}

    int getYPos() {return this.y;}

    float getFeedback() {return this.feedback;}

    float computeFeedback(MapN var1, double var2) {
        int var4 = Math.abs(this.x - var1.getXPos());
        int var5 = Math.abs(this.y - var1.getYPos());
        return (float)Math.exp((double)(-(var4 * var4 + var5 * var5)) / var2);
    }
}
