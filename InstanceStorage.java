package com.company;

import java.io.Serializable;
import java.util.HashMap;

/* Hashmap to map User ID's to instances of that particular user */


public class InstanceStorage implements Serializable {
    private static  final InstanceStorage storage = new InstanceStorage();

    private  HashMap<String,User> userId_userInstance_map;
    private final Admin admin =Admin.getInstance();


    private InstanceStorage() {
        userId_userInstance_map = new HashMap<>();
    }

    public static InstanceStorage getInstance() {
        return storage;
    }

    public Admin getAdmin() {
        return admin;
    }

    public User getUserById(String userId) {
        return userId_userInstance_map.get(userId);
    }

    public void addUser(String userId,User user) {
        userId_userInstance_map.put(userId,user);
    }

    public HashMap<String,User> getUserId_userInstance_map() {
        return userId_userInstance_map;
    }
    public void setUserId_userInstance_map(HashMap<String,User> userId_userInstance_map1) {
        userId_userInstance_map=userId_userInstance_map1;
    }


}





