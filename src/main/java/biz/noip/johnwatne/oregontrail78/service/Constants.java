package biz.noip.johnwatne.oregontrail78.service;

/**
 * Container class for constant values used by program
 *
 * @author John Watne
 *
 */
public class Constants {
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
    public static final int MINIMUM_HUNTING_AMMUNITION = 39;

    /**
     * The reduction in mileage for the turn due to stopping at a fort or
     * hunting compared to continuing on.
     */
    public static final int REDUCED_MILEAGE_FOR_NOT_CHOOSING_CONTINUE = 45;

    /**
     * Maximum food amount added by hunting, before reducing for actual response
     * time.
     */
    public static final int MAX_HUNTING_FOOD_AMOUNT = 28;

    /**
     * Additional ammunition used per unit of response time.
     */
    public static final int RESPONSE_TIME_ADDITIONAL_AMMUNITION = 3;

    /**
     * Minimum amount of ammunition used for hunting, before increases for
     * actual response time.
     */
    public static final int MIN_HUNTING_AMMUNITION_USED = 10;

    /**
     * Reduction in amount of food from hunting per unit of response time.
     */
    public static final int RESPONSE_TIME_FOOD_REDUCTION = 2;

    /**
     * Maximum additional random amount of food from &quot;RIGHT BETWEEN THE
     * EYES&quot; hunting result.
     */
    public static final double MAX_RANDOM_RIGHT_BETWEEN_EYES_FOOD_AMOUNT = 6.0;

    /**
     * Minimum food amount from &quot;RIGHT BETWEEN THE EYES&quot; hunting
     * result.
     */
    public static final int RIGHT_BETWEEN_EYES_MIN_FOOD_AMOUNT = 52;

    /**
     * Maximum additional random amount of ammunition used for &quot;RIGHT
     * BETWEEN THE EYES&quot; hunting result.
     */
    public static final double RIGHT_BETWEEN_EYES_MAX_RANDOM_AMMUNITION = 4.0;

    /**
     * Minimum amount of ammunition used for &quot;RIGHT BETWEEN THE EYES&quot;
     * hunting result.
     */
    public static final int RIGHT_BETWEEN_EYES_MIN_AMMUNITION = 10;

    /**
     * Additional amount of food consumed per turn per value for eating choice.
     */
    public static final int ADDITIONAL_FOOD_CONSUMED_PER_EATING_CHOICE = 5;

    /**
     * Minimum amount of food consumed per turn before adding on additional food
     * amount based on eating choice.
     */
    public static final int MIN_FOOD_CONSUMED_PER_TURN = 8;

    /**
     * Maximum additional random mileage per turn.
     */
    public static final double MAX_RANDOM_MILEAGE_PER_TURN = 10.0;

    /**
     * Divisor used for calculating animal mileage factor.
     */
    public static final double ANIMAL_MILEAGE_DIVISOR = 5.0;

    /**
     * Amount subtracted from animal value to determine additional/reduced
     * mileage per turn.
     */
    public static final int ANIMAL_MILEAGE_REDUCTION = 220;

    /**
     * Mileage per turn before applying animal factor and random amount.
     */
    public static final int BASE_MILEAGE_PER_TURN = 200;

}
