import java.util.Map;

class Eagle extends Carnivore {
    private static final Map<Class<? extends Organism>, Integer> preyMap = Map.of(
            Fox.class, 10, Rabbit.class, 90, Mouse.class, 90, Duck.class, 80
    );

    public Eagle() { super("Eagle", 4, 3, 6.0, preyMap); }

    @Override
    public void feed(Zone zone) {
        hunt(zone);
    }

    @Override
    protected Organism createNew() {
        return new Eagle();
    }

    @Override public Organism reproduce() { return new Eagle(); }
}