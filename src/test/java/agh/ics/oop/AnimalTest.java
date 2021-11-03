package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class AnimalTest {


    @Test
    void orientationTest() {
        Animal abc = new Animal();


        MoveDirection[][] moves = {
                {MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT}, //test1
                {MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.BACKWARD,MoveDirection.BACKWARD}, //test2
                {MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT}, //test3
                {MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT} //test4
        };

        MapDirection[] result = {MapDirection.NORTH, MapDirection.SOUTH, MapDirection.EAST,MapDirection.WEST};
        int i = 0;

        for(MoveDirection[] p1: moves) {
            for(MoveDirection a: p1) {
                abc.move(a);
            }
            assertEquals(abc.getDirection(), result[i]);
            i++;

        }
    }


    @Test
    void isAtMapTest() {
        Animal abc = new Animal();


        MoveDirection[] moves = {MoveDirection.FORWARD,MoveDirection.FORWARD,
                MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD};
        for(int i=0; i<10; i++) {
            for(MoveDirection dir: moves) {
                abc.move(dir);
            }
            abc.move(MoveDirection.LEFT);

            assertTrue(abc.getPos().x <= 4 && 0 <= abc.getPos().x &&
                    abc.getPos().y <= 4 && 0 <= abc.getPos().y);
        }
    }


    @Test
    void positionTest() {
        Animal abc = new Animal();


        Vector2d[] vectors = {new Vector2d(3,1),
                                new Vector2d(3,4),
                                new Vector2d(0,2),
                                new Vector2d(1,3)};

        MoveDirection[][] moves = {
                {MoveDirection.BACKWARD,MoveDirection.LEFT,MoveDirection.BACKWARD,MoveDirection.RIGHT}, //test1
                {MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.BACKWARD,MoveDirection.BACKWARD}, //test2
                {MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD}, //test3
                {MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.LEFT,MoveDirection.FORWARD} //test4
        };
        int i = 0;

        for(MoveDirection[] p: moves) {
            for(MoveDirection dir: p) {
                abc.move(dir);

            }
        assertTrue(abc.isAt(vectors[i]));
        i++;

        }
    }


    @Test
    void testMove() {
        Animal a = new Animal();
        assertEquals(new Vector2d(2, 2), a.getPos());
        assertEquals(MapDirection.NORTH, a.getDirection());

        MoveDirection[] moves = {MoveDirection.FORWARD,MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.RIGHT,
                        MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD,MoveDirection.RIGHT, MoveDirection.FORWARD,
                        MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.FORWARD,
                        MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.LEFT,
                        MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD};

        Vector2d[] positions = {new Vector2d(2, 3), new Vector2d(2, 3), new Vector2d(3, 3),
                new Vector2d(3, 3), new Vector2d(3, 2), new Vector2d(3, 1),
                new Vector2d(3, 0), new Vector2d(3, 0), new Vector2d(2, 0),
                new Vector2d(1, 0), new Vector2d(0, 0), new Vector2d(0, 0),
                new Vector2d(0, 0), new Vector2d(0, 1), new Vector2d(0, 1),
                new Vector2d(0, 1), new Vector2d(0, 1), new Vector2d(0, 1),
                new Vector2d(0, 1), new Vector2d(0, 0), new Vector2d(0, 1),
                new Vector2d(0, 2), new Vector2d(0, 3), new Vector2d(0, 4)};

        MapDirection[]  directions = { MapDirection.NORTH ,MapDirection.EAST ,MapDirection.EAST ,
                MapDirection.SOUTH ,MapDirection.SOUTH ,MapDirection.SOUTH ,MapDirection.SOUTH ,
                MapDirection.WEST ,MapDirection.WEST ,MapDirection.WEST ,MapDirection.WEST ,
                MapDirection.WEST ,MapDirection.NORTH ,MapDirection.NORTH ,MapDirection.WEST ,
                MapDirection.WEST , MapDirection.WEST ,MapDirection.WEST ,MapDirection.SOUTH ,
                MapDirection.SOUTH , MapDirection.SOUTH , MapDirection.SOUTH ,MapDirection.SOUTH ,
        };

        int i = 0;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
              a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;

        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPos());
        assertEquals(directions[i], a.getDirection());
        i++;
    }
}




