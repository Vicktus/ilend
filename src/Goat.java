class Goat extends Organism {
    public Goat() { super("Goat", 3, 10, 60.0); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Goat(); }
}