package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_CONSTRAINTS =
            "Error: Invalid email address. Please provide a valid email in the format: example@domain.com";
    public static final String MESSAGE_LENGTH_CONSTRAINTS =
            "Error: Email exceeds maximum allowed length. "
            + "Total length must not exceed 254 characters "
            + "and the local part (before @) must not exceed 64 characters.";
    public static final int MAX_LENGTH = 254;
    public static final int MAX_LOCAL_PART_LENGTH = 64;

    private static final String SPECIAL_CHARACTERS = "+_.-";
    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    // Top-Level Domain must be at least two letters
    private static final String DOMAIN_LAST_PART_REGEX = "[a-zA-Z]{2,}$";
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)+" + DOMAIN_LAST_PART_REGEX;
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        String trimmedEmail = email.trim().toLowerCase();
        checkArgument(trimmedEmail.matches(VALIDATION_REGEX), MESSAGE_CONSTRAINTS);
        checkArgument(isWithinLengthLimits(trimmedEmail), MESSAGE_LENGTH_CONSTRAINTS);
        value = trimmedEmail;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX) && isWithinLengthLimits(test);
    }

    private static boolean isWithinLengthLimits(String test) {
        if (test.length() > MAX_LENGTH) {
            return false;
        }
        int atIdx = test.indexOf('@');
        return atIdx < 0 || atIdx <= MAX_LOCAL_PART_LENGTH;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
