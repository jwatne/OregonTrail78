package biz.noip.johnwatne.oregontrail78.service;

/**
 * Container class for constant values used by program
 *
 * @author John Watne
 *
 */
public class Constants {
    /**
     * Zero as a Long.
     */
    public static final Long ZERO_LONG = Long.valueOf(0);
    /**
     * Number of bullets purchased per dollar
     */
    public static final int BULLETS_PER_DOLLAR = 50;
    /**
     * Cutoff dollar amount for low food amount.
     */
    public static final int LOW_FOOD_AMOUNT = 13;
    /**
     * Minimum amount of ammunition for hunting.
     */
    public static final long MINIMUM_HUNTING_AMMUNITION = 39L;

    /**
     * The reduction in mileage for the turn due to stopping at a fort compared
     * to continuing on.
     */
    public static final int MILES_LOST_FOR_STOPPING_AT_FORT = 45;

}
