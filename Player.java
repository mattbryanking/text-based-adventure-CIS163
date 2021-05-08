

public class Player extends Creature {

    /** current experience points of player, used to level up */
    int XP;

    /** max health of player, cannot heal past this amount */
    int maxHealth;
   
    /******************************************************************
     * Constructor for player class, calls creature (parent) 
     * constructor. sets health and strength to
     * inputted values, and sets tick values and XP to 0.
     * 
     * @param health the starting / max health of the player
     * @param strength the starting strength of the player
     * @throws NullPointerException if any values are null
     * @throws IndexOutOfBoundsException if any values are negative
     *****************************************************************/
    public Player(int health, int strength) {
        super(health, strength);
        maxHealth = health;
        XP = 0;
    }

    /******************************************************************
     * Increases health of player by specified amount. Health **IS
     * NOT ALLOWED** to increase past max health of player. If amount
     * inputted would heal past that value, health is simply reset to 
     * max. This will remove the heal spell from the player, making it 
     * null. This does not work if canCast() fails.
     * 
     * @param amt specified amount of health to give to player
     * @throws IndexOutOfBoundsException if amt is negative
     * @return int to signal if health was reset to max (1 if health
     * healed to max, 0 otherwise)
    *****************************************************************/
    public int healPlayer(int amt) {
        if (amt < 0) {
            throw new IndexOutOfBoundsException("Cannot heal negative amount!");
        }
        if (health + amt > maxHealth) {
            health = maxHealth;
            spell = null;
            return 1;
        }
        else {
            health += amt;
            spell = null;
            return 0;
        }
    }
    
    /******************************************************************
     * Increases XP of player by specified amount. This method is used
     * with getXPValue() in creature class.
     * 
     * @param amt specified amount of XP to give to player
     * @throws IndexOutOfBoundsException if amt is negative
    *****************************************************************/
    public void addXP(int amt) {
        if (amt < 0) {
            throw new IndexOutOfBoundsException("Cannot add negative amount of XP!");
        }
        XP += amt;
    }

    /******************************************************************
     * Gets current XP of player.
     * 
     * @return int representing current XP of player
     *****************************************************************/
    public int getXP() {
        return XP;
    }

    /******************************************************************
     * Returns a boolean representing if player can level up (XP is 150
     * or greater).
     * 
     * @return true if player can level up
     *****************************************************************/
    public boolean canLevelUp() {
        return (XP >= 150);
    }

    /******************************************************************
     * Gets max health of player.
     * 
     * @return int representing max health of player
     *****************************************************************/
    public int getMaxHealth() {
        return maxHealth;
    }

     /******************************************************************
     * Levels up player. Subtracts 150 XP from player, adds 5 to
     * strength and 10 to player's max health. Fully heals player and
     * gives player a spell of their choice. In arena implementation,
     * the player can choose to not aquire a new spell, in which case
     * their current spell will be used in the parameter of the method
     * (which is why no error is thrown if type is null). Will not work
     * if canLevelUp() fails.
     * 
     * @param type spell to give to player
     *****************************************************************/
    public void levelUp(SpellType type) {
        if (canLevelUp()) {
            XP -= 150;
            strength += 5;    
            maxHealth += 10;
            health = maxHealth;
            giveSpell(type);
        }
    }
}
