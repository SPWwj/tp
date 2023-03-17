package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.OfficeConnectModel.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.model.OfficeConnectModel;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListTaskCommand extends Command {
    public static final String COMMAND_WORD = "listtask";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute(OfficeConnectModel officeConnectModel) {
        requireAllNonNull(officeConnectModel);
        officeConnectModel.getTaskModelManager().updateFilteredItemList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
