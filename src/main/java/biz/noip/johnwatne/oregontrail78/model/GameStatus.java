package biz.noip.johnwatne.oregontrail78.model;

/**
 * The status of the game.
 *
 * @author John Watne
 *
 */
public class GameStatus {
    boolean fort = true;
    boolean injured = false;
    boolean ill = false;
    boolean clearedSouthPass = false;
    boolean clearedBlueMountains = false;
    boolean blizzard = false;
    int TripMileage = 0;
    boolean clearedSouthPassSettingMileage = false;
    boolean insufficientColdWeatherClothing = false;
    int cash = -1;
    int animals = 0;
    int turnNumber = 0;
    int food;
    int ammunition;
    int clothing;
    int misc;
    boolean finished = false;
    int tacticsChoice = 0;
    boolean ridersHostile;
    int eventGeneratingCounter = 1;
    int randomEvent;
    int eatingChoice = -1;
    int shootingExpertiseLevel = 0;

    /**
     * Indicates whether the player is in a fort.
     *
     * @return <code>true</code> if the player is in a fort.
     */
    public boolean isFort() {
        return fort;
    }

    public void setFort(boolean fort) {
        this.fort = fort;
    }

    /**
     * Indicates whether a member of the party is injured.
     *
     * @return <code>true</code> if a member of the party is injured.
     */
    public boolean isInjured() {
        return injured;
    }

    public void setInjured(boolean injured) {
        this.injured = injured;
    }

    /**
     * Indicates whether a member of the party is ill.
     *
     * @return <code>true</code> if a member of the party is ill.
     */
    public boolean isIll() {
        return ill;
    }

    public void setIll(boolean ill) {
        this.ill = ill;
    }

    /**
     * Indicates whether the party cleared South Pass.
     *
     * @param clearedSouthPass
     *            <code>true</code> if the party cleared South Pass.
     */
    public boolean isClearedSouthPass() {
        return clearedSouthPass;
    }

    public void setClearedSouthPass(boolean clearedSouthPass) {
        this.clearedSouthPass = clearedSouthPass;
    }

    /**
     * Indicates whether the party cleared the Blue Mountains.
     *
     * @return <code>true</code> if the party cleared the Blue Mountains.
     */
    public boolean isClearedBlueMountains() {
        return clearedBlueMountains;
    }

    public void setClearedBlueMountains(boolean clearedBlueMountains) {
        this.clearedBlueMountains = clearedBlueMountains;
    }

    /**
     * Indicates whether there is a blizzard.
     *
     * @return <code>true</code> if there is a blizzard.
     */
    public boolean isBlizzard() {
        return blizzard;
    }

    public void setBlizzard(boolean blizzard) {
        this.blizzard = blizzard;
    }

    /**
     * Returns the total mileage for the whole trip.
     *
     * @return the total mileage for the whole trip.
     */
    public int getTripMileage() {
        return TripMileage;
    }

    public void setTripMileage(int tripMileage) {
        TripMileage = tripMileage;
    }

    /**
     * Indicates whether the party cleared South Pass in setting the mileage.
     *
     * @return <code>true</code> if the party cleared South Pass in setting the
     *         mileage.
     */
    public boolean isClearedSouthPassSettingMileage() {
        return clearedSouthPassSettingMileage;
    }

    public void setClearedSouthPassSettingMileage(
            boolean clearedSouthPassSettingMileage) {
        this.clearedSouthPassSettingMileage = clearedSouthPassSettingMileage;
    }

    /**
     * Indicates whether the family has insufficient cold weather clothing.
     *
     * @return <code>true</code> if the family has insufficient cold weather
     *         clothing.
     */
    public boolean isInsufficientColdWeatherClothing() {
        return insufficientColdWeatherClothing;
    }

    public void setInsufficientColdWeatherClothing(
            boolean insufficientColdWeatherClothing) {
        this.insufficientColdWeatherClothing = insufficientColdWeatherClothing;
    }

    /**
     * Returns the turn number for setting the date.
     *
     * @return the turn number for setting the date.
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    /**
     * Returns the cash currently held by the family.
     *
     * @return the cash currently held by the family.
     */
    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    /**
     * Returns current dollar value of animals owned by family.
     *
     * @return current dollar value of animals owned by family.
     */
    public int getAnimals() {
        return animals;
    }

    public void setAnimals(int animals) {
        this.animals = animals;
    }

    /**
     * Returns current dollar value of food for family.
     *
     * @return current dollar value of food for family.
     */
    public int getFood() {
        return food;
    }

    public void setFood(final int food) {
        this.food = food;
    }

    /**
     * Returns amount of ammunition.
     *
     * @return amount of ammunition.
     */
    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(final int ammunition) {
        this.ammunition = ammunition;
    }

    /**
     * Returns dollar amount of clothing owned by family.
     *
     * @return dollar amount of clothing owned by family.
     */
    public int getClothing() {
        return clothing;
    }

    public void setClothing(final int clothing) {
        this.clothing = clothing;
    }

    /**
     * Returns dollar amount of miscellaneous supplies owned by family.
     *
     * @return dollar amount of miscellaneous supplies owned by family.
     */
    public int getMisc() {
        return misc;
    }

    public void setMisc(final int misc) {
        this.misc = misc;
    }

    /**
     * Indicates whether the game is finished.
     *
     * @return <code>true</code> if the game is finished.
     */
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Returns the most recent tactics choice input value entered.
     *
     * @return the most recent tactics choice input value entered.
     */
    public int getTacticsChoice() {
        return tacticsChoice;
    }

    public void setTacticsChoice(int tacticsChoice) {
        this.tacticsChoice = tacticsChoice;
    }

    /**
     * Indicates whether the most recently encountered riders are hostile. It
     * appears that in the original BASIC code, S5 = 0 indicates riders are
     * hostile, and S5 = 1 indicates riders are NOT hostile.
     *
     * @return <code>true</code> if the most recently encountered riders are
     *         hostile.
     */
    public boolean isRidersHostile() {
        return ridersHostile;
    }

    public void setRidersHostile(boolean ridersHostile) {
        this.ridersHostile = ridersHostile;
    }

    /**
     * Returns the current value of the event generating counter.
     *
     * @return the current value of the event generating counter.
     */
    public int getEventGeneratingCounter() {
        return eventGeneratingCounter;
    }

    public void setEventGeneratingCounter(int eventGeneratingCounter) {
        this.eventGeneratingCounter = eventGeneratingCounter;
    }

    /**
     * Returns the random event number.
     *
     * @return the random event number.
     */
    public int getRandomEvent() {
        return randomEvent;
    }

    public void setRandomEvent(int randomEvent) {
        this.randomEvent = randomEvent;
    }

    /**
     * Returns the most recently selected choice for how well to eat:
     * <ol>
     * <li>Poorly</li>
     * <li>Moderately</li>
     * <li>Well</li>
     * </ol>
     * The value is initialized to -1 so that checks on value prompt for
     * entering a valid value.
     *
     * @return the most recently selected choice for how well to eat: 1, 2, or
     *         3.
     */
    public int getEatingChoice() {
        return eatingChoice;
    }

    public void setEatingChoice(final int eatingChoice) {
        this.eatingChoice = eatingChoice;
    }

    /**
     * Returns the user-entered shooting expertise level. The values are
     * <ol>
     * <li>Ace Marksman</li>
     * <li>Good Shot</li>
     * <li>Fair to Middlin'</li>
     * <li>Need More Practice</li>
     * <li>Shaky Knees</li>
     * </ol>
     *
     * @return the user-entered shooting expertise level.
     */
    public int getShootingExpertiseLevel() {
        return shootingExpertiseLevel;
    }

    public void setShootingExpertiseLevel(final int shootingExpertiseLevel) {
        this.shootingExpertiseLevel = shootingExpertiseLevel;
    }

}
