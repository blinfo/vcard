package vcard;

import java.util.Optional;

/**
 *
 * @author Håkan Lidén
 */
public enum Version {

    V2_1, V3_0, V4_0;

    public String getVersionNumber() {
        return name().substring(1).replaceAll("_", ".");
    }

    @Override
    public String toString() {
        return getVersionNumber();
    }

    public static Optional<Version> find(String versionNumber) {
        if (versionNumber == null || versionNumber.isBlank()) {
            return Optional.empty();
        }
        for (Version v : values()) {
            if (v.getVersionNumber().equals(versionNumber)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }
}
