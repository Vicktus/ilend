class Horse extends Organism {
    public Horse() { super("Horse", 4, 10, 120.0); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Horse(); }
}