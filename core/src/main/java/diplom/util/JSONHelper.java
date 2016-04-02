package diplom.util;

/**
 * Created by vova on 19.03.16.
 */
public class JSONHelper {

    public static String getQuotedValue(String text) {
        if (text == null)
            return null;
        return "\"" + text + "\"";
    }

    public static String getPair(String param, String value, boolean last) {
        if (param == null || value == null)
            return null;
        String jParam = getQuotedValue(param);
        String jValue = getQuotedValue(value);
        String result = jParam + ": " + jValue;
        if (!last)
            result += ", ";
        return result;
    }
}
