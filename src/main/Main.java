package main;

//import cardgame.Card;
import cardgame.Deck;
import blackjack.Player;
import blackjack.DealerPlayer;
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
        int numPlayers = 0;

        Player player = new Player("George", deck.draw(), deck.draw());
        DealerPlayer dealer = new DealerPlayer(deck.draw(), deck.draw());

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
                default:
                    System.out.println("Unrecognized input, try again:");
            }
            
            System.out.print(player.printFormatted());
        }
        

        System.out.println("Dealer's turn:\n");
        System.out.print(dealer.printFormatted());
        while (dealer.getState() == "canHit") 
        {
            dealer.hit(deck.draw());
            System.out.print(dealer.printFormatted());
        }
        dealer.stand();

    }

}
