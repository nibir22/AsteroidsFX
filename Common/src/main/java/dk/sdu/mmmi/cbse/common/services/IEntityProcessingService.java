package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This is a processing service interface for the entity processing services.
 * This handles game logic and updates for the entities in the game.
 *
 */

public interface IEntityProcessingService {

    /**
     * Process entities based on game logic.
     *
     * Pre-condition: The gameData and world objects must be initialized (Cannot be null).
     * Post-condition: The entities in the world object will be updated based on the game logic.
     *
     * @param gameData
     * @param world
     * @throws
     */
    void process(GameData gameData, World world);
}
