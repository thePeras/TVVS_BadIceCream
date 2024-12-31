package badIceCream.viewer;

import badIceCream.GUI.Graphics;
import badIceCream.model.game.elements.Wall;
import net.jqwik.api.*;

import static org.mockito.Mockito.*;

public class WallViewerTest {

    @Property
    void testStepDrawMonster(@ForAll("types") int type) {
        WallViewer wallViewer = new WallViewer();
        Wall wall = mock(Wall.class);

        Graphics graphics = mock(Graphics.class);
        wallViewer.draw(wall, graphics, type);

        if(type == 2){
            verify(graphics, times(1)).drawStoneWall(wall.getPosition());
        }else if(type < 12){
            verify(graphics, times(1)).drawIceWall(wall.getPosition(), type);
        }
}

    @Provide
    public Arbitrary<Integer> types() {
        return Arbitraries.integers().between(1, 12);
    }
}
