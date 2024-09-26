import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int goals;

    public Player(String name) {
        this.name = name;
        this.goals = 0;
    }

    public String getName() {
        return name;
    }

    public int getGoals() {
        return goals;
    }

    public void addGoals(int goals) {
        this.goals += goals;
    }
}

class Team {
    private String name;
    private int points;
    private int goalsFor;
    private int goalsAgainst;
    private ArrayList<Player> players;

    public Team(String name) {
        this.name = name;
        this.points = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player findPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public void addMatchResult(int goalsFor, int goalsAgainst, boolean isWin, boolean isDraw, String[] scorers) {
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;

        // Add points based on match result
        if (isWin) {
            this.points += 3;
        } else if (isDraw) {
            this.points += 1;
        }

        // Update goal count for each scorer
        for (String scorerName : scorers) {
            Player scorer = findPlayerByName(scorerName.trim());
            if (scorer != null) {
                scorer.addGoals(1);
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}

class League {
    private String name;
    private ArrayList<Team> teams;

    public League(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public Team findTeamByName(String name) {
        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }

    public void playMatch(String team1Name, String team2Name, int goals1, int goals2, String[] scorersTeam1, String[] scorersTeam2) {
        Team team1 = findTeamByName(team1Name);
        Team team2 = findTeamByName(team2Name);

        if (team1 != null && team2 != null) {
            boolean isDraw = goals1 == goals2;
            boolean team1Win = goals1 > goals2;

            // Update match results
            team1.addMatchResult(goals1, goals2, team1Win, isDraw, scorersTeam1);
            team2.addMatchResult(goals2, goals1, !team1Win && !isDraw, isDraw, scorersTeam2);
        }
    }

    public String getLeagueRankings() {
        teams.sort((team1, team2) -> {
            int pointDiff = team2.getPoints() - team1.getPoints();
            if (pointDiff != 0) {
                return pointDiff;
            }
            return team2.getGoalDifference() - team1.getGoalDifference();
        });

        StringBuilder sb = new StringBuilder();
        sb.append("League Table:\n");
        for (Team team : teams) {
            sb.append(team.getName()).append(" - ").append(team.getPoints())
                    .append(" points, Goal Difference: ").append(team.getGoalDifference()).append("\n");
        }
        return sb.toString();
    }

    public String getTopScorers() {
        List<Player> allPlayers = new ArrayList<>();
        for (Team team : teams) {
            allPlayers.addAll(team.getPlayers());
        }

        allPlayers.sort((p1, p2) -> p2.getGoals() - p1.getGoals());

        StringBuilder sb = new StringBuilder();
        sb.append("Top Scorers:\n");
        for (Player player : allPlayers) {
            if (player.getGoals() > 0) {
                sb.append(player.getName()).append(" - ").append(player.getGoals()).append(" goals\n");
            }
        }
        return sb.toString();
    }
}

// Comments for an Update: 
// Change the classes based on the recieved feedback (Can find it in the readme file)
// Make the goals randomaly generatated rather than user input. Achieves the aim of the code? (Futher Discuss with team in person)!!

// What to change regarding classes: 
// Create a new class could "Statistics" or "Stats" where the goal difference, points, goals for and againts, are recorded. 
// This makes the classes have a cleaner and more readable sensable structure.
// Similarly with league Class move stats related methods to the Stats class, again making it organised. 