package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;

/**
 * Marks a candidate as priority or removes priority status in the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a candidate as priority.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PRIORITY + "STATUS (yes/no)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PRIORITY + "yes";

    public static final String MESSAGE_MARK_SUCCESS = "Candidate at index %1$s successfully marked as priority: %2$s.";
    public static final String MESSAGE_REDUNDANT_MARK = "This candidate is already marked with priority status: %1$s.";

    private final Index index;
    private final Priority priority;

    /**
     * @param index of the person in the filtered person list to edit
     * @param priority the new priority status
     */
    public MarkCommand(Index index, Priority priority) {
        requireNonNull(index);
        requireNonNull(priority);

        this.index = index;
        this.priority = priority;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMark = lastShownList.get(index.getZeroBased());
        
        if (personToMark.getPriority().equals(priority)) {
            throw new CommandException(String.format(MESSAGE_REDUNDANT_MARK, priority.value));
        }

        Person markedPerson = createMarkedPerson(personToMark, priority);

        model.setPerson(personToMark, markedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        
        // Sorting MVP: Group priority candidates at the top, then sort by date added descending
        model.sortFilteredPersonList(Comparator.comparing((Person p) -> !p.getPriority().isPriority)
                .thenComparing((Person p) -> p.getDateAdded().date, Comparator.reverseOrder()));

        return new CommandResult(String.format(MESSAGE_MARK_SUCCESS, index.getOneBased(), priority.value));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToMark}
     * updated with the new {@code priority}.
     */
    private static Person createMarkedPerson(Person personToMark, Priority newPriority) {
        assert personToMark != null;

        return new Person(personToMark.getName(), personToMark.getPhone(), personToMark.getEmail(),
                personToMark.getAddress(), personToMark.getTags(), personToMark.getStatus(),
                personToMark.getRejectionReasons(), personToMark.getDateAdded(), newPriority);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return index.equals(otherMarkCommand.index)
                && priority.equals(otherMarkCommand.priority);
    }
}
