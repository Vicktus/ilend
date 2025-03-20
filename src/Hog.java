import java.util.Iterator;
import java.util.Map;

class Hog extends Organism {
    private static final Map<Class<? extends Organism>, Integer> preyMap = Map.of(Mouse.class, 50, Caterpillar.class, 90);

    public Hog() { super("Hog", 2, 50, 400.0); }

    @Override
    public void feed(Zone zone) {
        Iterator<Organism> iterator = zone.organisms.iterator();
        boolean hasEaten = false;

        while (iterator.hasNext()) {
            Organism prey = iterator.next();
            if (preyMap.containsKey(prey.getClass()) && Math.random() * 100 < preyMap.get(prey.getClass())) {
                iterator.remove();
                System.out.println(species + " съел " + prey.getSpecies());
                hasEaten = true;
                break;
            }
        }

        if (!hasEaten && zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Hog(); }
}