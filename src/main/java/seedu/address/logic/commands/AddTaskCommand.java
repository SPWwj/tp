package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.OfficeConnectModel;
import seedu.address.model.RepositoryModelManager;
import seedu.address.model.task.Task;

/**
 * Adds a task to OfficeConnect
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addtask";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to OfficeConnect. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_CONTENT + "CONTENT "
            + PREFIX_STATUS + "STATUS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Complete Project X "
            + PREFIX_CONTENT + "Do the UML diagram "
            + PREFIX_STATUS + "false ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private final Task toAdd;
    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(OfficeConnectModel officeConnectModel) throws CommandException {
        requireNonNull(officeConnectModel);

        RepositoryModelManager<Task> taskModelManager = officeConnectModel.getTaskModelManager();
        if (taskModelManager.hasItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        taskModelManager.addItem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
