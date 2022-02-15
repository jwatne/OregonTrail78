package biz.noip.johnwatne.oregontrail78.model;

import biz.noip.johnwatne.oregontrail78.service.Constants;

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
    Long TripMileage = Constants.ZERO_LONG;
    boolean clearedSouthPassSettingMileage = false;
    Long cash = -1L;
    Long animals = Constants.ZERO_LONG;
    Long turnNumber = Constants.ZERO_LONG;
    Long food;
    Long ammunition;
    Long clothing;
    Long misc;

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
     * Returns the total mileage for the whole trip.
     *
     * @return the total mileage for the whole trip.
     */
    public Long getTripMileage() {
        return TripMileage;
    }

    public void setTripMileage(Long tripMileage) {
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
     * Returns the turn number for setting the date.
     *
     * @return the turn number for setting the date.
     */
    public Long getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Long turnNumber) {
        this.turnNumber = turnNumber;
    }

    /**
     * Returns the cash currently held by the family.
     *
     * @return the cash currently held by the family.
     */
    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    /**
     * Returns current dollar value of animals owned by family.
     *
     * @return current dollar value of animals owned by family.
     */
    public Long getAnimals() {
        return animals;
    }

    public void setAnimals(Long animals) {
        this.animals = animals;
    }

    /**
     * Returns current dollar value of food for family.
     *
     * @return current dollar value of food for family.
     */
    public Long getFood() {
        return food;
    }

    public void setFood(final Long food) {
        this.food = food;
    }

    /**
     * Returns amount of ammunition.
     *
     * @return amount of ammunition.
     */
    public Long getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(final Long ammunition) {
        this.ammunition = ammunition;
    }

    /**
     * Returns dollar amount of clothing owned by family.
     *
     * @return dollar amount of clothing owned by family.
     */
    public Long getClothing() {
        return clothing;
    }

    public void setClothing(final Long clothing) {
        this.clothing = clothing;
    }

    /**
     * Returns dollar amount of miscellaneous supplies owned by family.
     *
     * @return dollar amount of miscellaneous supplies owned by family.
     */
    public Long getMisc() {
        return misc;
    }

    public void setMisc(final Long misc) {
        this.misc = misc;
    }

}
