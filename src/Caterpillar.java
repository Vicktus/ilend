class Caterpillar extends Organism {
    public Caterpillar() { super("Caterpillar", 0, 0, 0.01); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Caterpillar(); }
}