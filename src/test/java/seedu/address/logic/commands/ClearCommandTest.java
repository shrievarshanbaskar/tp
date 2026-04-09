package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_returnsConfirmation() {
        Model model = new ModelManager();

        CommandResult result = new ClearCommand().execute(model);
        assertTrue(result.isRequiresConfirmation());
        assertEquals(ClearCommand.MESSAGE_CONFIRMATION, result.getFeedbackToUser());
    }

    @Test
    public void execute_nonEmptyAddressBook_returnsConfirmation() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        CommandResult result = new ClearCommand().execute(model);
        assertTrue(result.isRequiresConfirmation());
        assertEquals(ClearCommand.MESSAGE_CONFIRMATION, result.getFeedbackToUser());
    }

    @Test
    public void execute_confirmedAction_clearsAddressBook() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        CommandResult confirmationResult = new ClearCommand().execute(model);
        assertTrue(confirmationResult.getConfirmedAction().isPresent());

        // Simulate user pressing "Yes"
        CommandResult clearResult = confirmationResult.getConfirmedAction().get().execute();
        assertEquals(ClearCommand.MESSAGE_SUCCESS, clearResult.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
}
