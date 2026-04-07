package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditRejectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.RejectionReason;

/**
 * Parses input arguments and creates a new EditRejectCommand object.
 */
public class EditRejectCommandParser implements Parser<EditRejectCommand> {

    public static final String MESSAGE_INVALID_FORMAT =
            "Error: Invalid format. Usage: editreject INDEX REJECT_INDEX NEW_REASON\n"
            + "Both INDEX and REJECT_INDEX must be positive integers, followed by the new reason.";

    /**
     * Parses the given {@code String} of arguments in the context of the EditRejectCommand
     * and returns an EditRejectCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditRejectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmed = args.trim();

        if (trimmed.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        // Split into at least 3 parts: INDEX REJECT_INDEX REASON...
        String[] tokens = trimmed.split("\\s+", 3);
        if (tokens.length < 3) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        Index targetIndex;
        Index rejectIndex;
        try {
            targetIndex = ParserUtil.parseIndex(tokens[0]);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_FORMAT, pe);
        }
        try {
            rejectIndex = ParserUtil.parseIndex(tokens[1]);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_FORMAT, pe);
        }

        String reasonString = tokens[2].trim();
        if (!RejectionReason.isValidReason(reasonString)) {
            throw new ParseException(RejectionReason.MESSAGE_CONSTRAINTS);
        }

        return new EditRejectCommand(targetIndex, rejectIndex, reasonString);
    }
}
