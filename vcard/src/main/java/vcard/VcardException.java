package vcard;

/**
 *
 * @author Håkan Lidén
 */
public class VcardException extends RuntimeException {

    public VcardException(String message) {
        super(message);
    }

    public VcardException(String message, Throwable cause) {
        super(message, cause);
    }

    public VcardException(Throwable cause) {
        super(cause);
    }

}
