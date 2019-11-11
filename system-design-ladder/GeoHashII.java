import java.util.HashMap;

public class GeoHashII {
    private static char[] digits = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 
        'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    private static HashMap<Character, String> charToNumMap = new HashMap<>();
    static {
        for (int i = 0; i < digits.length; i++) {
            String binary = Integer.toBinaryString(i);
            StringBuilder sb = new StringBuilder();
            while (sb.length() < 5 - binary.length()) 
                sb.append('0');
            sb.append(binary);
            charToNumMap.put(digits[i], sb.toString());
        }
    }

    /*
     * @param geohash: geohash a base32 string
     * @return: latitude and longitude a location coordinate pair
     */
    public double[] decode(String geohash) {
        StringBuilder sb = new StringBuilder(geohash.length()5);
        for (int i = 0; i < geohash.length(); i++) {
            sb.append(charToNumMap.get(geohash.charAt(i)));
        }
        System.out.println(sb.toString());
        double longitude = this.get(sb, 0, sb.length()-1, -180, 180);
        double latitude = this.get(sb, 1, sb.length()-1, -90, 90);
        return new double[] {latitude, longitude};
    }

    private double get(StringBuilder sb, int startIndex, int endIndex, double start, double end) {
        while (startIndex <= endIndex) {
            double mid = (start + end) / 2;
            if (sb.charAt(startIndex) == '0')
                end = mid;
            else
                start = mid;
            startIndex += 2;
        }
        return (start + end) / 2;
    }
}