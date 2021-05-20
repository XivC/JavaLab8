package com.xivs.client.data;

import com.xivs.common.Utils.WorkerContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilteredWorkersDataProvider extends DataProvider<ArrayList<WorkerContainer>>{

   ArrayList<WorkerContainer> filteredData;
   Predicate<WorkerContainer> filter;



    public void filter(){



        if (filter == null){
            this.filteredData = data;
            System.out.println(this.filteredData);
        }
        else{
            this.filteredData = (ArrayList<WorkerContainer>) this.data.stream().filter(filter).collect(Collectors.toList());

        }

        this.updateEvent();


    }
    public FilteredWorkersDataProvider(DataProvider<ArrayList<WorkerContainer>> provider){
        super(provider);
        this.data = new ArrayList<>();
        this.filteredData = new ArrayList<>();

    }

    @Override
    @SuppressWarnings("unchecked")
    void update() {
        this.data = (ArrayList<WorkerContainer>) innerProvider.getData();
        this.filter();


    }
    @Override
    public ArrayList<WorkerContainer> getData(){
        return filteredData;
    }
    public void setFilter(Predicate<WorkerContainer> filter){
        this.filter = filter;
    }

}
