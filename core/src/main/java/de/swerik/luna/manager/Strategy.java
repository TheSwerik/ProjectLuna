package de.swerik.luna.manager;

import com.artemis.BaseSystem;
import com.artemis.InvocationStrategy;
import com.artemis.SystemInvocationStrategy;
import de.swerik.luna.ecs.systems.RenderSystem;
import de.swerik.luna.ecs.systems.TurnSystem;

public class Strategy extends SystemInvocationStrategy {
    @Override
    protected void process() {
        BaseSystem[] systemsData = systems.getData();
        for (int i = 0, s = systems.size(); s > i; i++) {
            if (disabled.get(i) || systemsData[i] instanceof RenderSystem || systemsData[i] instanceof TurnSystem)
                continue;

            updateEntityStates();
            systemsData[i].process();
        }

        updateEntityStates();
    }

    public void render() {
        BaseSystem[] systemsData = systems.getData();
        for (int i = 0, s = systems.size(); s > i; i++) {
            if (disabled.get(i) || (!(systemsData[i] instanceof RenderSystem) && !(systemsData[i] instanceof TurnSystem)))
                continue;
            updateEntityStates();
            systemsData[i].process();
        }

        updateEntityStates();

    }
}
