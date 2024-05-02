package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

    /**
     *
     * Manages game plugin lifecycles and operations.
     *
     */

public interface IGamePluginService {

    /**
     * Initializes the games elements
     *
     *  Precondition: The gameData and world must be initialized/Not null
     *  Post-condition: The game elements are initialized
     */

    void start(GameData gameData, World world);


    /**
     * Stops the game elements
     *
     *  Precondition: The gameData and world must be initialized/Not null
     *  Post-condition: The game elements are stopped/ run its implementation of the stop method
     */

    void stop(GameData gameData, World world);
}
