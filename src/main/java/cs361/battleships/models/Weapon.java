package cs361.battleships.models;

public class Weapon {
    private boolean upgrade;

    public Weapon() {
        upgrade = false;
    }

    public void setUpgrade() {
        this.upgrade = true;
    }

    public boolean getUpgrade() {
        return upgrade;
    }

}
