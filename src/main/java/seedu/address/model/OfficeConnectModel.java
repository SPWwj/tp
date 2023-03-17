package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.model.mapping.AssignTask;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;


/**
 * The API of the OfficeConnectModel component.
 */
public class OfficeConnectModel {
    public static final Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;
    private final RepositoryModelManager<Task> taskModelManager;
    private final RepositoryModelManager<AssignTask> assignTaskModelManager;
    private final RepositoryModelManager<Person> personRepositoryModelManager;

    /**
     * Initializes a OfficeConnectModel empty data.
     */
    public OfficeConnectModel() {
        taskModelManager = new RepositoryModelManager<>(new Repository<Task>());
        assignTaskModelManager = new RepositoryModelManager<>(new Repository<AssignTask>());
        personRepositoryModelManager = new RepositoryModelManager<>(new Repository<Person>());
    }
    /**
     * Initializes a OfficeConnectModel given data.
     */
    public OfficeConnectModel(RepositoryModelManager<Person> personRepositoryModelManager,
                              RepositoryModelManager<Task> taskModelManager,
                              RepositoryModelManager<AssignTask> assignTaskModelManager) {
        this.taskModelManager = taskModelManager;
        this.assignTaskModelManager = assignTaskModelManager;
        this.personRepositoryModelManager = personRepositoryModelManager;
    }

    public RepositoryModelManager<Task> getTaskModelManager() {
        return taskModelManager;
    }

    public RepositoryModelManager<AssignTask> getAssignTaskModelManager() {
        return assignTaskModelManager;
    }
    public RepositoryModelManager<Person> getPersonRepositoryModelManager() {
        return personRepositoryModelManager;
    }

}
