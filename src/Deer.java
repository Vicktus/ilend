class Deer extends Organism {
    public Deer() { super("Deer", 3, 6, 80.0); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Deer(); }
}