package vcard;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Håkan Lidén
 */
public class VcardReaderTest {

    private final Person person = new VcardReader(VcardReaderTest.class.getResourceAsStream("/sample/vcard.4.0.vcf")).read();

    @Test
    public void test_name() {
        assertEquals("First name should be Forrest", "Forrest", person.getFirstName());
        assertEquals("Last name should be Gump", "Gump", person.getLastName());
    }

    @Test
    public void test_organisation() {
        assertEquals("Organisation should be Bubba Gump Shrimp Co.", "Bubba Gump Shrimp Co.", person.getOrganisation());
    }

    @Test
    public void test_photo() {
        assertEquals("Photo should be http://www.example.com/dir_photos/my_photo.gif", "http://www.example.com/dir_photos/my_photo.gif", person.getPhoto());
    }
}
