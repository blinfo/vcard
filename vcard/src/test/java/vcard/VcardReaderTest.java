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
        String expectedGivenName = "Forrest";
        String expectedFamilyName = "Gump";
        String expectedPrefix = "Mr.";
        Name name = person.getName();
        assertEquals("Given name should be " + expectedGivenName, expectedGivenName, name.getGivenName());
        assertEquals("Family name should be " + expectedFamilyName, expectedFamilyName, name.getFamilyName());
        assertEquals("Prefix should be " + expectedPrefix, expectedPrefix, name.getPrefixes());
    }

    @Test
    public void test_organisation() {
        String expectedOrg = "Bubba Gump Shrimp Co.";
        assertEquals("Organisation should be " + expectedOrg, expectedOrg, person.getOrganisation());
    }

    @Test
    public void test_photo() {
        String expectedPhotoUrl = "http://www.example.com/dir_photos/my_photo.gif";
        String expectedPhotoMediaType = "image/gif";
        assertEquals("Photo url should be ", expectedPhotoUrl, person.getPhoto().getUrl());
        assertEquals("Photo media-type should be ", expectedPhotoMediaType, person.getPhoto().getMediaType());
    }
}
