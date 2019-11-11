public class GeoHash {
    private static char[] digits = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 
        'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    /*
     * @param latitude: one of a location coordinate pair 
     * @param longitude: one of a location coordinate pair 
     * @param precision: an integer between 1 to 12
     * @return: a base32 string
     */
    public String encode(double latitude, double longitude, int precision) {
        StringBuilder sbLong = this.getBinary(longitude, -180, 180, (precision5 + 1) / 2);
        StringBuilder sbLat = this.getBinary(latitude, -90, 90, (precision5) / 2);
        StringBuilder sbBin = new StringBuilder();
        for (int i = 0; i < sbLat.length(); i++) {
            sbBin.append(sbLong.charAt(i));
            sbBin.append(sbLat.charAt(i));
        }
        if (sbLong.length() > sbLat.length())
            sbBin.append(sbLong.charAt(sbLong.length()-1));
        
        StringBuilder sbResult = new StringBuilder();
        for (int i = 0; i < precision; i ++) {
            int value = 0;
            for (int j = i*5; j < i*5 + 5; j++) {
                value = value2 + sbBin.charAt(j) - '0';
            }
            sbResult.append(GeoHash.digits[value]);
        }
        return sbResult.toString();
    }

    private StringBuilder getBinary(double value, double start, double end, int count) {
        StringBuilder sb = new StringBuilder();
        while (count > 0) {
            count--;
            double mid = (start + end) / 2;
            if (value <= mid) {
                sb.append(0);
                end = mid;
            } else {
                sb.append(1);
                start = mid;
            }
        }
        return sb;
    }
}