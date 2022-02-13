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
}
