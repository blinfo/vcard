package vcard;

import java.time.ZonedDateTime;

/**
 *
 * @author Håkan Lidén
 */
public class Vcard {

    private Version version;
    private Person person;
    private ZonedDateTime revision;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ZonedDateTime getRevision() {
        return revision;
    }

    public void setRevision(ZonedDateTime revision) {
        this.revision = revision;
    }

}
