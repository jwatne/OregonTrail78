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
    Long turnNumber = Constants.ZERO_LONG;

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

}
