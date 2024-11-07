import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    private static League league = new League("Premier League");

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Soccer League Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        // Create main tabbed pane
        JTabbedPane mainTabbedPane = new JTabbedPane();

        // Add Team Management as a main tab
        JPanel teamManagementPanel = createTeamManagementPanel();
        mainTabbedPane.addTab("Team Management", teamManagementPanel);

        // Add Game Scores Tab with nested tabs
        JTabbedPane gameScoresTabbedPane = new JTabbedPane();
        JPanel gameScoresPanel = createGameScoresPanel();
        JPanel team1Panel = createTeamPlayersPanel("Team 1");
        JPanel team2Panel = createTeamPlayersPanel("Team 2");
        gameScoresTabbedPane.addTab("Game Scores", gameScoresPanel);
        gameScoresTabbedPane.addTab("Team 1 Players", team1Panel);
        gameScoresTabbedPane.addTab("Team 2 Players", team2Panel);
        mainTabbedPane.addTab("Game Scores", gameScoresTabbedPane);

        // Add Rankings and Stats Tab with nested tabs
        JTabbedPane rankingsStatsTabbedPane = new JTabbedPane();
        JPanel rankingsPanel = createRankingsPanel();
        JPanel playerStatsPanel = createPlayerStatsPanel();
        rankingsStatsTabbedPane.addTab("Rankings", rankingsPanel);
        rankingsStatsTabbedPane.addTab("Player Stats", playerStatsPanel);
        mainTabbedPane.addTab("Rankings and Stats", rankingsStatsTabbedPane);

        // Add the main tabbed pane to the frame
        frame.add(mainTabbedPane);
        frame.setVisible(true);
    }

    // Create Team Management Tab
    private static JPanel createTeamManagementPanel() {
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));

        JTextField teamNameField = new JTextField(15);
        teamPanel.add(new JLabel("Enter Team Name:"));
        teamPanel.add(teamNameField);

        JTextField playerNameField = new JTextField(15);
        teamPanel.add(new JLabel("Enter Player Names (comma-separated):"));
        teamPanel.add(playerNameField);

        JButton addButton = new JButton("Add Team and Players");
        teamPanel.add(addButton);

        // Action listener for adding teams and players
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teamName = teamNameField.getText();
                String[] playerNames = playerNameField.getText().split(",");

                Team team = league.findTeamByName(teamName);
                if (team == null) {
                    team = new Team(teamName);
                    league.addTeam(team);
                }
                for (String playerName : playerNames) {
                    Player player = new Player(playerName.trim());
                    team.addPlayer(player);
                }

                JOptionPane.showMessageDialog(null, "Team and players added successfully!");
                teamNameField.setText("");
                playerNameField.setText("");
            }
        });

        return teamPanel;
    }

    // Create Game Scores Tab (only team names and scores)
    private static JPanel createGameScoresPanel() {
        JPanel gameScoresPanel = new JPanel();
        gameScoresPanel.setLayout(new BoxLayout(gameScoresPanel, BoxLayout.Y_AXIS));

        JTextField team1Field = new JTextField(15);
        JTextField team2Field = new JTextField(15);
        JTextField score1Field = new JTextField(5);
        JTextField score2Field = new JTextField(5);

        gameScoresPanel.add(new JLabel("Enter Team 1 Name:"));
        gameScoresPanel.add(team1Field);
        gameScoresPanel.add(new JLabel("Enter Team 1 Score:"));
        gameScoresPanel.add(score1Field);

        gameScoresPanel.add(new JLabel("Enter Team 2 Name:"));
        gameScoresPanel.add(team2Field);
        gameScoresPanel.add(new JLabel("Enter Team 2 Score:"));
        gameScoresPanel.add(score2Field);

        JButton nextButton = new JButton("Proceed to Player Stats");
        gameScoresPanel.add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Proceed to add players' stats.");
            }
        });

        return gameScoresPanel;
    }

    // Create Team Players Panel (for scorers, assisters, yellow cards, red cards)
    private static JPanel createTeamPlayersPanel(String team) {
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));

        JTextField scorersField = new JTextField(15);
        JTextField assistersField = new JTextField(15);
        JTextField yellowCardsField = new JTextField(15);
        JTextField redCardsField = new JTextField(15);

        teamPanel.add(new JLabel(team + " Scorers (comma-separated):"));
        teamPanel.add(scorersField);
        teamPanel.add(new JLabel(team + " Assisters (comma-separated):"));
        teamPanel.add(assistersField);
        teamPanel.add(new JLabel(team + " Yellow Cards (comma-separated):"));
        teamPanel.add(yellowCardsField);
        teamPanel.add(new JLabel(team + " Red Cards (comma-separated):"));
        teamPanel.add(redCardsField);

        JButton submitButton = new JButton("Submit Player Stats");
        teamPanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, team + " Player stats submitted.");
            }
        });

        return teamPanel;
    }

    // Create Rankings Tab (league rankings)
    private static JPanel createRankingsPanel() {
        JPanel rankingsPanel = new JPanel();
        rankingsPanel.setLayout(new BoxLayout(rankingsPanel, BoxLayout.Y_AXIS));

        JButton refreshButton = new JButton("Refresh Rankings");
        rankingsPanel.add(refreshButton);

        JTextArea rankingsArea = new JTextArea(10, 30);
        rankingsArea.setEditable(false);
        rankingsPanel.add(new JScrollPane(rankingsArea));

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankingsArea.setText(league.getLeagueRankings());
            }
        });

        return rankingsPanel;
    }

    // Create Player Stats Tab (top scorers, assisters, and card stats)
    private static JPanel createPlayerStatsPanel() {
        JPanel playerStatsPanel = new JPanel();
        playerStatsPanel.setLayout(new BoxLayout(playerStatsPanel, BoxLayout.Y_AXIS));

        JButton topScorersButton = new JButton("View Top Scorers");
        playerStatsPanel.add(topScorersButton);

        JTextArea scorersArea = new JTextArea(5, 30);
        scorersArea.setEditable(false);
        playerStatsPanel.add(new JScrollPane(scorersArea));

        JButton topAssistersButton = new JButton("View Top Assisters");
        playerStatsPanel.add(topAssistersButton);

        JTextArea assistersArea = new JTextArea(5, 30);
        assistersArea.setEditable(false);
        playerStatsPanel.add(new JScrollPane(assistersArea));

        JButton cardStatsButton = new JButton("View Player Card Stats");
        playerStatsPanel.add(cardStatsButton);

        JTextArea cardStatsArea = new JTextArea(5, 30);
        cardStatsArea.setEditable(false);
        playerStatsPanel.add(new JScrollPane(cardStatsArea));

        // Action listener for viewing top scorers
        topScorersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scorersArea.setText(league.getTopScorers());
            }
        });

        // Action listener for viewing top assisters
        topAssistersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assistersArea.setText(league.getTopAssisters());
            }
        });

        // Action listener for viewing player card stats
        cardStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardStatsArea.setText(league.getCardStats());
            }
        });

        return playerStatsPanel;
    }
}