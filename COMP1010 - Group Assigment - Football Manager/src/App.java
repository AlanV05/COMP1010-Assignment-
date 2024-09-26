import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    private static League league = new League("Premier League");

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Soccer League Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add Team Management Tab
        JPanel teamPanel = createTeamManagementPanel();
        tabbedPane.addTab("Team Management", teamPanel);

        // Add Match Management Tab
        JPanel matchPanel = createMatchManagementPanel();
        tabbedPane.addTab("Match Management", matchPanel);

        // Add Rankings Tab
        JPanel rankingsPanel = createRankingsPanel();
        tabbedPane.addTab("Rankings", rankingsPanel);

        // Add the tabbed pane to the frame
        frame.add(tabbedPane);
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
        teamPanel.add(new JLabel("Enter Player Name:"));
        teamPanel.add(playerNameField);

        JButton addButton = new JButton("Add Team and Player");
        teamPanel.add(addButton);

        // Action listener for adding teams and players
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teamName = teamNameField.getText();
                String playerName = playerNameField.getText();

                if (!teamName.isEmpty() && !playerName.isEmpty()) {
                    Team team = league.findTeamByName(teamName);
                    if (team == null) {
                        team = new Team(teamName);
                        league.addTeam(team);
                    }
                    team.addPlayer(new Player(playerName));
                    JOptionPane.showMessageDialog(null, "Player " + playerName + " added to Team " + teamName);
                    teamNameField.setText("");
                    playerNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter both team and player names.");
                }
            }
        });

        return teamPanel;
    }

    // Create Match Management Tab
    private static JPanel createMatchManagementPanel() {
        JPanel matchPanel = new JPanel();
        matchPanel.setLayout(new BoxLayout(matchPanel, BoxLayout.Y_AXIS));

        JTextField team1Field = new JTextField(15);
        matchPanel.add(new JLabel("Enter Team 1 Name:"));
        matchPanel.add(team1Field);

        JTextField team2Field = new JTextField(15);
        matchPanel.add(new JLabel("Enter Team 2 Name:"));
        matchPanel.add(team2Field);

        JTextField score1Field = new JTextField(5);
        matchPanel.add(new JLabel("Enter Team 1 Score:"));
        matchPanel.add(score1Field);

        JTextField score2Field = new JTextField(5);
        matchPanel.add(new JLabel("Enter Team 2 Score:"));
        matchPanel.add(score2Field);

        JTextField scorersTeam1Field = new JTextField(15);
        matchPanel.add(new JLabel("Enter Scorers for Team 1 (comma-separated):"));
        matchPanel.add(scorersTeam1Field);

        JTextField scorersTeam2Field = new JTextField(15);
        matchPanel.add(new JLabel("Enter Scorers for Team 2 (comma-separated):"));
        matchPanel.add(scorersTeam2Field);

        JButton matchButton = new JButton("Submit Match Result");
        matchPanel.add(matchButton);

        // Action listener for submitting match results
        matchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String team1Name = team1Field.getText();
                String team2Name = team2Field.getText();
                int score1 = Integer.parseInt(score1Field.getText());
                int score2 = Integer.parseInt(score2Field.getText());
                String[] scorersTeam1 = scorersTeam1Field.getText().split(",");
                String[] scorersTeam2 = scorersTeam2Field.getText().split(",");

                league.playMatch(team1Name, team2Name, score1, score2, scorersTeam1, scorersTeam2);
                JOptionPane.showMessageDialog(null, "Match result recorded!");

                // Clear fields after submission
                team1Field.setText("");
                team2Field.setText("");
                score1Field.setText("");
                score2Field.setText("");
                scorersTeam1Field.setText("");
                scorersTeam2Field.setText("");
            }
        });

        return matchPanel;
    }

    // Create Rankings Tab
    private static JPanel createRankingsPanel() {
        JPanel rankingsPanel = new JPanel();
        rankingsPanel.setLayout(new BoxLayout(rankingsPanel, BoxLayout.Y_AXIS));

        JButton refreshButton = new JButton("Refresh Rankings");
        rankingsPanel.add(refreshButton);

        JTextArea rankingsArea = new JTextArea(10, 30);
        rankingsArea.setEditable(false);
        rankingsPanel.add(new JScrollPane(rankingsArea));

        JButton topScorersButton = new JButton("View Top Scorers");
        rankingsPanel.add(topScorersButton);

        JTextArea scorersArea = new JTextArea(5, 30);
        scorersArea.setEditable(false);
        rankingsPanel.add(new JScrollPane(scorersArea));

        // Action listener for refreshing the league rankings
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankingsArea.setText(league.getLeagueRankings());
            }
        });

        // Action listener for viewing top scorers
        topScorersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scorersArea.setText(league.getTopScorers());
            }
        });

        return rankingsPanel;
    }
}
