package biz.noip.johnwatne.oregontrail78.service;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Service class for executing game turns.
 *
 * @author John Watne
 *
 */
public class TurnService {
    private InputService inputService;
    private DeathService deathService;
    private GameStatus gameStatus;
    private StatusService statusService = new StatusService();
    private ShootingService shootingService;
    private RandomEventExecutionService randomEventExecutionService;
    private RiderEncounterService riderEncounterService;
    private SuccessfulFinishService successfulFinishService;

    private enum Actions {
        STOP_AT_FORT, HUNT, CONTINUE
    };

    /**
     * Constructs the TurnService with the classes needed to execute the turn.
     *
     * @param inputService
     *            the input processing service.
     * @param gameStatus
     *            the current game status.
     */
    public TurnService(final InputService inputService,
            final GameStatus gameStatus) {
        this.inputService = inputService;
        this.gameStatus = gameStatus;
        this.deathService = new DeathService(gameStatus, inputService);
        this.shootingService = new ShootingService(inputService, gameStatus);
        this.randomEventExecutionService = new RandomEventExecutionService(
                gameStatus, shootingService, deathService);
        this.riderEncounterService = new RiderEncounterService(gameStatus,
                inputService, shootingService, randomEventExecutionService,
                deathService);
        this.successfulFinishService = new SuccessfulFinishService(gameStatus);
    }

    /**
     * Executes a single turn, after calling code prints out the date.
     */
    public void executeTurn() {
        if (!gameStatus.isFinished()) {
            gameStatus.setFood(Math.max(0, gameStatus.getFood()));
            gameStatus.setAmmunition(Math.max(0, gameStatus.getAmmunition()));
            gameStatus.setClothing(Math.max(0, gameStatus.getClothing()));
            gameStatus.setMisc(Math.max(0, gameStatus.getMisc()));

            if (gameStatus.getFood() < Constants.LOW_FOOD_AMOUNT) {
                System.out.println(
                        "YOU'D BETTER DO SOME HUNTING OR BUY FOOD AND SOON!!!!");
            }

            gameStatus.setStartOfTurnTotalMileage(gameStatus.getTripMileage());

            if (gameStatus.isIll() || gameStatus.isInjured()) {
                gameStatus.setCash(gameStatus.getCash() - 20);

                if (gameStatus.getCash() < 0) {
                    deathService.cannotAffordDoctor();
                } else {
                    System.out.println("DOCTOR'S BILL IS $20");
                    gameStatus.setInjured(false);
                    gameStatus.setIll(false);
                }
            }

            if (gameStatus.isClearedSouthPassSettingMileage()) {
                System.out.println("TOTAL MILEAGE IS 950");
                gameStatus.setClearedSouthPassSettingMileage(false);
            } else {
                System.out.println(
                        "TOTAL MILEAGE IS " + gameStatus.getTripMileage());
            }

            statusService.printInventory(gameStatus);

            if (gameStatus.isAtFort()) {
                huntOrContinue();
            } else {
                gameStatus.setAtFort(true);
                stopAtFortHuntOrContinue();
            }
        }
    }

    /**
     * Checks whether the destination has been reached. If not, do setup for
     * next turn. Assumes game is not finished.
     */
    public void checkForGameFinishOrSetupForNextTurn() {
        if (gameStatus.getTripMileage() >= 2040) {
            successfulFinishService.finalTurn();
        } else {
            // Set date.
            gameStatus.setTurnNumber(gameStatus.getTurnNumber() + 1);
            System.out.println();
            System.out.println();
            System.out.print("MONDAY ");
            int turnNumber = gameStatus.getTurnNumber();
            printDateForNextTurn(turnNumber);
        }
    }

    /**
     * Prints date for start of next turn, or handles death if on trail for too
     * long.
     *
     * @param turnNumber
     *            the turn number for setting the date.
     */
    private void printDateForNextTurn(final int turnNumber) {
        String dateString = "";

        switch (turnNumber) {
        case 1:
            dateString = "APRIL 12 ";
            break;
        case 2:
            dateString = "APRIL 26 ";
            break;
        case 3:
            dateString = "MAY 10 ";
            break;
        case 4:
            dateString = "MAY 24 ";
            break;
        case 5:
            dateString = "JUNE 7 ";
            break;
        case 6:
            dateString = "JUNE 21 ";
            break;
        case 7:
            dateString = "JULY 5 ";
            break;
        case 8:
            dateString = "JULY 19 ";
            break;
        case 9:
            dateString = "AUGUST 2 ";
            break;
        case 10:
            dateString = "AUGUST 16 ";
            break;
        case 11:
            dateString = "AUGUST 31 ";
            break;
        case 12:
            dateString = "SEPTEMBER 13 ";
            break;
        case 13:
            dateString = "SEPTEMBER 27 ";
            break;
        case 14:
            dateString = "OCTOBER 11 ";
            break;
        case 15:
            dateString = "OCTOBER 25 ";
            break;
        case 16:
            dateString = "NOVEMBER 8 ";
            break;
        case 17:
            dateString = "NOVEMBER 22";
            break;
        case 18:
            dateString = "DECEMBER 6 ";
            break;
        case 19:
            dateString = "DECEMBER 20 ";
            break;
        default:
            System.out.println("YOU HAVE BEEN ON THE TRAIL TOO LONG  ------");
            System.out.println(
                    "YOUR FAMILY DIES IN THE FIRST BLIZZARD OF WINTER");
            deathService.makeDeathArrangements();
        }

        if (turnNumber <= 18) {
            // Turn not ended; continue.
            System.out.println(dateString + "1847");
            System.out.println();
        }
    }

    /**
     * Asks the user whether they want to stop at the next fort, hunt, or
     * continue, and stores the result.
     */
    private void stopAtFortHuntOrContinue() {
        System.out.println(
                "DO YOU WANT TO (1) STOP AT THE NEXT FORT, (2) HUNT, ");
        System.out.println("OR (3) CONTINUE");
        int action = inputService.getIntFromInput(3);
        Actions actionType = Actions.CONTINUE;

        if (action == 1) {
            actionType = Actions.STOP_AT_FORT;
        } else if (action == 2) {
            actionType = Actions.HUNT;
        }

        executeAction(actionType);

    }

    /**
     * Asks the user whether they want to hunt or continue, when stopping at the
     * next fort is not an option, and stores the result.
     */
    private void huntOrContinue() {
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("DO YOU WANT TO (1) HUNT, OR (2) CONTINUE");
            int action = inputService.getIntFromInput(3);
            Actions actionType = Actions.CONTINUE;

            if (1 == action) {
                actionType = Actions.HUNT;
            }

            if ((actionType == Actions.CONTINUE) || (gameStatus
                    .getAmmunition() > Constants.MINIMUM_HUNTING_AMMUNITION)) {
                validChoice = true;
                toggleAtFort();
                executeAction(actionType);
            } else {
                System.out
                        .println("TOUGH---YOU NEED MORE BULLETS TO GO HUNTING");
            }
        }
    }

    /**
     * Toggle the value returned by {@link GameStatus#isAtFort()}.
     */
    private void toggleAtFort() {
        gameStatus.setAtFort(!gameStatus.isAtFort());

    }

    /**
     * Execute the user's chosen stop at fort, hunt, or continue action.
     *
     * @param actionType
     *            the action to be performed.
     */
    private void executeAction(final Actions actionType) {
        switch (actionType) {
        case STOP_AT_FORT:
            stopAtFort();
            break;
        case HUNT:
            huntIfAble();
            break;
        case CONTINUE:
            /*
             * Do nothing aside from the continue option executed for all
             * choices.
             */
            break;
        default:
            throw new IllegalArgumentException(
                    "Invalid actionType: " + actionType);
        }

        if (!gameStatus.isFinished()) {
            checkForSufficientFood();
        }
    }

    /**
     * Stop at fort.
     */
    private void stopAtFort() {
        System.out.println("ENTER WHAT YOU WISH TO SPEND ON THE FOLLOWING");
        System.out.println("FOOD");
        int amountSpentAtFort = getAmountSpent(); // GOSUB
        gameStatus
                .setFood(gameStatus.getFood() + ((2 * amountSpentAtFort) / 3));
        System.out.println("AMMUNITION");
        amountSpentAtFort = getAmountSpent();
        gameStatus.setAmmunition(gameStatus.getAmmunition()
                + (2 * amountSpentAtFort / 3) * Constants.BULLETS_PER_DOLLAR);
        System.out.println("CLOTHING");
        amountSpentAtFort = getAmountSpent();
        gameStatus.setClothing(
                gameStatus.getClothing() + (2 * amountSpentAtFort) / 3);
        System.out.println("MISCELLANEOUS SUPPLIES");
        amountSpentAtFort = getAmountSpent();
        gameStatus.setMisc(gameStatus.getMisc() + (2 * amountSpentAtFort) / 3);
        gameStatus.setTripMileage(gameStatus.getTripMileage()
                - Constants.REDUCED_MILEAGE_FOR_NOT_CHOOSING_CONTINUE);
    }

    /**
     * Prompts the user for the amount to spend and confirms that it is not more
     * than the cash available.
     *
     * @return the amount spent on the item, or 0 if the entered amount is more
     *         than the cash available.
     */
    private int getAmountSpent() {
        int amountSpent = Math.max(0, inputService.getIntFromInput(0));

        if (amountSpent > 0) {
            gameStatus.setCash(gameStatus.getCash() - amountSpent);

            if (gameStatus.getCash() < 0) {
                System.out.println(
                        "YOU DON'T HAVE THAT MUCH--KEEP YOUR SPENDING DOWN");
                gameStatus.setCash(gameStatus.getCash() + amountSpent);
                amountSpent = 0;
            }
        }

        return amountSpent;
    }

    /**
     * Hunt if there is sufficient ammunition on hand. Otherwise, tell the user
     * they don't have enough bullets, and prompt for a different action to take
     * on the turn.
     */
    private void huntIfAble() {
        if (gameStatus.getAmmunition() > Constants.MINIMUM_HUNTING_AMMUNITION) {
            hunt();
        } else {
            System.out.println("TOUGH---YOU NEED MORE BULLETS TO GO HUNTING");
            stopAtFortHuntOrContinue();
        }
    }

    /**
     * Go hunting.
     */
    private void hunt() {
        gameStatus.setTripMileage(gameStatus.getTripMileage()
                - Constants.REDUCED_MILEAGE_FOR_NOT_CHOOSING_CONTINUE);
        int actualResponseTime = shootingService.getShootingResult(); // GOSUB

        if (actualResponseTime <= 1) {
            rightBetweenTheEyesHuntingResult();
        } else if (100.0 * Math.random() < 13.0 * actualResponseTime) {
            missedHuntingResult();
        } else {
            gameStatus.setFood(
                    gameStatus.getFood() + Constants.MAX_HUNTING_FOOD_AMOUNT
                            - Constants.RESPONSE_TIME_FOOD_REDUCTION
                                    * actualResponseTime);
            System.out.println(
                    "NICE SHOT--RIGHT ON TARGET--GOOD EATIN' TONIGHT!!");
            gameStatus.setAmmunition(gameStatus.getAmmunition()
                    - Constants.MIN_HUNTING_AMMUNITION_USED
                    - Constants.RESPONSE_TIME_ADDITIONAL_AMMUNITION
                            * actualResponseTime);
        }

        checkForSufficientFood();
    }

    /**
     * Show the message and process the case of getting the &quot;RIGHT BETWEEN
     * THE EYES&quot; hunting result.
     */
    private void rightBetweenTheEyesHuntingResult() {
        System.out.println("RIGHT BETWEEN THE EYES---YOU GOT A BIG ONE!!!!");
        System.out.println("FULL BELLIES TONIGHT!");
        gameStatus.setFood(gameStatus.getFood()
                + Constants.RIGHT_BETWEEN_EYES_MIN_FOOD_AMOUNT
                + (int) (Constants.MAX_RANDOM_RIGHT_BETWEEN_EYES_FOOD_AMOUNT
                        * Math.random()));
        gameStatus.setAmmunition(gameStatus.getAmmunition()
                - Constants.RIGHT_BETWEEN_EYES_MIN_AMMUNITION
                - (int) (Constants.RIGHT_BETWEEN_EYES_MAX_RANDOM_AMMUNITION
                        * Math.random()));
    }

    /**
     * Show the message for missing the prey while hunting. No food added to
     * inventory.
     */
    private void missedHuntingResult() {
        System.out.println("YOU MISSED---AND YOUR DINNER GOT AWAY.....");
    }

    /**
     * Check whether the party has sufficient food and process accordingly.
     */
    private void checkForSufficientFood() {
        if (gameStatus.getFood() >= Constants.LOW_FOOD_AMOUNT) {
            setFoodConsumptionAndCheckForRiderAttack();
        } else {
            deathService.starveToDeath();
        }
    }

    /**
     * Prompt for the food consumption for the turn, then check whether riders
     * approach, and process the encounter if so.
     */
    private void setFoodConsumptionAndCheckForRiderAttack() {
        int amountConsumedPerTurn = Constants.MIN_FOOD_CONSUMED_PER_TURN;
        final int initialFood = gameStatus.getFood();

        do {
            System.out
                    .println("DO YOU WANT TO EAT (1) POORLY   (2) MODERATELY");
            System.out.println("OR (3) WELL");
            int eatingChoice = -1; // Invalid amount.

            while ((eatingChoice < 1) || (eatingChoice > 3)) {
                eatingChoice = inputService.getIntInRangeFromInput(eatingChoice,
                        -1, 3);
            }

            gameStatus.setEatingChoice(eatingChoice);
            amountConsumedPerTurn = Constants.MIN_FOOD_CONSUMED_PER_TURN
                    + Constants.ADDITIONAL_FOOD_CONSUMED_PER_EATING_CHOICE
                            * eatingChoice;

            if (amountConsumedPerTurn > initialFood) {
                System.out.println("YOU CAN'T EAT THAT WELL");
            }
        } while (amountConsumedPerTurn > initialFood);

        gameStatus.setFood(initialFood - amountConsumedPerTurn);
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() + Constants.BASE_MILEAGE_PER_TURN
                        + (int) ((gameStatus.getAnimals()
                                - Constants.ANIMAL_MILEAGE_REDUCTION)
                                / Constants.ANIMAL_MILEAGE_DIVISOR
                                + Constants.MAX_RANDOM_MILEAGE_PER_TURN
                                        * Math.random()));
        gameStatus.setBlizzard(false);
        gameStatus.setInsufficientColdWeatherClothing(false);
        final double tripMileageFactor =
                Math.pow((gameStatus.getTripMileage() - 4.0), 2.0);
        final double riderAttackChanceValue = 10.0 * Math.random();
        final double riderAttackCutoff =
                (tripMileageFactor + 72.0) / (tripMileageFactor + 12.0) - 1.0;
        System.out.println();

        // Riders attack
        if (riderAttackChanceValue > riderAttackCutoff) {
            randomEventExecutionService.chooseRandomEventForTurn();
        } else {
            riderEncounterService.handleRiderEncounter();
        }
    }

}
