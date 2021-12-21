package agh.ics.oop;

import java.awt.desktop.SystemEventListener;
import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver,IMapObserver {


    public static MapBoundary boundedMap= new MapBoundary();
    protected Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grassFields = new HashMap<>();
    private static final Vector2d MARGIN = new Vector2d(1,1);


    private double jungleRatio;
    protected int plantEnergy = 1;
    private int moveEnergy=1; //placeholder
    private int startEnergy = 100; //placeholder
    private Vector2d jungleBL;
    private Vector2d jungleTR;
    private final int mapWidth=20; //placeholder
    private final int mapHeight=20; //placeholder
    private final Vector2d mapBL =  new Vector2d(0,0);
    private final Vector2d mapTR = new Vector2d (mapWidth-1, mapHeight-1);
    private int currentlyLivingAnimals = 0;
    private final Set<IMapObserver> observers = new HashSet<>();




    public void addObserver(IMapObserver observer) {
        observers.add(observer);
    }

    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(mapBL.substract(MARGIN), mapTR.add(MARGIN));
    }


    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        Object a = objectAt(animal.getPosition());
        if (!canMoveTo(animal.getPosition())) {
            if (a instanceof Animal) {
                throw new IllegalArgumentException("cannot place at " + animal.getPosition());
            }
        }
        animal.setRandomGene();
        animal.setEnergy(startEnergy, moveEnergy);
        insertToAnimals(animal, animal.getPosition());
        currentlyLivingAnimals++;

        animal.addObserver(this);

        return true;
    }

    public void insertToAnimals(Animal animal, Vector2d position) {
        if (isOccupied(position)){
            animals.get(position).add(animal);
        }
        else {
            ArrayList<Animal> animalsAtPos = new ArrayList<>();
            animalsAtPos.add(animal);
            animals.put(position,animalsAtPos);
        }
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(mapBL) && position.precedes(mapTR);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) ;
    }

    public boolean isOccupiedByGrass(Vector2d position) {
        return grassFields.containsKey(position);
    }


    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            if (animals.get(position) != null){
                return animals.get(position);
            }
        }
        if(isOccupiedByGrass(position)){
            return this.grassAt(position); }
        return null;
    }

    public Object grassAt(Vector2d position) {
        return grassFields.get(position);
    }


    public int posChangedForNAniamls = 0;

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        posChangedForNAniamls++;
        if (!oldPosition.equals(newPosition)) {
            for (Animal animal1: animals.get(oldPosition)) {

                if (animal1.equals(animal)) {
                    animals.get(oldPosition).remove(animal);

                    if(animals.get(oldPosition).size()==0) {
                        animals.remove(oldPosition);
                    }
                    insertToAnimals(animal, newPosition);
                    break;
                }
            }
        }

        if (currentlyLivingAnimals==posChangedForNAniamls) {
            allMoved();
        }
    }



    public void allMoved() {
        for (IMapObserver obeserver: observers) {
            obeserver.simulateDay();
        }
        posChangedForNAniamls=0;

    }


    public int getNumOfCurLivingAnimals() {
        return currentlyLivingAnimals;
    }



    public void eatPlants() {
        for(ArrayList<Animal> arrayList: animals.values()) {

            if (isOccupiedByGrass(arrayList.get(0).getPosition())) {
                int actualHighestEnergy = 0;
                for (Animal animal : arrayList) {
                    if (animal.getEnergy() > actualHighestEnergy) {
                        actualHighestEnergy = animal.getEnergy();
                    }
                }
                for(Animal animal:arrayList) {
                    if(animal.getEnergy()==actualHighestEnergy) {
                        animal.eatGrass(plantEnergy);
                        grassFields.remove(animal.getPosition());
                        break;
                    }
                }
            }
        }
    }


    public void stopSimulation() {
        System.exit(0);
    }


    public void removeDeadAnimals() {
        ArrayList<Vector2d> positionsToDelete = new ArrayList<>();
        Map<Vector2d, ArrayList<Integer>> indexesToDelete = new HashMap<>();
//        System.out.println(animals);
        for(ArrayList<Animal> arrayList: animals.values() ) {
            Integer i = 0;
            for(Animal animal: arrayList) {
                if(animal.getEnergy()<=0) {
                    if(arrayList.size()==1) {
                        positionsToDelete.add(animal.getPosition());
                    }
                    else {
                        if (indexesToDelete.containsKey(animal.getPosition())) {
                            ArrayList<Integer> arrOfIndexes = indexesToDelete.get(animal.getPosition());
                            arrOfIndexes.add(i);
                        }
                        else {
                            ArrayList<Integer> arrOfIndexes = new ArrayList<>();
                            arrOfIndexes.add(i);
                            indexesToDelete.put(animal.getPosition(), arrOfIndexes);
                        }
                    }
                    currentlyLivingAnimals--;
                }
                i++;
            }

        }
        for(Vector2d positionToDelete: positionsToDelete) {
            animals.remove(positionToDelete);
        }
        for(ArrayList<Animal> arrayList: animals.values()) {
            Vector2d somePosition= arrayList.get(0).getPosition();
            if(indexesToDelete.containsKey(somePosition)) {
                if(indexesToDelete.get(somePosition).size()==animals.get(somePosition).size()) {
                    animals.remove(somePosition);
                }
                else {
                    int i = 0;
                    ArrayList<Animal> tmpToInsert = new ArrayList<>();
                    for(Integer index: indexesToDelete.get(somePosition)) {
                        if(index!=i) {
                            tmpToInsert.add(arrayList.get(i));
                        }
                        i++;
                    }
                    animals.remove(somePosition);
                    animals.put(somePosition, tmpToInsert);
                }
            }

        }


//        System.out.println(animals);

        if(animals.isEmpty()) {
            stopSimulation();
        }


    }

    public void spawnNewAnimal(Animal animal) {
        animals.get(animal.getPosition()).add(animal);
        animal.addObserver(this);
    }

    public void setJungle(double jungleRatio) {
        int jungleHeight = (int) ((this.mapHeight-1)*jungleRatio);
        int jungleWidth = (int) ((this.mapWidth-1)*jungleRatio);

        this.jungleBL = new Vector2d((int)((this.mapWidth-1)-jungleWidth)/2,
                (int)((this.mapHeight-1)-jungleHeight)/2);
        this.jungleTR = new Vector2d(jungleBL.x+jungleWidth-1, jungleBL.y+jungleHeight-1);

        spawnGrassInTheJungle(jungleBL, jungleTR);
    }



    public void spawnGrassInTheJungle(Vector2d botLeft, Vector2d topRight) {

        for(int i =botLeft.x; i<topRight.x;i++) {
            for(int j=botLeft.y;j<topRight.y;j++) {
                if (!this.isOccupied(new Vector2d(i,j))) {
                    Grass newGrass = new Grass(new Vector2d(i,j), this.plantEnergy);
                    grassFields.put(new Vector2d(i,j), newGrass);
                }
            }
        }
    }


    public void spawnGrassOnSteppe(int grassSpawnedEachDay) {
        int i = 0;
        int tooManyTimes = (int) Math.pow(grassSpawnedEachDay,2);
        int cnt =0;

        Random generator= new Random();

        while (i<grassSpawnedEachDay && cnt < tooManyTimes) {

            Vector2d grassSpawnedPos = new Vector2d(generator.nextInt(this.mapWidth),
                    generator.nextInt(this.mapHeight));

            if(!this.isOccupied(grassSpawnedPos) && !this.isOccupiedByGrass(grassSpawnedPos)) {
                if (grassSpawnedPos.x < jungleBL.x || grassSpawnedPos.x > jungleTR.x || grassSpawnedPos.y<jungleBL.y || grassSpawnedPos.y>jungleTR.y) {
                    Grass ngrass = new Grass(grassSpawnedPos, plantEnergy);
                    grassFields.put(ngrass.getPosition(), ngrass);
                    i++;
                }
            }

            cnt++;

        }
    }

}