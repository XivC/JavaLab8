package com.xivs.client;

import com.xivs.client.data.Client;

public enum Application {
    APP;
    public final Client client;
    Application(){
        this.client = new Client();
    }

}
