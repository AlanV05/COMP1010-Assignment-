
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class tests {
    
    private League league;
    private Team team1;
    private Team team2;

    @Before
    public void setUp() {
        league = new League("Premier League");
        team1 = new Team("Team A");
        team2 = new Team("Team B");
        league.addTeam(team1);
        league.addTeam(team2);
    }

    @Test
    public void testAddTeam() {
        Team team3 = new Team("Team C");
        league.addTeam(team3);
        assertEquals("Team C should be added to the league", team3, league.findTeamByName("Team C"));
    }

    @Test
    public void testAddPlayerToTeam() {
        Player player = new Player("John Doe");
        team1.addPlayer(player);
        assertEquals("Player should be added to Team A", player, team1.findPlayerByName("John Doe"));
    }

    @Test
    public void testMatchResultWin() {
        String[] scorersTeam1 = {"John Doe"};
        String[] assistersTeam1 = {};
        String[] yellowCardsTeam1 = {};
        String[] redCardsTeam1 = {};
        
        String[] scorersTeam2 = {};
        String[] assistersTeam2 = {};
        String[] yellowCardsTeam2 = {};
        String[] redCardsTeam2 = {};

        Match match = new Match(team1, team2, 2, 1);
        match.play(scorersTeam1, assistersTeam1, yellowCardsTeam1, redCardsTeam1, 
                   scorersTeam2, assistersTeam2, yellowCardsTeam2, redCardsTeam2);
        
        assertEquals("Team A should have 3 points", 3, team1.getStats().getPoints());
        assertEquals("Team B should have 0 points", 0, team2.getStats().getPoints());
        
    }

    @Test
    public void testMatchResultDraw() {
        String[] scorersTeam1 = {"John Doe"};
        String[] assistersTeam1 = {};
        String[] yellowCardsTeam1 = {};
        String[] redCardsTeam1 = {};
        
        String[] scorersTeam2 = {"Jane Doe"};
        String[] assistersTeam2 = {};
        String[] yellowCardsTeam2 = {};
        String[] redCardsTeam2 = {};

        Match match = new Match(team1, team2, 1, 1);
        match.play(scorersTeam1, assistersTeam1, yellowCardsTeam1, redCardsTeam1, 
                   scorersTeam2, assistersTeam2, yellowCardsTeam2, redCardsTeam2);
        
        assertEquals("Team A should have 1 point", 1, team1.getStats().getPoints());
        assertEquals("Team B should have 1 point", 1, team2.getStats().getPoints());
    }

    @Test
    public void testLeagueRankings() {
        Player player1 = new Player("John Doe");
        Player player2 = new Player("Jane Doe");
        
        team1.addPlayer(player1);
        team2.addPlayer(player2);

        Match match = new Match(team1, team2, 2, 1);
        match.play(new String[]{"John Doe"}, new String[]{}, new String[]{}, new String[]{}, 
                   new String[]{}, new String[]{}, new String[]{}, new String[]{});

        String rankings = league.getLeagueRankings();
        assertTrue("Rankings should contain Team A", rankings.contains("Team A"));
        assertTrue("Rankings should contain Team B", rankings.contains("Team B"));
    }

    @Test
    public void testTopScorers() {
        Player player1 = new Player("John Doe");
        team1.addPlayer(player1);
        player1.addGoals(3); // Simulating goals for John Doe
        
        String topScorers = league.getTopScorers();
        assertTrue("Top scorers should contain John Doe", topScorers.contains("John Doe - 3 goals"));
    }
    @Test
    public void testAddMultiplePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        team1.addPlayer(player1);
        team1.addPlayer(player2);
        
        assertEquals("Team A should have 2 players", 2, team1.getPlayers().size());
    }

    @Test
    public void testPlayerStatistics() {
        Player player = new Player("Player 3");
        team1.addPlayer(player);
        
        // Simulating goals and assists
        player.addGoals(2);
        player.addAssists(1);
        player.addYellowCard();
        
        assertEquals("Player 3 should have 2 goals", 2, player.getGoals());
        assertEquals("Player 3 should have 1 assist", 1, player.getAssists());
        assertEquals("Player 3 should have 1 yellow card", 1, player.getYellowCards());
    }

    @Test
    public void testMatchResultLoss() {
        String[] scorersTeam2 = {"Jane Doe"};
        String[] assistersTeam2 = {};
        String[] yellowCardsTeam2 = {};
        String[] redCardsTeam2 = {};

        Match match = new Match(team1, team2, 1, 2);
        match.play(new String[]{}, new String[]{}, new String[]{}, new String[]{}, 
                   scorersTeam2, assistersTeam2, yellowCardsTeam2, redCardsTeam2);
        
        assertEquals("Team A should have 0 points", 0, team1.getStats().getPoints());
        assertEquals("Team B should have 3 points", 3, team2.getStats().getPoints());
    }

    @Test
    public void testGoalDifferenceCalculation() {
        Match match = new Match(team1, team2, 3, 1);
        match.play(new String[]{"Player 1"}, new String[]{}, new String[]{}, new String[]{}, 
                   new String[]{}, new String[]{}, new String[]{}, new String[]{});

        assertEquals("Team A goal difference should be 2", 2, team1.getStats().getGoalDifference());
        assertEquals("Team B goal difference should be -2", -2, team2.getStats().getGoalDifference());
    }


}