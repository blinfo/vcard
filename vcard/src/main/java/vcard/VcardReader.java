package vcard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Håkan Lidén
 */
public class VcardReader {

    private final InputStream source;
    private List<Phone> phones;

    public VcardReader(InputStream source) {
        this.source = source;
    }

    public Vcard read() {
        Vcard result = new Vcard();
        Person person = new Person();
        result.setPerson(person);
        try {
            getLines().forEach(line -> {
                if (line.startsWith("VERSION")) {
                    result.setVersion(extractVersion(line));
                }
                if (line.startsWith("REV")) {
                    result.setRevision(extractRevision(line));
                }
                if (line.startsWith("N")) {
                    person.setName(extractName(line));
                }
                if (line.startsWith("ORG")) {
                    person.setOrganisation(extractOrganisationName(line));
                }
                if (line.startsWith("PHOTO")) {
                    person.setPhoto(extractPhoto(line));
                }
                if (line.startsWith("TEL")) {
                    addPhone(line);
                }
            });
            person.setPhones(phones);
            phones = new ArrayList<>();
            return result;
        } catch (IOException ex) {
            throw new VcardException("Could not extract data from source", ex);
        }
    }

    private void addPhone(String line) {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        Phone phone = new Phone();
        String[] parts = line.split(";");
        for (String part : parts) {
            if (part.toUpperCase().startsWith("TYPE")) {
                phone.setLabels(Stream.of(part.substring(part.indexOf("=") + 1).split(",")).map(s -> s.trim()).collect(Collectors.toList()));
            }
            if (part.toUpperCase().startsWith("VALUE")) {
                phone.setNumber(part.substring(part.lastIndexOf(":") + 1));
            }
        }
        phones.add(phone);
    }

    private ZonedDateTime extractRevision(String line) {
        String format = "yyyyMMdd'T'HHmmss";
        String timestamp = line.substring(line.indexOf(":") + 1);
        if (line.endsWith("Z")) {
            return ZonedDateTime.parse(timestamp, DateTimeFormatter.ofPattern(format + "z"));
        } else if (line.contains("+") || line.contains("-")) {
            return OffsetDateTime.parse(timestamp, DateTimeFormatter.ofPattern(format + "XXXXX")).atZoneSameInstant(ZoneId.systemDefault());
        } else {
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern(format)).atZone(ZoneId.systemDefault());
        }
    }

    private Version extractVersion(String line) {
        return Version.find(line.substring(line.indexOf(":") + 1))
                .orElseThrow(() -> new VcardException("Could not determine vesion of the vcard"));
    }

    private Photo extractPhoto(String line) {
        Photo photo = new Photo();
        photo.setUrl(line.substring(line.indexOf(":") + 1));
        photo.setMediaType(line.substring(line.indexOf("=") + 1, line.indexOf(":")));
        return photo;
    }

    private static String extractOrganisationName(String line) {
        return line.substring(4);
    }

    private Name extractName(String line) {
        Name name = new Name();
        int given = 1,
                family = 0,
                additional = 2,
                prefixes = 3,
                suffixes = 4;
        String[] nameParts = line.substring(line.indexOf(":") + 1).split(";");
        name.setGivenName(nameParts[given]);
        name.setFamilyName(nameParts[family]);
        name.setAdditionalName(nameParts[additional]);
        if (nameParts.length > prefixes) {
            name.setPrefixes(nameParts[prefixes]);
        }
        if (nameParts.length > suffixes) {
            name.setSuffixes(nameParts[suffixes]);
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
