package seedu.address.testutil;

import seedu.address.model.ReadOnlyRepository;
import seedu.address.model.Repository;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Repository<Person> addressBook;

    public AddressBookBuilder() {
        addressBook = new Repository<>();
    }

    public AddressBookBuilder(ReadOnlyRepository<Person> addressBook) {
        this.addressBook = Repository.of(addressBook);
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addItem(person);
        return this;
    }

    public Repository<Person> build() {
        return addressBook;
    }
}
