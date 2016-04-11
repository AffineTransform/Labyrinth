package io.at.game;

import io.at.game.objects.Decoration;
import io.at.game.objects.DynamicsCalculator;
import io.at.game.objects.GameObject;
import io.at.game.visual.Window;
import io.at.game.visual.Screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/**
 * Main game class.
 */
public final class Game implements Runnable {

    //Constant values
    private static final String TITLE = "Labyrinth";
    private static final String VERSION = "alpha-0.0.1";
    private static final int WIDTH = 800;
    private static final int HEIGHT = WIDTH * 3 / 4;

    private static final String CLASS_PATH = Game.class.getResource("").getPath().replace("file:", "");

    //Non-constant values
    private Screen screen;
    private KeyInput keyInput;
    private MouseInput mouseInput;
    private Window window;
    private DynamicsCalculator calculator;
    private static Vector<Decoration> loadedDecorations;
    private static Vector<GameObject> loadedObjects;

    /**
     * Player accessor.
     * @return player
     */
    static GameObject player() {
        return loadedObjects.get(0);
    }

    private static boolean running;
    private static boolean paused; //TODO Pause
    private static int exitCode;

    /**
     * Stop process and exit the game.
     * @param errorCode - exit code.
     */
    public static void stop(final int errorCode) {
        exitCode = errorCode;
        if (exitCode == ErrorCodes.SCREEN_IS_NULL_ERROR) {
            System.err.println("Screen variable is null!");
        } else if (exitCode == ErrorCodes.CALCULATOR_IS_NULL_ERROR) {
            System.err.println("Dynamics calculator is null!");
        } else if (exitCode == ErrorCodes.OBJECTS_ARRAY_IS_NULL_ERROR) {
            System.err.println("Objects array is null!");
        } else if (exitCode == ErrorCodes.PLAYER_IS_NULL_ERROR) {
            System.err.println("Player is null!");
        } else if (errorCode == ErrorCodes.THREAD_WAS_INTERRUPTED_ERROR) {
            System.err.println("Thread was interrupted!");
        } else if (errorCode == ErrorCodes.FILE_READING_ERROR) {
            System.err.println("Error while reading sprite!");
        } else if (errorCode == ErrorCodes.FILE_NOT_FOUND_ERROR) {
            System.err.println("File wasn't found!");
        }
        running = false;
    }

    /**
     * Switch paused boolean to opposite value.
     */
    static void switchPause() {
        paused = !paused;
        if (paused) {
            System.out.println("Paused!");
        } else {
            System.out.println("Unpaused!");
        }
    }

    static boolean isPaused() {
        return paused;
    }

    /**
     * Main constructor.
     */
    Game() {
        running = true;
        paused = false;
        exitCode = ErrorCodes.EVERYTHING_IS_OK;

        keyInput = new KeyInput();
        mouseInput = new MouseInput();
        screen = new Screen(WIDTH, HEIGHT);
        screen.addKeyListener(keyInput);
        screen.addMouseListener(mouseInput);
        screen.addMouseMotionListener(mouseInput);
        window = new Window(screen, TITLE + " " + VERSION);

        calculator = new DynamicsCalculator();
        loadedDecorations = new Vector<>();
        loadedObjects = new Vector<>();

        try {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    loadedDecorations.add(new Decoration(200 * i, 200 * j, CLASS_PATH + "sprites/ground/grass_0.png"));
                }
            }
            loadedObjects.add(new GameObject("Kant", 0, 0, CLASS_PATH + "sprites/hero/hero_0.png"));
        } catch (FileNotFoundException e) {
            Game.stop(ErrorCodes.FILE_NOT_FOUND_ERROR);
        } catch (IOException e) {
            Game.stop(ErrorCodes.FILE_READING_ERROR);
        }

        new Thread(this).start();
        new Thread(mouseInput).start();
        new Thread(keyInput).start();
    }

    /**
     * Main game method. Contains main game cycle.
     */
    public void run() {
        final int delay = 1000 / 60;

        while (running) {

            try {
                calculator.calculate(loadedObjects);
                screen.render(mouseInput.getX(), mouseInput.getY(), loadedDecorations, loadedObjects);

            } catch (NullPointerException e) {
                if (screen == null) {
                    stop(ErrorCodes.SCREEN_IS_NULL_ERROR);
                } else if (calculator == null) {
                    stop(ErrorCodes.CALCULATOR_IS_NULL_ERROR);
                } else if (loadedObjects == null) {
                    stop(ErrorCodes.OBJECTS_ARRAY_IS_NULL_ERROR);
                }
            }

            //Delay
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                stop(ErrorCodes.THREAD_WAS_INTERRUPTED_ERROR);
            }
        }

        System.exit(exitCode);
    }
}
