package badIceCream.viewer;

import badIceCream.GUI.GUI;
import badIceCream.GUI.Graphics;
import badIceCream.model.Position;
import badIceCream.model.game.elements.fruits.Fruit;
import badIceCream.model.game.elements.monsters.Monster;
import net.jqwik.api.*;

import static org.mockito.Mockito.*;

public class FruitViewerTest {

    @Property
    void testStepDrawMonster(@ForAll("types") int type) {
        FruitViewer fruitViewer = new FruitViewer();
        Fruit fruit = mock(Fruit.class);

        Graphics graphics = mock(Graphics.class);
        fruitViewer.draw(fruit, graphics, type);

        switch (type) {
            case 1:
                verify(graphics, times(1)).drawAppleFruit(fruit.getPosition());
                break;
            case 2:
                verify(graphics, times(1)).drawBananaFruit(fruit.getPosition());
                break;
            case 3:
                verify(graphics, times(1)).drawCherryFruit(fruit.getPosition());
                break;
            case 4:
                verify(graphics, times(1)).drawPineappleFruit(fruit.getPosition());
                break;
            case 5:
                verify(graphics, times(1)).drawStrawberryFruit(fruit.getPosition());
                break;
        }
    }

    @Provide
    public Arbitrary<Integer> types() {
        return Arbitraries.integers().between(1, 6);
    }
}
