import java.util.Map;

class Boa extends Carnivore {
    private static final Map<Class<? extends Organism>, Integer> preyMap = Map.of(
            Fox.class, 15, Rabbit.class, 20, Mouse.class, 40, Duck.class, 10
    );

    public Boa() { super("Boa", 1, 3, 15.0, preyMap); }

    @Override
    public void feed(Zone zone) {
        hunt(zone);
    }

    @Override
    protected Organism createNew() {
        return new Boa();
    }

    @Override public Organism reproduce() { return new Boa(); }
}