public class Creature {
    
    /** current health value of creature */
    int health;

    /** strength value of creature */
    int strength;

    /** current spell posessed by creature */
    SpellType spell = null;

    /** how many turns remaining of creature being frozen */
    private int frostTick;

    /** how many turns remaining of creature being burned */
    private int fireTick;

    /** max health of creature, used for XP calculation */
    private int XPHealth;

    /******************************************************************
     * Constructor for creature class, sets health and strength to
     * inputted values, and sets tick values to 0.
     * 
     * @param health the starting / max health of the creature
     * @param strength the starting strength of the creature
     * @throws NullPointerException if any values are null
     * @throws IndexOutOfBoundsException if any values are negative
     *****************************************************************/
    public Creature(int health, int strength) {
        if (health < 0) {
            throw new IndexOutOfBoundsException("Health cannot be negative!");
        }
        if ((Integer) health == null) {
            throw new NullPointerException("Health is null!");
        }
        if (strength < 0) {
            throw new IndexOutOfBoundsException("Strength cannot be negative!");
        }
        if ((Integer) strength == null) {
            throw new NullPointerException("Strength is null!");
        }

        this.health = health;
        this.strength = strength;
        frostTick = 0;
        fireTick = 0;
        XPHealth = health;
    }

    /******************************************************************
     * Gets current health of creature.
     * 
     * @return int representing current health of creature
     *****************************************************************/
    public int getHealth() {
        return health;
    }

     /******************************************************************
     * Sets health of creature to desired amount. Does not work if 
     * health inputted is negative or null.
     * 
     * @param health desired health for creature
     * @throws IndexOutOfBoundsException if desired health is negative
     * @throws NullPointerException if desired health is null
     *****************************************************************/
    public void setHealth(int health) {
        if (health < 0) {
            throw new IndexOutOfBoundsException("Health cannot be negative!");
        }
        if ((Integer) health == null) {
            throw new NullPointerException("Health is null!");
        }
        this.health = health;
    }

    /******************************************************************
     * Gets current strength of creature.
     * 
     * @return int representing current strength of creature
     *****************************************************************/
    public int getStrength() {
        return strength;
    }

    /******************************************************************
     * Sets strength of creature to desired amount. Does not work if 
     * strength inputted is negative or null.
     * 
     * @param strength desired strength for creature
     * @throws IndexOutOfBoundsException if desired strength is negative
     * @throws NullPointerException if desired strength is null
     *****************************************************************/
    public void setStrength(int strength) {
        if (strength < 0) {
            throw new IndexOutOfBoundsException("Strength cannot be negative!");
        }
        if ((Integer) strength == null) {
            throw new NullPointerException("Strength is null!");
        }
        this.strength = strength;
    }

    /******************************************************************
     * Gets amount of XP that is gained from killing a monster (max 
     * health + strength), used with addXP() method in player class to 
     * add XP to player after every kill.
     * 
     * @return total XP value of creature
     *****************************************************************/
    public int getXPValue() {
        return XPHealth + strength;
    }

    /******************************************************************
     * Calculates a random numerical value to represent damage that 
     * will be done to a targeted creature. Typically used with hurt() 
     * method. Max damage is dictated by the strength of the creature
     * who is attacking. Only works if creature is NOT frozen.
     * 
     * @return int representing damage to be done to target creature
     *****************************************************************/
    public int attack() {
        if (!isFrozen()) {
            return (int)(Math.random() * strength);
        }
        else {
            return 0;
        }
    }

    /******************************************************************
     * Decrements health of target creature by inputted amount. Cannot
     * be used to increase health (does not work with negative 
     * damage).
     * 
     * @param damage health to be taken off (usually calculated with
     * attack() method)
     * @throws IndexOutOfBoundsException if damage is negative
     *****************************************************************/
    public void hurt(int damage) {
        if (damage < 0) {
            throw new IndexOutOfBoundsException("Damage cannot be negative!");
        }
        health -= damage;
    }

    /******************************************************************
     * Returns a boolean representing if creature is dead (health is 
     * 0 or less).
     * 
     * @return true if creature is dead
     *****************************************************************/
    public boolean isDead() {
        return health <= 0;
    }

    /******************************************************************
     * Gives specified spell to creature. Only accepts spells listed 
     * in the SpellType enum. If a creature already has a spell, 
     * using this method will replace their current spell.
     * 
     * @param type spell to give to creature
     * @throws NullPointerException if spell is null
     *****************************************************************/
    public void giveSpell(SpellType type) {
        spell = type;
    }

    /******************************************************************
     * Checks to see if creature can cast a current spell (has that
     * spell available). Does not check if creature is frozen.
     * 
     * @param type spell to cast
     * @return true if creature can cast desired spell
     * @throws NullPointerException if spell is null
     *****************************************************************/
    public boolean canCast(SpellType type) {
        if (type == null) {
            throw new NullPointerException("Spell is null!");
        }
        return spell == type;
    }

    /******************************************************************
     * Checks to see if creature currently has a spell. This is the
     * exact same as using the canCast() method on every spell. 
     * 
     * @return true if creature has a spell
     *****************************************************************/
    public boolean hasSpell() {
        if (canCast(SpellType.Fire) || canCast(SpellType.Frost) ||
            canCast(SpellType.Heal) || canCast(SpellType.Lightning)) {
            return true;
        }
        return false;

    }

    /******************************************************************
     * Freezes target, restricting them from moving for 2-4 turns.
     * Sets frostTick to random number in aformentioned range, and 
     * removes frost spell from creature, making it null. This does
     * not work if canCast() fails.
     * 
     * @param target desired creature to freeze
     * @throws IllegalArgumentException if spell cannot be cast
     * @throws NullPointerException if target is null
     *****************************************************************/
    public void freeze(Creature target) {
        if (!canCast(SpellType.Frost)) {
            throw new IllegalArgumentException("Cannot cast this spell!");
        }
        if (target == null) {
            throw new NullPointerException("Target is null!");
        }
        target.frostTick = (int) ((Math.random() * (2)) + 2);
        spell = null;
    }

    /******************************************************************
     * Checks to see if target is frozen by using frostTick variable.
     * 
     * @return true if target is frozen
     *****************************************************************/
    public boolean isFrozen() {
        return frostTick > 0;
    }

    /******************************************************************
     * Decreases frostTick by one. This is called after every turn 
     * loop and has no effect if target is not frozen (if frostTick is
     * already at 0).
     *****************************************************************/
    public void decFreezeTimer() {
        if (frostTick > 0) {
            frostTick--;
        }
    }

    /******************************************************************
     * Sets target on fire, dealing 10 damage to them for 2-4 turns.
     * Sets fireTick to random number in aformentioned range, and 
     * removes fire spell from creature, making it null. This does
     * not work if canCast() fails.
     * 
     * @param target desired creature to burn
     * @throws IllegalArgumentException if spell cannot be cast
     * @throws NullPointerException if target is null
     *****************************************************************/
    public void setOnFire(Creature target) {
        if (!canCast(SpellType.Fire)) {
            throw new IllegalArgumentException("Cannot cast this spell!");
        }
        if (target == null) {
            throw new NullPointerException("Target is null!");
        }
        target.fireTick = (int) ((Math.random() * (2)) + 2);
        spell = null;
    }

    /******************************************************************
     * Checks to see if target is on fire by using fireTick variable.
     * 
     * @return true if target is on fire
     *****************************************************************/
    public boolean isOnFire() {
        return fireTick > 0;
    }

    /******************************************************************
     * Decreases fireTick by one. This is called after every turn loop
     * and has no effect if target is not frozen (if fireTick is 
     * already at 0).
     *****************************************************************/
    public void decFireTimer() {
        if (fireTick > 0) {
            fireTick--;
        }
    }

    /******************************************************************
     * Increases health of creature by specified amount. Health **IS
     * ALLOWED** to increase past original health of creature. Since 
     * creatures use it as soon as they can, the method would be useless 
     * if this was not allowed. This will remove heal spell from 
     * creature, making it null. This does not work if canCast() fails.
     * 
     * @param amt specified amount of health to give to creature
     * @throws IndexOutOfBoundsException if health is negative
     *****************************************************************/
    public void heal(int amt) {
        if (amt < 0) {
            throw new IndexOutOfBoundsException("Cannot heal negative amount!");
        }
        health += amt;
        spell = null;
    }

    /******************************************************************
     * Gets current frostTick value of creature.
     * 
     * @return current frostTick value
     *****************************************************************/
    public int getFrostTick() {
        return frostTick;
    }

    /******************************************************************
     * Gets current fireTick value of creature.
     * 
     * @return current fireTick value
     *****************************************************************/
    public int getFireTick() {
        return fireTick;
    }
}

