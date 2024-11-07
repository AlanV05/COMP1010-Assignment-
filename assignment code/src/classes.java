import java.util.ArrayList;
import java.util.List;

// Stats class to handle goals and points tracking
class Stats {
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    public Stats() {
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.points = 0;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    public void addMatchResult(int goalsFor, int goalsAgainst, boolean isWin, boolean isDraw) {
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
        if (isWin) {
            this.points += 3;
        } else if (isDraw) {
            this.points += 1;
        }
    }
}

// Player class to store player details and goal information
class Player {
    private String name;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;


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
    
    public int getAssists() {
        return assists;
    }
    
    public void addAssists(int assists) {
        this.assists += assists;
    }
    
    public int getYellowCards() {
        return yellowCards;
    }
    
    public void addYellowCard() {
        this.yellowCards += 1;
    }
    
    public int getRedCards() {
        return redCards;
    }
    
    public void addRedCard() {
        this.redCards += 1;
    }
    
}

// Team class now uses Stats class to track statistics
class Team {
    private String name;
    private Stats stats;
    private ArrayList<Player> players;

    public Team(String name) {
        this.name = name;
        this.stats = new Stats();
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Stats getStats() {
        return stats;
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

    public void addMatchResult(int goalsFor, int goalsAgainst, boolean isWin, boolean isDraw, 
                           String[] scorers, String[] assisters, String[] yellowCarded, String[] redCarded) {
    stats.addMatchResult(goalsFor, goalsAgainst, isWin, isDraw);

    for (String scorerName : scorers) {
        Player scorer = findPlayerByName(scorerName.trim());
        if (scorer != null) {
            scorer.addGoals(1);
        }
    }

    for (String assisterName : assisters) {
        Player assister = findPlayerByName(assisterName.trim());
        if (assister != null) {
            assister.addAssists(1);
        }
    }

    for (String yellowCardedName : yellowCarded) {
        Player player = findPlayerByName(yellowCardedName.trim());
        if (player != null) {
            player.addYellowCard();
        }
    }

    for (String redCardedName : redCarded) {
        Player player = findPlayerByName(redCardedName.trim());
        if (player != null) {
            player.addRedCard();
        }
    }
}


    public ArrayList<Player> getPlayers() {
        return players;
    }
}

// Match class to encapsulate match details
class Match {
    private Team team1;
    private Team team2;
    private int goalsTeam1;
    private int goalsTeam2;

    public Match(Team team1, Team team2, int goalsTeam1, int goalsTeam2) {
        this.team1 = team1;
        this.team2 = team2;
        this.goalsTeam1 = goalsTeam1;
        this.goalsTeam2 = goalsTeam2;
    }

    public void play(String[] scorersTeam1, String[] assistersTeam1, String[] yellowCardsTeam1, String[] redCardsTeam1, 
    String[] scorersTeam2, String[] assistersTeam2, String[] yellowCardsTeam2, String[] redCardsTeam2) {
    boolean isDraw = goalsTeam1 == goalsTeam2;
    boolean team1Win = goalsTeam1 > goalsTeam2;

    team1.addMatchResult(goalsTeam1, goalsTeam2, team1Win, isDraw, scorersTeam1, assistersTeam1, yellowCardsTeam1, redCardsTeam1);
    team2.addMatchResult(goalsTeam2, goalsTeam1, !team1Win && !isDraw, isDraw, scorersTeam2, assistersTeam2, yellowCardsTeam2, redCardsTeam2);
    }

}

// League class now supports recursive sub-leagues
class League {
    private String name;
    private List<Team> teams;
    private List<League> subLeagues; // Recursive structure: a League can have sub-leagues

    public League(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
        this.subLeagues = new ArrayList<>();
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void addSubLeague(League subLeague) {
        subLeagues.add(subLeague); // Adding sub-league (recursive)
    }

    public Team findTeamByName(String name) {
        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        // Search in sub-leagues recursively
        for (League subLeague : subLeagues) {
            Team team = subLeague.findTeamByName(name);
            if (team != null) {
                return team;
            }
        }
        return null;
    }

    public String getLeagueRankings() {
        teams.sort((team1, team2) -> {
            int pointDiff = team2.getStats().getPoints() - team1.getStats().getPoints();
            if (pointDiff != 0) {
                return pointDiff;
            }
            return team2.getStats().getGoalDifference() - team1.getStats().getGoalDifference();
        });

        StringBuilder sb = new StringBuilder();
        sb.append("League Table:\n");
        for (Team team : teams) {
            sb.append(team.getName()).append(" - ").append(team.getStats().getPoints())
                    .append(" points, Goal Difference: ").append(team.getStats().getGoalDifference()).append("\n");
        }

        // Recursively append rankings from sub-leagues
        for (League subLeague : subLeagues) {
            sb.append(subLeague.getLeagueRankings());
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
    
    public String getTopAssisters() {
        List<Player> allPlayers = new ArrayList<>();
        for (Team team : teams) {
            allPlayers.addAll(team.getPlayers());
        }
    
        allPlayers.sort((p1, p2) -> p2.getAssists() - p1.getAssists());
    
        StringBuilder sb = new StringBuilder();
        sb.append("Top Assisters:\n");
        for (Player player : allPlayers) {
            if (player.getAssists() > 0) {
                sb.append(player.getName()).append(" - ").append(player.getAssists()).append(" assists\n");
            }
        }
    
        return sb.toString();
    }
    
    public String getCardStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player Card Stats:\n");
    
        for (Team team : teams) {
            for (Player player : team.getPlayers()) {
                if (player.getYellowCards() > 0) {
                    sb.append(player.getName()).append(" - ").append(player.getYellowCards()).append(" yellow cards\n");
                }
                if (player.getRedCards() > 0) {
                    sb.append(player.getName()).append(" - ").append(player.getRedCards()).append(" red cards\n");
                }
            }
        }
    
        return sb.toString();
    }
    
}

