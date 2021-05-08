
import static org.junit.Assert.*;
import org.junit.*;

public class CreatureTest {

    @Test
    public void getHealth() {
        Creature george = new Creature(50, 20);
        assertTrue(george.getHealth() == 50);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void negativeHealth() {
        Creature george = new Creature(-50, 20);
    }

    @Test (expected = NullPointerException.class)
    public void nullHealth() {
        Integer health = null;
        Creature george = new Creature(health, 20);
    }

    @Test
    public void getStrength() {
        Creature george = new Creature(50, 20);
        assertTrue(george.getStrength() == 20);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void negativeStrength() {
        Creature george = new Creature(50, -20);
    }

    @Test (expected = NullPointerException.class)
    public void nullStrength() {
        Integer strength = null;
        Creature george = new Creature(50, strength);
    }

    @Test
    public void setHealth() {
        Creature george = new Creature(50, 20);
        george.setHealth(10);
        assertTrue(george.getHealth() == 10);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void negativeSetHealth() {
        Creature george = new Creature(50, 20);
        george.setHealth(-100);
    }

    @Test (expected = NullPointerException.class)
    public void nullSetHealth() {
        Integer health = null;
        Creature george = new Creature(50, 20);
        george.setHealth(health);
    }

    @Test
    public void setStrength() {
        Creature george = new Creature(50, 20);
        george.setStrength(10);
        assertTrue(george.getStrength() == 10);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void negativeSetStrength() {
        Creature george = new Creature(50, 20);
        george.setStrength(-100);
    }

    @Test (expected = NullPointerException.class)
    public void nullSetStrength() {
        Integer strength = null;
        Creature george = new Creature(50, 20);
        george.setStrength(strength);
    }

    @Test
    public void defaultSpell() {
        Creature george = new Creature(50, 20);
        assertTrue(george.spell == null);
    }

    @Test
    public void giveFrostHasSpell() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Frost);
        assertTrue(george.spell == SpellType.Frost);
    }

    @Test
    public void giveFrostUseSpellNull() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Frost);
        george.freeze(joe);
        assertTrue(george.spell == null);
    }

    @Test
    public void defaultFrostTick() {
        Creature george = new Creature(50, 20);
        assertTrue(george.getFrostTick() == 0);
    }

    @Test
    public void activeFrostTick() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Frost);
        joe.freeze(george);
        assertTrue(george.getFrostTick() >= 2 && george.getFrostTick() <= 4);
    }

    @Test
    public void decFrostTimer() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Frost);
        joe.freeze(george);
        george.decFreezeTimer();
        assertTrue(george.getFrostTick() >= 1 && george.getFrostTick() <= 3);
    }
    
    @Test
    public void zeroDecFrostTimer() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Frost);
        joe.freeze(george);
        george.decFreezeTimer();
        george.decFreezeTimer();
        george.decFreezeTimer();
        george.decFreezeTimer();
        george.decFreezeTimer();
        assertTrue(george.getFrostTick() == 0);
    }

    @Test
    public void isFrozenTrue() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Frost);
        joe.freeze(george);
        assertTrue(george.isFrozen());
    }

    @Test
    public void isFrozenFalse() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);;
        assertFalse(george.isFrozen());
    }

    
    @Test (expected = IllegalArgumentException.class)
    public void isFrozenCannotCast() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.freeze(george);
    }

    @Test (expected = NullPointerException.class)
    public void isFrozenNull() {
        Player joe = new Player(50, 20);
        Creature george = null;
        joe.giveSpell(SpellType.Frost);
        joe.freeze(george);
    }


    @Test
    public void giveFireHasSpell() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Fire);
        assertTrue(george.spell == SpellType.Fire);
    }

    @Test
    public void giveFireUseSpellNull() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Fire);
        george.setOnFire(joe);
        assertTrue(george.spell == null);
    }

    @Test
    public void defaultFireTick() {
        Creature george = new Creature(50, 20);
        assertTrue(george.getFireTick() == 0);
    }

    @Test
    public void activeFireTick() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Fire);
        joe.setOnFire(george);
        assertTrue(george.getFireTick() >= 2 && george.getFireTick() <= 4);
    }

    @Test
    public void decFireTimer() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Fire);
        joe.setOnFire(george);
        george.decFreezeTimer();
        assertTrue(george.getFireTick() >= 1 && george.getFireTick() <= 3);
    }

    @Test
    public void zeroDecFireTimer() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Fire);
        joe.setOnFire(george);
        george.decFireTimer();
        george.decFireTimer();
        george.decFireTimer();
        george.decFireTimer();
        george.decFireTimer();
        assertTrue(george.getFireTick() == 0);
    }

    @Test
    public void isOnFireTrue() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Fire);
        joe.setOnFire(george);
        assertTrue(george.isOnFire());
    }

    @Test
    public void isOnFireFalse() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);;
        assertFalse(george.isOnFire());
    }

    
    @Test (expected = IllegalArgumentException.class)
    public void isOnFireCannotCast() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.setOnFire(george);
    }

    @Test (expected = NullPointerException.class)
    public void isOnFireNull() {
        Player joe = new Player(50, 20);
        Creature george = null;
        joe.giveSpell(SpellType.Fire);
        joe.setOnFire(george);
    }

    @Test
    public void giveHealHasSpell() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Heal);
        assertTrue(george.spell == SpellType.Heal);
    }

    @Test
    public void giveHealUseSpellNull() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Heal);
        george.heal(10);
        assertTrue(george.spell == null);
    }

    @Test
    public void healHealth() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Heal);
        george.heal(10);
        assertTrue(george.getHealth() == 60);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void healHealthNegative() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Heal);
        george.heal(-10);
    }

    @Test
    public void giveLightningHasSpell() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Lightning);
        assertTrue(george.spell == SpellType.Lightning);
    }

    @Test
    public void canCastFire() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Fire);
        assertTrue(george.canCast(SpellType.Fire));
    }

    @Test
    public void canCastFrost() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Frost);
        assertTrue(george.canCast(SpellType.Frost));
    }

    @Test
    public void canCastHeal() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Heal);
        assertTrue(george.canCast(SpellType.Heal));
    }

    @Test
    public void canCastLightning() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Lightning);
        assertTrue(george.canCast(SpellType.Lightning));
    }

    @Test
    public void canCastFireFalse() {
        Creature george = new Creature(50, 20);
        assertFalse(george.canCast(SpellType.Fire));
    }

    @Test
    public void canCastFrostFalse() {
        Creature george = new Creature(50, 20);
        assertFalse(george.canCast(SpellType.Frost));
    }

    @Test
    public void canCastHealFalse() {
        Creature george = new Creature(50, 20);
        assertFalse(george.canCast(SpellType.Heal));
    }

    @Test
    public void canCastLightningFalse() {
        Creature george = new Creature(50, 20);
        assertFalse(george.canCast(SpellType.Lightning));
    }

    @Test (expected = NullPointerException.class)
    public void canCastNull() {
        Creature george = new Creature(50, 20);
        george.canCast(null);
    }

    @Test
    public void hasSpell() {
        Creature george = new Creature(50, 20);
        george.giveSpell(SpellType.Frost);
        assertTrue(george.hasSpell());
    }

    @Test
    public void hasSpellFalse() {
        Creature george = new Creature(50, 20);
        assertFalse(george.hasSpell());
    }

    @Test
    public void xpValue() {
        Creature george = new Creature(50, 20);
        assertTrue(george.getXPValue() == 70);
    }

    @Test
    public void xpValueDamage() {
        Creature george = new Creature(50, 20);
        george.hurt(20);
        assertTrue(george.getXPValue() == 70);
    }

    @Test
    public void hurt() {
        Creature george = new Creature(50, 20);
        george.hurt(20);
        assertTrue(george.getHealth() == 30);
    }
    
    @Test (expected = IndexOutOfBoundsException.class)
    public void hurtNegative() {
        Creature george = new Creature(50, 20);
        george.hurt(-20);
    }

    @Test
    public void attackValue() {
        Creature george = new Creature(50, 20);
        int attackNum = george.attack();
        assertTrue(attackNum >= 0 && attackNum <= 20);
    }

    @Test
    public void attackFrozen() {
        Player joe = new Player(50, 20);
        Creature george = new Creature(50, 20);
        joe.giveSpell(SpellType.Frost);
        joe.freeze(george);
        int attackNum = george.attack();
        assertTrue(attackNum == 0);
    }

    @Test
    public void isDeadTrue() {
        Creature george = new Creature(50, 20);
        george.hurt(1000);
        assertTrue(george.isDead());
    }

    @Test
    public void isDeadFalse() {
        Creature george = new Creature(50, 20);
        george.hurt(1);
        assertFalse(george.isDead());
    }
}


