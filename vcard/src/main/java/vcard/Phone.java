package vcard;

import java.util.List;

/**
 *
 * @author Håkan Lidén
 */
public class Phone {

    private final List<String> labels;
    private final String number;

    private Phone(String number, List<String> labels) {
        this.number = number;
        this.labels = labels;
    }

    public static Phone of(String number, List<String> labels) {
        return new Phone(number, labels);
    }

    public List<String> getLabels() {
        return labels;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Phone{" + "labels=" + labels + ", number=" + number + '}';
    }

}
