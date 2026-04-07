package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteRejectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRejectCommand object.
 */
public class DeleteRejectCommandParser implements Parser<DeleteRejectCommand> {

    public static final String MESSAGE_INVALID_FORMAT =
            "Error: Invalid format. Usage: deletereject INDEX REJECT_INDEX\n"
            + "Both INDEX and REJECT_INDEX must be positive integers.";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRejectCommand
     * and returns a DeleteRejectCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteRejectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        String[] tokens = trimmedArgs.split("\\s+");

        if (tokens.length != 2) {
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

        return new DeleteRejectCommand(targetIndex, rejectIndex);
    }
}
