package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRepository;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.mapping.AssignTask;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;



    Optional<ReadOnlyRepository<Person>> readAddressBook() throws DataConversionException, IOException;


    void saveAddressBook(ReadOnlyRepository<Person> addressBook) throws IOException;

    Optional<ReadOnlyRepository<Task>> readTaskBook() throws DataConversionException, IOException;

    void saveTaskBook(ReadOnlyRepository<Task> taskBook) throws IOException;

    Optional<ReadOnlyRepository<AssignTask>> readPersonTaskBook() throws DataConversionException, IOException;

    void savePersonTaskBook(ReadOnlyRepository<AssignTask> personTaskBook) throws IOException;

}
