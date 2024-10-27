import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {
    private Stats stats;

    @BeforeEach
    void setUp() {
        // Initialize a new Stats object before each test
        stats = new Stats();
    }

    @Test
    void testInitialStats() {
        // Verify that the initial goalsFor, goalsAgainst, and points are all zero
        assertEquals(0, stats.getGoalsFor());
        assertEquals(0, stats.getGoalsAgainst());
        assertEquals(0, stats.getPoints());
    }

    @Test
    void testAddMatchResultWin() {
        // Test adding a match result where the team wins
        stats.addMatchResult(2, 1, true, false);
        assertEquals(2, stats.getGoalsFor()); // Goals for should increase
        assertEquals(1, stats.getGoalsAgainst()); // Goals against should increase
        assertEquals(3, stats.getPoints()); // Points should increase by 3 for a win
    }

    @Test
    void testAddMatchResultDraw() {
        // Test adding a match result where the match is a draw
        stats.addMatchResult(1, 1, false, true);
        assertEquals(1, stats.getGoalsFor()); // Goals for should increase
        assertEquals(1, stats.getGoalsAgainst()); // Goals against should increase
        assertEquals(1, stats.getPoints()); // Points should increase by 1 for a draw
    }

    @Test
    void testGoalDifference() {
        // Test the calculation of goal difference after a match
        stats.addMatchResult(3, 1, true, false);
        assertEquals(2, stats.getGoalDifference()); // Goal difference should be goalsFor - goalsAgainst
    }
}

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        // Initialize a new Player object before each test
        player = new Player("John Doe");
    }

    @Test
    void testInitialPlayerStats() {
        // Verify that the initial player stats are set correctly
        assertEquals("John Doe", player.getName()); // Check player name
        assertEquals(0, player.getGoals()); // Check initial goals
        assertEquals(0, player.getAssists()); // Check initial assists
        assertEquals(0, player.getYellowCards()); // Check initial yellow cards
        assertEquals(0, player.getRedCards()); // Check initial red cards
    }

    @Test
    void testAddGoals() {
        // Test adding goals to the player
        player.addGoals(2);
        assertEquals(2, player.getGoals()); // Verify goals count
    }

    @Test
    void testAddAssists() {
        // Test adding assists to the player
        player.addAssists(1);
        assertEquals(1, player.getAssists()); // Verify assists count
    }

    @Test
    void testAddYellowCard() {
        // Test adding a yellow card to the player
        player.addYellowCard();
        assertEquals(1, player.getYellowCards()); // Verify yellow cards count
    }

    @Test
    void testAddRedCard() {
        // Test adding a red card to the player
        player.addRedCard();
        assertEquals(1, player.getRedCards()); // Verify red cards count
    }
}

class TeamTest {
    private Team team;
    private Player player;

    @BeforeEach
    void setUp() {
        // Initialize a new Team object and add a Player before each test
        team = new Team("Team A");
        player = new Player("John Doe");
        team.addPlayer(player);
    }

    @Test
    void testAddPlayer() {
        // Verify that a player can be successfully added to the team
        assertEquals(player, team.findPlayerByName("John Doe")); // Check if the player is found by name
    }

    @Test
    void testAddMatchResult() {
        // Test adding a match result and updating player and team stats
        String[] scorers = {"John Doe"};
        String[] assisters = {};
        String[] yellowCards = {};
        String[] redCards = {};
        team.addMatchResult(3, 1, true, false, scorers, assisters, yellowCards, redCards);
        
        // Verify the team's stats after the match
        assertEquals(3, team.getStats().getGoalsFor()); // Goals for should be updated
        assertEquals(1, team.getStats().getGoalsAgainst()); // Goals against should be updated
        assertEquals(3, team.getStats().getPoints()); // Points should be updated
    }
}