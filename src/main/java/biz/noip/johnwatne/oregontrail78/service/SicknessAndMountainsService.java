package biz.noip.johnwatne.oregontrail78.service;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Services for dealing with sickness during a turn, and determining whether
 * specific mountains or passes have been reached.
 *
 * @author John Watne
 *
 */
public class SicknessAndMountainsService {
    private GameStatus gameStatus;
    private DeathService deathService;

    public SicknessAndMountainsService(final GameStatus gameStatus,
            final DeathService deathService) {
        this.gameStatus = gameStatus;
        this.deathService = deathService;
    }

    /**
     * Determines if mild, bad, or serious illness, and handles appropriately.
     */
    public void handleSickness() {
        final int eatingChoiceFactor = gameStatus.getEatingChoice() - 1;

        if (100.0 * Math.random() < 10.0 + 35.0 * eatingChoiceFactor) {
            handleMildIllness();
        } else if (100.0 * Math.random() < 100.0
                - (40.0 / Math.pow(4.0, eatingChoiceFactor))) {
            handleBadIllness();
        } else {
            System.out.println("SERIOUS ILLNESS---");
            System.out.println("YOU MUST STOP FOR MEDICAL ATTENTION");
            gameStatus.setMisc(gameStatus.getMisc() - 10);
            gameStatus.setIll(true);
            finishTurnWithIllness();
        }
    }

    /**
     * Determine whether the party cleared South Pass when setting the mileage.
     */
    public void determineIfClearedSouthPassSettingMileage() {
        final int tripMileage = gameStatus.getTripMileage();
        gameStatus.setClearedSouthPassSettingMileage(tripMileage <= 950);
        // End this turn. Execute line 700.
        // Temp debugging print:
        System.out.println("Current trip mileage: " + tripMileage);
    }

    /**
     * Determines whether or not the trip mileage has reached the amount to be
     * in the mountains, and ends the turn accordingly.
     */
    public void checkIfAtMountains() {
        final int tripMileage = gameStatus.getTripMileage();

        if (tripMileage > 950) {
            if (10.0 * Math.random() > 9.0
                    - (Math.pow(tripMileage / 100.0 - 15.0, 2.0) + 72.0)
                            / (Math.pow(tripMileage / 100.0 - 15.0, 2.0)
                                    + 12.0)) {
                clearSouthPass();
            } else {
                System.out.println("RUGGED MOUNTAINS");

                if (Math.random() > 0.1) {
                    determineIfWagonDamaged();
                } else {
                    System.out.println(
                            "YOU GOT LOST---LOSE VALUABLE TIME TRYING TO FIND TRAIL!");
                    gameStatus.setTripMileage(tripMileage - 60);
                    clearSouthPass();
                }
            }
        }
    }

    /**
     * Randomly determines if the wagon is damaged and continues accordingly.
     */
    private void determineIfWagonDamaged() {
        if (Math.random() > 0.11) {
            goingGetsSlow();
        } else {
            System.out.println("WAGON DAMAGED!---LOSE TIME AND SUPPLIES");
            gameStatus.setMisc(gameStatus.getMisc() - 5);
            gameStatus.setAmmunition(gameStatus.getAmmunition() - 200);
            gameStatus.setTripMileage(gameStatus.getTripMileage() - 20
                    - (int) (30 * Math.random()));
            clearSouthPass();
        }
    }

    /**
     * Regular &quot;going gets slow&quot; slow-down in mountains when wagon NOT
     * damaged.
     */
    private void goingGetsSlow() {
        System.out.println("THE GOING GETS SLOW");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 45
                - (int) (Math.random() / 0.02));
        clearSouthPass();
    }

    /**
     * Determines whether or not cleared South Pass on a previous turn and
     * processes accordingly. If not previously cleared, indicate making it
     * safely through South Pass and set indicator value.
     */
    private void clearSouthPass() {
        if (gameStatus.isClearedSouthPass()) {
            clearBlueMountains();
        } else {
            gameStatus.setClearedSouthPass(true);

            if (Math.random() < 0.8) {
                blizardInMountainPass();
            } else {
                System.out.println(
                        "YOU MADE IT SAFELY THROUGH SOUTH PASS--NO SNOW");
                clearBlueMountains();
            }
        }
    }

    /**
     * Determines whether or not cleared Blue Mountains on a previous turn and
     * processes accordingly. No indication is made of this, but it determines
     * which options may appear at this point. If not previously cleared, set
     * indicator value.
     */
    private void clearBlueMountains() {
        if ((gameStatus.getTripMileage() < 1700)
                || gameStatus.isClearedBlueMountains()) {
            determineIfClearedSouthPassSettingMileage();
        } else {
            gameStatus.setClearedBlueMountains(true);

            if (Math.random() < 0.7) {
                blizardInMountainPass();
            } else {
                determineIfClearedSouthPassSettingMileage();
            }
        }
    }

    /**
     * Blizzard in mountain pass event.
     */
    private void blizardInMountainPass() {
        System.out.println("BLIZZARD IN MOUNTAIN PASS-TIME AND SUPPLIES LOST");
        gameStatus.setBlizzard(true);
        gameStatus.setFood(gameStatus.getFood() - 25);
        gameStatus.setMisc(gameStatus.getMisc() - 10);
        gameStatus.setAmmunition(gameStatus.getAmmunition() - 300);
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 30 - (int) (40 * Math.random()));

        if (gameStatus.getClothing() < 18 + (int) (2 * Math.random())) {
            handleSickness();
        } else {
            determineIfClearedSouthPassSettingMileage();
        }
    }

    /**
     * Handles a mild illness.
     */
    private void handleMildIllness() {
        System.out.println("MILD ILLNESS---MEDICINE USED");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 5);
        gameStatus.setMisc(gameStatus.getMisc() - 2);
        finishTurnWithIllness();
    }

    /**
     * Handles a bad illness.
     */
    private void handleBadIllness() {
        System.out.println("BAD ILLNESS---MEDICINE USED");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 5);
        gameStatus.setMisc(gameStatus.getMisc() - 5);
        finishTurnWithIllness();
    }

    /**
     * Finishes a turn when illness occurred during the turn.
     */
    private void finishTurnWithIllness() {
        if (gameStatus.getMisc() < 0) {
            deathService.runOutOfMedicalSupplies();
        } else if (gameStatus.isBlizzard()) {
            determineIfClearedSouthPassSettingMileage();
        } else {
            checkIfAtMountains();
        }
    }
}
