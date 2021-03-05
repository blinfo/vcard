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

    private final InputStream is;

    public VcardReader(InputStream is) {
        this.is = is;
    }

    public Person read() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (Reader r = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = r.read()) != -1) { sb.append((char) c); }
        }
        String[] vc = sb.toString().split("\n");
        Person p = new Person();
        for (String l : vc) {
        if (l.startsWith("N:")) {
            String[] ns = l.substring(2).split(";");
            p.setFirstName(ns[1]);
                p.setLastName(ns[0]);
        }
        if (l.startsWith("ORG:")) 
            p.setOrganisation(l.substring(4));
        if (l.startsWith("PHOTO;")) {
            p.setPhoto(l.substring(26));
        }
        }
        return p;
    }

    public static void main(String[] args) throws IOException {
        InputStream s = VcardReader.class.getResourceAsStream("/sample/vcard.4.0.vcf");
        VcardReader r = new VcardReader(s);
        Person p = r.read();
        System.out.println(p.getFirstName());
        System.out.println(p.getLastName());
        System.out.println(p.getOrganisation());
        System.out.println(p.getPhoto());
    }
}
