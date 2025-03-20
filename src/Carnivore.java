import java.util.Iterator;
import java.util.Map;

abstract class Carnivore extends Organism {
    protected final Map<Class<? extends Organism>, Integer> preyMap;
    private boolean isStarving = false;
    private int hunger = 5;

    public Carnivore(String species, int movementSpeed, int maxFoodIntake, double weight, Map<Class<? extends Organism>, Integer> preyMap) {
        super(species, movementSpeed, maxFoodIntake, weight);
        this.preyMap = preyMap;
    }

    protected void hunt(Zone zone) {
        Iterator<Organism> iterator = zone.organisms.iterator();
        boolean hasEaten = false;

        while (iterator.hasNext()) {
            Organism prey = iterator.next();
            if (preyMap.containsKey(prey.getClass()) && Math.random() * 100 < preyMap.get(prey.getClass())) {
                iterator.remove();
                isStarving = true;
                hunger = 5;
                System.out.println(species + " съел " + prey.getSpecies());
                hasEaten = true;
                break;
            }
        }

        if (!hasEaten) {
            hunger--;
            if (hunger <= 0) {
                System.out.println(species + " умер от голода.");
                zone.organisms.remove(this);
            }
        }
    }

    @Override
    public Organism reproduce() {
        if (isStarving && hunger > 2 && Math.random() < 0.1) {
            isStarving = false;
            System.out.println(species + " размножился.");
            return createNew();
        }
        return null;
    }

    protected abstract Organism createNew();
}