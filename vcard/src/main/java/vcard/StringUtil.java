package vcard;

/**
 *
 * @author Håkan Lidén
 */
public class StringUtil {

    public static String getLineValue(String line) {
        return line.substring(line.indexOf(":") + 1);
    }
}
