package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UndoCommandTest {

    @Test
    public void execute_noHistory_throwsCommandException() {
        Model model = new ModelManager();
        UndoCommand undoCommand = new UndoCommand();

        assertThrows(CommandException.class,
            UndoCommand.MESSAGE_NO_UNDOABLE_COMMAND, () -> undoCommand.execute(model));
    }

    @Test
    public void execute_afterCommit_success() throws Exception {
        Model model = new ModelManager();
        Person person = new PersonBuilder().build();
        model.addPerson(person);
        model.commitAddressBook();

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        assertFalse(model.hasPerson(person));
    }
}
