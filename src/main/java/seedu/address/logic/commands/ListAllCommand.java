package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.OfficeConnectModel;

/**
 * Lists all persons and tasks in the address book
 */
public class ListAllCommand extends Command {
    public static final String COMMAND_WORD = "listall";

    public static final String MESSAGE_SUCCESS = "Listed all persons and tasks";

    @Override
    public CommandResult execute(OfficeConnectModel officeConnectModel) {
        requireAllNonNull(officeConnectModel);


        listAll(officeConnectModel);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    private static void listAll(OfficeConnectModel officeConnectModel) {
        officeConnectModel.getTaskModelManager().showAllItems();
        officeConnectModel.getPersonRepositoryModelManager().showAllItems();
    }

}
