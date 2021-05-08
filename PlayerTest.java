

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerTest {

    @Test
    public void healPlayer() {
        Player joe = new Player(50, 20);
        joe.setHealth(10);
        joe.healPlayer(30);
        assertTrue(joe.getHealth() == 40);
    }

    @Test
    public void healPlayerReturn() {
        Player joe = new Player(50, 20);
        joe.setHealth(10);
        assertTrue(joe.healPlayer(30) == 0);
    }

    @Test
    public void healPlayerMax() {
        Player joe = new Player(50, 20);
        joe.setHealth(10);
        joe.healPlayer(100);
        ((Creature) joe).heal(10);
        assertTrue(joe.getHealth() == 50);
    }

    @Test
    public void healPlayerMaxReturn() {
        Player joe = new Player(50, 20);
        joe.setHealth(10);
        assertTrue(joe.healPlayer(100) == 1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void healPlayerNegative() {
        Player joe = new Player(50, 20);
        joe.healPlayer(-100);
    }

    @Test
    public void getXP() {
        Player joe = new Player(50, 20);
        joe.setHealth(10);
        assertTrue(joe.healPlayer(100) == 1);
    }

    @Test
    public void addXP() {
        Player joe = new Player(50, 20);
        joe.addXP(30);
        joe.addXP(10);
        assertTrue(joe.getXP() == 40);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void addXPNegative() {
        Player joe = new Player(50, 20);
        joe.addXP(-100);
    }

    @Test 
    public void canLevelUpTrue() {
        Player joe = new Player(50, 20);
        joe.addXP(150);
        assertTrue(joe.canLevelUp());
    }

    @Test 
    public void canLevelUpFalse() {
        Player joe = new Player(50, 20);
        joe.addXP(20);
        assertFalse(joe.canLevelUp());
    }

    @Test
    public void getMaxHealthDefault() {
        Player joe = new Player(50, 20);
        assertTrue(joe.getMaxHealth() == 50);
    }

    @Test
    public void getMaxHealthDec() {
        Player joe = new Player(50, 20);
        joe.setHealth(20);
        assertTrue(joe.getMaxHealth() == 50);
    }

    @Test
    public void levelUpXP() {
        Player joe = new Player(50, 20);
        joe.addXP(200);
        joe.levelUp(SpellType.Fire);
        assertTrue(joe.getXP() == 50);
    }

    @Test
    public void levelUpStrength() {
        Player joe = new Player(50, 20);
        joe.addXP(200);
        joe.levelUp(SpellType.Fire);
        assertTrue(joe.getStrength() == 25);
    }

    @Test
    public void levelUpMaxHealth() {
        Player joe = new Player(50, 20);
        joe.addXP(200);
        joe.levelUp(SpellType.Fire);
        assertTrue(joe.getMaxHealth() == 60);
    }

    @Test
    public void levelUpHealth() {
        Player joe = new Player(50, 20);
        joe.addXP(200);
        joe.levelUp(SpellType.Fire);
        assertTrue(joe.getHealth() == 60);
    }
}