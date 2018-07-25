public class InputVal {
    int x;
    int y;
    int z;

    public InputVal() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public InputVal(int var1, int var2, int var3) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
    }

    void setX(int var1) {this.x = var1;}
    void setY(int var1) {this.y = var1;}
    void setZ(int var1) {this.z = var1;}
    int getX() {return this.x;}
    int getY() {return this.y;}
    int getZ() {return this.z;}
}
