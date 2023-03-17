package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.OfficeConnectModel;
import seedu.address.model.RepositoryModelManager;
import seedu.address.model.mapping.AssignTask;
import seedu.address.model.shared.Id;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.TitleContainsExactKeywordsPredicate;


/**
 * Review the list of persons assigned to a specified task.
 */
public class ReviewTaskCommand extends Command {
    public static final String COMMAND_WORD = "reviewtask";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Review person/s assigned to a task.\n"
            + "Parameters: TASK_NAME \n"
            + "Example: " + COMMAND_WORD + " CS2103 TP";

    public static final String MESSAGE_PERSON_ASSIGNED = "%1$s has been assigned to the following person/s:";
    public static final String MESSAGE_NO_PERSON_ASSIGNED = "%1$s has not been assigned to anyone.";

    private final TitleContainsExactKeywordsPredicate predicate;

    /**
     * Creates ReviewTask object with given taskIndex
     */
    public ReviewTaskCommand(TitleContainsExactKeywordsPredicate predicate) {
        requireAllNonNull(predicate);

        this.predicate = predicate;
    }

    /**
     * Executes ReviewTaskCommand
     */
    @Override
    public CommandResult execute(OfficeConnectModel officeConnectModel) throws CommandException {
        requireAllNonNull(officeConnectModel);

        ObservableList<Task> taskList = officeConnectModel.getTaskModelManager()
            .getReadOnlyRepository().getData()
            .filtered(predicate);
        if (taskList.size() != 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK);
        }
        Task task = taskList.get(0);
        Id tId = task.getId();
        Title title = task.getTitle();

        ObservableList<AssignTask> assignedPersonList = getAssignedPersonList(officeConnectModel, tId);

        displayAssignedTasksAndPersons(officeConnectModel, assignedPersonList, tId);

        if (assignedPersonList.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_PERSON_ASSIGNED, title));
        } else {
            return new CommandResult(String.format(MESSAGE_PERSON_ASSIGNED, title));
        }
    }

    private static void displayAssignedTasksAndPersons(OfficeConnectModel officeConnectModel,
                                                       ObservableList<AssignTask> assignedPersonList, Id tId) {
        RepositoryModelManager<Task> taskModelManager = officeConnectModel.getTaskModelManager();
        officeConnectModel.getPersonRepositoryModelManager()
            .updateFilteredItemList(person -> assignedPersonList.stream()
                .anyMatch(personTask -> personTask.getPersonId().equals(person.getId())));
        taskModelManager.updateFilteredItemList(task -> task.getId().equals(tId));
    }

    private static ObservableList<AssignTask> getAssignedPersonList(OfficeConnectModel officeConnectModel, Id tId) {
        RepositoryModelManager<AssignTask> personTaskModelManager = officeConnectModel.getAssignTaskModelManager();
        return personTaskModelManager
                .getFilteredItemList()
                .filtered(persontask -> persontask.getTaskId().equals(tId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReviewTaskCommand // instanceof handles nulls
                && this.predicate.equals(((ReviewTaskCommand) other).predicate)); // state check
    }
}
