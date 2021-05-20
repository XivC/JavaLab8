package com.xivs.client.data;

import com.xivs.common.Utils.WorkerContainer;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.lab.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkersDataProvider extends DataProvider<ArrayList<WorkerContainer>>{

    public WorkersDataProvider(Client provider){
        super(provider);
        this.data = new ArrayList<>();
    }
    @SuppressWarnings("unchecked")
    synchronized void update(){
        this.data.clear();
        HashMap<String, Worker> workers = (HashMap<String, Worker>) (((HashMap<String, DataTransference<?>>)innerProvider.getData()).get("workers").get());
        HashMap<String, String> keyOwnerRelating = (HashMap<String, String>) (((HashMap<String, DataTransference<?>>)innerProvider.getData()).get("keyOwnerRelating").get());
        for(String key: workers.keySet()){
            WorkerContainer container = new WorkerContainer(key, keyOwnerRelating.get(key), workers.get(key));
            this.data.add(container);
        }
        this.updateEvent();
    }
}
