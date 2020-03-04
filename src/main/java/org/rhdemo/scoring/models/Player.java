package org.rhdemo.scoring.models;

public class Player {
    public static class Avatar {
        private int body;
        private int eyes;
        private int mouth;
        private int ears;
        private int nose;
        private int color;

        public Avatar() {

        }

        public Avatar(Avatar copy) {
            this.body = copy.body;
            this.eyes = copy.eyes;
            this.mouth = copy.mouth;
            this.ears = copy.ears;
            this.nose = copy.nose;
            this.color = copy.color;
        }

        public int getBody() {
            return body;
        }

        public void setBody(int body) {
            this.body = body;
        }

        public int getEyes() {
            return eyes;
        }

        public void setEyes(int eyes) {
            this.eyes = eyes;
        }

        public int getMouth() {
            return mouth;
        }

        public void setMouth(int mouth) {
            this.mouth = mouth;
        }

        public int getEars() {
            return ears;
        }

        public void setEars(int ears) {
            this.ears = ears;
        }

        public int getNose() {
            return nose;
        }

        public void setNose(int nose) {
            this.nose = nose;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    private String id;
    private String username;
    private Avatar avatar;

    public Player() {

    }

    public Player(Player copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.avatar = new Avatar(copy.avatar);
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

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
