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
     * Returns the value read from the next line entered as input as a Long,
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
    public Long getLongInRangeFromInput(final Long defaultValue,
            final Long minimum, final Long maximum) {
        Long shootingExpertiseLevel = Math.min(
                Math.max(getLongFromInput(defaultValue), minimum), maximum);
        return shootingExpertiseLevel;
    }

    /**
     * Returns the value read from the next line entered as input as a Long, or
     * the given default value if erroneous input is entered.
     *
     * @param defaultValue
     *            the default value to return if erroneous input is entered.
     * @return the value read from the next line entered as input as a Long, or
     *         the given default value if erroneous input is entered.
     */
    public Long getLongFromInput(final Long defaultValue) {
        Long returnValue = defaultValue;

        try {
            final String nextLine = scanner.nextLine();
            returnValue = Long.parseLong(nextLine);
        } catch (Exception e) {
            returnValue = defaultValue;
        }

        return returnValue;
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
    public Long getAmountToSpendFromInput(final String item) {
        Long spendingAmount = -1L;

        while (spendingAmount < 0L) {
            System.out.println("HOW MUCH DO YOU WANT TO SPEND ON " + item);
            spendingAmount = this.getLongFromInput(-1L);

            if (spendingAmount < 0L) {
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
