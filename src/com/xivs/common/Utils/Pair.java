package com.xivs.common.Utils;

public class Pair<T, V> {
    T first;
    V second;
    public Pair(T first, V second){
        this.first = first;
        this.second = second;
    }
    public T get1(){
        return first;
    }
    public V get2(){
        return  second;
    }
}
