package lab2.mapfold;

/**
 *
 * @author Ganijon
 */
public class Main {

    public static int f(int x) {
        switch (x) {
            case 4: return 4;
            case 3: return 3;
            default:return 1;
        }
    }

    public static int g(int x, int y) {
        return x * y;
    }

    public static boolean isNiceArray(int result) {
        double n = Math.log(result) / Math.log(4);
        return (n == 0) || !isInteger(n);
    }

    public static boolean isInteger(double n) {
        return n == (int) n;
    }
    
    public static void main(String[] args) {
        
        //int[] a = {7, 6, 2, 3, 1};         //nice
        //int[] a = {7, 6, 2, 4, 1};         //NOT nice. 4 is there, but 3 is not there
        //int[] a = {3, 6, 2, 3, 4};         //nice
        //int[] a = {3, 4, 2, 3, 4, 7, 4};   //nice
        int[] a = {1, 6, 2, 8, 2, 9};      //nice

        int[] b = new int[a.length];
        int[] c = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            b[i] = f(a[i]);
        }

        int x = 1;
        for (int i = 0; i < a.length; i++) {
            x = g(x, b[i]);
            c[i] = x;
        }
        
        System.out.printf("Nice array: %s\n", isNiceArray(x) ? "YES" : "NO");

        for (int i = 0; i < a.length; i++) {
            System.out.print("\t" + a[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < a.length; i++) {
            System.out.print("\t" + b[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < a.length; i++) {
            System.out.print("\t" + c[i] + " ");
        }
        System.out.println();
    }
}
