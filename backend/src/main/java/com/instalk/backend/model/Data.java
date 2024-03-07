package com.instalk.backend.model;

import java.util.List;

public class Data {

    private List<User> followers;
    private List<User> followings;

    public List<User> getFollowers() {
        return followers;
    }
    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
    public List<User> getFollowings() {
        return followings;
    }
    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

}


