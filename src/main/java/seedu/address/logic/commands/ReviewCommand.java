package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.OfficeConnectModel;
import seedu.address.model.RepositoryModelManager;
import seedu.address.model.mapping.AssignTask;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsExactKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.shared.Id;
import seedu.address.model.task.Task;

/**
 * Reviews the list of tasks assigned to a specific person
 */
public class ReviewCommand extends Command {
    public static final String COMMAND_WORD = "review";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Review task/s assigned to a person."
            + "Parameters: PERSON_NAME \n"
            + "Example: " + COMMAND_WORD + " John Cena";

    public static final String MESSAGE_TASK_ASSIGNED = "%1$s has been assigned to the following tasks:";
    public static final String MESSAGE_NO_TASK_ASSIGNED = "%1$s does not have any assigned tasks.";

    private final NameContainsExactKeywordsPredicate predicate;

    /**
     * Creates Review object with given personIndex
     */
    public ReviewCommand(NameContainsExactKeywordsPredicate predicate) {
        requireAllNonNull(predicate);

        this.predicate = predicate;
    }

    /**
     * Executes ReviewCommand
     */
    @Override
    public CommandResult execute(OfficeConnectModel officeConnectModel) throws CommandException {
        requireAllNonNull(officeConnectModel);

        List<Person> personList = officeConnectModel.getPersonRepositoryModelManager().getReadOnlyRepository()
            .getData()
            .filtered(predicate);

        if (personList.size() != 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON);
        }

        Person person = personList.get(0);
        Id pId = person.getId();
        Name name = person.getName();

        ObservableList<AssignTask> assignedTaskList = getAssignedTaskList(officeConnectModel, pId);

        displayAssignedTaskAndPerson(officeConnectModel, assignedTaskList, pId);

        if (assignedTaskList.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_TASK_ASSIGNED, name));
        } else {
            return new CommandResult(String.format(MESSAGE_TASK_ASSIGNED, name));
        }
    }

    private static void displayAssignedTaskAndPerson(OfficeConnectModel officeConnectModel,
                                                     ObservableList<AssignTask> assignedTaskList, Id pId) {
        RepositoryModelManager<Task> taskModelManager = officeConnectModel.getTaskModelManager();
        officeConnectModel.getPersonRepositoryModelManager()
            .updateFilteredItemList(person -> person.getId().equals(pId));
        taskModelManager.updateFilteredItemList(task -> assignedTaskList.stream()
            .anyMatch(personTask -> personTask.getTaskId().equals(task.getId())));
    }

    private static ObservableList<AssignTask> getAssignedTaskList(OfficeConnectModel officeConnectModel, Id pId) {
        RepositoryModelManager<AssignTask> personTaskModelManager = officeConnectModel.getAssignTaskModelManager();
        return personTaskModelManager
                .getFilteredItemList()
                .filtered(persontask -> persontask.getPersonId().equals(pId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReviewCommand // instanceof handles nulls
                && this.predicate.equals(((ReviewCommand) other).predicate)); // state check
    }
}
