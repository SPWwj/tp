package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.OfficeConnectModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(OfficeConnectModel officeConnectModel) {
        requireNonNull(officeConnectModel);
        officeConnectModel.getPersonRepositoryModelManager().showAllItems();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
