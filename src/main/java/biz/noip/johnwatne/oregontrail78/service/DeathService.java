package biz.noip.johnwatne.oregontrail78.service;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Service class for handling deaths of various types during a turn.
 *
 * @author John Watne
 *
 */
public class DeathService {
    private GameStatus gameStatus;
    private InputService inputService;

    public DeathService(final GameStatus gameStatus,
            final InputService inputService) {
        this.gameStatus = gameStatus;
        this.inputService = inputService;
    }

    /**
     * Determine funeral rites and whether to inform next of kin, and end
     * program.
     */
    public void makeDeathArrangements() {
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
     * Cannot afford doctor, so die.
     */
    public void cannotAffordDoctor() {
        gameStatus.setCash(0);
        System.out.println("YOU CAN'T AFFORD A DOCTOR");
        dieOfInjuriesOrPneumonia();
    }

    /**
     * Indicates whether death was by injuries or pneumonia.
     */
    public void dieOfInjuriesOrPneumonia() {
        System.out.println("YOU DIED OF ");

        if (gameStatus.isInjured()) {
            System.out.println("INJURIES");
        } else {
            System.out.println("PNEUMONIA");
        }

        makeDeathArrangements();
    }

    /**
     * Starve to death.
     */
    public void starveToDeath() {
        System.out.println("YOU RAN OUT OF FOOD AND STARVED TO DEATH");
        makeDeathArrangements();
    }

    /**
     * Run out of medical supplies and die.
     */
    public void runOutOfMedicalSupplies() {
        System.out.println("YOU RAN OUT OF MEDICAL SUPPLIES");
        dieOfInjuriesOrPneumonia();
    }

}
