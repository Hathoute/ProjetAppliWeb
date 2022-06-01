package pack.util;

public class Utilities {

    public static String formatPrice(long price) {
        long leftover = price%100;
        return "" + price/100 + (leftover > 0 ? "." + price%100 : "") + "€";
    }
}
