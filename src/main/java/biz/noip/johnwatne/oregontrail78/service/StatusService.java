package biz.noip.johnwatne.oregontrail78.service;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Services returning or setting information about game status.
 *
 * @author John Watne
 *
 */
public class StatusService {
    /**
     * Prints the family's current inventory, as recorded in gameStatus.
     *
     * @param gameStatus
     *            the current status of the game.
     */
    public void printInventory(GameStatus gameStatus) {
        System.out.println("FOOD\tBULLETS\tCLOTHING\tMISC. SUPP.\tCASH LEFT");
        System.out.println(
                "" + gameStatus.getFood() + "\t" + gameStatus.getAmmunition()
                        + "\t" + gameStatus.getClothing() + "\t\t"
                        + gameStatus.getMisc() + "\t\t" + gameStatus.getCash());
    }

}
