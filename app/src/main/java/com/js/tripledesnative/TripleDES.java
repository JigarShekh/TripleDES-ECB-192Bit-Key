package com.js.tripledesnative;

@SuppressWarnings("ALL")
public class TripleDES {

    static {
        System.loadLibrary("TripleDES");
    }

    public static native int encryptData(byte[] key, byte[] plainData, byte[] encryptData);
    public static native int decryptData(byte[] key, byte[] encryptText, byte[] plainData);

    public static final int KEY_NULL = -1100;
    public static final int PLAIN_DATA_NULL = -1101;
    public static final int ENCRYPT_DATA_NULL = -1102;
    public static final int INVALID_KEY_LENGTH = -1103;
    public static final int INVALID_PLAIN_DATA_LENGTH = -1104;
    public static final int INVALID_ENCRYPT_DATA_LENGTH = -1105;

    private static final String HEXES = "0123456789abcdef";
    private static final String HEX_INDICATOR = "0x";
    private static final String SPACE = " ";
    public static String byteToHexString(byte[] data) {
        if (data != null) {
            StringBuilder hex = new StringBuilder(2 * data.length);
            for (int i = 0; i <= data.length - 1; i++) {
                byte dataAtIndex = data[i];
                hex.append(HEX_INDICATOR);
                hex.append(HEXES.charAt((dataAtIndex & 0xF0) >> 4))
                        .append(HEXES.charAt((dataAtIndex & 0x0F)));
                hex.append(SPACE);
            }
            return hex.toString();
        } else {
            return null;
        }
    }
}
