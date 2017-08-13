package co.tide.labescape;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class LabEscape {

    private static final char FREE = ' ';
    private static final char PATH = '•';

    /**
     * @param labyrinth A labyrinth drawn on a matrix of characters. It's at least 4x4, can be a rectangle or a square.
     *                  Walkable areas are represented with a space character, walls are represented with a big O character.
     *                  The escape point is always on the border (see README)
     * @param startX    Starting row number for the escape. 0 based.
     * @param startY    Starting column number for the escape. 0 based.
     * @return A char matrix with the same labyrinth and a path drawn from the starting point to the escape
     */
    public static char[][] drawPathForEscape(char[][] labyrinth, int startX, int startY) {
        Position startingPosition = new Position(startX, startY);

        Queue<Position> queue = new LinkedList<>();
        queue.offer(startingPosition);

        Map<Position, Position> parents = new HashMap<>();
        Set<Position> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (!visited.contains(current)) {
                visited.add(current);

                if (isEscape(current, labyrinth)) {
                    return drawPath(current, parents, labyrinth);
                }

                Position up = current.up();
                if (isValidWay(up, labyrinth) && !visited.contains(up)) {
                    queue.offer(up);
                    parents.put(up, current);
                }

                Position down = current.down();
                if (isValidWay(down, labyrinth) && !visited.contains(down)) {
                    queue.offer(down);
                    parents.put(down, current);
                }

                Position left = current.left();
                if (isValidWay(left, labyrinth) && !visited.contains(left)) {
                    queue.offer(left);
                    parents.put(left, current);
                }

                Position right = current.right();
                if (isValidWay(right, labyrinth) && !visited.contains(right)) {
                    queue.offer(right);
                    parents.put(right, current);
                }
            }
        }

        throw new NoEscapeException();
    }

    private static boolean isValidWay(Position position, char[][] labyrinth) {
        return isInsideLabyrinth(position, labyrinth) && labyrinth[position.getX()][position.getY()] == FREE;
    }

    private static boolean isInsideLabyrinth(Position position, char[][] labyrinth) {
        return position.getX() >= 0 && position.getX() < labyrinth.length &&
                position.getY() >= 0 && position.getY() < labyrinth[0].length;
    }

    private static boolean isEscape(Position position, char[][] labyrinth) {
        return isEdge(position, labyrinth) && labyrinth[position.getX()][position.getY()] == FREE;
    }

    private static char[][] drawPath(Position escapePosition, Map<Position, Position> parents, char[][] labyrinth) {
        char[][] labyrinthWithEscape = deepCopy(labyrinth);

        Position current = escapePosition;
        while (parents.containsKey(current)) {
            labyrinthWithEscape[current.getX()][current.getY()] = PATH;
            current = parents.remove(current);
        }
        labyrinthWithEscape[current.getX()][current.getY()] = PATH;

        return labyrinthWithEscape;
    }

    private static char[][] deepCopy(char[][] labyrinth) {
        char[][] copy = new char[labyrinth.length][];
        for (int i = 0; i < labyrinth.length; i++) {
            copy[i] = Arrays.copyOf(labyrinth[i], labyrinth[i].length);
        }
        return copy;
    }

    private static boolean isEdge(Position position, char[][] labyrinth) {
        boolean isHorizontalEdge = position.getX() == 0 || position.getX() == labyrinth.length - 1;
        boolean isVerticalEdge = position.getY() == 0 || position.getY() == labyrinth[0].length - 1;

        return isHorizontalEdge || isVerticalEdge;

    }

}
