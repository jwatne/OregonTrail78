package biz.noip.johnwatne.oregontrail78.service;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Services for executing random events during a turn.
 *
 * @author John Watne
 *
 */
public class RandomEventExecutionService {
    private GameStatus gameStatus;
    private SicknessAndMountainsService sicknessAndMountainsService;
    private ShootingService shootingService;
    private DeathService deathService;

    public RandomEventExecutionService(final GameStatus gameStatus,
            final ShootingService shootingService,
            final DeathService deathService) {
        this.gameStatus = gameStatus;
        this.shootingService = shootingService;
        this.deathService = deathService;
        this.sicknessAndMountainsService =
                new SicknessAndMountainsService(gameStatus, deathService);
    }

    /**
     * Executes the random event for the given value of the event generating
     * counter.
     *
     * @param counter
     *            the current value of the event generating counter.
     */
    public void executeEventForCounterValue(final int counter) {
        switch (counter) {
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
            helpfulIndiansShowWhereToFindFood();
            break;
        default:
            throw new IllegalArgumentException(
                    "Invalid gameStatus.getEventGeneratingCounter() value: "
                            + gameStatus.getEventGeneratingCounter());
        }
    }

    /**
     * Helpful Indians show you where to find more food event.
     */
    public void helpfulIndiansShowWhereToFindFood() {
        System.out.println("HELPFUL INDIANS SHOW YOU WHERE TO FIND MORE FOOD");
        gameStatus.setFood(gameStatus.getFood() + 14);
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Wagon breaks down event.
     */
    private void wagonBreaksDown() {
        System.out
                .println("WAGON BREAKS DOWN--LOSE TIME AND SUPPLIES FIXING IT");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 15 - (int) (5 * Math.random()));
        gameStatus.setMisc(gameStatus.getMisc() - 8);
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Ox injures leg event.
     */
    private void oxInjuresLeg() {
        System.out.println("OX INJURES LEG---SLOWS YOU DOWN REST OF TRIP");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 25);
        gameStatus.setAnimals(gameStatus.getAnimals() - 20);
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Daughter breaks arm event.
     */
    private void daughterBreaksArm() {
        System.out.println("BAD LUCK---YOUR DAUGHTER BROKE HER ARM");
        System.out.println("YOU HAD TO STOP AND USE SUPPLIES TO MAKE A SLING");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 5 - (int) (4 * Math.random()));
        gameStatus
                .setMisc(gameStatus.getMisc() - 2 - (int) (3 * Math.random()));
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Ox wanders off event.
     */
    private void oxWandersOff() {
        System.out.println("OX WANDERS OFF---SPEND TIME LOOKING FOR IT");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 17);
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Son gets lost event.
     */
    private void sonGetsLost() {
        System.out.println(
                "YOUR SON GETS LOST---SPEND HALF THE DAY LOKING FOR HIM");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 10);
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Unsafe water event.
     */
    private void unsafeWater() {
        System.out.println("UNSAFE WATER--LOSE TIME LOOKING FOR CLEAN SPRING");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - (int) (10 * Math.random()) - 2);
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Heavy rains even if occurs before mountains. Otherwise sufficient
     * clothing for cold weather test.
     */
    private void heavyRainsIfBeforeMountains() {
        if (gameStatus.getTripMileage() > 950) {
            coldWeather();
        } else {
            System.out.println("HEAVY RAINS---TIME AND SUPPLIES LOST");
            gameStatus.setFood(gameStatus.getFood() - 10);
            gameStatus.setAmmunition(gameStatus.getAmmunition() - 500);
            gameStatus.setMisc(gameStatus.getMisc() - 15);
            gameStatus.setTripMileage(gameStatus.getTripMileage()
                    - (int) (10 * Math.random()) - 5);
            sicknessAndMountainsService.checkIfAtMountains();
        }
    }

    /**
     * Bandits attack event.
     */
    private void banditsAttack() {
        System.out.println("BANDITS ATTACK");
        final int actualResponseTime = shootingService.getShootingResult();
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
    private void checkResultOfBanditAttack(final int actualResponseTime) {
        if (actualResponseTime <= 1) {
            quickestDraw();
        } else {
            shotInLegAndOxTaken();
        }
    }

    /**
     * Get shot in leg and have oxen taken event.
     */
    private void shotInLegAndOxTaken() {
        System.out.println(
                "YOU GOT SHOT IN THE LEG AND THEY TOOK ONE OF YOUR OXEN");
        gameStatus.setInjured(true);
        System.out.println("BETER HAVE A DOC LOOK AT YOUR WOUND");
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 5);
        gameStatus.setAnimals(gameStatus.getAnimals() - 20);
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Quickest draw outside Dodge City when defending against bandit attack
     * event.
     */
    private void quickestDraw() {
        System.out.println("QUICKEST DRAW OUTSIDE OF DODGE CITY!!!");
        System.out.println("YOU GOT 'EM!");
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Fire in wagon event.
     */
    private void wagonFire() {
        System.out.println(
                "THERE WAS A FIRE IN YOUR WAGON--FOOD AND SUPPLIES DAMAGED");
        gameStatus.setFood(gameStatus.getFood() - 40);
        gameStatus.setAmmunition(gameStatus.getAmmunition() - 400);
        gameStatus
                .setMisc(gameStatus.getMisc() - (int) (8 * Math.random()) - 3);
        gameStatus.setTripMileage(gameStatus.getTripMileage() - 15);
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Lose way in heavy fog event.
     */
    private void lostInFog() {
        System.out.println("LOSE YOUR WAY IN HEAVY FOG---TIME IS LOST");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 10 - (int) (5 * Math.random()));
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Killed poisonous snake after it bit you event.
     */
    private void snakeBite() {
        System.out.println("YOU KILLED A POISONOUS SNAKE AFTER IT BIT YOU");
        gameStatus.setAmmunition(gameStatus.getAmmunition() - 10);
        gameStatus.setMisc(gameStatus.getMisc() - 5);

        if (gameStatus.getMisc() >= 0) {
            sicknessAndMountainsService.checkIfAtMountains();
        } else {
            System.out
                    .println("YOU DIE OF SNAKEBITE SINCE YOU HAVE NO MEDICINE");
            deathService.makeDeathArrangements();
        }
    }

    /**
     * Wagon swamped fording river event.
     */
    private void wagonSwamped() {
        System.out.println(
                "WAGON GETS SWAMPED FORDING RIVER--LOSE FOOD AND CLOTHES");
        gameStatus.setFood(gameStatus.getFood() - 30);
        gameStatus.setClothing(gameStatus.getClothing() - 20);
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - (int) (28 * Math.random()));
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Wild animals attack event.
     */
    private void wildAnimalAttack() {
        System.out.println("WILD ANIMALS ATTACK!");
        final int actualResponseTime = shootingService.getShootingResult();

        if (gameStatus.getAmmunition() <= 39) {
            System.out.println("YOU WERE TOO LOW ON BULLETS--");
            System.out.println("THE WOLVES OVERPOWERED YOU");
            gameStatus.setInjured(true);
            deathService.dieOfInjuriesOrPneumonia();
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
            sicknessAndMountainsService.checkIfAtMountains();
        }
    }

    /**
     * Cold weather event.
     */
    private void coldWeather() {
        System.out.print("COLD WEATHER---BRRRRRRR!---YOU ");
        final boolean insufficietColdWeatherClothing =
                (gameStatus.getClothing() <= 22 + (int) (4 * Math.random()));

        if (insufficietColdWeatherClothing) {
            System.out.print("DON'T ");
        }

        System.out.println("HAVE ENOUGH CLOTHING TO KEEP YOU WARM");

        if (insufficietColdWeatherClothing) {
            sicknessAndMountainsService.handleSickness();
        } else {
            sicknessAndMountainsService.checkIfAtMountains();
        }
    }

    /**
     * Hail storm event.
     */
    private void hailStorm() {
        System.out.println("HAIL STORM---SUPPLIES DAMAGED");
        gameStatus.setTripMileage(
                gameStatus.getTripMileage() - 5 - (int) (10 * Math.random()));
        gameStatus.setAmmunition(gameStatus.getAmmunition() - 200);
        gameStatus
                .setMisc(gameStatus.getMisc() - 4 - (int) (3 * Math.random()));
        sicknessAndMountainsService.checkIfAtMountains();
    }

    /**
     * Set the desired eating choice for the current turn.
     */
    private void setEatingChoice() {
        final int eatingChoice = gameStatus.getEatingChoice();

        switch (eatingChoice) {
        case 1:
            sicknessAndMountainsService.handleSickness();
            break;
        case 2:
            if (Math.random() > 0.25) {
                sicknessAndMountainsService.handleSickness();
            } else {
                sicknessAndMountainsService.checkIfAtMountains();
            }

            break;
        case 3:
            if (Math.random() < 0.5) {
                sicknessAndMountainsService.handleSickness();
            } else {
                sicknessAndMountainsService.checkIfAtMountains();
            }

            break;
        default:
            throw new IllegalArgumentException(
                    "Invalid eating choice: " + eatingChoice);
        }
    }

}
