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

    private VcardReader(InputStream source) {
        this.source = source;
    }

    public static Vcard from(InputStream source) {
        VcardReader reader = new VcardReader(source);
        try {
            List<String> lines = reader.getLines();
            Person person = new Person();
            ZonedDateTime revision = lines.stream()
                    .filter(line -> line.startsWith("REV"))
                    .map(reader::extractRevision)
                    .findFirst()
                    .orElseThrow(() -> new VcardException("No revision found"));
            Version version = lines.stream()
                    .filter(line -> line.startsWith("VERSION"))
                    .map(reader::extractVersion)
                    .findFirst()
                    .orElseThrow(() -> new VcardException("No version found"));
            lines.forEach(line -> {
                if (line.startsWith("N")) {
                    person.setName(reader.extractName(line));
                }
                if (line.startsWith("ORG")) {
                    person.setOrganisation(reader.extractOrganisationName(line));
                }
                if (line.startsWith("PHOTO")) {
                    person.setPhoto(reader.extractPhoto(line));
                }
                if (line.startsWith("TEL")) {
                    reader.addPhone(line);
                }
            });
            // TODO: Improve handling of phone numbers
            person.setPhones(reader.phones);
            reader.phones = new ArrayList<>();
            return Vcard.builder().revision(revision).version(version).person(person).build();
        } catch (IOException ex) {
            throw new VcardException("Could not extract data from source", ex);
        }
    }

    private void addPhone(String line) {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        phones.add(extractPhone(line));
    }

    private Phone extractPhone(String line) {
        List<String> labels = new java.util.ArrayList<>();
        String number = "";
        String[] parts = line.split(";");
        for (String part : parts) {
            if (part.toUpperCase().startsWith("TYPE")) {
                labels.addAll(Stream.of(part.substring(part.indexOf("=") + 1).split(",")).map(s -> s.trim()).collect(Collectors.toList()));
            }
            if (part.toUpperCase().startsWith("VALUE")) {
                number = part.substring(part.lastIndexOf(":") + 1);
            }
        }
        return Phone.of(number, labels);
    }

    private ZonedDateTime extractRevision(String line) {
        String format = "yyyyMMdd'T'HHmmss";
        String timestamp = StringUtil.getLineValue(line);
        if (line.endsWith("Z")) {
            return ZonedDateTime.parse(timestamp, DateTimeFormatter.ofPattern(format + "z"));
        } else if (line.contains("+") || line.contains("-")) {
            return OffsetDateTime.parse(timestamp, DateTimeFormatter.ofPattern(format + "XXXXX")).atZoneSameInstant(ZoneId.systemDefault());
        } else {
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern(format)).atZone(ZoneId.systemDefault());
        }
    }

    private Version extractVersion(String line) {
        return Version.find(StringUtil.getLineValue(line))
                .orElseThrow(() -> new VcardException("Could not determine vesion of the vcard"));
    }

    private Photo extractPhoto(String line) {
        String url = StringUtil.getLineValue(line);
        String mediaType = line.substring(line.indexOf("=") + 1, line.indexOf(":"));
        return Photo.of(url, mediaType);
    }

    private String extractOrganisationName(String line) {
        return StringUtil.getLineValue(line);
    }

    private Name extractName(String line) {
        Name name = new Name();
        int given = 1,
                family = 0,
                additional = 2,
                prefixes = 3,
                suffixes = 4;
        String[] nameParts = StringUtil.getLineValue(line).split(";");
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
