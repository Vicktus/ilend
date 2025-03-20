import java.util.Map;

class Fox extends Carnivore {
    private static final Map<Class<? extends Organism>, Integer> preyMap = Map.of(
            Rabbit.class, 70, Mouse.class, 90, Duck.class, 60, Caterpillar.class, 40
    );

    public Fox() { super("Fox", 2, 2, 8.0, preyMap); }

    @Override
    public void feed(Zone zone) {
        hunt(zone);
    }

    @Override
    protected Organism createNew() {
        return new Fox();
    }

    @Override public Organism reproduce() { return new Fox(); }
}