import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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