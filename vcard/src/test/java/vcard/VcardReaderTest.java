package vcard;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Test
    public void test_phones() {
        long expectedNumberOfPhones = 2l;
        String expectedFirstLabel = "work";
        String expectedFirstNumber = "+1-111-555-1212";
        String expectedSecondLabel = "home";
        String expectedSecondNumber = "+1-404-555-1212";
        System.out.println(vcard);
        List<Phone> phones = vcard.getPerson().getPhones();
        assertEquals("There should be " + expectedNumberOfPhones + " phones", expectedNumberOfPhones, phones.size());
        assertTrue("First phone should contain label " + expectedFirstLabel, phones.get(0).getLabels().contains(expectedFirstLabel));
        assertEquals("First phone should have number " + expectedFirstNumber, expectedFirstNumber, phones.get(0).getNumber());
        assertTrue("Second phone should contain label " + expectedSecondLabel, phones.get(1).getLabels().contains(expectedSecondLabel));
        assertEquals("Second phone should have number " + expectedSecondNumber, expectedSecondNumber, phones.get(1).getNumber());
    }
}
