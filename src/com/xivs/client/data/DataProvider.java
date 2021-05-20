package com.xivs.client.data;

import java.io.InputStream;
import java.util.ArrayList;

public abstract class DataProvider<T> {
     T data;

     ArrayList<Runnable> updateDataEvents = new ArrayList<>();
     DataProvider<?> innerProvider;
     public DataProvider(){

     }
     public DataProvider(DataProvider<?> innerProvider){
         this.innerProvider = innerProvider;
         this.innerProvider.addUpdateEvent(this::update);

     }
     synchronized void updateEvent(){

         for(Runnable r: updateDataEvents){
             r.run();
         }
     }
     public void addUpdateEvent(Runnable event){
         this.updateDataEvents.add(event);
     }

     abstract void update();


     public T getData(){return data;}



}
