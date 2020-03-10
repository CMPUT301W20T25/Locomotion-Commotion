package com.example.locomotioncommotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Rider {
    private Request currentRequest;

    public Rider(){
        this.currentRequest = null;
    }

    public Request createRequest(Location start, Location end, Integer fare){
        return null;
    }

    public Request getCurrentRequest(){
        return this.currentRequest;
    }

    public void notifyRequestComplete(){
        int a = 0;
    }

    public String requestStatus(){
        return "On fire";
    }

    public QRCode generateQRPrice(){
        return null;
    }

}
