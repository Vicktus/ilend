class Rabbit extends Organism {
    public Rabbit() { super("Rabbit", 2, 4, 2.5); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Rabbit(); }
}
