import com.Difficulty;
import com.board.Board;
import com.input.ConsoleInput;
import com.input.Input;
import com.sprite.*;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Random;

public class Game {

    public static boolean gameOver;
    private static Board board;
    private static boolean redraw;
    private static Input<String> consoleInput;
    private static boolean finish;

    private static final String PARSE_STRING_BY = ",";

    //TODO ask for input here
    public static void init(){
        Difficulty difficulty = prompt();
        if(difficulty == Difficulty.NONE || difficulty ==null){
            gameOver = true;
            finish = true;
            return;
        }
        board = new Board(difficulty.getRows(), difficulty.getColumns(), difficulty.getBombs());
        //add bombs
        addBombs(board);
        //add numbers
        addNumbers(board);
        //add blanks
        addBlanks(board);


        redraw = true;
        gameOver = false;

    }

    private static Difficulty prompt() {
        System.out.println("Start new game? Y/N");
        String line = null;
        try {
            line = consoleInput.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert line != null;
        switch(line.toLowerCase()){
            case "y":
                break;
            case "n":{
                return Difficulty.NONE;
            }
            default:
                return Difficulty.NONE;

        }

        System.out.println("Please enter difficulty: Easy, Medium, Hard");
        try {
            line = consoleInput.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert line != null;
        switch(line.toLowerCase()){
            case "easy":
                return Difficulty.EASY;
            case "normal":
                return Difficulty.NORMAL;
            case "hard":
                return Difficulty.HARD;
            default:
                return Difficulty.NONE;
        }

    }

    private static void selectSprite(int row, int col){
        Sprite sprite = board.getSprite(row, col);
        if(sprite instanceof Bomb){
            for(Sprite bomb: board.getBombs()){
                bomb.flip();
            }
            board.draw();
            gameOver = true;
        }
        else{
            Board.search(sprite, board);
        }
        redraw = true;
    }


    private static void addBlanks(Board board) {

        for(int i = 0; i < board.getRows(); i++){
            for(int j = 0; j < board.getColumns(); j++){
                Sprite sprite = board.getSprite(i, j);
                if(sprite==null){
                    sprite = SpriteFactory.newBlank(i, j);
                    board.add(i, j, sprite);
                }
            }
        }
    }

    private static void addNumbers(Board board) {

        for(Sprite sprite: board.getBombs()){
            addNumbersHelper(board, sprite);
        }
    }

    private static void addNumbersHelper(Board board, Sprite sprite) {
        int[][] cellOffsets = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};


        for(int i = 0; i < cellOffsets.length; i++){
            int[] cellOffset = cellOffsets[i];
            int row = sprite.getRow() + cellOffset[0];
            int col = sprite.getColumn() + cellOffset[1];
            if(row >= board.getRows() || row < 0 ||
                    col >= board.getColumns() || col < 0)
                continue;

            Sprite numberSprite = board.getSprite(row, col);
            if(numberSprite!=null){
                if(numberSprite instanceof Bomb)
                    continue;
                else{
                    ((NumberSprite)numberSprite).increment();
                }

            }else{
                numberSprite = SpriteFactory.newNumberSprite(row, col);
                board.add(row, col, numberSprite);
            }
        }

    }

    //TODO need to change this to O(n)
    private static void addBombs(Board board) {
        int i = 0;
        while(i < board.getNumberOfBombs()){
            Random random = new Random();
            boolean set = false;
            while(!set){
                int row = random.nextInt(board.getRows());
                int col = random.nextInt(board.getColumns());
                Sprite sprite = board.getSprite(row, col);
                if(sprite== null){
                    sprite = SpriteFactory.newBomb(row, col);
                    board.add(row, col, sprite);
                    board.addBomb(sprite);
                    set = true;
                }

            }

            i++;
        }

    }

    public static void destroy(){
        board.reset();
    }

    public static void update(){

        boolean isWon = board.allExposed();

        if(isWon){

            System.out.println("Game is won! Congratulations!");
            for(Sprite bomb: board.getBombs()){
                bomb.flip();
            }
            board.draw();

            gameOver = true;
            return;
        }
        if(redraw){
            board.draw();
            redraw = false;
        }
        System.out.println("Select cell coordinates with comma (,) between row and column coordinate: ");
        String line = null;
        try {
            line = consoleInput.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(line==null || line.equals("q")){
            gameOver = true;
            finish = true;
        }else if(line.equals("r")){
            gameOver = true;
        }else{
            try{
                Pair<Integer, Integer> coordinates = parseString(line);
                selectSprite(coordinates.getKey(), coordinates.getValue());
            }catch(IllegalArgumentException e){
                update();
            }

        }



    }



    private static Pair<Integer,Integer> parseString(String line) {

        String[] parsedString = line.split(PARSE_STRING_BY);
        if(parsedString.length!=2)
            throw new IllegalArgumentException("Input string is not properly formatted");
        int x_coordinate = Integer.valueOf(parsedString[0]);
        int y_coordinate = Integer.valueOf(parsedString[1]);
        return new Pair<>(x_coordinate, y_coordinate);

    }

    public static void main(String[] args) {

        try(Input<String> ci = new ConsoleInput()){
            consoleInput = ci;
            while(!finish){
                init();
                while(!gameOver){
                    update();
                }
                if(!finish)
                    destroy();

            }

        }catch(Exception e){
            System.out.println("Failed to init console");
            e.printStackTrace();
        }




        //int[][] cellOffsets = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //int[] cellOffset = cellOffsets[2];
      //  System.out.println(cellOffset[0]
       /// );
        //System.out.println(cellOffset[1]);

    }
}
