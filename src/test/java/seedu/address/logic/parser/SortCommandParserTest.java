package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortDateCommand;
import seedu.address.logic.commands.SortPriorityCommand;

/**
 * Contains unit tests for {@code SortCommandParser}.
 */
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validPriorityArgs_returnsSortPriorityCommand() {
        // Lowercase priority ascending
        assertParseSuccess(parser, "pr o/asc", new SortPriorityCommand(true));
        // Lowercase priority descending
        assertParseSuccess(parser, "pr o/desc", new SortPriorityCommand(false));
    }

    @Test
    public void parse_validDateArgs_returnsSortDateCommand() {
        // Lowercase date ascending
        assertParseSuccess(parser, "date o/asc", new SortDateCommand(true));
        // Lowercase date descending
        assertParseSuccess(parser, "date o/desc", new SortDateCommand(false));
    }

    @Test
    public void parse_validArgsWithLeadingSpaces_returnsCommand() {
        // Leading and trailing whitespace should be handled
        assertParseSuccess(parser, "  pr o/asc  ", new SortPriorityCommand(true));
        assertParseSuccess(parser, "  date o/desc  ", new SortDateCommand(false));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", SortCommandParser.MESSAGE_INVALID_SORT_TYPE);
        assertParseFailure(parser, "   ", SortCommandParser.MESSAGE_INVALID_SORT_TYPE);
    }

    @Test
    public void parse_invalidSortType_throwsParseException() {
        // Completely wrong sort type
        assertParseFailure(parser, "invalid", SortCommandParser.MESSAGE_INVALID_SORT_TYPE);
        assertParseFailure(parser, "name", SortCommandParser.MESSAGE_INVALID_SORT_TYPE);
        assertParseFailure(parser, "priority", SortCommandParser.MESSAGE_INVALID_SORT_TYPE);
    }

    @Test
    public void parse_invalidDateArgs_throwsParseException() {
        // Invalid order for date
        assertParseFailure(parser, "date o/invalid", SortDateCommandParser.MESSAGE_INVALID_ORDER);
        // Missing order prefix for date
        assertParseFailure(parser, "date asc", SortDateCommandParser.MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPriorityArgs_throwsParseException() {
        // Missing order prefix for priority
        assertParseFailure(parser, "pr", SortPriorityCommandParser.MESSAGE_INVALID_FORMAT);
        // Invalid order value for priority
        assertParseFailure(parser, "pr o/invalid", SortPriorityCommandParser.MESSAGE_INVALID_ORDER);
        assertParseFailure(parser, "pr o/up", SortPriorityCommandParser.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void parse_dateMixedCase_returnsSortDateCommand() {
        // "date" is case-insensitive in the sort type detection
        assertParseSuccess(parser, "DATE o/asc", new SortDateCommand(true));
        assertParseSuccess(parser, "Date o/desc", new SortDateCommand(false));
    }

    @Test
    public void parse_prMixedCase_returnsSortPriorityCommand() {
        // "pr" is case-insensitive in the sort type detection
        assertParseSuccess(parser, "PR o/asc", new SortPriorityCommand(true));
        assertParseSuccess(parser, "Pr o/desc", new SortPriorityCommand(false));
    }
}
