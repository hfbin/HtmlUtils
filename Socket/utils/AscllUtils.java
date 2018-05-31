package cn.hfbin.lzzmjx.utils;

/**
 * Created by: HuangFuBin
 * Date: 2018/5/24
 * Time: 17:36
 * Such description:
 */
public  class AscllUtils {
    public static String bytesToHexString(byte[] src) {
        StringBuffer sb = new StringBuffer("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
            if (i != src.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }


}
