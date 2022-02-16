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
    private GameStatus gameStatus;
    private StatusService statusService = new StatusService();

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
     * Executes a single turn.
     */
    public void executeTurn() {
        gameStatus.setFood(Math.max(0L, gameStatus.getFood()));
        gameStatus.setAmmunition(Math.max(0L, gameStatus.getAmmunition()));
        gameStatus.setClothing(Math.max(0L, gameStatus.getClothing()));
        gameStatus.setMisc(Math.max(0L, gameStatus.getMisc()));

        if (gameStatus.getFood() < Constants.LOW_FOOD_AMOUNT) {
            System.out.println(
                    "YOU'D BETTER DO SOME HUNTING OR BUY FOOD AND SOON!!!!");
        }

        @SuppressWarnings("unused")
        final Long startOfTurnTotalMileage = gameStatus.getTripMileage();

        if (gameStatus.isIll() || gameStatus.isInjured()) {
            gameStatus.setCash(gameStatus.getCash() - 20L);

            if (gameStatus.getCash() < 0L) {
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
            System.out
                    .println("TOTAL MILEAGE IS " + gameStatus.getTripMileage());
        }

        statusService.printInventory(gameStatus);

        if (gameStatus.isFort()) {
            line1350();
        } else {
            gameStatus.setFort(true);
            System.out.println(
                    "DO YOU WANT TO (1) STOP AT THE NEXT FORT, (2) HUNT, ");
            System.out.println("OR (3) CONTINUE");
            Long action = inputService.getLongFromInput(3L);
            Actions actionType = Actions.CONTINUE;

            if (action == 1L) {
                actionType = Actions.STOP_AT_FORT;
            } else if (action == 2L) {
                actionType = Actions.HUNT;
            }

            line1400(actionType);
        }
    }

    public void line1310() {
        // TODO Auto-generated method stub
    
    }

    public void line1350() {
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("DO YOU WANT TO (1) HUNT, OR (2) CONTINUE");
            Long action = inputService.getLongFromInput(3L);
            Actions actionType = Actions.CONTINUE;

            if (1L == action) {
                actionType = Actions.HUNT;
            }

            if ((actionType == Actions.CONTINUE) || (gameStatus
                    .getAmmunition() > Constants.MINIMUM_HUNTING_AMMUNITION)) {
                validChoice = true;
                line1395();
                line1400(actionType);
            } else {
                System.out
                        .println("TOUGH---YOU NEED MORE BULLETS TO GO HUNTING");
            }
        }
    }

    public void line1395() {
        gameStatus.setFort(!gameStatus.isFort());

    }

    public void line1400(final Actions actionType) {
        switch (actionType) {
        case STOP_AT_FORT:
            line1500();
            break;
        case HUNT:
            line1700();
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

        line1800();
    }

    /**
     * Stop at fort.
     */
    public void line1500() {
        System.out.println("ENTER WHAT YOU WISH TO SPEND ON THE FOLLOWING");
        System.out.println("FOOD");
        Long amountSpentAtFort = line1520(); // GOSUB
        gameStatus
                .setFood(gameStatus.getFood() + ((2 * amountSpentAtFort) / 3));
        System.out.println("AMMUNITION");
        amountSpentAtFort = line1520();
        gameStatus.setAmmunition(gameStatus.getAmmunition()
                + (2 * amountSpentAtFort / 3) * Constants.BULLETS_PER_DOLLAR);
        System.out.println("CLOTHING");
        amountSpentAtFort = line1520();
        gameStatus.setClothing(
                gameStatus.getClothing() + (2 * amountSpentAtFort) / 3);
        System.out.println("MISCELLANEOUS SUPPLIES");
        amountSpentAtFort = line1520();
        gameStatus.setMisc(gameStatus.getMisc() + (2 * amountSpentAtFort) / 3);
        gameStatus.setTripMileage(gameStatus.getTripMileage()
                - Constants.MILES_LOST_FOR_STOPPING_AT_FORT);
        // GOTO 1800.
    }

    public Long line1520() {
        Long amountSpentAtFort =
                Math.max(0L, inputService.getLongFromInput(0L));

        if (amountSpentAtFort > 0L) {
            gameStatus.setCash(gameStatus.getCash() - amountSpentAtFort);

            if (gameStatus.getCash() < 0) {
                System.out.println(
                        "YOU DON'T HAVE THAT MUCH--KEEP YOUR SPENDING DOWN");
                gameStatus.setCash(gameStatus.getCash() + amountSpentAtFort);
                amountSpentAtFort = 0L;
            }
        }

        return amountSpentAtFort;

    }

    /**
     * Hunting
     */
    public void line1700() {
        if (gameStatus.getAmmunition() > Constants.MINIMUM_HUNTING_AMMUNITION) {
            // GOTO 1715
        } else {
            System.out.println("TOUGH---YOU NEED MORE BULLETS TO GO HUNTING");
            line1310();
        }
    }

    public void line1800() {
        // TODO Auto-generated method stub

    }

    public void line3520() {
        // TODO Auto-generated method stub

    }
}
