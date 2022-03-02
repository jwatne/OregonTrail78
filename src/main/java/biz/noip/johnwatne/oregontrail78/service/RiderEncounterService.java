package biz.noip.johnwatne.oregontrail78.service;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Services for dealing with encounters with riders, hostile and not hostile.
 *
 * @author John Watne
 *
 */
public class RiderEncounterService {
    private GameStatus gameStatus;
    private InputService inputService;
    private ShootingService shootingService;
    private RandomEventExecutionService randomEventExecutionService;
    private DeathService deathService;

    public RiderEncounterService(final GameStatus gameStatus,
            final InputService inputService,
            final ShootingService shootingService,
            final RandomEventExecutionService randomEventExecutionService,
            final DeathService deathService) {
        this.gameStatus = gameStatus;
        this.inputService = inputService;
        this.shootingService = shootingService;
        this.randomEventExecutionService = randomEventExecutionService;
        this.deathService = deathService;
    }

    public void handleRiderEncounter() {
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

            gameStatus.setTacticsChoice(inputService.getIntFromInput(choice));
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
        randomEventExecutionService.chooseRandomEventForTurn();
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
            randomEventExecutionService.chooseRandomEventForTurn();
        }

    }

    /**
     * Do follow-up when riders were actually hostile.
     */
    private void ridersWereHostile() {
        System.out.println("RIDERS WERE HOSTILE--CHECK FOR LOSSES");

        if (gameStatus.getAmmunition() >= 0) {
            randomEventExecutionService.chooseRandomEventForTurn();
        } else {
            System.out.println(
                    "YOU RAN OUT OF BULLETS AND GOT MASSACRED BY THE RIDERS");
            deathService.makeDeathArrangements();
        }
    }
}
