package org.rhdemo.scoring.models;

public class Player {
    private String id;
    private String username;
    private String creationServer;
    private String gameServer;
    private Avatar avatar;

    public Player() {

    }

    public Player(Player copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.avatar = copy.avatar;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreationServer() {
        return creationServer;
    }

    public void setCreationServer(String creationServer) {
        this.creationServer = creationServer;
    }

    public String getGameServer() {
        return gameServer;
    }

    public void setGameServer(String gameServer) {
        this.gameServer = gameServer;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
