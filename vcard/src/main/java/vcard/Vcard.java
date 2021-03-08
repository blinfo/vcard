package vcard;

import java.time.ZonedDateTime;

/**
 *
 * @author Håkan Lidén
 */
public class Vcard {

    private final Version version;
    private final Person person;
    private final ZonedDateTime revision;

    private Vcard(Version version, Person person, ZonedDateTime revision) {
        this.version = version;
        this.person = person;
        this.revision = revision;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Version getVersion() {
        return version;
    }

    public Person getPerson() {
        return person;
    }

    public ZonedDateTime getRevision() {
        return revision;
    }

    @Override
    public String toString() {
        return "Vcard{" + "version=" + version + ", person=" + person + ", revision=" + revision + '}';
    }

    public static class Builder {

        private Version version;
        private Person person;
        private ZonedDateTime revision;

        private Builder() {
        }

        public Builder version(Version version) {
            this.version = version;
            return this;
        }

        public Builder person(Person person) {
            this.person = person;
            return this;
        }

        public Builder revision(ZonedDateTime revision) {
            this.revision = revision;
            return this;
        }

        public Vcard build() {
            return new Vcard(version, person, revision);
        }
    }
}
