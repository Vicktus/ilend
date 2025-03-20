import java.util.Random;

abstract class Organism {
    protected String species;
    protected int movementSpeed;
    protected double maxFoodIntake;
    protected double weight;
    protected boolean isAlive = true;

    public Organism(String species, int movementSpeed, double maxFoodIntake, double weight) {
        this.species = species;
        this.movementSpeed = movementSpeed;
        this.maxFoodIntake = maxFoodIntake;
        this.weight = weight;
    }

    public String getSpecies() { return species; }
    public abstract void feed(Zone zone);
    public void move(Island island, int currentX, int currentY) {
        Random random = new Random();
        int newX = Math.max(0, Math.min(island.getWidth() - 1, currentX + random.nextInt(3) - 1));
        int newY = Math.max(0, Math.min(island.getHeight() - 1, currentY + random.nextInt(3) - 1));
        island.moveOrganism(this, currentX, currentY, newX, newY);
    }
    public abstract Organism reproduce();
}