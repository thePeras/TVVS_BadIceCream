package badIceCream.viewer;

import badIceCream.GUI.GUI;
import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.game.elements.monsters.Monster;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MonsterViewerTest {

    @Property
    void testStepDrawMonster(@ForAll("types") int type) {
        MonsterViewer monsterViewer = new MonsterViewer();
        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(1, 1));
        when(monster.getType()).thenReturn(type);
        when(monster.getLastAction()).thenReturn(GUI.ACTION.UP);
        when(monster.isRunning()).thenReturn(true);

        Graphics graphics = mock(Graphics.class);
        monsterViewer.draw(monster, graphics, type);

        switch (type) {
            case 1:
                verify(graphics, times(1)).drawDefaultMonster(monster.getPosition(), monster.getLastAction());
                break;
            case 2:
                verify(graphics, times(1)).drawJumperMonster(monster.getPosition(), monster.getLastAction());
                break;
            case 3:
                verify(graphics, times(1)).drawRunnerMonster(monster.getPosition(), monster.getLastAction(), monster.isRunning());
                break;
            case 4:
                verify(graphics, times(1)).drawWallBreakerMonster(monster.getPosition(), monster.getLastAction());
                break;
        }
    }

    @Provide
    public Arbitrary<Integer> types() {
        return Arbitraries.integers().between(1, 5);
    }
}
