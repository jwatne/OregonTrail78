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
            finalTurn();
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
     * Final turn - successfully completed journey.
     */
    private void finalTurn() {
        final int startOfTurnTotalMileage =
                gameStatus.getStartOfTurnTotalMileage();
        final int lastTurnMileage = (2040 - startOfTurnTotalMileage)
                / (gameStatus.getTripMileage() - startOfTurnTotalMileage);
        gameStatus.setFood(gameStatus.getFood() + (1 - lastTurnMileage)
                * (8 + 5 * gameStatus.getEatingChoice()));
        System.out.println();
        System.out.println("YOU FINALLY ARRIVED AT OREGON CITY");
        System.out.println("AFTER 2040 LONG MILES---HOORAY!!!!!");
        System.out.println();
        int lastDay = lastTurnMileage * 14;
        int turnNumber = gameStatus.getTurnNumber();
        turnNumber *= 14;
        turnNumber += lastDay;
        gameStatus.setTurnNumber(turnNumber);
        lastDay++;

        if (lastDay >= 8) {
            lastDay -= 7;
        }

        printDayOfWeekForFinalTurn(lastDay);

        if (turnNumber > 124) {
            printIfAugustDate();
        } else {
            turnNumber -= 93;
            gameStatus.setTurnNumber(turnNumber);
            System.out.println("JULY " + turnNumber + " 1847");
            printFinalInventoryAndCongratulations();
        }
    }

    /**
     * Prints the day of the week for the final turn.
     *
     * @param lastDay
     *            the day of week value for determining the day of week text.
     */
    private void printDayOfWeekForFinalTurn(final int lastDay) {
        String day = "";

        switch (lastDay) {
        case 1:
            day = "MONDAY ";
            break;
        case 2:
            day = "TUESDAY ";
            break;
        case 3:
            day = "WEDNESDAY ";
            break;
        case 4:
            day = "THURSDAY ";
            break;
        case 5:
            day = "FRIDAY ";
            break;
        case 6:
            day = "SATURDAY ";
            break;
        case 7:
            day = "SUNDAY ";
            break;
        default:
            throw new IllegalArgumentException("Invalid lastDay: " + lastDay);
        }

        System.out.print(day);
    }

    /**
     * Checks if the turn number represents a date in August. If so, print that
     * date. Otherwise, continue checking which month the turn number falls
     * into.
     */
    private void printIfAugustDate() {
        int turnNumber = gameStatus.getTurnNumber();

        if (turnNumber > 155) {
            printIfSeptemberDate();
        } else {
            turnNumber -= 124;
            gameStatus.setTurnNumber(turnNumber);
            System.out.println("AUGUST " + turnNumber + " 1847");
            printFinalInventoryAndCongratulations();
        }
    }

    /**
     * Checks if the turn number represents a date in September. If so, print
     * that date. Otherwise, continue checking which month the turn number falls
     * into.
     */
    private void printIfSeptemberDate() {
        int turnNumber = gameStatus.getTurnNumber();

        if (turnNumber > 185) {
            printIfOctoberDate();
        } else {
            turnNumber -= 155;
            gameStatus.setTurnNumber(turnNumber);
            System.out.println("SEPTEMBER " + turnNumber + " 1847");
            printFinalInventoryAndCongratulations();
        }

    }

    /**
     * Checks if the turn number represents a date in October. If so, print that
     * date. Otherwise, continue checking which month the turn number falls
     * into.
     */
    private void printIfOctoberDate() {
        int turnNumber = gameStatus.getTurnNumber();

        if (turnNumber > 216) {
            printNovemberDate();
        } else {
            turnNumber -= 185;
            gameStatus.setTurnNumber(turnNumber);
            System.out.println("OCTOBER " + turnNumber + " 1847");
            printFinalInventoryAndCongratulations();
        }
    }

    /**
     * Prints the date in November that the turn number falls into.
     */
    private void printNovemberDate() {
        gameStatus.setTurnNumber(gameStatus.getTurnNumber() - 216);
        System.out.println("NOVEMBER " + gameStatus.getTurnNumber() + " 1847");
        printFinalInventoryAndCongratulations();
    }

    /**
     * Prints the final inventory at the end of the game and a congratulatory
     * message from the President.
     */
    private void printFinalInventoryAndCongratulations() {
        System.out.println();
        System.out.println("FOOD\tBULLETS\tCLOTHING\tMISC. SUPP.\tCASH");
        gameStatus.setAmmunition(Math.max(0, gameStatus.getAmmunition()));
        gameStatus.setClothing(Math.max(0, gameStatus.getClothing()));
        gameStatus.setMisc(Math.max(0, gameStatus.getMisc()));
        gameStatus.setCash(Math.max(0, gameStatus.getCash()));
        gameStatus.setFood(Math.max(0, gameStatus.getFood()));
        System.out.println(
                "" + gameStatus.getFood() + "\t" + gameStatus.getAmmunition()
                        + "\t" + gameStatus.getClothing() + "\t\t"
                        + gameStatus.getMisc() + "\t\t" + gameStatus.getCash());
        System.out.println();
        System.out.println("PRESIDENT JAMES K. POLK SENDS YOU HIS");
        System.out.println("      HEARTIEST CONGRATULATIONS");
        System.out.println();
        System.out.println("           AND WISHES YOU A PROSPEROUS LIFE AHEAD");
        System.out.println();
        System.out.println("                      AT YOUR NEW HOME");
        gameStatus.setFinished(true);
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

        checkForSufficientFood();
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
            chooseRandomEventForTurn();
        } else {
            gameStatus.setRidersHostile(true);
            System.out.print("RIDERS AHEAD.  THEY ");
            // It appears that in original BASIC code:
            // S5 = 0 indicates hostile
            // S5 = 1 indicates not hostile

            if (Math.random() >= 0.8) {
                System.out.print("DON'T ");
                gameStatus.setRidersHostile(false);
            }

            System.out.println("LOOK HOSTILE");
            System.out.println("TACTICS");
            gameStatus.setTacticsChoice(0);
            int choice = gameStatus.getTacticsChoice();

            do {
                System.out.println(
                        "(1) RUN  (2) ATTACK  (3) CONTINUE  (4) CIRCLE WAGONS");
                System.out.println(
                        "IF YOU RUN YOU'LL GAIN TIME BUT WEAR DOWN YOUR OXEN");
                System.out.println("IF YOU CIRLE YOU'LL LOSE TIME");

                if (Math.random() <= 0.2) {
                    gameStatus.setRidersHostile(!gameStatus.isRidersHostile());
                }

                gameStatus
                        .setTacticsChoice(inputService.getIntFromInput(choice));
                choice = gameStatus.getTacticsChoice();
            } while ((choice < 1) || (choice > 4));

            if (!gameStatus.isRidersHostile()) {
                handleNonHostileRiders();
            } else if (choice > 1) {
                attackContinueOrCircleWagons();
            } else {
                gameStatus.setTripMileage(gameStatus.getTripMileage() + 20);
                gameStatus.setMisc(gameStatus.getMisc() - 15);
                gameStatus.setAmmunition(gameStatus.getAmmunition() - 150);
                gameStatus.setAnimals(gameStatus.getAnimals() - 40);
                determineIfRidersActuallyHostile();
            }
        }
    }

    /**
     * Attack, continue, or circle wagons in response to riders appearing.
     */
    private void attackContinueOrCircleWagons() {
        if (gameStatus.getTacticsChoice() > 2) {
            continueOrCircleWagons();
        } else {
            final int actualResponseTime = shootingService.getShootingResult();
            gameStatus.setAmmunition(
                    gameStatus.getAmmunition() - actualResponseTime * 40 - 80);
            checkResultsOfShootingAtRiders(actualResponseTime);
        }
    }

    /**
     * Checks the result of shooting at the riders.
     *
     * @param actualResponseTime
     *            the actual [adjusted] response time in &quot;shooting&quot;.
     */
    private void checkResultsOfShootingAtRiders(final int actualResponseTime) {
        if (actualResponseTime > 1) {
            checkWhetherBadOrMediocreShot(actualResponseTime);
        } else {
            System.out.println("NICE SHOOTING---YOU DROVE THEM OFF");
            determineIfRidersActuallyHostile();
        }
    }

    /**
     * Given that the result of shooting at the riders was NOT &quot;NICE
     * SHOOTING&quot;, handle either being &quot;KINDA SLOW&quot; or a
     * &quot;LOUSY SHOT&quot;.
     *
     * @param actualResponseTime
     *            the actual [adjusted] response time in &quot;shooting&quot;.
     */
    private void checkWhetherBadOrMediocreShot(final int actualResponseTime) {
        if (actualResponseTime <= 4) {
            kindaSlowShooting();
        } else {
            System.out.println("LOUSY SHOT---YOU GOT KNIFED");
            gameStatus.setInjured(true);
            System.out.println("YOU HAVE TO SEE OL' DOC BLANCHARD");
            determineIfRidersActuallyHostile();
        }
    }

    /**
     * Show &quot;KINDA SLOW&quot; message and continue.
     */
    private void kindaSlowShooting() {
        System.out.println("KINDA SLOW WITH YOUR COLT .45");
        determineIfRidersActuallyHostile();
    }

    /**
     * Handle continue or circle wagons strategies in response to riders.
     */
    private void continueOrCircleWagons() {
        if (gameStatus.getTacticsChoice() > 3) {
            circleWagons();
        } else if (Math.random() > 0.8) {
            ridersDoNotAttack();
        } else {
            gameStatus.setAmmunition(gameStatus.getAmmunition() - 150);
            gameStatus.setMisc(gameStatus.getMisc() - 15);
            determineIfRidersActuallyHostile();
        }

    }

    /**
     * Circle wagons in response to riders.
     */
    private void circleWagons() {
        final int actualResponseTime = shootingService.getShootingResult();
        gameStatus.setAmmunition(
                gameStatus.getAmmunition() - actualResponseTime * 30 - 80);
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 25);
        checkResultsOfShootingAtRiders(actualResponseTime);
    }

    /**
     * Process results for non-hostile riders encountered.
     */
    private void handleNonHostileRiders() {
        if (gameStatus.getTacticsChoice() > 1) {
            handleNonRunningStrategies();
        } else {
            gameStatus.setTripMileage(gameStatus.getTripMileage() + 15);
            gameStatus.setAnimals(gameStatus.getAnimals() - 10);
            determineIfRidersActuallyHostile();
        }
    }

    /**
     * Process results when choosing a strategy other than running in response
     * to non-hostile riders.
     */
    private void handleNonRunningStrategies() {
        if (gameStatus.getTacticsChoice() > 2) {
            nonHostileContinueOrCircleWagons();
        } else {
            gameStatus.setTripMileage(gameStatus.getTripMileage() - 5);
            gameStatus.setAmmunition(gameStatus.getAmmunition() - 100);
            determineIfRidersActuallyHostile();
        }
    }

    /**
     * Process results when choosing to continue or circle wagons in response to
     * non-hostile riders.
     */
    private void nonHostileContinueOrCircleWagons() {
        if (gameStatus.getTacticsChoice() > 3) {
            nonHostileCircleWagons();
        } else {
            determineIfRidersActuallyHostile();
        }
    }

    /**
     * Process results when choosing to circle wagons in response to non-hostile
     * riders.
     */
    private void nonHostileCircleWagons() {
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 20);
        determineIfRidersActuallyHostile();
    }

    /**
     * Handle the case where riders do not attack.
     */
    private void ridersDoNotAttack() {
        System.out.println("THEY DID NOT ATTACK");
        chooseRandomEventForTurn();
    }

    /**
     * Indicates whether riders were actually hostile and processes results
     * appropriately.
     */
    private void determineIfRidersActuallyHostile() {
        if (gameStatus.isRidersHostile()) {
            ridersWereHostile();
        } else {
            System.out.println(
                    "RIDERS WERE FRIENDLY, BUT CHECK FOR POSSIBLE LOSSES");
            chooseRandomEventForTurn();
        }

    }

    /**
     * Do follow-up when riders were actually hostile.
     */
    private void ridersWereHostile() {
        System.out.println("RIDERS WERE HOSTILE--CHECK FOR LOSSES");

        if (gameStatus.getAmmunition() >= 0) {
            chooseRandomEventForTurn();
        } else {
            System.out.println(
                    "YOU RAN OUT OF BULLETS AND GOT MASSACRED BY THE RIDERS");
            deathService.makeDeathArrangements();
        }
    }

    /**
     * Chooses a random event to happen during the current turn.
     */
    private void chooseRandomEventForTurn() {
        // Selection of events
        gameStatus.setEventGeneratingCounter(0);
        // Line 2505 RESTORE ????
        gameStatus.setRandomEvent((int) (100 * Math.random()));
        executeRandomEvent();
    }

    /**
     * Executes the chosen random event.
     */
    private void executeRandomEvent() {
        gameStatus.setEventGeneratingCounter(
                gameStatus.getEventGeneratingCounter() + 1);

        if (gameStatus.getEventGeneratingCounter() == 16) {
            randomEventExecutionService.helpfulIndiansShowWhereToFindFood();
        } else {
            int randomEventData = RandomEventDataService.getNextValue();

            if (gameStatus.getRandomEvent() > randomEventData) {
                executeRandomEvent();
            } else {
                int counter = gameStatus.getEventGeneratingCounter();
                randomEventExecutionService
                        .executeEventForCounterValue(counter);
            }
        }
    }

}
