package com.xivs.client.data;

import com.xivs.common.Utils.WorkerContainer;
import com.xivs.common.lab.Worker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SortedWorkersDataProvider extends DataProvider<ArrayList<WorkerContainer>>{
    public static final int ASCENDING = 1;
    public static final int DESCENDING = 0;
    int sortMode;
    String sortField;
    public void sort(){

        switch(sortField){
            case "key": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.key)).collect(Collectors.toList());break;
            case "id": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.id)).collect(Collectors.toList());break;
            case "owner": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.owner)).collect(Collectors.toList());break;
            case "name": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.name)).collect(Collectors.toList());break;
            case "creationDate": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.creationDate)).collect(Collectors.toList());break;
            case "salary": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.salary)).collect(Collectors.toList());break;
            case "endDate": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.endDate)).collect(Collectors.toList());break;
            case "status": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.status)).collect(Collectors.toList());break;
            case "position": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.position)).collect(Collectors.toList());break;
            case "x": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.x)).collect(Collectors.toList());break;
            case "y": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.y)).collect(Collectors.toList());break;
            case "street": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.street)).collect(Collectors.toList());break;
            case "zipCode": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.zipCode)).collect(Collectors.toList());break;
            case "annualTurnover": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.annualTurnover)).collect(Collectors.toList());break;
            case "type": this.data = (ArrayList<WorkerContainer>) this.data.stream().sorted(Comparator.comparing(c -> c.type)).collect(Collectors.toList());break;
            default: return;
        }
        if (this.sortMode == DESCENDING) Collections.reverse(data);

        this.updateEvent();


    }
    public SortedWorkersDataProvider(DataProvider<ArrayList<WorkerContainer>> provider){
        super(provider);
        this.data = new ArrayList<>();
        this.sortMode = ASCENDING;
        this.sortField = "key";
    }

    @Override
    @SuppressWarnings("unchecked")
    void update() {
        this.data = (ArrayList<WorkerContainer>) innerProvider.getData();
        this.sort();


    }
    public void setSortMode(int filterMode){
        this.sortMode = filterMode;
    }
    public void setSortField(String field){
        this.sortField = field;
    }
}
