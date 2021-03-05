package vcard;

import java.util.List;

/**
 *
 * @author Håkan Lidén
 */
public class Person {

    private Name name;
    private String organisation;
    private String title;
    private Photo photo;
    private List<Phone> phones;
    private String addressWork;
    private String addressHome;
    private String email;

    public Person() {
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getAddressWork() {
        return addressWork;
    }

    public void setAddressWork(String addressWork) {
        this.addressWork = addressWork;
    }

    public String getAddressHome() {
        return addressHome;
    }

    public void setAddressHome(String addressHome) {
        this.addressHome = addressHome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{"
                + "name=" + name + ", "
                + "organisation=" + organisation + ", "
                + "title=" + title + ", "
                + "photo=" + photo + ", "
                + "phones=" + phones + ", "
                + "addressWork=" + addressWork + ", "
                + "addressHome=" + addressHome + ", "
                + "email=" + email + '}';
    }

}
