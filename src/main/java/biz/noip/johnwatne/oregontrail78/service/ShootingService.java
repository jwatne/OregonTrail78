package biz.noip.johnwatne.oregontrail78.service;

import java.time.Duration;
import java.time.Instant;

import biz.noip.johnwatne.oregontrail78.model.GameStatus;

/**
 * Services for determining results for shooting, used for determining hunting
 * or defense results.
 *
 * @author John Watne
 *
 */
public class ShootingService {
    private static final String[] SHOOTING_WORDS =
            {"BANG", "BLAM", "POW", "WHAM"};
    final private static int MAX_RESPONSE_TIME = 8;
    private InputService inputService;
    private GameStatus gameStatus;

    public ShootingService(final InputService inputService,
            final GameStatus gameStatus) {
        this.inputService = inputService;
        this.gameStatus = gameStatus;
    }

    /**
     * Returns actual response time from the shooting subroutine.
     *
     * @return actual response time.
     */
    public int getShootingResult() {
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
}
