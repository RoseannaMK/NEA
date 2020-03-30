package util;

import android.app.Application;

public class AppAPI extends Application {
    private String username;
    private String userId;
    private String password;
    private String score;
    private static AppAPI instance;

    public static AppAPI getInstance() {
        if (instance == null)
            instance = new AppAPI();
        return instance;

    }

    public AppAPI(){}

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {return userId;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() { return password;}

    public void setPassword(String password){this.password = password;}

    public String getScore() {return score;}

    public void setScore(String score) {this.score = score;}
}
