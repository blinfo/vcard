package vcard;

/**
 *
 * @author Håkan Lidén
 */
public class Photo {

    private final String url;
    private final String mediaType;

    private Photo(String url, String mediaType) {
        this.url = url;
        this.mediaType = mediaType;
    }

    public static Photo of(String url, String mediaType) {
        return new Photo(url, mediaType);
    }

    public String getUrl() {
        return url;
    }

    public String getMediaType() {
        return mediaType;
    }

    @Override
    public String toString() {
        return "Photo{" + "url=" + url + ", mediaType=" + mediaType + '}';
    }

}
