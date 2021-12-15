package agh.ics.oop;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SimulationEngine implements IEngine, IPositionChangeObserver, Runnable {

    private final List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves;
    private final AbstractWorldMap map;
    private final Set<IPositionChangeObserver> observers = new HashSet<>();
    private int grassEnergy = 5;
    private int grassSpawnedEachDay = 2;

    public SimulationEngine( AbstractWorldMap map, Vector2d[] initialPositions) {
        this.map = map;
        map.setJungle(0.4);
        for (Vector2d pos : initialPositions) {

            Animal animal = new Animal(map, pos);
            animals.add(animal);
            animal.addObserver(this);
            map.place(animal);

        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public void setGrassEnergy(int grassEnergy) {
        this.grassEnergy = grassEnergy;
    }

    public void setMoves(List<MoveDirection> moves) {
        this.moves=moves;
    }


    @Override
    public void run() {
        System.out.println("its running");


        //BASIC VERSION
        int currentAnimal = 0;
        for (MoveDirection oneMove : moves) {
            animals.get(currentAnimal).move(oneMove);
            currentAnimal = (currentAnimal + 1) % animals.size();
        }
        // RANDOM MOVES
//        int i = 0;
//        while(i<100) {
//            for(Animal animal: animals) {
//                animal.moveWithPref();
////                System.out.println(i);
//            }
//            map.spawnGrassOnSteppe(this.grassSpawnedEachDay);
//            map.removeDeadAnimals(this);
//            i++;
//        }

    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver obs: observers) {
                obs.positionChanged(oldPosition, newPosition);
        }
    }
}