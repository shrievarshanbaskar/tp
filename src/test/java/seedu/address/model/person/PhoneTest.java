package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 digits
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("1234567890123456")); // exceeds 15 digits
        assertFalse(Phone.isValidPhone("-91234567")); // separator before first digit
        assertFalse(Phone.isValidPhone("(91234567")); // leading '(' rejected — must start with digit
        assertFalse(Phone.isValidPhone("+62-812-0000000000000")); // too many digits

        // valid phone numbers — plain digits
        assertTrue(Phone.isValidPhone("911")); // exactly 3 digits
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // exactly 15 digits
        assertTrue(Phone.isValidPhone("+6591234567")); // with + prefix, no separators

        // valid phone numbers — international formats with separators
        assertTrue(Phone.isValidPhone("+65-9123-4567")); // hyphens
        assertTrue(Phone.isValidPhone("+62 812 5555 1234")); // spaces (12 digits)
        assertTrue(Phone.isValidPhone("+1 (415) 555-2671")); // US format with parentheses
        assertTrue(Phone.isValidPhone("9312 1534")); // spaces within local number
        assertTrue(Phone.isValidPhone("+44 20 7946 0958")); // UK number with spaces
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));

        // phone with + prefix equals phone without + prefix (normalized)
        assertTrue(phone.equals(new Phone("+999")));

        // phone without + prefix equals phone with + prefix (normalized)
        Phone phoneWithPlus = new Phone("+6591234567");
        assertTrue(phoneWithPlus.equals(new Phone("6591234567")));

        // both with + prefix -> equals
        assertTrue(phoneWithPlus.equals(new Phone("+6591234567")));

        // different numbers, one with + prefix -> not equal
        assertFalse(phoneWithPlus.equals(new Phone("+6599999999")));

        // formatted variants normalise to the same digits -> equal
        assertTrue(phoneWithPlus.equals(new Phone("+65-9123-4567")));
        assertTrue(phoneWithPlus.equals(new Phone("+65 9123 4567")));
        assertTrue(new Phone("+1 (415) 555-2671").equals(new Phone("+14155552671")));
    }

    @Test
    public void isValidPhone_allZeros_returnsFalse() {
        assertFalse(Phone.isValidPhone("000"));
        assertFalse(Phone.isValidPhone("0000000"));
        assertFalse(Phone.isValidPhone("+000"));
        assertFalse(Phone.isValidPhone("+00-0")); // separators don't save all-zero
    }

    @Test
    public void hashCode_normalizedPhones_sameHashCode() {
        // plain vs + prefix
        assertTrue(new Phone("+999").hashCode() == new Phone("999").hashCode());
        // formatted variants
        assertTrue(new Phone("+6591234567").hashCode() == new Phone("+65-9123-4567").hashCode());
        assertTrue(new Phone("+6591234567").hashCode() == new Phone("+65 9123 4567").hashCode());
    }
}
