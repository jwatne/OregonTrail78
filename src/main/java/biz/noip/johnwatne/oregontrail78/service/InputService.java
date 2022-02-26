package biz.noip.johnwatne.oregontrail78.service;

import java.util.Scanner;

/**
 * Services for reading values from the console input.
 *
 * @author John Watne
 *
 */
public class InputService {
    private Scanner scanner;

    /**
     * Creates the InputService with the specified scanner for user input.
     *
     * @param scanner
     *            the specified scanner for user input.
     */
    public InputService(final Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Returns the value read from the next line entered as input as an int,
     * adjusted to be between the minimum and maximum specified values if out of
     * range, or the given default value if erroneous input is entered.
     *
     * @param defaultValue
     *            the default value to return if erroneous input is entered.
     * @param minimum
     *            the minimum value allowed.
     * @param maximum
     *            the maximum value allowed.
     * @return the value read from the next line entered as input as a Long,
     *         adjusted to be between the minimum and maximum specified values
     *         if out of range, or the given default value if erroneous input is
     *         entered.
     */
    public int getIntInRangeFromInput(final int defaultValue, final int minimum,
            final int maximum) {
        int intInRange = Math
                .min(Math.max(getIntFromInput(defaultValue), minimum), maximum);
        return intInRange;
    }

    /**
     * Returns the value read from the next line entered as input as an int, or
     * the given default value if erroneous input is entered.
     *
     * @param defaultValue
     *            the default value to return if erroneous input is entered.
     * @return the value read from the next line entered as input as a Long, or
     *         the given default value if erroneous input is entered.
     */
    public int getIntFromInput(final int defaultValue) {
        int returnValue = defaultValue;

        try {
            final String nextLine = scanner.nextLine();
            returnValue = Integer.parseInt(nextLine);
        } catch (Exception e) {
            returnValue = defaultValue;
        }

        return returnValue;
    }

    /**
     * Returns the next line entered as input.
     *
     * @return the next line entered as input.
     */
    public String getStringFromInput() {
        return scanner.nextLine();
    }

    /**
     * Returns a non-negative amount entered as input for the amount to spend on
     * the prompted item. The prompt is repeated until an amount equal to or
     * greater than zero is entered.
     *
     * @param item
     *            the item for which the spending amount is to be entered.
     *
     * @return a non-negative amount entered as input for the amount to spend on
     *         the prompted item.
     */
    public int getAmountToSpendFromInput(final String item) {
        int spendingAmount = -1;

        while (spendingAmount < 0) {
            System.out.println("HOW MUCH DO YOU WANT TO SPEND ON " + item);
            spendingAmount = this.getIntFromInput(-1);

            if (spendingAmount < 0) {
                System.out.println("IMPOSSIBLE");
            }
        }

        return spendingAmount;
    }

    /**
     * Indicates whether the user answered &quot;Yes&quot; in the last line
     * entered. In keeping with the original 1978 coding of the game, the first
     * character of the response must be an &quot;N&quot; in order to be treated
     * as a &quot;No&quot;. All other values are treated as &quot;Yes&quot;
     * responses.
     *
     * @return <code>true</code> if the user answered &quot;Yes&quot; in the
     *         last line entered.
     */
    public boolean isYesAnswerEntered() {
        final String yesNoResponse = scanner.nextLine();
        boolean answeredNo = ((yesNoResponse != null)
                && yesNoResponse.toUpperCase().startsWith("N"));
        return !answeredNo;
    }
}
