class Sheep extends Organism {
    public Sheep() { super("Sheep", 3, 15, 70.0); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Sheep(); }
}