import java.util.Map;

class Wolf extends Carnivore {
    private static final Map<Class<? extends Organism>, Integer> preyMap = Map.of(
            Rabbit.class, 60, Deer.class, 40, Mouse.class, 80, Horse.class, 10,
            Goat.class, 60, Sheep.class, 70, Hog.class, 15, Buffalo.class, 10, Duck.class, 40
    );

    public Wolf() { super("Wolf", 3, 8, 50.0, preyMap); }

    @Override
    public void feed(Zone zone) {
        hunt(zone);
    }

    @Override
    protected Organism createNew() {
        return new Wolf();
    }

    @Override public Organism reproduce() { return new Wolf(); }
}