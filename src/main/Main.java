package main;

//import cardgame.Card;
import cardgame.Deck;
import blackjack.Player;
import blackjack.DealerPlayer;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{

    public static void main(String[] args) throws IOException 
    {
        BufferedReader br = 
            new BufferedReader(new InputStreamReader(System.in));
        
        Deck deck = new Deck(true);
        ArrayList<Player> players = new ArrayList<Player>(0);

        int numPlayers;
        System.out.println("How many players? (Max of 10 players allowed)");
        numPlayers = Integer.parseInt(br.readLine());
        if(numPlayers < 1 || numPlayers > 10)
        {
            throw new IllegalArgumentException("invalid number of players ("
                + numPlayers + ")");
        }

        for(int i = 1; i <= numPlayers; i++)
        {
            System.out.println("Name of player" + i + ": ");
            String name = br.readLine();
            players.add(new Player(name, deck.draw(), deck.draw()));
        }
        System.out.println("==============");
        
        DealerPlayer dealer = new DealerPlayer(deck.draw(), deck.draw());
        System.out.println("Dealer's card: " + dealer.getPocketCard().toString());
        System.out.println("==============");

        for (Player player : players)
        {
            System.out.print(player.printFormatted());
            while (player.getState() == "canHit") 
            {
                System.out.print(player.getName() + "'s turn: ");
                String decision = br.readLine();
                switch(decision)
                {
                    case "hit":
                        player.hit(deck.draw());
                        break;
                    case "stand":
                        player.stand();
                        break;
                    case "double": case "split":
                        System.out.println("Unimplemented feature!");
                        break;
                    case "help":
                        System.out.println("Commands:\nhit\nstand\nhelp");
                    default:
                        System.out.println("Unrecognized input, try again:");
                }

                System.out.print(player.printFormatted());
            }
        }

        System.out.println("Dealer's turn:\n");
        System.out.print(dealer.printFormatted());
        while (dealer.getState() == "canHit") 
        {
            dealer.hit(deck.draw());
            System.out.print(dealer.printFormatted());
        }
        
        int dealerScore = dealer.getScore();
        int playerScore = 0; //iterate through user scores
        if(dealer.getState() != "bust")
        {
            for(Player current : players)
            {
                playerScore = current.getScore();
                if(current.getState() == "bust")
                {
                    System.out.print(current.getName() + " busted!");
                }
                else if(playerScore > dealerScore)
                {
                    System.out.print(current.getName() + " won over the Dealer!");
                }
                else if(playerScore == dealerScore)
                {
                    System.out.print(current.getName() + " tied with the Dealer!");
                }
                else
                {
                    System.out.print(current.getName() + " lost to the Dealer!");
                }
                System.out.println(" (Score of " + current.getScore() + ")");
            }
        }
        else
        {
            System.out.println("Dealer Busted!");
            for(Player current : players)
            {
                if(current.getState() != "bust") 
                {
                    System.out.println(current.getName() + " won over the Dealer!");
                }
                else
                {
                    System.out.println(current.getName() + " busted!");
                }
            }
        }
    }

}
