package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.OfficeConnectModel;
import seedu.address.model.Repository;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(OfficeConnectModel officeConnectModel) {
        requireNonNull(officeConnectModel);
        officeConnectModel.getPersonRepositoryModelManager().setRepository(new Repository<>());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

