
import sprites.Obstacle;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alexgriese on 5/31/15.
 */
public class ObstacleGenerator {
    public ObstacleGenerator() {
        Obstacle obstacle = new Obstacle();
        obstacle.setPosition(800,300);
        obstacle.genRandomSize();
    }

    public List<Obstacle>  genMultipleObstacles() {
        List<Obstacle> obstacles = new ArrayList<Obstacle>();

        Random random = new Random();
        int randInt = random.nextInt(4);
        while (randInt == 0) randInt = random.nextInt(4);
        for (int i = 0; i < randInt; i ++) obstacles.add(new Obstacle());
        return obstacles;
    }
}
