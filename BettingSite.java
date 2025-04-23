import java.util.*;

class Match {
    int id;
    String teamA;
    String teamB;
    String winner;

    public Match(int id, String teamA, String teamB) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.winner = null; // Match not finished yet
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return id + ": " + teamA + " vs " + teamB + (winner != null ? " | Winner: " + winner : "");
    }
}

class Bet {
    int matchId;
    String chosenTeam;
    double amount;

    public Bet(int matchId, String chosenTeam, double amount) {
        this.matchId = matchId;
        this.chosenTeam = chosenTeam;
        this.amount = amount;
    }

    public boolean isWinningBet(Match match) {
        return match.winner != null && match.winner.equalsIgnoreCase(chosenTeam);
    }
}

public class BettingSite {
    static List<Match> matches = new ArrayList<>();
    static List<Bet> bets = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMatches();

        while (true) {
            System.out.println("\n--- Betting Site ---");
            System.out.println("1. View Matches");
            System.out.println("2. Place a Bet");
            System.out.println("3. Set Match Result");
            System.out.println("4. View Bets");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    viewMatches();
                    break;
                case 2:
                    placeBet();
                    break;
                case 3:
                    setMatchResult();
                    break;
                case 4:
                    viewBets();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void initializeMatches() {
        matches.add(new Match(1, "Team Alpha", "Team Beta"));
        matches.add(new Match(2, "Team Delta", "Team Gamma"));
    }

    static void viewMatches() {
        for (Match match : matches) {
            System.out.println(match);
        }
    }

    static void placeBet() {
        System.out.println("Enter Match ID:");
        int matchId = scanner.nextInt();
        scanner.nextLine();

        Match match = findMatchById(matchId);
        if (match == null) {
            System.out.println("Invalid Match ID.");
            return;
        }

        System.out.println("Choose team (" + match.teamA + " / " + match.teamB + "):");
        String team = scanner.nextLine();

        if (!team.equalsIgnoreCase(match.teamA) && !team.equalsIgnoreCase(match.teamB)) {
            System.out.println("Invalid team selection.");
            return;
        }

        System.out.println("Enter bet amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        bets.add(new Bet(matchId, team, amount));
        System.out.println("Bet placed successfully.");
    }

    static void setMatchResult() {
        System.out.println("Enter Match ID to set result:");
        int matchId = scanner.nextInt();
        scanner.nextLine();

        Match match = findMatchById(matchId);
        if (match == null) {
            System.out.println("Invalid Match ID.");
            return;
        }

        System.out.println("Enter winning team (" + match.teamA + " / " + match.teamB + "):");
        String winner = scanner.nextLine();

        if (!winner.equalsIgnoreCase(match.teamA) && !winner.equalsIgnoreCase(match.teamB)) {
            System.out.println("Invalid winner.");
            return;
        }

        match.setWinner(winner);
        System.out.println("Match result updated.");
    }

    static void viewBets() {
        for (Bet bet : bets) {
            Match match = findMatchById(bet.matchId);
            String result = match != null && match.winner != null
                ? (bet.isWinningBet(match) ? "WON" : "LOST")
                : "Pending";
            System.out.println("Bet on Match " + bet.matchId + ": " + bet.chosenTeam +
                ", Amount: $" + bet.amount + ", Result: " + result);
        }
    }

    static Match findMatchById(int id) {
        for (Match match : matches) {
            if (match.id == id) return match;
        }
        return null;
    }
}

    