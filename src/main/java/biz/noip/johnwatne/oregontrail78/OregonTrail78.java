package biz.noip.johnwatne.oregontrail78;

import java.util.Scanner;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;
import biz.noip.johnwatne.oregontrail78.service.Constants;
import biz.noip.johnwatne.oregontrail78.service.InputService;
import biz.noip.johnwatne.oregontrail78.service.StatusService;
import biz.noip.johnwatne.oregontrail78.service.TurnService;

/**
 * Oregon Trail 78 - Java port of the 1978 edition of Oregon Trail. Paraphrasing
 * original source comments:
 * <p>
 * This is an updated version of what [was] on the MECC/HP at TIES.
 * <p>
 * Original programming by Bill Heinemann - 1971.
 * <p>
 * Support research and materials by Don Rawitsch, Minnesota Educational
 * Computing Consortium staff.
 * <p>
 * Programming revisions by Don Rawitsch - 1975
 * <p>
 * Revised 7/78 by James A. Henke -TIES-
 * <p>
 * For the meaning of the variables used, list lines 4900-4960 [of original
 * BASIC code; reproduced below with addition of Java variable name to be used
 * in its place.]
 * <ul>
 * <li>A = Amount spent on animals [animals]</li>
 * <li>B = Amount spent on ammunition [ammunition]</li>
 * <li>B1 = Actual response time for inputing &quot;bang&quot;
 * [actualResponseTime]</li>
 * <li>B2 = Maximum response time for inputing &quot;bang&quot;
 * [maxResponseTime]</li>
 * <li>C = Amount spent on clothing [clothing]</li>
 * <li>C1 = Flag for insufficient clothing in cold weather
 * [insufficientColdWeatherClothing]</li>
 * <li>C$ = Yes/no response to questions [yesNoResponse]</li>
 * <li>D1 = Counter in generating events [eventGeneratingCounter]</li>
 * <li>D3 = Turn number for setting date [turnNumber]</li>
 * <li>D4 = Current date [currentDate]</li>
 * <li>D9 = Choice of shooting expertise level [shootingExpertiseLevel]</li>
 * <li>E = Choice of eating [eatingChoice]</li>
 * <li>F = Amount spent on food [food]</li>
 * <li>F1 = Flag for clearing South Pass [clearedSouthPass]</li>
 * <li>F2 = Flag for clearing Blue Mountains [clearedBlueMountains]</li>
 * <li>K8 = Flag for injury [injured]</li>
 * <li>L1 = Flag for blizzard [blizzard]</li>
 * <li>M = Total mileage whole trip [tripMileage]</li>
 * <li>M1 = Amount spent on miscellaneous supplies [misc]</li>
 * <li>M2 = Total mileage up through previous turn
 * [startOfTurnTotalMileage]</li>
 * <li>M9 = Flag for clearing South Pass in setting mileage
 * [clearedSouthPassSettingMileage]</li>
 * <li>P = Amount spent on items at fort [amountSpentAtFort]</li>
 * <li>R1 = Random number in choosing events [randomEvent]</li>
 * <li>S4 = Flag for illness [ill]</li>
 * <li>S5 = &quot;Hostility of riders&quot; factor [ridersHostile]</li>
 * <li>S6 = Shooting word selector [shootingWordSelector]</li>
 * <li>S$ = Variations of shooting word [SHOOTING_WORDS]</li>
 * <li>T = Cash left after initial purchases [cash]</li>
 * <li>X = Choice of action for each turn [action]</li>
 * <li>X1 = Flag for fort option [fort]</li>
 * </ul>
 *
 * @author [Java port only] John Watne
 *
 */
public class OregonTrail78 {
    private static final int INITIAL_CASH = 700;
    private static final long MIN_ANIMAL_SPENDING_AMOUNT = 200;
    private static final long MAX_ANIMAL_SPENDING_AMOUNT = 300;
    private InputService inputService;
    private GameStatus gameStatus = new GameStatus();
    @SuppressWarnings("unused")
    private Scanner scanner;
    private StatusService statusService = new StatusService();

    /**
     * Constructs the game object with the specified scanner for user input.
     *
     * @param scanner
     *            the scanner for user input.
     */
    public OregonTrail78(final Scanner scanner) {
        this.scanner = scanner;
        this.inputService = new InputService(scanner);
    }

    /**
     * Main method of application.
     *
     * @param args
     *            command-line arguments; not used.
     */
    public static void main(final String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final OregonTrail78 game = new OregonTrail78(scanner);
            game.startGame();
        }
    }

    /**
     * Prints instructions for program.
     */
    public void printInstructions() {
        System.out.println(
                "THIS PROGRAM SIMULATES A TRIP OVER THE OREGON TRAIL FROM");
        System.out.println(
                "INDEPENDENCE, MISSOURI TO OREGON CITY, OREGON IN 1847.");
        System.out.println(
                "YOUR FAMILY OF FIVE WILL COVER THE 2000 MILE OREGON TRAIL");
        System.out.println("IN 5-6 MONTHS --- IF YOU MAKE IT ALIVE.");
        System.out.println();
        System.out.println(
                "YOU HAD SAVED $900 TO SPEND FOR THE TRIP, AND YOU'VE JUST");
        System.out.println("   PAID $200 FOR A WAGON.");
        System.out.println(
                "YOU WILL NEED TO SPEND THE REST OF YOUR MONEY ON THE");
        System.out.println("   FOLLOWING ITEMS:");
        System.out.println();
        System.out.println("     OXEN - YOU CAN SPEND $200-$300 ON YOUR TEAM");
        System.out.println(
                "             THE MORE YOU SPEND, THE FASTER YOU'LL GO");
        System.out
                .println("                BECAUSE YOU'LL HAVE BETTER ANIMALS");
        System.out.println();
        System.out.println(
                "     FOOD - THE MORE YOU HAVE, THE LESS CHANCE THERE");
        System.out.println("              IS OF GETTING SICK");
        System.out.println();
        System.out.println("     AMMUNITION - $1 BUYS A BELT OF 50 BULLETS");
        System.out.println(
                "            YOU WILL NEED BULLETS FOR ATTACKS BY ANIMALS");
        System.out.println("               AND BANDITS, AND FOR HUNTING FOOD");
        System.out.println();
        System.out.println(
                "     CLOTHING - THIS IS ESPECIALLY IMPORTANT FOR THE COLD");
        System.out.println(
                "               WEATHER YOU WILL ENCOUNTER WHEN CROSSING");
        System.out.println("               THE MOUNTAINS");
        System.out.println();
        System.out.println(
                "     MISCELLANEOUS SUPPLIES - THIS INCLUDES MEDICINE AND");
        System.out.println(
                "               OTHER THINGS YOU WILL NEED FOR SICKENESS");
        System.out.println("               AND EMERGENCY REPAIRS");
        System.out.println();
        System.out.println();
        System.out.println(
                "YOU CAN SPEND ALL YOUR MONEY BEFORE YOU START YOUR TRIP -");
        System.out.println(
                "OR YOU CAN SAVE SOME OF YOUR CASH TO SPEND AT FORTS ALONG");
        System.out.println(
                "THE WAY WHEN YOU RUN LOW.  HOWEVER, ITEMS COST MORE AT");
        System.out.println(
                "THE FORTS.  YOU CAN ALSO GO HUNTING ALONG THE WAY TO GET");
        System.out.println("MORE FOOD.");
        System.out.println(
                "WHENEVER YOU HAVE TO USE YOUR TRUSTY RIFLE ALONG THE WAY,");
        System.out.println(
                "YOU WILL BE TOLD TO TYPE IN A WORD (ONE THAT SOUNDS LIKE A");
        System.out.println(
                "GUN SHOT). THE FASTER YOU TYPE IN THAT WORD AND HIT THE");
        System.out.println(
                "'RETURN' KEY, THE BETTER LUCK YOU'LL HAVE WITH YOUR GUN.");
        System.out.println();
        System.out
                .println("AT EACH TURN, ALL ITEMS YOU OWN ARE SHOWN IN DOLLAR");
        System.out.println("AMOUNTS EXCEPT BULLETS");
        System.out
                .println("WHEN ASKED TO ENTER MONEY AMOUNTS, DON'T USE A '$'.");
        System.out.println();
        System.out.println("GOOD LUCK!!!");
    }

    /**
     * Starts the game [starting at line 270 in original BASIC code]
     */
    public void startGame() {
        System.out.println("DO YOU NEED INSTRUCTIONS  (YES/NO)");

        if (inputService.isYesAnswerEntered()) {
            printInstructions();
        }

        setShootingExpertiseLevel();
        makeInitialPurchases();
        System.out.println();
        System.out.println("MONDAY MARCH 29 1847");
        System.out.println();
        // GOTO 1000: BEGINNING EACH TURN
        final TurnService turnService =
                new TurnService(inputService, gameStatus);

        while (!gameStatus.isFinished()) {
            turnService.executeTurn();

            if (!gameStatus.isFinished()) {
                turnService.line700();
            }
        }
    }

    /**
     * Prompts for and sets the shooting expertise level.
     */
    private void setShootingExpertiseLevel() {
        System.out.println();
        System.out.println();
        System.out.println("HOW GOOD A SHOT ARE YOU WITH YOUR RIFLE?");
        System.out.println(
                "  (1) ACE MARKSMAN,  (2) GOOD SHOT,  (3) FAIR TO MIDDLIN'");
        System.out.println("      (4) NEED MORE PRACTICE,  (5) SHAKY KNEES");
        System.out.println(
                "ENTER ONE OF THE ABOVE -- THE BETTER YOU CLAIM YOU ARE, THE");
        System.out.println(
                "FASTER YOU'LL HAVE TO BE WITH YOUR GUN TO BE SUCCESSFUL.");
        final int shootingExpertiseLevel =
                inputService.getIntInRangeFromInput(0, 0, 5);
        gameStatus.setShootingExpertiseLevel(shootingExpertiseLevel);
    }

    /**
     * Make the initial purchases at the start of the trip.
     */
    private void makeInitialPurchases() {
        // Initialization of various flag at the start of the INITIAL PURCHASES
        // section of the original code moved to initialization of gameStatus.
        do {
            gameStatus.setCash(-1);
            gameStatus.setAnimals(0);
            getSpendingAmounts();
            // Convert $ spent on ammunition to # of bullets.
            gameStatus.setAmmunition(
                    Constants.BULLETS_PER_DOLLAR * gameStatus.getAmmunition());
            System.out.println();
            System.out.println("THIS IS WHAT YOU JUST SPENT:");
            statusService.printInventory(gameStatus);
            System.out.println();
            System.out.println("ARE ALL OF THE AMOUNTS OK");
        } while (!inputService.isYesAnswerEntered());
    }

    /**
     * Enter spending amounts for each item until the total does not exceed
     * {@link #INITIAL_CASH}.
     */
    private void getSpendingAmounts() {
        while (gameStatus.getCash() < 0) {
            System.out.println();
            System.out.println();

            while ((gameStatus.getAnimals() < MIN_ANIMAL_SPENDING_AMOUNT)
                    || (gameStatus.getAnimals() > MAX_ANIMAL_SPENDING_AMOUNT)) {
                System.out.println(
                        "HOW MUCH DO YOU WANT TO SPEND ON YOUR OXEN TEAM");
                gameStatus.setAnimals(inputService.getIntFromInput(0));

                if (gameStatus.getAnimals() < MIN_ANIMAL_SPENDING_AMOUNT) {
                    System.out.println("NOT ENOUGH");
                } else if (gameStatus
                        .getAnimals() > MAX_ANIMAL_SPENDING_AMOUNT) {
                    System.out.println("TOO MUCH");
                }
            }

            gameStatus.setFood(inputService.getAmountToSpendFromInput("FOOD"));
            gameStatus.setAmmunition(
                    inputService.getAmountToSpendFromInput("AMMUNITION"));
            gameStatus.setClothing(
                    inputService.getAmountToSpendFromInput("CLOTHING"));
            gameStatus.setMisc(inputService
                    .getAmountToSpendFromInput("MISCELLANEOUS SUPPLIES"));
            gameStatus.setCash(INITIAL_CASH - gameStatus.getAnimals()
                    - gameStatus.getFood() - gameStatus.getAmmunition()
                    - gameStatus.getClothing() - gameStatus.getMisc());

            if (gameStatus.getCash() < 0) {
                System.out.println("YOU OVERSPENT--YOU ONLY HAD $"
                        + INITIAL_CASH + " TO SPEND. BUY AGAIN");
            }

        }
    }
}
