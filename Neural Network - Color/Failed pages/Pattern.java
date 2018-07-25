public class Pattern {
    int[] pat;
    int nr;
    String type;
    String patString;

    public Pattern(String var1, String[][] var2) {
        this.patString = var1;
        this.pat = new int[var1.length() * var2[0][1].length()];
        String var3 = new String();
        new String();

        for(int var5 = 0; var5 < var1.length(); ++var5) {
            for(int var6 = 0; var6 < var2.length; ++var6) {
                String var4 = String.valueOf(var1.charAt(var5));
                if (var4.compareTo(" ") == 0) {
                    var3 = var3 + var2[0][1];
                    break;
                }

                if (var4.compareTo(var2[var6][0]) == 0) {
                    var3 = var3 + var2[var6][1];
                    break;
                }
            }
        }

        char[] var8 = new char[var3.length()];
        var8 = var3.toCharArray();

        for (int var7 = 0; var7 < this.pat.length; ++var7) {
            this.pat[var7] = Character.digit(var8[var7],10);
        }
    }

    String getPatternString() {return this.patString;}
    float getValue(int var1) {return (float)this.pat[var1];}
    int size() {return this.pat.length;}

    boolean checkValues() {
        for (int var1 = 0; var1 < this.pat.length; ++var1) {
            if (this.pat[var1] < 0 || this.pat[var1] > 1) {
                return false;
            }
        }

        return true;
    }
}
