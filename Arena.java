import java.util.Scanner;
import java.util.ArrayList;

/********************************************************************************
 * Class to begin and maintain gameplay loop, behind-the-scenes functions, 
 * take inputs from player, and many (many) other things. 
 * 
 * DO NOT RUN WHILE INSTANCE IS ALREADY OPEN. MAKE SURE TO KILL ACTIVE TERMINAL 
 * BEFORE RESTARTING. THE CODE DISPLAYED WHEN STARTING AN INSTANCE WILL BE 
 * TAKEN AS AN INPUT, CAUSING "INPUT NOT RECOGNIZED" TO DISPLAY RAPIDLY.
 * 
 * A few changes were made to XP, lightning and healing spells, etc. to balance
 * gameplay and increase quality of life :) 
 *
 * @author Matthew King
 * @version 4/11/2020
 ******************************************************************************/

public class Arena extends Output {

    /** player object to hold information about user character */
    Player player;

    /** counter to keep track of how many turns have gone by */
    int turnCount = 0;

    /** northern kill tracker, used to prevent 'hidden' enemies from attacking */
    boolean northKill;

    /** eastern kill tracker, used to prevent 'hidden' enemies from attacking */
    boolean eastKill;

    /** southern kill tracker, used to prevent 'hidden' enemies from attacking */
    boolean southKill;

    /** western kill tracker, used to prevent 'hidden' enemies from attacking */
    boolean westKill;

    /** list of all monsters appearing from the north */
    Queue<Creature> northMonsters;

    /** list of all monsters appearing from the east */
    Queue<Creature> eastMonsters;

    /** list of all monsters appearing from the south */
    Queue<Creature> southMonsters;

    /** list of all monsters appearing from the west */
    Queue<Creature> westMonsters;

    /** list of all four queues, used for iterating through all directions */
    ArrayList<Queue<Creature>> directionList = new ArrayList<Queue<Creature>>();

    /********************************************************************************
     * Constructor instantiates player object and clears/prepares lists to hold
     * monsters.
     ******************************************************************************/
    private Arena() {
        player = new Player(100, 50);
        northMonsters = new Queue<Creature>();
        eastMonsters = new Queue<Creature>();
        southMonsters = new Queue<Creature>();
        westMonsters = new Queue<Creature>();

        directionList.add(northMonsters);
        directionList.add(eastMonsters);
        directionList.add(southMonsters);
        directionList.add(westMonsters);

        northKill = false;
        eastKill = false;
        southKill = false;
        westKill = false;
    }

    /********************************************************************************
     * Rotates through standard gameloop and iterrates the turn counter after every
     * loop. Instantly creates a monster for the first round, then the rotation
     * begins with the players turn as to correctly time the game over sequence.
     * Once the loop is exited, the correct game over text will be displayed.
     ******************************************************************************/
    private void gameLoop() {
        createMonster();

        // continues to loop until game is over
        while (!isGameOver()) {
            playersTurn();
            monstersTurn();
            if (!isGameOver()) {
                createMonster();
            }
            turnCount++;
        }

        // checks to see if the game over was caused by a win or loss
        if (player.isDead()) {
            printGameOver(turnCount);
        } 
        else {
            printWin(turnCount);
        }
    }

    /********************************************************************************
     * Checks if the game should end, either by the death of the player or by the
     * elimination of all monsters.
     * 
     * @return a boolean determining if the game is over
     ******************************************************************************/
    private boolean isGameOver() {
        if (turnCount > 0) {
            if (player.isDead()) {
                return true;
            } 
            else {
                // counter to keep track of how many directions have monsters
                int popCounter = 0;
                for (int i = 0; i < directionList.size(); i++) {
                    if (directionList.get(i).size() > 0) {
                        popCounter++;
                    }
                }
                // returns true if all directions are empty
                return (popCounter == 0);
            }
        }
        return false;
    }

    /********************************************************************************
     * Creates monster to attack the player. There is only a 40% chance of this
     * method executing, but it will always execute for the first 2 turns of the
     * game to prevent a premature game over. Monsters are given a random amount of
     * health and strength within a given range, are sometimes assigned a spell, and
     * are placed in a random direction. If the player is dead, the method is
     * skipped in the game loop.
     ******************************************************************************/
    private void createMonster() {
        if (!player.isDead()) {
            double spawnChance = Math.random();

            // 40 % chance of monster appearing, guaruntees monsters on turns 1-2
            if (spawnChance < .4 || turnCount == 0 || turnCount == 1) {
                double spellChance = Math.random();
                // num to decide what direction monster is spawned
                int spawnPosition = (int) ((Math.random() * 4));
                // max health for creatures is 50, min is 30
                int health = (int) ((Math.random() * 30) + 20);
                // max strength (damage output) for creatures is 30, min is 20
                int strength = (int) ((Math.random() * 10) + 20);

                Creature monster = new Creature(health, strength);

                // 33% chance of monster getting spell
                if (spellChance < .33) {
                    // num to decide what spell is given
                    int spellNum = (int) ((Math.random() * 4));

                    switch (spellNum) {
                    case 0:
                        monster.giveSpell(SpellType.Frost);
                        break;
                    case 1:
                        monster.giveSpell(SpellType.Fire);
                        break;
                    case 2:
                        monster.giveSpell(SpellType.Lightning);
                        break;
                    case 3:
                        monster.giveSpell(SpellType.Heal);
                        break;
                    default:
                        throw new RuntimeException("spellNum is in an undefined state: " 
                                + spellNum);
                    }
                }

                System.out.print("\n" + "A monster appears to the " + ANSI_YELLOW);
                switch (spawnPosition) {
                case 0:
                    northMonsters.enqueue(monster);
                    System.out.print("NORTH");
                    break;
                case 1:
                    eastMonsters.enqueue(monster);
                    System.out.print("EAST");
                    break;
                case 2:
                    southMonsters.enqueue(monster);
                    System.out.print("SOUTH");
                    break;
                case 3:
                    westMonsters.enqueue(monster);
                    System.out.print("WEST");
                    break;
                default:
                    throw new RuntimeException("spawnPosition is in an undefined state: " 
                            + spawnPosition);
                }
                System.out.println(ANSI_RESET + "!");
            }
        }
    }

    /********************************************************************************
     * Prints information about each monster that immediatly threatens the player,
     * and allows the player to attack or use a spell. Only relevant information is
     * displayed, meaning no option to use a spell will be displayed if none are
     * currently available. This will not allow an attack if the player is frozen,
     * and will check if the player can level up.
     *******************************************************************************/
    private void playersTurn() {

        // iterates through every direction
        for (int i = 0; i < directionList.size(); i++) {
            Queue<Creature> currentDirection = directionList.get(i);

            // prints header for monster info
            if (currentDirection.size() > 0) {
                switch (i) {
                case 0:
                    System.out.print(ANSI_YELLOW + "\n" + "NORTH");
                    break;
                case 1:
                    System.out.print(ANSI_YELLOW + "\n" + "EAST");
                    break;
                case 2:
                    System.out.print(ANSI_YELLOW + "\n" + "SOUTH");
                    break;
                case 3:
                    System.out.print(ANSI_YELLOW + "\n" + "WEST");
                    break;
                default:
                    throw new RuntimeException("direction is in an undefined state: " + i);
                }
                System.out.println(" MONSTER INFO:" + ANSI_RESET);

                Creature monster = currentDirection.peek();

                // prints enemy health and strength info
                System.out.println("Current health: " + monster.getHealth());
                System.out.println("Strength: " + monster.getStrength());

                // prints enemy spell info
                if (monster.canCast(SpellType.Frost)) {
                    System.out.println("Current spell: " 
                            + ANSI_CYAN + "FROST" + ANSI_RESET);
                } 
                else if (monster.canCast(SpellType.Fire)) {
                    System.out.println("Current spell: " 
                            + ANSI_RED + "FIRE" + ANSI_RESET);
                } 
                else if (monster.canCast(SpellType.Heal)) {
                    System.out.println("Current spell: " 
                            + ANSI_GREEN + "HEAL" + ANSI_RESET);
                } 
                else if (monster.canCast(SpellType.Lightning)) {
                    System.out.println("Current spell: " 
                            + ANSI_PURPLE + "LIGHTNING" + ANSI_RESET);
                } 
                else {
                    System.out.println("No spell");
                }

                // prints monster status effects
                if (monster.isOnFire() || monster.isFrozen()) {
                    System.out.print("Status: ");

                    if (monster.isOnFire() && monster.isFrozen()) {
                        System.out.println(
                                ANSI_RED + "ON FIRE " + ANSI_RESET 
                                + "and " + ANSI_CYAN + "FROZEN" + ANSI_RESET);
                    } 
                    else if (monster.isOnFire()) {
                        System.out.println(ANSI_RED + "ON FIRE " + ANSI_RESET);
                    } 
                    else if (monster.isFrozen()) {
                        System.out.println(ANSI_CYAN + "FROZEN" + ANSI_RESET);
                    }
                }
            }
        }

        if (!player.isFrozen()) {
            int cont = 0;

            // loops until recognized input is provided
            do {

                // returns value representing desired player action/circumstance
                switch (attackOrSpell()) {
                // attack selected
                case 0:
                    printAttack();
                    cont = 1;
                    break;
                // spell selected
                case 1:
                    if (player.hasSpell()) {
                        printSpell();
                        cont = 1;
                    } 
                    else {
                        System.out.println("\n" + "You don't have any spells! \n");
                    }
                    break;
                // no spells available, only 1 monster (to prevent useless prompts)
                case 2:
                    Scanner sc = new Scanner(System.in);
                    cont = 0;

                    // asks for ATTACK input until it is recieved
                    do {
                        System.out.println(
                                "\n" + "Type " + ANSI_BLUE + "ATTACK " 
                                + ANSI_RESET + "to swing at the monster! \n");
                        String input = sc.next();
                        if (input.equalsIgnoreCase("Attack")) {
                            cont = 1;
                        }
                        else {
                            System.out.println("\n" + "Input not recognized!");
                        }
                    } while (cont == 0);

                    printAttack();
                    cont = 1;
                    break;
                default:
                    throw new RuntimeException("attackOrSpell is in an undefined state");
                }
            } while (cont == 0);

            Scanner sc = new Scanner(System.in);
            cont = 0;

            // asks for NEXT input until it is recieved
            do {
                System.out.println("TYPE " + ANSI_BLUE + "NEXT" 
                        + ANSI_RESET + " TO CONTINUE: \n");
                String input = sc.next();
                if (input.equalsIgnoreCase("Next")) {
                    cont = 1;
                } 
                else {
                    System.out.println("\n" + "Input not recognized!");
                }
            } while (cont == 0);

        } 
        else {
            System.out.println("\n" + "You're " + ANSI_CYAN + "FROZEN" 
                    + ANSI_RESET + "! You can't move!");

            Scanner sc = new Scanner(System.in);
            int cont = 0;

            // asks for NEXT input until it is recieved
            do {
                System.out.println("\n" + "TYPE " + ANSI_BLUE 
                        + "NEXT" + ANSI_RESET + " TO CONTINUE: \n");
                String input = sc.next();
                if (input.equalsIgnoreCase("Next")) {
                    cont = 1;
                } 
                else {
                    System.out.println("\n" + "Input not recognized!");
                }
            } while (cont == 0);
        }

        player.decFireTimer();
        player.decFreezeTimer();

        // if game is over, skips levelup proccess
        if (player.canLevelUp() && !isGameOver()) {
            printLevelUp();
        }
    }

    /********************************************************************************
     * Iterates through each direction, allowing monsters to attack or use a spell.
     * If a monster has a spell, they'll instantly use it. Also applies burn status
     * effect to monster and player if applicable.
     *******************************************************************************/
    private void monstersTurn() {
        for (int i = 0; i < directionList.size(); i++) {
            if (directionList.get(i).size() > 0) {

                // keeps track of which monsters were just killed by player
                boolean isKilled = false;

                // isKilled becomes true if current directions monster died
                switch (i) {
                case 0:
                    isKilled = northKill;
                    break;
                case 1:
                    isKilled = eastKill;
                    break;
                case 2:
                    isKilled = southKill;
                    break;
                case 3:
                    isKilled = westKill;
                    break;
                default:
                    throw new RuntimeException("direction is in an undefined state: " + i);
                }

                // skips entire attack process, unfair to get attacked by monster you didn't
                // know was there!!!
                if (isKilled == false) {

                    Creature monster = directionList.get(i).peek();
                    String currentDirection = null;

                    // determines what list is currently being looked at, assigns adjective
                    switch (i) {
                    case 0:
                        currentDirection = "Northern";
                        break;
                    case 1:
                        currentDirection = "Eastern";
                        break;
                    case 2:
                        currentDirection = "Southern";
                        break;
                    case 3:
                        currentDirection = "Western";
                        break;
                    default:
                        throw new RuntimeException("direction is in an undefined state: " + i);
                    }

                    // casts spell or attacks, nothing if frozen
                    if (!monster.isFrozen()) {

                        // executes spell if monster can use it, provides relevant stylized output
                        if (monster.canCast(SpellType.Frost)) {
                            monster.freeze(player);
                            System.out.println("\n" + "Your muscles tense up " 
                                    + "and overwhelming cold washes ");
                            System.out.println("over you. You've been" + 
                                    ANSI_CYAN + " FROZEN" + ANSI_RESET + "! \n");
                        } 
                        else if (monster.canCast(SpellType.Fire)) {
                            monster.setOnFire(player);
                            System.out.println("\n" + "The " + currentDirection 
                                    + " monster shoots" + ANSI_RED + " FIRE " + ANSI_RESET 
                                    + "from its mouth and you feel ");
                            System.out.println("a scortching sensation all over your " +
                                    "skin. You're on fire! \n");
                        }   
                        else if (monster.canCast(SpellType.Heal)) {
                            int randHeal = (int) (Math.random() * 30);
                            monster.heal(randHeal);
                            System.out.println("\n" + "A golden aura surrounds the " 
                                    + currentDirection + " monster as it" + ANSI_GREEN 
                                    + " HEALS " + ANSI_RESET + "itself for "
                                    + ANSI_YELLOW + randHeal + ANSI_RESET + " points.");
                            System.out.println("Monster's current health: " 
                                    + ANSI_YELLOW + monster.getHealth()
                                    + ANSI_RESET + "\n");
                        } 
                        else if (monster.canCast(SpellType.Lightning)) {
                            player.hurt(25);
                            monster.spell = null;
                            System.out.println("\n" + "Your vision turns white as" 
                                    + ANSI_PURPLE + " LIGHTNING " + ANSI_RESET 
                                    + "envelops you.");
                            System.out.println("You've been hurt for " + ANSI_YELLOW 
                                    + "25 " + ANSI_RESET + "points! \n");
                        } 
                        else {
                            int damage = monster.attack();
                            player.hurt(damage);
                            if (damage == 0) {
                                System.out.println("\n" + "The " + currentDirection 
                                + " monster missed! \n");
                            }
                            else {
                                System.out.println("\n" + "The " + currentDirection 
                                + " monster attacks you for " + ANSI_YELLOW 
                                + damage + ANSI_RESET + " points. \n");
                            }
                        }
                    }
                    // if no spell is available, monster will attack
                    else {
                        System.out.println("\n" + "The " + currentDirection 
                                + " monster is " + ANSI_CYAN + "FROZEN"
                                + ANSI_RESET + "! It tries to attack but fails! \n");
                    }

                    // prevents unneccesary/contradicting text if player has died
                    if (player.isDead()) {
                        break;
                    }

                    // applies burn to monster
                    if (monster.isOnFire()) {
                        monster.hurt(10);
                        System.out.println("The monster is on " + ANSI_RED 
                                + "FIRE" + ANSI_RESET + "! It takes "
                                + ANSI_YELLOW + "10 " + ANSI_RESET 
                                + "points of damage.");
                        if (monster.isDead()) {
                            // kills monster
                            System.out.println("The fire has killed the monster! \n");
                            player.addXP(monster.getXPValue());
                            directionList.get(i).dequeue();
                            // if game is over bcz of kill, skips levelup dialougue
                            if (player.canLevelUp() && !isGameOver()) {
                                printLevelUp();
                            }
                        } 
                        else {
                            System.out.println();
                            monster.decFireTimer();
                            monster.decFreezeTimer();
                        }
                    } 
                    else {
                        // decreases turn tick for
                        monster.decFireTimer();
                        monster.decFreezeTimer();
                    }

                    Scanner sc = new Scanner(System.in);
                    int cont = 0;

                    // asks for NEXT input until it is recieved
                    do {
                        System.out.println("TYPE " + ANSI_BLUE + "NEXT" 
                                + ANSI_RESET + " TO CONTINUE: \n");
                        String input = sc.next();
                        if (input.equalsIgnoreCase("Next")) {
                            cont = 1;
                        } 
                        else {
                            System.out.println("\n" + "Input not recognized!");
                        }
                    } while (cont == 0);
                }
            }
        }

        // if player is on fire, applies burn
        if (!player.isDead()) {
            if (player.isOnFire()) {
                System.out.println("\n" + "You're on " + ANSI_RED + "FIRE" 
                        + ANSI_RESET + "! The burn damages you for "
                        + ANSI_YELLOW + "10" + ANSI_RESET + " points!");
                player.hurt(10);
            }

            // prints remaining health and XP
            System.out
                    .println("\n" + "You have " + ANSI_YELLOW + player.getHealth() 
                    + ANSI_RESET + " health remaining.");
            System.out.println("You have " + ANSI_YELLOW + player.getXP() + ANSI_RESET 
                    + " XP. \n");

            Scanner sc = new Scanner(System.in);
            int cont = 0;

            // asks for NEXT input until it is recieved
            do {
                System.out.println("TYPE " + ANSI_BLUE + "NEXT" + ANSI_RESET 
                        + " TO CONTINUE: \n");
                String input = sc.next();
                if (input.equalsIgnoreCase("Next")) {
                    cont = 1;
                } 
                else {
                    System.out.println("\n" + "Input not recognized!");
                }
            } while (cont == 0);
        }

        // resets kill trackers
        northKill = false;
        eastKill = false;
        southKill = false;
        westKill = false;
    }

    /********************************************************************************
     * Prints the logo, greeting text, intro, and begins the game loop
     *******************************************************************************/
    private static void startGame() {
        Scanner startsc = new Scanner(System.in);
        int cont = 0;

        printLogo();
        printGreeting();

        // loops until START or HELP input is provided
        do {
            String startInput = startsc.next();

            if (startInput.equalsIgnoreCase("Start")) {
                cont = 1;
            } 
            else if (startInput.equalsIgnoreCase("Help")) {
                printHelp();
            } 
            else {
                System.out.println("\n" + "Input not recognized! \n");
            }
        } while (cont == 0);

        cont = 0;
        printIntro();

        // asks for NEXT input until it is recieved
        do {
            String startInput = startsc.next();

            if (startInput.equalsIgnoreCase("Next")) {
                cont = 1;
            } 
            else {
                System.out.println("\n" + "Input not recognized! \n");
            }
        } while (cont == 0);

        Arena game = new Arena();
        game.gameLoop();
    }

    /********************************************************************************
     * Helper method for playerTurn. Prompts user to choose between attack or spell,
     * based on what they have available to them. Then returns the choice as an
     * integer
     * 
     * @return int representing desired move
     *******************************************************************************/
    private int attackOrSpell() {
        int actionNum = -1;
        int popCounter = 0;

        // iterates through all directions to find which are populated
        for (int i = 0; i < directionList.size(); i++) {
            if (directionList.get(i).size() > 0) {
                popCounter++;
            }
        }
        // if player has spell
        if (player.hasSpell()) {

            String ANSI_SPELL = null;

            // assigns ANSI color for player spell
            switch (player.spell) {
            case Fire:
                ANSI_SPELL = ANSI_RED;
                break;
            case Frost:
                ANSI_SPELL = ANSI_CYAN;
                break;
            case Heal:
                ANSI_SPELL = ANSI_GREEN;
                break;
            case Lightning:
                ANSI_SPELL = ANSI_PURPLE;
                break;
            default:    
                throw new RuntimeException("spell is in an undefined state: " + player.spell);
            }

            // loops until ATTACK or SPELL input is recieved
            do {
                System.out.println("\n" + "Will you " + ANSI_BLUE + "ATTACK" 
                        + ANSI_RESET + " or use your " + ANSI_SPELL 
                        + player.spell.toString().toUpperCase() + ANSI_RESET + " spell? \n");

                Scanner sc = new Scanner(System.in);
                String input = sc.next();

                if (input.equalsIgnoreCase("Attack")) {
                    actionNum = 0;
                } 
                else if (input.equalsIgnoreCase("Spell") || 
                         input.equalsIgnoreCase(player.spell.toString())) {
                    actionNum = 1;
                } 
                else {
                    System.out.println("\n" + "Input not recognized!");
                }
            } while (actionNum == -1);
        }
        // if no spell and only one monster
        else if (popCounter == 1) {
            actionNum = 2;
        }
        // if no spell
        else {
            actionNum = 0;
        }
        return actionNum;
    }

    /********************************************************************************
     * Determines desired direction of attack/spell based on player input. Does not
     * prompt user if there is only one direction to choose from.
     * 
     * @return int representing direction of attack/spell
     *******************************************************************************/
    private int printDirection() {
        int popCounter = 0;
        int direction = -1;
        Scanner sc = new Scanner(System.in);

        // iterates through list to see how many are occupied
        for (int i = 0; i < directionList.size(); i++) {
            if (directionList.get(i).size() > 0) {
                popCounter++;
            }
        }

        // if only 1, returns the correct list
        if (popCounter == 1) {
            for (int i = 0; i < directionList.size(); i++) {
                if (directionList.get(i).size() > 0) {
                    direction = i;
                    break;
                }
            }
        } 
        else {
            // loops until allowed input is recieved
            do {
                System.out.println(
                        "\n" + "What " + ANSI_BLUE + "DIRECTION" + ANSI_RESET 
                        + " would you like to attack? \n");
                String input = sc.next();

                // takes input and assigns to direction
                if (input.equalsIgnoreCase("North") || input.equalsIgnoreCase("Front")
                        || input.equalsIgnoreCase("Ahead") 
                        || input.equalsIgnoreCase("Forward")) {
                    direction = 0;
                } 
                else if (input.equalsIgnoreCase("Right") || input.equalsIgnoreCase("East")) {
                    direction = 1;
                } 
                else if (input.equalsIgnoreCase("South") || input.equalsIgnoreCase("Back")
                        || input.equalsIgnoreCase("Behind")) {
                    direction = 2;
                } 
                else if (input.equalsIgnoreCase("Left") || input.equalsIgnoreCase("West")) {
                    direction = 3;
                } 
                else {
                    System.out.println("\n" + "Input not recognized!");
                    direction = -1;
                }
                if (direction != -1 && directionList.get(direction).size() == 0) {
                    System.out.println("\n" + "There are no monsters in that direction!");
                }
            } while (direction == -1 || directionList.get(direction).size() == 0);
        }
        return direction;
    }

    /********************************************************************************
     * Player attacks, and information regarding attack is printed.
     *******************************************************************************/
    private void printAttack() {
        // gets direction of attack
        int direction = printDirection();
        int damage = (player.attack());
        directionList.get(direction).peek().hurt(damage);

        if (damage == 0) {
            System.out.println("\n" + "You swing at the monster and feel nothing but " 
                    + "air. You missed!");
        }
        else if (damage < 20) {
            System.out.println("\n" + "You slice the skin of the monster, " 
                    + "damaging it for " + ANSI_YELLOW + damage + ANSI_RESET + " points.");
        }
        else if (damage < 40) {
            System.out.println("\n" + "Your sword digs deep into the flesh of the monster, " 
            + "\n" + "damaging it for " + ANSI_YELLOW + damage + ANSI_RESET + " points.");
        }
        else {
            System.out.println("\n" + "You stab the monster all the way to the hilt of " 
            + "\n" + "your sword, damaging it for " + ANSI_YELLOW + damage + ANSI_RESET 
            + " points.");
        }
        if (directionList.get(direction).peek().isDead()) {
            System.out.println("It collapses to the ground. Good kill! \n");
            player.addXP(directionList.get(direction).peek().getXPValue());
            directionList.get(direction).dequeue();

            // variables are used to make sure monsters not shown yet cannot make a move
            switch (direction) {
            case 0:
                northKill = true;
                break;
            case 1:
                eastKill = true;
                break;
            case 2:
                southKill = true;
                break;
            case 3:
                westKill = true;
                break;
            default:
                throw new RuntimeException("direction is in an undefined state: " 
                        + direction);
            }
        } 
        else {

            // prints remaining health if monster did not die
            int health = directionList.get(direction).peek().getHealth();
            System.out.println("The monster still has " + ANSI_YELLOW + health 
            + ANSI_RESET + " health. \n");
        }
    }

    /********************************************************************************
     * Player uses spell, and prints relevant information/text
     *******************************************************************************/
    private void printSpell() {
        // heal does not need a direction, so is outside switch statement to avoid
        // useless text
        if (player.spell != SpellType.Heal) {
            // gets direction of attack
            int direction = printDirection();
            // uses spell and prints relevant information
            switch (player.spell) {
            case Fire:
                player.setOnFire(directionList.get(direction).peek());
                System.out.println("\n" + "The air turns hot as a wave of " 
                        + ANSI_RED + "FIRE" + ANSI_RESET + " shoots from your hands,");
                System.out.println("setting the monster ablaze. \n");
                break;
            case Frost:
                player.freeze(directionList.get(direction).peek());
                System.out.println("\n" + "A chill shoots down your spine as " 
                        + "the monster is " + ANSI_CYAN + "FROZEN" + ANSI_RESET 
                        + " in \n" + "front of you, completely encased in a " 
                        + "solid block of ice. \n");
                break;
            case Lightning:
                player.addXP(directionList.get(direction).peek().getXPValue());
                directionList.get(direction).dequeue();
                player.spell = null;
                System.out.println("\n" + "The red sky turns dark and a bolt of " 
                        + ANSI_PURPLE + "LIGHTNING" + ANSI_RESET + " strikes from the \n"
                        + "heavens. The air fills with electricity as the monster " 
                        + "is instantly \nobliterated. \n");
                break;
            default:
                throw new RuntimeException("spell is in an undefined state: " 
                        + player.spell);
            }
        } 
        else {
            // heals random amount between 30 and 50
            int heal = (int) ((Math.random() * (20)) + 30);
            // different dialogue if heal to max health
            if (player.healPlayer(heal) == 0) {
                System.out.println("\n" + "You " + ANSI_GREEN + "HEALED" + ANSI_RESET 
                        + " for " + ANSI_YELLOW + heal + ANSI_RESET + " points!");
            } 
            else {
                System.out.println("\n" + "You " + ANSI_GREEN + "HEALED" + ANSI_RESET 
                        + " yourself!");
            }
            System.out.println("Current health = " + ANSI_YELLOW + player.getHealth() 
                    + ANSI_RESET + ". \n");
        }
    }

    /********************************************************************************
     * Prints level up information and options, increases max health and strength,
     * subtracts XP, and fully heals player. Also lets player pick a spell or skip.
     * Picking a spell will replace the current one, choosing the spell you already
     * have has the exact same effect as skipping.
     *******************************************************************************/
    private void printLevelUp() {
        System.out.println("\n" + "Congratulations! You can " + ANSI_YELLOW + 
                "level up" + ANSI_RESET + "!");
        System.out.println(
                "Your health has been increased to " + ANSI_YELLOW 
                + (player.getMaxHealth() + 10) + ANSI_RESET + ". \n"
                + "and your strength has been increased to " + ANSI_YELLOW 
                + (player.getStrength() + 5) + ANSI_RESET + ".");
        System.out.println(ANSI_YELLOW + "150" + ANSI_RESET + " XP has been " 
                + "deducted, leaving you at " + ANSI_YELLOW 
                + (player.getXP() - 150) + ANSI_RESET + ".");
        System.out.print("Please choose a spell: ");
        System.out.print(ANSI_RED + "FIRE" + ANSI_RESET + ", ");
        System.out.print(ANSI_CYAN + "FROST" + ANSI_RESET + ", ");
        System.out.print(ANSI_GREEN + "HEAL" + ANSI_RESET + ", or ");
        System.out.println(ANSI_PURPLE + "LIGHTNING" + ANSI_RESET + ".");
        System.out.println(
                "If you would like to keep your current spell, type " 
                + ANSI_BLUE + "SKIP" + ANSI_RESET + ". \n");

        int cont = 0;
        do {
            Scanner sc = new Scanner(System.in);
            String input = sc.next();

            if (input.equalsIgnoreCase("Frost")) {
                player.levelUp(SpellType.Frost);
                System.out.println("\n" + ANSI_CYAN + "FROST " + ANSI_RESET 
                        + "spell aquired!");
                cont = 1;
            } 
            else if (input.equalsIgnoreCase("Heal")) {
                player.levelUp(SpellType.Heal);
                System.out.println("\n" + ANSI_GREEN + "HEALING " + ANSI_RESET 
                        + "spell aquired!");
                cont = 1;
            } 
            else if (input.equalsIgnoreCase("Fire")) {
                player.levelUp(SpellType.Fire);
                System.out.println("\n" + ANSI_RED + "FIRE " + ANSI_RESET 
                        + "spell aquired!");
                cont = 1;
            } 
            else if (input.equalsIgnoreCase("Lightning")) {
                player.levelUp(SpellType.Lightning);
                System.out.println("\n" + ANSI_PURPLE + "LIGHTNING " + ANSI_RESET 
                        + "spell aquired!");
                cont = 1;
            } 
            else if (input.equalsIgnoreCase("Skip")) {
                player.levelUp(player.spell);
                System.out.println("\n" + "You chose not to obtain a new spell.");
                cont = 1;
            } 
            else {
                System.out.println("\n" + "Input not recognized! \n");
            }
        } while (cont == 0);

        cont = 0;
        System.out.println("\n" + "TYPE " + ANSI_BLUE + "NEXT" + ANSI_RESET 
                + " TO CONTINUE: \n");

        do {
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            if (input.equalsIgnoreCase("Next")) {
                cont = 1;
            } 
            else {
                System.out.println("\n" + "Input not recognized!");
            }
        } while (cont == 0);
    }

    public static void main(String[] args) {
        startGame();
    }
}