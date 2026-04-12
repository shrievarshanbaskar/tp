package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.SortMode;

/**
 * Sorts all candidates in the address book by priority.
 */
public class SortPriorityCommand extends Command {

    public static final String COMMAND_WORD = "sort"; // Preamble is pr

    public static final String MESSAGE_USAGE = COMMAND_WORD + " pr: Sorts candidates by priority.\n"
            + "Secondary sorts by Date Added (descending) and Name (alphabetically).\n"
            + "Parameters: " + PREFIX_ORDER + "ORDER\n"
            + "Example: " + COMMAND_WORD + " pr " + PREFIX_ORDER + "desc";

    public static final String MESSAGE_SUCCESS_ASC =
            "Sorted all candidates by priority status (low-priority first).";
    public static final String MESSAGE_SUCCESS_DESC =
            "Sorted all candidates by priority status (high-priority first).";
    public static final String MESSAGE_EMPTY_ADDRESS_BOOK = "The address book is currently empty. Nothing to sort.";

    private static final Logger logger = LogsCenter.getLogger(SortPriorityCommand.class);

    private final boolean isAscending;

    /**
     * Creates a SortPriorityCommand.
     *
     * @param isAscending if true, low-priority candidates appear first (asc);
     *                    if false, high-priority candidates appear first (desc).
     */
    public SortPriorityCommand(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.getAddressBook().getPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_ADDRESS_BOOK);
        }

        model.setSortMode(isAscending ? SortMode.PRIORITY_ASC : SortMode.PRIORITY_DESC);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String resultMessage = isAscending ? MESSAGE_SUCCESS_ASC : MESSAGE_SUCCESS_DESC;
        logger.fine("SortPriorityCommand executed: " + resultMessage);
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SortPriorityCommand
                && isAscending == ((SortPriorityCommand) other).isAscending);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isAscending", isAscending)
                .toString();
    }
}
