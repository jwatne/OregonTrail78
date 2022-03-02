package biz.noip.johnwatne.oregontrail78.service;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Services for handling a successful finish to the game.
 *
 * @author John Watne
 *
 */
public class SuccessfulFinishService {
    private GameStatus gameStatus;

    public SuccessfulFinishService(final GameStatus gameStatus) {
        this.gameStatus = gameStatus;
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
}
