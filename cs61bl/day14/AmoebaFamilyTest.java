import junit.framework.TestCase;

import org.junit.Test;

public class AmoebaFamilyTest extends TestCase {

	@Test
	public void testMakeNamesLowercase() {
		AmoebaFamily family = new AmoebaFamily("Amos McCoy");
		family.addChild("Amos McCoy", "mom/dad");
		family.addChild("Amos McCoy", "auntie");
		family.addChild("mom/dad", "me");
		family.addChild("mom/dad", "Fred");
		family.addChild("mom/dad", "Wilma");
		family.addChild("me", "Mike");
		family.addChild("me", "Homer");
		family.addChild("me", "Marge");
		family.addChild("Mike", "Bart");
		family.addChild("Mike", "Lisa");
		family.addChild("Marge", "Bill");
		family.addChild("Marge", "Hilary");
		family.makeNamesLowercase();
		assertEquals(family.getMyRootToString(), "amos mccoy");
	}

	public void testReplaceName() {
		AmoebaFamily family1 = new AmoebaFamily("Amos McCoy");
		family1.addChild("Amos McCoy", "mom/dad");
		family1.addChild("Amos McCoy", "auntie");
		family1.addChild("mom/dad", "me");
		family1.addChild("mom/dad", "Fred");
		family1.addChild("mom/dad", "Wilma");
		family1.addChild("me", "Mike");
		family1.addChild("me", "Homer");
		family1.addChild("me", "Marge");
		family1.addChild("Mike", "Bart");
		family1.addChild("Mike", "Lisa");
		family1.addChild("Marge", "Bill");
		family1.addChild("Marge", "Hilary");
		family1.replaceName("Amos McCoy", "Hee Hwang");
		assertEquals(family1.getMyRootToString(), "Hee Hwang");
	}

	public void testlongestName() {
		AmoebaFamily family2 = new AmoebaFamily("Amos McCoy");
		family2.addChild("Amos McCoy", "mom/dad");
		family2.addChild("Amos McCoy", "auntie");
		family2.addChild("mom/dad", "me");
		family2.addChild("mom/dad", "Fred");
		family2.addChild("mom/dad", "Wilma");
		family2.addChild("me", "Mike");
		family2.addChild("me", "Homer");
		family2.addChild("me", "Marge");
		family2.addChild("Mike", "Bart");
		family2.addChild("Mike", "Lisa");
		family2.addChild("Marge", "Bill");
		family2.addChild("Marge", "Hilaryyyyyyyyyy");
		String l = family2.longestName();
		assertEquals(l, "Hilaryyyyyyyyyy");
	}

	public void testSize() {
		AmoebaFamily family = new AmoebaFamily("Amos McCoy");
		family.addChild("Amos McCoy", "mom/dad");
		family.addChild("Amos McCoy", "auntie");
		family.addChild("mom/dad", "me");
		family.addChild("mom/dad", "Fred");
		family.addChild("mom/dad", "Wilma");
		family.addChild("me", "Mike");
		family.addChild("me", "Homer");
		family.addChild("me", "Marge");
		family.addChild("Mike", "Bart");
		family.addChild("Mike", "Lisa");
		family.addChild("Marge", "Bill");
		family.addChild("Marge", "Hilary");
		assertEquals(family.size(), 13);
	}

	public void testHeight() {
		AmoebaFamily family = new AmoebaFamily("Amos McCoy");
		family.addChild("Amos McCoy", "mom/dad");
		family.addChild("Amos McCoy", "auntie");
		family.addChild("mom/dad", "me");
		family.addChild("mom/dad", "Fred");
		family.addChild("mom/dad", "Wilma");
		family.addChild("me", "Mike");
		family.addChild("me", "Homer");
		family.addChild("me", "Marge");
		family.addChild("Mike", "Bart");
		family.addChild("Mike", "Lisa");
		family.addChild("Marge", "Bill");
		family.addChild("Marge", "Hilary");
		assertEquals(family.height(), 5);
	}
}
