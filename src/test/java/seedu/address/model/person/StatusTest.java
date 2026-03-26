package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void isValidStatus_validValues_returnsTrue() {
        assertTrue(Status.isValidStatus("ACTIVE"));
        assertTrue(Status.isValidStatus("REJECTED"));
        assertTrue(Status.isValidStatus("HIRED"));
        assertTrue(Status.isValidStatus("BLACKLISTED"));
        // case-insensitive
        assertTrue(Status.isValidStatus("active"));
        assertTrue(Status.isValidStatus("hired"));
        assertTrue(Status.isValidStatus("Rejected"));
        assertTrue(Status.isValidStatus("Blacklisted"));
    }

    @Test
    public void isValidStatus_invalidValues_returnsFalse() {
        assertFalse(Status.isValidStatus("NONE"));
        assertFalse(Status.isValidStatus("ARCHIVED"));
        assertFalse(Status.isValidStatus("pending"));
        assertFalse(Status.isValidStatus(""));
        assertFalse(Status.isValidStatus("unknown"));
    }

    @Test
    public void parse_validValues_returnsCorrectEnum() {
        assertEquals(Status.ACTIVE, Status.parse("active"));
        assertEquals(Status.ACTIVE, Status.parse("ACTIVE"));
        assertEquals(Status.ACTIVE, Status.parse("Active"));
        assertEquals(Status.REJECTED, Status.parse("rejected"));
        assertEquals(Status.REJECTED, Status.parse("REJECTED"));
        assertEquals(Status.HIRED, Status.parse("hired"));
        assertEquals(Status.HIRED, Status.parse("HIRED"));
        assertEquals(Status.BLACKLISTED, Status.parse("blacklisted"));
        assertEquals(Status.BLACKLISTED, Status.parse("BLACKLISTED"));
    }

    @Test
    public void parse_valueWithWhitespace_returnsCorrectEnum() {
        assertEquals(Status.ACTIVE, Status.parse("  active  "));
        assertEquals(Status.HIRED, Status.parse("  hired  "));
    }

    @Test
    public void parse_invalidValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Status.parse("pending"));
        assertThrows(IllegalArgumentException.class, () -> Status.parse("NONE"));
        assertThrows(IllegalArgumentException.class, () -> Status.parse("ARCHIVED"));
        assertThrows(IllegalArgumentException.class, () -> Status.parse(""));
    }

    @Test
    public void parse_nullValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Status.parse(null));
    }
}
