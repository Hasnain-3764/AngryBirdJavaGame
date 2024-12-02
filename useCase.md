# 1. Use Case: Start Game

### a. Overview
- **Name:** Start Game
- **Actor:** Player
- **Description:** Allows the player to initiate a new game session from the splash screen and navigate to the main menu to begin gameplay.

### b. Preconditions
- The game application is installed and launched.
- All necessary assets (textures, sounds) are loaded successfully.

### c. Basic Flow
1. **Launch Game:** The player starts the game application.
2. **Display Splash Screen:** The SplashScreen is displayed, showing animations or logos.
3. **Transition to Main Menu:** After a set duration or upon player input (e.g., touch/click), the game transitions to the MainMenuScreen.
4. **Select "Start Game":** The player selects the "Start Game" option from the main menu.
5. **Initialize First Level:** The UIManager invokes the GamePlayScreen for Level 1 (Level1).
6. **Begin Gameplay:** The player is presented with the first level, ready to play.

### d. Alternate Flows
- **a. Exit During Splash Screen:**
    - During the splash screen, the player chooses to exit the game.
    - The game terminates gracefully.
- **b. Navigate to Settings:**
    - From the main menu, the player selects "Settings" instead of "Start Game".
    - The SettingsScreen is displayed for the player to adjust preferences.

---

# 2. Use Case: Select Level

### a. Overview
- **Name:** Select Level
- **Actor:** Player
- **Description:** Enables the player to choose a specific level to play from the level selection screen.

### b. Preconditions
- The player is on the main menu or level selection screen.
- Multiple levels are available and unlocked for selection.

### c. Basic Flow
1. **Access Level Selection:** From the main menu, the player selects the "Level Select" option.
2. **Display Level Select Screen:** The UIManager presents a LevelSelectScreen listing all available levels.
3. **Choose a Level:** The player selects a desired level (e.g., Level 2).
4. **Load Selected Level:** The UIManager initializes the corresponding GamePlayScreen subclass (e.g., Level2).
5. **Begin Selected Level:** Gameplay starts for the chosen level.

### d. Alternate Flows
- **a. Locked Levels:**
    - The player attempts to select a level that is locked.
    - The game displays a message indicating the level is locked and how to unlock it.
- **b. Return to Main Menu:**
    - The player decides not to select any level.
    - The player navigates back to the main menu.

---

# 3. Use Case: Launch Bird

### a. Overview
- **Name:** Launch Bird
- **Actor:** Player
- **Description:** Allows the player to aim and launch birds using the slingshot mechanism to destroy structures and eliminate pigs.

### b. Preconditions
- The player is in an active game session (GamePlayScreen).
- Birds are available to be launched.
- The game is not paused.

### c. Basic Flow
1. **Select Bird:** The player selects a bird from the available queue.
2. **Aim Slingshot:** The player touches or clicks on the bird to initiate dragging. The bird follows the player's touch, stretching the slingshot elastic.
3. **Release to Launch:** Upon releasing the touch/click, the bird is propelled towards the structures with a velocity based on the drag distance and direction.
4. **Bird Flight:** The bird travels through the air, interacting with structures and pigs upon collision.
5. **Bird Consumption:** After landing or coming to rest, the bird is removed from the active queue, and the next bird becomes available.

### d. Alternate Flows
- **a. Invalid Launch (Insufficient Drag):**
    - The player drags the bird minimally. Upon release, the bird does not gain enough velocity to launch.
    - The game prompts the player to drag further or cancels the launch.
- **b. Special Abilities Activation:**
    - The player activates a bird's special ability (e.g., BlueBird's split ability).
    - The ability is executed during or after the launch, affecting gameplay dynamics.

---

# 4. Use Case: Save Game State

### a. Overview
- **Name:** Save Game State
- **Actor:** Player
- **Description:** Allows the player to save the current state of the game, enabling them to resume later from the same point.

### b. Preconditions
- The player is in an active game session (GamePlayScreen).
- The game is not in a state that prohibits saving (e.g., during intense physics simulation).

### c. Basic Flow
1. **Access Save Option:** The player selects the "Save Game" button from the in-game UI.
2. **Trigger Save Process:** The GamePlayScreen invokes the saveGameState() method.
3. **Serialize Game State:** Current positions and states of birds, pigs, structures, and other relevant entities are serialized into BirdData, PigData, and StructureData objects, aggregated into a GameState object.
4. **Write to File:** The serialized GameState is written to a file (savegame.dat) using ObjectOutputStream.
5. **Confirmation:** The game displays a confirmation message indicating the game has been saved successfully.

### d. Alternate Flows
- **a. Save Failure:**
    - An error occurs during the save process (e.g., insufficient storage).
    - The game catches the exception and notifies the player of the failure.
- **b. Overwriting Existing Save:**
    - A previous save exists.
    - The player opts to overwrite the existing save, which is handled gracefully.

---

# 5. Use Case: Load Game State

### a. Overview
- **Name:** Load Game State
- **Actor:** Player
- **Description:** Enables the player to load a previously saved game state, restoring all game entities to their saved positions and statuses.

### b. Preconditions
- A saved game state exists (savegame.dat).
- The player is at a point in the game where loading is applicable (e.g., main menu, pause menu).

### c. Basic Flow
1. **Access Load Option:** The player selects the "Load Game" button from the main menu or in-game UI.
2. **Trigger Load Process:** The GamePlayScreen invokes the loadGameState() method.
3. **Deserialize Game State:** The GameState object is read from the file (savegame.dat) using ObjectInputStream.
4. **Reconstruct Game Entities:** Birds, pigs, structures, and other entities are recreated based on the deserialized data.
5. **Resume Gameplay:** The game state is restored to the saved point, allowing the player to continue playing seamlessly.

### d. Alternate Flows
- **a. No Save Found:**
    - The player attempts to load a game, but no save file exists.
    - The game notifies the player that no saved game was found.
- **b. Corrupted Save File:**
    - The save file (savegame.dat) is corrupted or incompatible.
    - The game catches the exception and informs the player of the issue, possibly suggesting starting a new game.

---

*This document includes only the first five use cases to keep it concise. Let me know if you need the rest or any further details!*
