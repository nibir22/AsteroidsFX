package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author jcs
 *
 * Processes game logic after all entities have been processed
 *
 */
public interface IPostEntityProcessingService {

    /**
     * Processes game logic after all entities have been processed
     *
     * Precondition: Regular entity processing has been completed
     * Post-condition: Post-processing updates have been applied to the game
     *
     */

    void process(GameData gameData, World world);
}
