
/*
 * Created by G. Allen Johnson on 9/16/2015.
 */


public class QATest {

	/*
	 * Return the number of times the provided numberToMatch appears in content. The
	 * numberToMatch is provided in integer form but you should match both
	 * exact numbers and exact English/spelled out version of the number. Write the
	 * code with an emphasis on being maintainable and readable.
	 * 
	 * @param numberToMatch
	 * a number between 1 and 99 inclusive
	 * @param context
	 * a block of text to search for matches
	 * @return the number of times the numberToMatch appears content
	 *   
	 */

	private static final String BAD_CHARS = "[,.;!?$%+*/']";                //Used to sanitize the context string
	private static final String SPLIT_DELIMITER = "\\s+";                   //Used in initial split of the context string
	private static final int MIN_TEENS_NUM = 10;                            //Value checked to call onesCheck
	private static final int MIN_DOUBLES_NUM = 20;                          //Value checked to call teensCheck


	/*
	 * Compares numToMatch to the least significant value of the initial string
	 * and returns a boolean value.
	 */
	private static boolean onesCheck(int numToMatch, String numString) {

		switch (numString) {
			case "one":
				return (numToMatch == 1);
			case "two":
				return (numToMatch == 2);
			case "three":
				return (numToMatch == 3);
			case "four":
				return (numToMatch == 4);
			case "five":
				return (numToMatch == 5);
			case "six":
				return (numToMatch == 6);
			case "seven":
				return (numToMatch == 7);
			case "eight":
				return (numToMatch == 8);
			case "nine":
				return (numToMatch == 9);
			default:
				return false;
		}
	}


	/*
	 * Compares numToMatch to the last two values of the initial string
	 * if those values are greater than 9 and less than 20.
	 * Returns a boolean value.
	 */
	private static boolean teensCheck(int numToMatch, String numString) {

		switch (numString) {
			case "ten":
				return (numToMatch == 10);
			case "eleven":
				return (numToMatch == 11);
			case "twelve":
				return (numToMatch == 12);
			case "thirteen":
				return (numToMatch == 13);
			case "fourteen":
				return (numToMatch == 14);
			case "fifteen":
				return (numToMatch == 15);
			case "sixteen":
				return (numToMatch == 16);
			case "seventeen":
				return (numToMatch == 17);
			case "eighteen":
				return (numToMatch == 18);
			case "nineteen":
				return (numToMatch == 19);
			default:
				return false;
		}
	}


	/*
	 * Strips the least significant value from the initial numToMatch
	 * and returns the updated int value.
	 */
	private static int convertTens(int num) {
		return ((num / 10) * 10);
	}


	/*
	 * Strips the most significant value from the initial numToMatch
	 * and returns the updated int value.
	 */
	private static int convertOnes(int num) {
		return (num % 10);
	}


	/*
	 * Compares the most significant value of the initial numToMatch
	 * against double-digit values 20 to 90. Splits the initial
	 * context string if it contains a hyphen, passes onesCheck the
	 * least significant value and passes the most significant value
	 * back to itself. Returns a boolean value.
	 */
	private static boolean doublesCheck(int numToMatch, String numString) {

		if (numString.contains("-")) {
			String[] doubleDigitsString = numString.split("-");

			return (doublesCheck(convertTens(numToMatch), doubleDigitsString[0]) && onesCheck(
					convertOnes(numToMatch), doubleDigitsString[1]));
		} else {
			switch (numString) {
				case "twenty":
					return (numToMatch == 20);
				case "thirty":
					return (numToMatch == 30);
				case "forty":
					return (numToMatch == 40);
				case "fifty":
					return (numToMatch == 50);
				case "sixty":
					return (numToMatch == 60);
				case "seventy":
					return (numToMatch == 70);
				case "eighty":
					return (numToMatch == 80);
				case "ninety":
					return (numToMatch == 90);
				default:
					return false;
			}
		}
	}


	/*
	 * Replaces the special characters defined by BAD_CHARS
	 * with a space, " ". Converts string to lower case.
	 * Returns modified string.
	 */
	private static String sanitizeString(String newString) {
		return (newString.replaceAll(BAD_CHARS, " ").toLowerCase());
	}


	int countOccurrences(int numberToMatch, String context) {
		int numCounter = 0;
		String[] myChunkedString = sanitizeString(context).split(
				SPLIT_DELIMITER);

		for (String element : myChunkedString) {
			if ((numberToMatch + "").equals(element)) {                 //Quick int to string comparison hack
				numCounter++;                                           //to test for value equivalence.
			} else {
				boolean testCheck = false;                              //Else check for instances of the
				if (numberToMatch < MIN_TEENS_NUM) {                    //numToMatch spelled out in the
					testCheck = onesCheck(numberToMatch, element);      //context string.
				} else if (numberToMatch < MIN_DOUBLES_NUM) {
					testCheck = teensCheck(numberToMatch, element);
				} else {
					testCheck = doublesCheck(numberToMatch, element);
				}
				if (testCheck) {
					numCounter++;
				}
			}
		}

		return numCounter;
	}


	void test() {
		//Vanilla path test.
		assert (countOccurrences(2, "two brown mice twenty-two feet tall took 2 carrots") == 2);

		//Vanilla path test.
		assert (countOccurrences(1, "once upon a time there was one very small mouse who lived in a cone") == 1);

		//Tests a ones value for numToMatch with special characters, capitalized occurrences,
		//and values similar to numToMatch.
		assert (countOccurrences(1, "1's the loneliest number. Poor 1, NO ONE likes him. " +
				"Unlike his older brother, 11. Every one likes him.") == 4);

		//Tests a teens value for numToMatch with special characters, capitalized occurrences,
		//and values similar to numToMatch.
		assert (countOccurrences(16, "This is a teens test for the number 16." +
				"It is written SIXTEEN. This is a 16's trick:" +
				"1616 to see if sixteen is counted correctly. $16 is a special character test.") == 5);

		//Tests a double-digit value with only a most significant value
		//for numToMatch with special characters, capitalized occurrences,
		//and values similar to numToMatch.
		assert (countOccurrences(30, "This is a double-digits test for the number 30." +
				"It is written THIRTY. This is a 30's trick: " +
				"3030 to see if thirty is counted correctly. " +
				"$30 is 30% of $100.") == 6);

		//Tests a double-digit value with both a most and least significant value
		//for numToMatch with special characters, capitalized occurrences,
		//and values similar to numToMatch.
		assert (countOccurrences(22, "TWENTY-TWO is 22 written out. " +
				"Checking if 22-22 is incorrectly counted." +
				"There should be 4 instances of twenty-two. " +
				"Especially after I write out Two-hundred and Twenty-Two.") == 4);
	}


	//Test driver for class.

	public static void main(String[] args) {
		QATest myTest = new QATest();
		myTest.test();
	}

}
