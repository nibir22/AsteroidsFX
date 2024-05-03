package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin  implements IGamePluginService {



    @Override
    public void start(GameData gameData, World world) {
        System.out.println("PlayerPlugin started, Hello from the other side");
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
    }
}
