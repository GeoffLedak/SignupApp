package com.geoffledak.signupapp.model;

import org.parceler.Parcel;

/**
 * Created by geoff on 6/21/17.
 */

@Parcel
public class Media {


    private String m;


    public Media() { }

    public Media(String m) {

        this.m = m;
    }



    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }



}
