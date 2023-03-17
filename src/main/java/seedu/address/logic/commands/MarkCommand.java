package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.OfficeConnectModel;
import seedu.address.model.RepositoryModelManager;
import seedu.address.model.shared.Id;
import seedu.address.model.task.Content;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * Marks a task as completed
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the task identified by the index number as completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    private final Index taskIndex;

    public MarkCommand(Index taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandResult execute(OfficeConnectModel officeConnectModel) throws CommandException {
        requireAllNonNull(officeConnectModel);

        RepositoryModelManager<Task> taskManager = officeConnectModel.getTaskModelManager();
        List<Task> taskList = taskManager.getFilteredItemList();

        if (taskIndex.getZeroBased() >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToMark = taskList.get(taskIndex.getZeroBased());
        if (taskToMark.getStatus().equals(new Status(true))) {
            throw new CommandException(Messages.MESSAGE_TASK_ALREADY_DONE);
        }

        Task markedTask = createMarkedTask(taskToMark);
        taskManager.setItem(taskToMark, markedTask);

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, markedTask));
    }

    private static Task createMarkedTask(Task taskToMark) {
        assert taskToMark != null;

        Title title = taskToMark.getTitle();
        Content content = taskToMark.getContent();
        Status doneStatus = new Status(true);
        Id id = taskToMark.getId();
        return new Task(title, content, doneStatus, id);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkCommand // instanceof handles nulls
                && taskIndex.equals(((MarkCommand) other).taskIndex)); // state check
    }
}
