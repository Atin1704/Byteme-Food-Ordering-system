package com.company;

import java.io.Serializable;

/* just to create and return singleton instance of admin*/
public class Admin implements Serializable {
    private static  final Admin admin=new Admin();



    private Admin(){}




    public static Admin getInstance() {
        return admin;
    }
}
