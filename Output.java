

public class Output implements OutputColors {

        public static void printLogo() {
                System.out.println(ANSI_YELLOW + "   ________    ___    ____  _______  __________  ____ ");
                System.out.println("  / ____/ /   /   |  / __ \\/  _/   |/_  __/ __ \\/ __ \\");
                System.out.println(" / / __/ /   / /| | / / / // // /| | / / / / / / /_/ /");
                System.out.println("/ /_/ / /___/ ___ |/ /_/ // // ___ |/ / / /_/ / _, _/ ");
                System.out.println("\\____/_____/_/  |_/_____/___/_/  |_/_/  \\____/_/ |_|  ");
                System.out.println("");
                System.out.println("" + ANSI_RESET);
        }

        public static void printGreeting() {
                System.out.println("Welcome to " + ANSI_RED + "GLADIATOR" + ANSI_RESET + ": a "
                                + "text-based fantasy arena fighting game." + "\n" + "This project was created"
                                + " for CIS163 by Matthew King.");
                System.out.println("\n" + "TYPE " + ANSI_BLUE + "HELP" + ANSI_RESET + " FOR INSTRUCTIONS:");
                System.out.println("TYPE " + ANSI_BLUE + "START" + ANSI_RESET + " TO BEGIN:" + "\n");
        }

        public static void printHelp() {
                System.out.println(ANSI_YELLOW + "\n" + "GAMEPLAY: " + ANSI_RESET + "\n"
                                + "Each turn, a monster can appear in one of four cardinal directions." + "\n"
                                + "You will get a chance to attack monsters appearing around you, and they will get a chance to do the same. "
                                + "\n"
                                + "Both parties can attack normally or use a spell if available. You will NOT be attacked by a monster if the " 
                                + "\n"
                                + "game has not shown them to you yet. The game ends if the player dies, or if all monsters are slain." + "\n");
                System.out.println(ANSI_YELLOW + "PLAYER AND MONSTERS: " + ANSI_RESET + "\n"
                                + "Each monster has two stats, health and strength. " + "\n"
                                + "Health determines how much damage a party can take before death, and strength determines the maximum amount "
                                + "\n"
                                + "of damage a party can dish out. Each party can also hold a single use spell that has various effects. "
                                + "\n"
                                + "Upon killing a monster, the player earns XP equal to the combined strength and max health of the monster "
                                + "\n" + "slain. Once the player earns 150 XP, they level up, gaining a spell." + "\n");
                System.out.println(ANSI_YELLOW + "SPELLS: " + ANSI_RESET + "\n"
                                + "There are four distinct spells that can be wielded by the player or by monsters."
                                + "\n" + ANSI_RED + "FIRE" + ANSI_RESET
                                + ": A lasting spell that inflicts 10 burn damage onto the target for 2-4 turns." + "\n"
                                + ANSI_GREEN + "HEAL" + ANSI_RESET
                                + ": A restoration spell that heals monsters up to 30 points, or heals the player up to 50 points or their max health."
                                + "\n" + ANSI_CYAN + "FROST" + ANSI_RESET
                                + ": A lasting spell that freezes the target, rendering them unable to move for 2-4 turns."
                                + "\n" + ANSI_PURPLE + "LIGHTNING" + ANSI_RESET
                                + ": A powerful spell that instantly kills a targeted monster, or inflicts 25 damage onto the player target."
                                + "\n");
                System.out.println(ANSI_YELLOW + "INPUT AND OUTPUT:" + ANSI_RESET + "\n"
                                + "There is a good deal of flexibility when prompted to input text. For example, attacking behind "
                                + "\n"
                                + "you can be achieved with South, Back, or Behind. Capitalization and upper/lower cases do not matter. "
                                + "\n" + ""
                                + "If an input isn't recognized, the game will simply ask you to try again. Yellow text is typically "
                                + "\n"
                                + "used for important information, while dark blue text is used for input prompts. If a prompt is redundant, "
                                + "\n"
                                + "it will not shown to the player. For example, if there is only one direction that has an enemy, a "
                                + "\n"
                                + "direction will not be asked for.");
                System.out.println("\n" + "TYPE " + ANSI_BLUE + "HELP" + ANSI_RESET + " FOR INSTRUCTIONS:");
                System.out.println("TYPE " + ANSI_BLUE + "START" + ANSI_RESET + " TO BEGIN:" + "\n");
        }

        public static void printIntro() {
                System.out.println("\n"
                                + "The sound of battle horns pierces the air as your eyes jolt open. You're in an old, Roman arena, with cracked cobblestone"
                                + "\n"
                                + "and burning sand beneath your feet. A massive crowd chants wildly and you can feel the anger in their voice, but their faces are"
                                + "\n"
                                + "all a blur. It's as if their oppresive aura is blocking your vision. As you sturdy yourself on your feet, you feel the "
                                + "\n"
                                + "rough hilt of a sword in your hand. You look down at it and see your reflection in the shiny blade, and you suddenly"
                                + "\n" + "get the sense that you've seen this before." + "\n" + "\n"
                                + "Before you can ponder, however, you see a harrowing pair of yellow eyes staring you down from behind a metal gate. You "
                                + "\n"
                                + "quickly rotate, seeing 3 more gates corresponding to the cardinal directions. As the first gate opens, you dig"
                                + "\n"
                                + "your heels into the sand and ready yourself for combat as the eyes lunge forward.");
                System.out.println("\n" + "TYPE " + ANSI_BLUE + "NEXT" + ANSI_RESET + " TO CONTINUE: \n");
        }

        public static void printWin(int turns) {
                System.out.println("\n"
                                + "The blood red sky opens up, revealing a familar, comforting blue one. You slowly look around you and, "
                                + "\n"
                                + "after realizing there are no more monsters, you lower your guard. Birds distantly chirp in the background and you "
                                + "\n"
                                + "hear the soft rustling of leaves. Finally, you have triumphed over the arena.");
                System.out.println("\n" + "You win! You survived " + ANSI_YELLOW + turns + ANSI_RESET
                                + " turns! (and many, many timelines)." + "\n");
        }

        public static void printGameOver(int turns) {
                System.out.println("Your legs give out and the life drains from your body. Blood spews from your newly fatal wound and the "
                                + "\n"
                                + "spectator's screams become unbearably loud. As the monsters overwhelm you, your vision fades and you"
                                + "\n"
                                + "feel like you're entering another plane of existance: one with the same horrors you've just been put through...");
                System.out.println("\n" + "You Lose! You survived " + ANSI_YELLOW + turns + ANSI_RESET
                                + " turns. Try again! \n");
        }
}
