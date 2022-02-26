package biz.noip.johnwatne.oregontrail78.service;

/**
 * Service that returns a sequence of values used in determining random events
 * to occur during turns. This takes the place of the original BASIC code's
 * logic using RESTORE, READ D, and DATA.
 *
 * @author John Watne
 *
 */
public class RandomEventDataService {
    private static int[] values = new int[] {6, 11, 13, 15, 17, 22, 32, 35, 37,
            42, 44, 54, 64, 69, 95};
    private static int index = 0;

    public static int getNextValue() {
        int nextValue = values[index];
        index++;

        if (index >= values.length) {
            index = 0; // Reset to beginning.
        }

        return nextValue;
    }
}
