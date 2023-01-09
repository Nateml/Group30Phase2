package graphvis.group30.HGA;

import java.util.HashMap;
import java.util.Map;

public class TabuList {
    
    HashMap<Configuration, TabuTenure> tabuMap;
    public TabuList() { 
        tabuMap = new HashMap<Configuration, TabuTenure>();
    }

    public void add(Configuration config) {
        TabuTenure tenure = new TabuTenure(config.getTotalConflictCount());
        tabuMap.put(config, tenure);
    }

    public void update() {
        HashMap<Configuration, TabuTenure> tempMap = new HashMap<>();
        tabuMap.replaceAll((config, tenure) -> tenure.decrease());
        for (Map.Entry<Configuration, TabuTenure> entry : tabuMap.entrySet()) {
            if (!entry.getValue().hasExpired()) {
                tempMap.put(entry.getKey(), entry.getValue());
            }
        }
        tabuMap = tempMap;
    }

    public boolean contains(Configuration config) {
        return tabuMap.keySet().contains(config);
    }

}
