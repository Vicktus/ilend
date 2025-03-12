import java.util.*;
import java.util.concurrent.*;

abstract class Organism {
    protected String species;
    protected int movementSpeed;
    protected double maxFoodIntake;
    protected double weight;
    protected boolean isAlive = true;

    public Organism(String species, int movementSpeed, double maxFoodIntake, double weight) {
        this.species = species;
        this.movementSpeed = movementSpeed;
        this.maxFoodIntake = maxFoodIntake;
        this.weight = weight;
    }

    public String getSpecies() { return species; }
    public abstract void feed(Zone zone);
    public void move(Island island, int currentX, int currentY) {
        Random random = new Random();
        int newX = Math.max(0, Math.min(island.getWidth() - 1, currentX + random.nextInt(3) - 1));
        int newY = Math.max(0, Math.min(island.getHeight() - 1, currentY + random.nextInt(3) - 1));
        island.moveOrganism(this, currentX, currentY, newX, newY);
    }
    public abstract Organism reproduce();
}

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

class Mouse extends Organism {
    private static final Map<Class<? extends Organism>, Integer> preyMap = Map.of(Caterpillar.class, 90);

    public Mouse() { super("Mouse", 1, 2, 0.5); }

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

    @Override public Organism reproduce() { return new Mouse(); }
}

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

class Horse extends Organism {
    public Horse() { super("Horse", 4, 10, 120.0); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Horse(); }
}

class Goat extends Organism {
    public Goat() { super("Goat", 3, 10, 60.0); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Goat(); }
}

class Sheep extends Organism {
    public Sheep() { super("Sheep", 3, 15, 70.0); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Sheep(); }
}

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

class Duck extends Organism {
    private static final Map<Class<? extends Organism>, Integer> preyMap = Map.of(Caterpillar.class, 90);

    public Duck() { super("Duck", 4, 0.15, 1.0); }

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

    @Override public Organism reproduce() { return new Duck(); }
}

class Caterpillar extends Organism {
    public Caterpillar() { super("Caterpillar", 0, 0, 0.01); }

    @Override
    public void feed(Zone zone) {
        if (zone.plantCount > 0) {
            zone.plantCount--;
            System.out.println(species + " ест траву.");
        }
    }

    @Override public Organism reproduce() { return new Caterpillar(); }
}

class Zone {
    List<Organism> organisms = new ArrayList<>();
    int plantCount = 5;
}

class Island {
    private final int width;
    private final int height;
    private final Zone[][] zones;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.zones = new Zone[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zones[i][j] = new Zone();
            }
        }
        populateIsland();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    private void populateIsland() {
        Random random = new Random();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (random.nextDouble() < 0.4) zones[i][j].organisms.add(new Wolf());
                if (random.nextDouble() < 0.4) zones[i][j].organisms.add(new Bear());
                if (random.nextDouble() < 0.4) zones[i][j].organisms.add(new Boa());
                if (random.nextDouble() < 0.4) zones[i][j].organisms.add(new Fox());
                if (random.nextDouble() < 0.4) zones[i][j].organisms.add(new Eagle());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Deer());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Horse());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Rabbit());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Mouse());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Buffalo());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Duck());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Caterpillar());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Sheep());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Goat());
                if (random.nextDouble() < 0.6) zones[i][j].organisms.add(new Hog());
            }
        }
    }

    public void moveOrganism(Organism organism, int oldX, int oldY, int newX, int newY) {
        if (zones[oldX][oldY].organisms.remove(organism)) {
            zones[newX][newY].organisms.add(organism);
        }
    }

    public void simulate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        scheduler.scheduleAtFixedRate(this::updateZones, 0, 2, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::printStatistics, 0, 3, TimeUnit.SECONDS);
    }

    private void updateZones() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zones[i][j].plantCount = Math.min(10, zones[i][j].plantCount + 1);

                List<Organism> organismsCopy = new ArrayList<>(zones[i][j].organisms);
                for (Organism organism : organismsCopy) {
                    organism.feed(zones[i][j]);
                    organism.move(this, i, j);
                    if (Math.random() < 0.1) {
                        zones[i][j].organisms.add(organism.reproduce());
                    }
                }
            }
        }
    }

    private void printStatistics() {
        try {
            System.out.println("Статистика острова:");
            boolean hasOrganisms = false;

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    List<Organism> organismsCopy = new ArrayList<>(zones[i][j].organisms);

                    if (!organismsCopy.isEmpty()) hasOrganisms = true;

                    Map<String, Integer> speciesCount = new HashMap<>();
                    for (Organism organism : organismsCopy) {
                        if (organism != null) {
                            speciesCount.put(organism.getSpecies(), speciesCount.getOrDefault(organism.getSpecies(), 0) + 1);
                        }
                    }
                    System.out.println("Квадрат [" + i + ", " + j + "]: " + speciesCount + ", Растений: " + zones[i][j].plantCount);
                }
            }

            if (!hasOrganisms) {
                System.out.println("Все организмы вымерли. Симуляция завершена.");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Island island = new Island(20, 100);
        island.simulate();
    }
}
