package checkers.src;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

    public Main(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        // Hier fügen Sie die Spielzustände hinzu
        addState(new GameState());
    }

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Main("Checkers"));
            app.setDisplayMode(1024, 1024, false);
            app.setTargetFrameRate(60);
            app.setShowFPS(false);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}