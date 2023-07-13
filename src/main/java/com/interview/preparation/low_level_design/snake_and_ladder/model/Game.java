package com.interview.preparation.low_level_design.snake_and_ladder.model;

import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

@Getter
public class Game {
    private int noOfSnakes;
    private int noOfLadders;

    private Queue<Player> players;
    private List<Snake> snakes;
    private List<Ladder> ladders;

    private Board board;
    private Dice dice;

    public Game(int noOfSnakes, int noOfLadders, int boardSize) {
        this.noOfSnakes = noOfSnakes;
        this.noOfLadders = noOfLadders;

        this.players = new ArrayDeque<>();
        this.snakes = new ArrayList<>(noOfSnakes);
        this.ladders = new ArrayList<>(noOfLadders);

        this.board = new Board(boardSize);
        this.dice = new Dice(1, 6, 2);

        initBoard();
    }

    public void initBoard() {
        Set<String> snakeLadderToExistSet = new HashSet<>();
        for (int i = 0; i < noOfSnakes; i++) {
            while(true){
                int snakeStart = RandomUtils.nextInt(board.getStart(), board.getSize());
                int snakeEnd = RandomUtils.nextInt(board.getStart(), board.getSize());
                if (snakeEnd >= snakeStart){
                    continue;
                }
                String startEndPair = String.valueOf(snakeStart) + snakeEnd;
                if(!snakeLadderToExistSet.contains(startEndPair)){
                    Snake snake = new Snake(snakeStart, snakeEnd);
                    snakes.add(snake);
                    snakeLadderToExistSet.add(startEndPair);
                    break;
                }
            }
        }

        for (int i = 0; i < noOfLadders; i++) {
            while(true){
                int startOfLadder = RandomUtils.nextInt(board.getStart(),board.getSize());
                int endOfLadder = RandomUtils.nextInt(board.getStart(),board.getSize());

                if(endOfLadder <= startOfLadder){
                    continue;
                }
                String startEndPair = String.valueOf(startOfLadder) + endOfLadder;
                if(!snakeLadderToExistSet.contains(startEndPair)){
                    Ladder ladder = new Ladder(startOfLadder,endOfLadder);
                    ladders.add(ladder);
                    snakeLadderToExistSet.add(startEndPair);
                    break;
                }
            }
        }

    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void playGame(){
        while (true){
            Player player = players.poll();
            int val = dice.roll();

            assert player != null;
            int newPosition = player.getPosition() + val;
            if(newPosition > board.getEnd()){
                player.setPosition(newPosition);
                players.offer(player);
            }else{
                player.setPosition(getNewPosition(newPosition));
                if(player.getPosition() == board.getEnd()){
                    player.setWon(true);
                    System.out.println("Player " + player.getName() + " Won.");
                }else{
                    System.out.println("Setting " + player.getName() + "'s new position to " + player.getPosition());
                    players.offer(player);
                }
            }
            if(players.size() < 2) {
                break;
            }
        }
    }

    private int getNewPosition(int newPosition) {
        for (Snake snake : snakes) {
            if (snake.getHead() == newPosition) {
                System.out.println("Snake Bite");
                return snake.getTail();
            }
        }
        for (Ladder ladder : ladders) {
            if (ladder.getStart() == newPosition) {
                System.out.println("Climbed ladder");
                return ladder.getEnd();
            }
        }
        return newPosition;
    }

}
