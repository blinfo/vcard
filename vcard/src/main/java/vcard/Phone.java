package vcard;

import java.util.List;

/**
 *
 * @author Håkan Lidén
 */
public class Phone {

    private List<String> labels;
    private String number;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" + "labels=" + labels + ", number=" + number + '}';
    }

}
