/**
 * Start starts the game
 * 
 * Only contains the main method to play
 *
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
 */
public class Start
{
    private Start() {
    }

    public static void main(String[] args) { 
        Game starter = new Game();
        starter.play();
    }
}
