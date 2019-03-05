package cs361.battleships.models;

public class Weapons {
    private boolean upgrade;

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    public boolean getUpgrade() {
        return upgrade;
    }

    public Weapons() {
        upgrade = false;
    }

}
