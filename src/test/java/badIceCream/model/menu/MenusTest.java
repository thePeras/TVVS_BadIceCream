package badIceCream.model.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenusTest {
    @Test
    public void testPauseMenu() {
        PauseMenu menu = new PauseMenu();
        assertFalse(menu.isSelectedMenu());
        assertTrue(menu.isSelectedResume());
        menu.nextEntry();
        assertTrue(menu.isSelectedMenu());
        assertFalse(menu.isSelectedResume());
    }

    @Test
    public void testGameOverMenu() {
        GameOverMenu menu = new GameOverMenu();
        assertTrue(menu.isSelectedPlayAgain());
        assertFalse(menu.isSelectedQuitToMainMenu());
        menu.nextEntry();
        assertFalse(menu.isSelectedPlayAgain());
        assertTrue(menu.isSelectedQuitToMainMenu());
    }

    @Test
    public void testMainMenu() {
        MainMenu menu = new MainMenu();
        assertTrue(menu.isSelectedStart());
        assertFalse(menu.isSelectedInstructions());
        assertFalse(menu.isSelectedExit());
        menu.nextEntry();
        assertFalse(menu.isSelectedStart());
        assertTrue(menu.isSelectedInstructions());
        assertFalse(menu.isSelectedExit());
        menu.nextEntry();
        assertFalse(menu.isSelectedStart());
        assertFalse(menu.isSelectedInstructions());
        assertTrue(menu.isSelectedExit());
    }

    //TODO: See a better solution here
    @Test
    public void testSelectLevelMenu() {
        SelectLevelMenu menu = new SelectLevelMenu();
        assertTrue(menu.isSelectedLevel1());
        assertFalse(menu.isSelectedLevel2());
        assertFalse(menu.isSelectedLevel3());
        assertFalse(menu.isSelectedLevel4());
        assertFalse(menu.isSelectedLevel5());
        menu.nextEntry();
        assertFalse(menu.isSelectedLevel1());
        assertTrue(menu.isSelectedLevel2());
        assertFalse(menu.isSelectedLevel3());
        assertFalse(menu.isSelectedLevel4());
        assertFalse(menu.isSelectedLevel5());
        menu.nextEntry();
        assertFalse(menu.isSelectedLevel1());
        assertFalse(menu.isSelectedLevel2());
        assertTrue(menu.isSelectedLevel3());
        assertFalse(menu.isSelectedLevel4());
        assertFalse(menu.isSelectedLevel5());
        menu.nextEntry();
        assertFalse(menu.isSelectedLevel1());
        assertFalse(menu.isSelectedLevel2());
        assertFalse(menu.isSelectedLevel3());
        assertTrue(menu.isSelectedLevel4());
        assertFalse(menu.isSelectedLevel5());
        menu.nextEntry();
        assertFalse(menu.isSelectedLevel1());
        assertFalse(menu.isSelectedLevel2());
        assertFalse(menu.isSelectedLevel3());
        assertFalse(menu.isSelectedLevel4());
        assertTrue(menu.isSelectedLevel5());
    }

    @Test
    public void LevelCompletedMenu() {
        LevelCompletedMenu menu = new LevelCompletedMenu();
        assertTrue(menu.isSelectedNextLevel());
        assertFalse(menu.isSelectedQuitToMainMenu());
        menu.nextEntry();
        assertFalse(menu.isSelectedNextLevel());
        assertTrue(menu.isSelectedQuitToMainMenu());
    }
}

