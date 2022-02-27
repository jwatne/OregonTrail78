package biz.noip.johnwatne.oregontrail78.service;

import java.time.Duration;
import java.time.Instant;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Service class for executing game turns.
 *
 * @author John Watne
 *
 */
public class TurnService {
    private InputService inputService;
    private GameStatus gameStatus;
    private StatusService statusService = new StatusService();
    private static final String[] SHOOTING_WORDS =
            {"BANG", "BLAM", "POW", "WHAM"};
    final private static int MAX_RESPONSE_TIME = 8;

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
                    line3520();
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
                System.out
                        .println("YOU HAVE BEEN ON THE TRAIL TOO LONG  ------");
                System.out.println(
                        "YOUR FAMILY DIES IN THE FIRST BLIZZARD OF WINTER");
                line3600();
            }

            if (turnNumber <= 18) {
                // Turn not ended; continue.
                System.out.println(dateString + "1847");
                System.out.println();
            }
        }
    }

    /**
     * Final turn - successfully completed journey.
     */
    public void finalTurn() {
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
     * Checks if the turn number represents a date in August. If so, print that
     * date. Otherwise, continue checking which month the turn number falls
     * into.
     */
    public void printIfAugustDate() {
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
    public void printIfSeptemberDate() {
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
    public void printIfOctoberDate() {
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
    public void printNovemberDate() {
        gameStatus.setTurnNumber(gameStatus.getTurnNumber() - 216);
        System.out.println("NOVEMBER " + gameStatus.getTurnNumber() + " 1847");
        printFinalInventoryAndCongratulations();
    }

    /**
     * Prints the final inventory at the end of the game and a congratulatory
     * message from the President.
     */
    public void printFinalInventoryAndCongratulations() {
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
    public void stopAtFortHuntOrContinue() {
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
    public void huntOrContinue() {
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
    public void toggleAtFort() {
        gameStatus.setAtFort(!gameStatus.isAtFort());

    }

    /**
     * Execute the user's chosen stop at fort, hunt, or continue action.
     *
     * @param actionType
     *            the action to be performed.
     */
    public void executeAction(final Actions actionType) {
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
    public void stopAtFort() {
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
    public int getAmountSpent() {
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
    public void huntIfAble() {
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
    public void hunt() {
        gameStatus.setTripMileage(gameStatus.getTripMileage()
                - Constants.REDUCED_MILEAGE_FOR_NOT_CHOOSING_CONTINUE);
        int actualResponseTime = line4500(); // GOSUB

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
    public void rightBetweenTheEyesHuntingResult() {
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
    public void missedHuntingResult() {
        System.out.println("YOU MISSED---AND YOUR DINNER GOT AWAY.....");
    }

    /**
     * Check whether the party has sufficient food and process accordingly.
     */
    public void checkForSufficientFood() {
        if (gameStatus.getFood() >= Constants.LOW_FOOD_AMOUNT) {
            setFoodConsumptionAndCheckForBanditAttack();
        } else {
            line3500();
        }
    }

    /**
     * Prompt for the food consumption for the turn, then check whether riders
     * approach, and process the encounter if so.
     */
    public void setFoodConsumptionAndCheckForBanditAttack() {
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
    public void attackContinueOrCircleWagons() {
        if (gameStatus.getTacticsChoice() > 2) {
            continueOrCircleWagons();
        } else {
            final int actualResponseTime = line4500();
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
    public void checkResultsOfShootingAtRiders(final int actualResponseTime) {
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
    public void checkWhetherBadOrMediocreShot(final int actualResponseTime) {
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
    public void kindaSlowShooting() {
        System.out.println("KINDA SLOW WITH YOUR COLT .45");
        determineIfRidersActuallyHostile();
    }

    /**
     * Handle continue or circle wagons strategies in response to riders.
     */
    public void continueOrCircleWagons() {
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
    public void circleWagons() {
        final int actualResponseTime = line4500();
        gameStatus.setAmmunition(
                gameStatus.getAmmunition() - actualResponseTime * 30 - 80);
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 25);
        checkResultsOfShootingAtRiders(actualResponseTime);
    }

    /**
     * Process results for non-hostile riders encountered.
     */
    public void handleNonHostileRiders() {
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
    public void handleNonRunningStrategies() {
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
    public void nonHostileContinueOrCircleWagons() {
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
    public void nonHostileCircleWagons() {
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 20);
        determineIfRidersActuallyHostile();
    }

    /**
     * Handle the case where riders do not attack.
     */
    public void ridersDoNotAttack() {
        System.out.println("THEY DID NOT ATTACK");
        chooseRandomEventForTurn();
    }

    /**
     * Indicates whether riders were actually hostile and processes results
     * appropriately.
     */
    public void determineIfRidersActuallyHostile() {
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
    public void ridersWereHostile() {
        System.out.println("RIDERS WERE HOSTILE--CHECK FOR LOSSES");

        if (gameStatus.getAmmunition() >= 0) {
            chooseRandomEventForTurn();
        } else {
            System.out.println(
                    "YOU RAN OUT OF BULLETS AND GOT MASSACRED BY THE RIDERS");
            line3600();
        }
    }

    /**
     * Chooses a random event to happen during the current turn.
     */
    public void chooseRandomEventForTurn() {
        // Selection of events
        gameStatus.setEventGeneratingCounter(0);
        // Line 2505 RESTORE ????
        gameStatus.setRandomEvent((int) (100 * Math.random()));
        executeRandomEvent();
    }

    /**
     * Executes the chosen random event.
     */
    public void executeRandomEvent() {
        gameStatus.setEventGeneratingCounter(
                gameStatus.getEventGeneratingCounter() + 1);

        if (gameStatus.getEventGeneratingCounter() == 16) {
            line3020();
        } else {
            int randomEventData = RandomEventDataService.getNextValue();

            if (gameStatus.getRandomEvent() > randomEventData) {
                executeRandomEvent();
            } else {
                switch (gameStatus.getEventGeneratingCounter()) {
                case 0:
                    wagonBreaksDown();
                    break;
                case 1:
                    oxInjuresLeg();
                    break;
                case 2:
                    daughterBreaksArm();
                    break;
                case 3:
                    oxWandersOff();
                    break;
                case 4:
                    sonGetsLost();
                    break;
                case 5:
                    unsafeWater();
                    break;
                case 6:
                    heavyRainsIfBeforeMountains();
                    break;
                case 7:
                    banditsAttack();
                    break;
                case 8:
                    wagonFire();
                    break;
                case 9:
                    lostInFog();
                    break;
                case 10:
                    snakeBite();
                    break;
                case 11:
                    wagonSwamped();
                    break;
                case 12:
                    wildAnimalAttack();
                    break;
                case 13:
                    hailStorm();
                    break;
                case 14:
                    setEatingChoice();
                    break;
                case 15:
                    line3020();
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Invalid gameStatus.getEventGeneratingCounter() value: "
                                    + gameStatus.getEventGeneratingCounter());
                }
            }
        }
    }

    /**
     * Wagon breaks down event.
     */
    public void wagonBreaksDown() {
        System.out
                .println("WAGON BREAKS DOWN--LOSE TIME AND SUPPLIES FIXING IT");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 15 - (int) (5 * Math.random()));
        gameStatus.setMisc(gameStatus.getMisc() - 8);
        line3100();
    }

    /**
     * Ox injures leg event.
     */
    public void oxInjuresLeg() {
        System.out.println("OX INJURES LEG---SLOWS YOU DOWN REST OF TRIP");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 25);
        gameStatus.setAnimals(gameStatus.getAnimals() - 20);
        line3100();
    }

    /**
     * Daughter breaks arm event.
     */
    public void daughterBreaksArm() {
        System.out.println("BAD LUCK---YOUR DAUGHTER BROKE HER ARM");
        System.out.println("YOU HAD TO STOP AND USE SUPPLIES TO MAKE A SLING");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 5 - (int) (4 * Math.random()));
        gameStatus
                .setMisc(gameStatus.getMisc() - 2 - (int) (3 * Math.random()));
        line3100();
    }

    /**
     * Ox wanders off event.
     */
    public void oxWandersOff() {
        System.out.println("OX WANDERS OFF---SPEND TIME LOOKING FOR IT");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 17);
        line3100();
    }

    /**
     * Son gets lost event.
     */
    public void sonGetsLost() {
        System.out.println(
                "YOUR SON GETS LOST---SPEND HALF THE DAY LOKING FOR HIM");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 10);
        line3100();
    }

    /**
     * Unsafe water event.
     */
    public void unsafeWater() {
        System.out.println("UNSAFE WATER--LOSE TIME LOOKING FOR CLEAN SPRING");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - (int) (10 * Math.random()) - 2);
        line3100();
    }

    /**
     * Heavy rains even if occurs before mountains. Otherwise sufficient
     * clothing for cold weather test.
     */
    public void heavyRainsIfBeforeMountains() {
        if (gameStatus.getTripMileage() > 950) {
            coldWeather();
        } else {
            System.out.println("HEAVY RAINS---TIME AND SUPPLIES LOST");
            gameStatus.setFood(gameStatus.getFood() - 10);
            gameStatus.setAmmunition(gameStatus.getAmmunition() - 500);
            gameStatus.setMisc(gameStatus.getMisc() - 15);
            gameStatus.setTripMileage(gameStatus.getTripMileage()
                    - (int) (10 * Math.random()) - 5);
            line3100();
        }
    }

    /**
     * Bandits attack event.
     */
    public void banditsAttack() {
        System.out.println("BANDITS ATTACK");
        final int actualResponseTime = line4500();
        gameStatus.setAmmunition(
                gameStatus.getAmmunition() - 20 * actualResponseTime);

        if (gameStatus.getAmmunition() >= 0) {
            checkResultOfBanditAttack(actualResponseTime);
        } else {
            System.out
                    .println("YOU RAN OUT OF BULLETS---THEY GET LOTS OF CASH");
            gameStatus.setCash(gameStatus.getCash() / 3);
            shotInLegAndOxTaken();
        }
    }

    /**
     * Checks the result of fighting off a bandit attack if have sufficient
     * bullets.
     *
     * @param actualResponseTime
     *            the actual [adjusted] response time in shooting at bandits.
     */
    public void checkResultOfBanditAttack(final int actualResponseTime) {
        if (actualResponseTime <= 1) {
            quickestDraw();
        } else {
            shotInLegAndOxTaken();
        }
    }

    /**
     * Get shot in leg and have oxen taken event.
     */
    public void shotInLegAndOxTaken() {
        System.out.println(
                "YOU GOT SHOT IN THE LEG AND THEY TOOK ONE OF YOUR OXEN");
        gameStatus.setInjured(true);
        System.out.println("BETER HAVE A DOC LOOK AT YOUR WOUND");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 5);
        gameStatus.setAnimals(gameStatus.getAnimals() - 20);
        line3100();
    }

    /**
     * Quickest draw outside Dodge City when defending against bandit attack
     * event.
     */
    public void quickestDraw() {
        System.out.println("QUICKEST DRAW OUTSIDE OF DODGE CITY!!!");
        System.out.println("YOU GOT 'EM!");
        line3100();
    }

    /**
     * Fire in wagon event.
     */
    public void wagonFire() {
        System.out.println(
                "THERE WAS A FIRE IN YOUR WAGON--FOOD AND SUPPLIES DAMAGED");
        gameStatus.setFood(gameStatus.getFood() - 40);
        gameStatus.setAmmunition(gameStatus.getAmmunition() - 400);
        gameStatus
                .setMisc(gameStatus.getMisc() - (int) (8 * Math.random()) - 3);
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 15);
        line3100();
    }

    /**
     * Lose way in heavy fog event.
     */
    public void lostInFog() {
        System.out.println("LOSE YOUR WAY IN HEAVY FOG---TIME IS LOST");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 10 - (int) (5 * Math.random()));
        line3100();
    }

    /**
     * Killed poisonous snake after it bit you event.
     */
    public void snakeBite() {
        System.out.println("YOU KILLED A POISONOUS SNAKE AFTER IT BIT YOU");
        gameStatus.setAmmunition(gameStatus.getAmmunition() - 10);
        gameStatus.setMisc(gameStatus.getMisc() - 5);

        if (gameStatus.getMisc() >= 0) {
            line3100();
        } else {
            System.out
                    .println("YOU DIE OF SNAKEBITE SINCE YOU HAVE NO MEDICINE");
            line3600();
        }
    }

    /**
     * Wagon swamped fording river event.
     */
    public void wagonSwamped() {
        System.out.println(
                "WAGON GETS SWAMPED FORDING RIVER--LOSE FOOD AND CLOTHES");
        gameStatus.setFood(gameStatus.getFood() - 30);
        gameStatus.setClothing(gameStatus.getClothing() - 20);
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - (int) (28 * Math.random()));
        line3100();
    }

    /**
     * Wild animals attack event.
     */
    public void wildAnimalAttack() {
        System.out.println("WILD ANIMALS ATTACK!");
        final int actualResponseTime = line4500();

        if (gameStatus.getAmmunition() <= 39) {
            System.out.println("YOU WERE TOO LOW ON BULLETS--");
            System.out.println("THE WOLVES OVERPOWERED YOU");
            gameStatus.setInjured(true);
            line3555();
        } else {
            if (actualResponseTime <= 1) {
                System.out.println(
                        "NICE SHOOTING' PARDNER---THEY DIDN'T GET MUCH");
            } else {
                System.out.println(
                        "SLOW ON THE DRAW---THEY GOT AT YOUR FOOD AND CLOTHES");
            }

            gameStatus.setAmmunition(
                    gameStatus.getAmmunition() - 20 * actualResponseTime);
            gameStatus.setClothing(
                    gameStatus.getClothing() - actualResponseTime * 4);
            gameStatus.setFood(gameStatus.getFood() - actualResponseTime * 8);
            line3100();
        }
    }

    /**
     * Cold weather event.
     */
    public void coldWeather() {
        System.out.print("COLD WEATHER---BRRRRRRR!---YOU ");
        final boolean insufficietColdWeatherClothing =
                (gameStatus.getClothing() <= 22 + (int) (4 * Math.random()));

        if (insufficietColdWeatherClothing) {
            System.out.print("DON'T ");
        }

        System.out.println("HAVE ENOUGH CLOTHING TO KEEP YOU WARM");

        if (insufficietColdWeatherClothing) {
            line4700();
        } else {
            line3100();
        }
    }

    /**
     * Hail storm event.
     */
    public void hailStorm() {
        System.out.println("HAIL STORM---SUPPLIES DAMAGED");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 5 - (int) (10 * Math.random()));
        gameStatus.setAmmunition(gameStatus.getAmmunition() - 200);
        gameStatus
                .setMisc(gameStatus.getMisc() - 4 - (int) (3 * Math.random()));
        line3100();
    }

    /**
     * Set the desired eating choice for the current turn.
     */
    public void setEatingChoice() {
        final int eatingChoice = gameStatus.getEatingChoice();

        switch (eatingChoice) {
        case 1:
            line4700();
            break;
        case 2:
            if (Math.random() > 0.25) {
                line4700();
            } else {
                line3100();
            }

            break;
        case 3:
            if (Math.random() < 0.5) {
                line4700();
            } else {
                line3100();
            }

            break;
        default:
            throw new IllegalArgumentException(
                    "Invalid eating choice: " + eatingChoice);
        }
    }

    public void line3020() {
        System.out.println("HELPFUL INDIANS SHOW YOU WHERE TO FIND MORE FOOD");
        gameStatus.setFood(gameStatus.getFood() + 14);
        line3100();
    }

    public void line3100() {
        // *** MOUNTAINS ***
        final int tripMileage = gameStatus.getTripMileage();

        if (tripMileage <= 950) {
            // End this turn. Execute line 700.
            // Temp debugging print:
            System.out.println("Current trip mileage: " + tripMileage);
        } else if (10.0 * Math.random() > 9.0
                - (Math.pow(tripMileage / 100.0 - 15.0, 2.0) + 72.0)
                        / (Math.pow(tripMileage / 100.0 - 15.0, 2.0) + 12.0)) {
            line3175();
        } else {
            System.out.println("RUGGED MOUNTAINS");

            if (Math.random() > 0.1) {
                line3135();
            } else {
                System.out.println(
                        "YOU GOT LOST---LOSE VALUABLE TIME TRYING TO FIND TRAIL!");
                gameStatus.setTripMileage(tripMileage - 60);
                line3175();
            }
        }
    }

    public void line3135() {
        if (Math.random() > 0.11) {
            line3160();
        } else {
            System.out.println("WAGON DAMAGED!---LOSE TIME AND SUPPLIES");
            gameStatus.setMisc(gameStatus.getMisc() - 5);
            gameStatus.setAmmunition(gameStatus.getAmmunition() - 200);
            gameStatus.setTripMileage(gameStatus.getTripMileage() - 20
                    - (int) (30 * Math.random()));
            line3175();
        }
    }

    public void line3160() {
        System.out.println("THE GOING GETS SLOW");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 45
                - (int) (Math.random() / 0.02));
        line3175();
    }

    public void line3175() {
        if (gameStatus.isClearedSouthPass()) {
            line3195();
        } else {
            gameStatus.setClearedSouthPass(true);

            if (Math.random() < 0.8) {
                line3300();
            } else {
                System.out.println(
                        "YOU MADE IT SAFELY THROUGH SOUTH PASS--NO SNOW");
                line3195();
            }
        }
    }

    public void line3195() {
        if ((gameStatus.getTripMileage() < 1700)
                || gameStatus.isClearedBlueMountains()) {
            line3215();
        } else {
            gameStatus.setClearedBlueMountains(true);

            if (Math.random() < 0.7) {
                line3300();
            } else {
                line3215();
            }
        }
    }

    public void line3215() {
        final int tripMileage = gameStatus.getTripMileage();
        gameStatus.setClearedSouthPassSettingMileage(tripMileage <= 950);
        // End this turn. Execute line 700.
        // Temp debugging print:
        System.out.println("Current trip mileage: " + tripMileage);
    }

    public void line3300() {
        System.out.println("BLIZZARD IN MOUNTAIN PASS-TIME AND SUPPLIES LOST");
        gameStatus.setBlizzard(true);
        gameStatus.setFood(gameStatus.getFood() - 25);
        gameStatus.setMisc(gameStatus.getMisc() - 10);
        gameStatus.setAmmunition(gameStatus.getAmmunition() - 300);
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 30 - (int) (40 * Math.random()));

        if (gameStatus.getClothing() < 18 + (int) (2 * Math.random())) {
            line4700();
        } else {
            line3215();
        }
    }

    // ***DYING***
    public void line3500() {
        System.out.println("YOU RAN OUT OF FOOD AND STARVED TO DEATH");
        line3600();
    }

    public void line3520() {
        gameStatus.setCash(0);
        System.out.println("YOU CAN'T AFFORD A DOCTOR");
        line3555();
    }

    public void line3550() {
        System.out.println("YOU RAN OUT OF MEDICAL SUPPLIES");
        line3555();
    }

    public void line3555() {
        System.out.println("YOU DIED OF ");

        if (gameStatus.isInjured()) {
            System.out.println("INJURIES");
        } else {
            System.out.println("PNEUMONIA");
        }

        line3600();
    }

    public void line3600() {
        System.out.println();
        System.out
                .println("DUE TO YOUR UNFORTUNATE SITUATION, THERE ARE A FEW");
        System.out.println("FORMALITIES WE MUST GO THROUGH");
        System.out.println();
        System.out.println("WOULD YOU LIKE A MINISTER?");
        // Nothing done with answers to first two questions, just ask for input.
        inputService.isYesAnswerEntered();
        System.out.println("WOULD YOU LIKE A FANCY FUNERAL?");
        inputService.isYesAnswerEntered();
        System.out.println("WOULD YOU LIKE TO INFORM YOUR NEXT OF KIN?");

        if (inputService.isYesAnswerEntered()) {
            System.out.println("THAT WILL BE $4.50 FOR THE TELEGRAPH CHARGE.");
        } else {
            System.out.println(
                    "BUT YOUR AUNT SADIE IN ST. LOUIS IS REALLY WORRIED ABOUT YOU!");
        }

        System.out.println();
        System.out.println(
                "WE THANK YOU FOR THIS INFORMATION AND WE ARE SORRY YOU");
        System.out.println("DIDN'T MAKE IT TO THE GREAT TERRITORY OF OREGON");
        System.out.println("BETTER LUCK NEXT TIME");
        System.out.println();
        System.out.println();
        System.out.println("                              SINCERELY");
        System.out.println();
        System.out.println(
                "                 THE OREGON CITY CHAMBER OF COMMERCE");
        gameStatus.setFinished(true);
    }

    /**
     * Returns actual response time from the shooting subroutine.
     *
     * @return actual response time.
     */
    public int line4500() {
        int responseTime = MAX_RESPONSE_TIME + 1;
        int shootingWordSelector =
                (int) (SHOOTING_WORDS.length * Math.random());
        final String shootingWord = SHOOTING_WORDS[shootingWordSelector];
        System.out.println("TYPE " + shootingWord);
        final Instant start = Instant.now();
        final String response = inputService.getStringFromInput();
        final Instant end = Instant.now();
        responseTime = (int) Duration.between(start, end).toSeconds();
        responseTime -= (gameStatus.getShootingExpertiseLevel() - 1);
        System.out.println();
        System.out.println();
        responseTime = Math.max(0, responseTime);

        if (!shootingWord.equalsIgnoreCase(response)) {
            responseTime = MAX_RESPONSE_TIME + 1;
        }

        return responseTime;
    }

    // ***ILLNESS SUB-ROUTINE***
    public void line4700() {
        final int eatingChoiceFactor = gameStatus.getEatingChoice() - 1;

        if (100.0 * Math.random() < 10.0 + 35.0 * eatingChoiceFactor) {
            line4740();
        } else if (100.0 * Math.random() < 100.0
                - (40.0 / Math.pow(4.0, eatingChoiceFactor))) {
            line4760();
        } else {
            System.out.println("SERIOUS ILLNESS---");
            System.out.println("YOU MUST STOP FOR MEDICAL ATTENTION");
            gameStatus.setMisc(gameStatus.getMisc() - 10);
            gameStatus.setIll(true);
            line4780();
        }

    }

    public void line4740() {
        System.out.println("MILD ILLNESS---MEDICINE USED");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 5);
        gameStatus.setMisc(gameStatus.getMisc() - 2);
        line4780();
    }

    public void line4760() {
        System.out.println("BAD ILLNESS---MEDICINE USED");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 5);
        gameStatus.setMisc(gameStatus.getMisc() - 5);
        line4780();
    }

    public void line4780() {
        if (gameStatus.getMisc() < 0) {
            line3550();
        } else if (gameStatus.isBlizzard()) {
            line3215();
        } else {
            line3100();
        }
    }
}
