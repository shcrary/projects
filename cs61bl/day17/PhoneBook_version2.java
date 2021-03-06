import java.util.ArrayList;
import java.util.TreeMap;

public class PhoneBook_version2 {
	// Uses the default constructor that takes no arguments

	// Initializes the instance variable book to be a TreeMap
	private TreeMap<Person, ArrayList<PhoneNumber>> book = new TreeMap<Person, ArrayList<PhoneNumber>>();

	/*
	 * Adds a person with this name to the phone book and associates with the
	 */

	// Check if the person is in the list
	// If he is, access the list and add the number
	// otherwise, make a new array and associate with the person.
	public void addEntry(Person personToAdd, PhoneNumber numberToAdd) {
		ArrayList<PhoneNumber> numArray;
		if (book.containsKey(personToAdd)) {
			numArray = book.get(personToAdd);
			numArray.add(numberToAdd);
			book.put(personToAdd, numArray);
		} else {
			numArray = new ArrayList<PhoneNumber>();
			numArray.add(numberToAdd);
			book.put(personToAdd, numArray);
		}
	}

	/*
	 * Access an entry in the phone book.
	 */
	public ArrayList<PhoneNumber> getNumber(Person personToLookup) {
		ArrayList<PhoneNumber> nums = book.get(personToLookup);
		if (nums == null) {
			throw new IllegalArgumentException("The person "
					+ personToLookup.toString()
					+ " was not found in this PhoneBook");
		}
		return nums;
	}

}
