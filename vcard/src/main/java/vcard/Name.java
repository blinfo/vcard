package vcard;

/**
 *
 * @author Håkan Lidén
 */
public class Name {

    private String givenName;
    private String familyName;
    private String additionalName;
    private String prefixes;
    private String suffixes;

    public Name() {
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(String prefixes) {
        this.prefixes = prefixes;
    }

    public String getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(String suffixes) {
        this.suffixes = suffixes;
    }

}
