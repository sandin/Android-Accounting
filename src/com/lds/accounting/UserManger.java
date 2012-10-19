package com.lds.accounting;

import android.content.Context;

import com.lds.accounting.model.User;

public class UserManger {
    private static UserManger mInstance;
    
    private User loginedUser = null;
    
    public static UserManger getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserManger(context);
        }
        return mInstance;
    }
    
    private UserManger(Context context) {
    }
    
    public User login(String username, String password) {
        if ("lds".equals(username) && password.equals("lds")) { // TODO: database
            loginedUser = new User();
            return loginedUser;
        }
        return null;
    }
    
    public boolean isLogin() {
        return loginedUser != null;
        //return true;
    }
    
    public void clear() {
        loginedUser = null;
    }

}
