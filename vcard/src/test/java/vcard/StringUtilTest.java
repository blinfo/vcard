package vcard;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Håkan Lidén
 */
public class StringUtilTest {
    
    @Test
    public void assert_getLineValue_works() {
        String line = "KEY:value of line";
        String expectedResult = "value of line";
        assertEquals("Value of line should be " + expectedResult, expectedResult, StringUtil.getLineValue(line));
    }

}
