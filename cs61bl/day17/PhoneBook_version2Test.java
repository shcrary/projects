import java.util.ArrayList;

import junit.framework.TestCase;

public class PhoneBook_version2Test extends TestCase {
	/*
	 * Tests that you can add a person,number pair to the book and later access
	 * the number for that person.
	 */
	public void testCanAddAndGet() {
		PhoneBook_version2 myBook = new PhoneBook_version2();
		Person person1 = new Person("Sally");
		PhoneNumber num1 = new PhoneNumber("5105551234");
		PhoneNumber num2 = new PhoneNumber("5105551235");
		PhoneNumber num3 = new PhoneNumber("5105551236");

		myBook.addEntry(person1, num1);
		myBook.addEntry(person1, num2);
		myBook.addEntry(person1, num3);

		ArrayList<PhoneNumber> nums = myBook.getNumber(person1);

		ArrayList<PhoneNumber> expected = new ArrayList<PhoneNumber>();
		expected.add(num1);
		expected.add(num2);
		expected.add(num3);
		assertEquals(nums, expected);

	}
}