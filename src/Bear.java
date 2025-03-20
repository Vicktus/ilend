import java.util.Map;

class Bear extends Carnivore {
    private static final Map<Class<? extends Organism>, Integer> preyMap = Map.of(
            Boa.class, 80, Horse.class, 40, Deer.class, 80, Rabbit.class, 80,
            Mouse.class, 90, Goat.class, 70, Sheep.class, 70, Hog.class, 50, Buffalo.class, 20, Duck.class, 10
    );

    public Bear() { super("Bear", 2, 15, 200.0, preyMap); }

    @Override
    public void feed(Zone zone) {
        hunt(zone);
    }

    @Override
    protected Organism createNew() {
        return new Bear();
    }

    @Override public Organism reproduce() { return new Bear(); }
}