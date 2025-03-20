class Buffalo extends Organism {
    public Buffalo() { super("Buffalo", 3, 100, 700.0); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Buffalo(); }
}