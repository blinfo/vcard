package vcard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

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
        Person person = new Person();
        try {
            getLines().forEach(line -> {
                if (line.startsWith("N:")) {
                    person.setName(extractName(line));
                }
                if (line.startsWith("ORG:")) {
                    person.setOrganisation(extractOrganisationName(line));
                }
                if (line.startsWith("PHOTO;")) {
                    person.setPhoto(extractPhoto(line));
                }
            });
            return person;
        } catch (IOException ex) {
            throw new VcardException("Could not extract data from source", ex);
        }
    }

    private Photo extractPhoto(String line) {
        Photo photo = new Photo();
        photo.setUrl(line.substring(line.indexOf(":") + 1));
        photo.setMediaType(line.substring(line.indexOf("=") + 1, line.indexOf(":")));
        return photo;
    }

    private String extractOrganisationName(String line) {
        return line.substring(4);
    }

    private Name extractName(String line) {
        Name name = new Name();
        String[] nameParts = line.substring(line.indexOf(":") + 1).split(";");
        name.setGivenName(nameParts[1]);
        name.setFamilyName(nameParts[0]);
        name.setAdditionalName(nameParts[2]);
        if (nameParts.length > 3) {
            name.setPrefixes(nameParts[3]);
        }
        if (nameParts.length > 4) {
            name.setSuffixes(nameParts[4]);
        }
        return name;
    }

    private List<String> getLines() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(source, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
        }
        String[] lines = stringBuilder.toString().split("\n");
        return Arrays.asList(lines);
    }
}
