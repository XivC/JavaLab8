package com.xivs.client;

import com.xivs.client.data.Client;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public enum Application {
    APP;
    ResourceBundle resources;
    public final Client client;
    private Locale locale;
    Application(){

        this.client = new Client();
        this.locale = Locale.getDefault();
        this.resources =  PropertyResourceBundle.getBundle("application");

    }
    public Locale getLocale(){
        return  this.locale;
    }
    public void setLocale(Locale locale){
        this.locale = locale;
        this.resources =  PropertyResourceBundle.getBundle("application", this.locale);
    }
    public ResourceBundle getResources(){
        return this.resources;
    }


}
