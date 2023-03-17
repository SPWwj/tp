package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRepository;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.mapping.AssignTask;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final UserPrefsStorage userPrefsStorage;
    private final RepositoryStorage<Person> personRepositoryStorage;

    private final RepositoryStorage<Task> taskRepositoryStorage;
    private final RepositoryStorage<AssignTask> personTaskRepositoryStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(RepositoryStorage<Person> personRepositoryStorage, UserPrefsStorage userPrefsStorage) {
        this.personRepositoryStorage = personRepositoryStorage;
        this.userPrefsStorage = userPrefsStorage;
        UserPrefs userPrefs = new UserPrefs();
        taskRepositoryStorage = new JsonTaskStorage(userPrefs.getTaskFilePath());
        personTaskRepositoryStorage = new JsonAssignTaskStorage(userPrefs.getPersonTaskPath());
    }

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(RepositoryStorage<Person> personRepositoryStorage, UserPrefsStorage userPrefsStorage,
                          RepositoryStorage<Task> taskRepositoryStorage,
                          RepositoryStorage<AssignTask> personTaskRepositoryStorage) {
        this.personRepositoryStorage = personRepositoryStorage;
        this.userPrefsStorage = userPrefsStorage;

        this.taskRepositoryStorage = taskRepositoryStorage;
        this.personTaskRepositoryStorage = personTaskRepositoryStorage;

    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================


    @Override
    public Optional<ReadOnlyRepository<Person>> readAddressBook() throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file");
        return personRepositoryStorage.readRepository();
    }


    @Override
    public void saveAddressBook(ReadOnlyRepository<Person> addressBook) throws IOException {
        logger.fine("Attempting to write to data file");
        personRepositoryStorage.saveRepository(addressBook);
    }

    @Override

    public Optional<ReadOnlyRepository<Task>> readTaskBook() throws DataConversionException, IOException {
        logger.fine("Attempting to read data from task file");
        return taskRepositoryStorage.readRepository();
    }
    @Override

    public void saveTaskBook(ReadOnlyRepository<Task> taskBook) throws IOException {
        logger.fine("Attempting to save data to task file");
        taskRepositoryStorage.saveRepository(taskBook);
    }
    @Override
    public Optional<ReadOnlyRepository<AssignTask>> readPersonTaskBook() throws DataConversionException, IOException {
        logger.fine("Attempting to read data to person_task file");
        return personTaskRepositoryStorage.readRepository();
    }
    @Override
    public void savePersonTaskBook(ReadOnlyRepository<AssignTask> personTaskBook) throws IOException {
        logger.fine("Attempting to save data to person_task file");
        personTaskRepositoryStorage.saveRepository(personTaskBook);
    }

}
