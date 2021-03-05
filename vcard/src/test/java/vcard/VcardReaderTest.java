package vcard;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Håkan Lidén
 */
public class VcardReaderTest {

    private final Vcard vcard = new VcardReader(VcardReaderTest.class.getResourceAsStream("/sample/vcard.4.0.vcf")).read();

    @Test
    public void test_name() {
        String expectedGivenName = "Forrest";
        String expectedFamilyName = "Gump";
        String expectedPrefix = "Mr.";
        Name name = vcard.getPerson().getName();
        assertEquals("Given name should be " + expectedGivenName, expectedGivenName, name.getGivenName());
        assertEquals("Family name should be " + expectedFamilyName, expectedFamilyName, name.getFamilyName());
        assertEquals("Prefix should be " + expectedPrefix, expectedPrefix, name.getPrefixes());
    }

    @Test
    public void test_organisation() {
        String expectedOrg = "Bubba Gump Shrimp Co.";
        assertEquals("Organisation should be " + expectedOrg, expectedOrg, vcard.getPerson().getOrganisation());
    }

    @Test
    public void test_photo() {
        String expectedPhotoUrl = "http://www.example.com/dir_photos/my_photo.gif";
        String expectedPhotoMediaType = "image/gif";
        Photo photo = vcard.getPerson().getPhoto();
        assertEquals("Photo url should be ", expectedPhotoUrl, photo.getUrl());
        assertEquals("Photo media-type should be ", expectedPhotoMediaType, photo.getMediaType());
    }
    
    @Test
    public void test_version() {
        Version expectedVersion = Version.V4_0;
        assertEquals("Vcard should be of version " + expectedVersion, expectedVersion, vcard.getVersion());
    }
}
