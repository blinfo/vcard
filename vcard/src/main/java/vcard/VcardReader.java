package vcard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Håkan Lidén
 */
public class VcardReader {

    private final InputStream source;

    public VcardReader(InputStream source) {
        this.source = source;
    }

    public Person read() {
        String[] lines;
        try {
            lines = getLines();
        } catch (IOException e) {
            throw new VcardException(e);
        }
        Person person = new Person();
        for (String line : lines) {
            if (line.startsWith("N:")) {
                Name name = new Name();
                String[] nameParts = line.substring(2).split(";");
                name.setGivenName(nameParts[1]);
                name.setFamilyName(nameParts[0]);
                name.setAdditionalName(nameParts[2]);
                name.setPrefixes(nameParts[3]);
                person.setName(name);
            }
            if (line.startsWith("ORG:")) {
                person.setOrganisation(line.substring(4));
            }
            if (line.startsWith("PHOTO;")) {
                Photo photo = new Photo();
                photo.setUrl(line.substring(line.indexOf(":") + 1));
                photo.setMediaType(line.substring(line.indexOf("=") + 1, line.indexOf(":")));
                person.setPhoto(photo);
            }
        }
        return person;
    }

    private String[] getLines() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(source, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
        }
        String[] lines = stringBuilder.toString().split("\n");
        return lines;
    }
}
