package model;

public class User {
    private static User instance = null;

    public static User getInstance() {
        if(instance==null){
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User();
                }
            }
        }
        return instance;
    }

    private User() {
    }

}
