package test;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class TickTackToe implements Serializable, Externalizable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8949529152786126675L;
	private ArrayList<ArrayList<Integer>> game;
	private int turn;
	private boolean hasWinner;
	private int winner;
	
	public TickTackToe clone(){
		try {
			return (TickTackToe)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ArrayList<ArrayList<Integer>>clone = new ArrayList<ArrayList<Integer>>();
		clone.add(new ArrayList<Integer>(game.get(0)));
		clone.add(new ArrayList<Integer>(game.get(1)));
		clone.add(new ArrayList<Integer>(game.get(2)));
		out.writeObject(clone);
		out.writeInt(turn);
		out.writeBoolean(hasWinner);
		out.writeInt(winner);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {		
		game = (ArrayList<ArrayList<Integer>>) in.readObject();
		turn = in.readInt();
		hasWinner = in.readBoolean();
		winner = in.readInt();
		
	}
	
	final int[] dx = {1,1,1,0,-1,-1,-1,0};
	final int[] dy = {1,0,-1,-1,-1,0,1,1};
	private Scanner in;
	
	public TickTackToe() {
		game  = new ArrayList<ArrayList<Integer>>();
		for(int i =0 ; i< 3;i++){
			game.add(new ArrayList<Integer>());
			for(int j =0 ; j< 3;j++){
				game.get(i).add(-1);
			}
		}
		turn = 0;
	}
	
	public boolean getHasWinner(){
		return hasWinner;
	}
	public void printGame(){
		for(int i =0 ; i< 3;i++){
			for(int j =0 ; j< 3;j++){
				if(game.get(i).get(j) ==-1){
					System.out.print("_");
				}else if(game.get(i).get(j)== 0){
					System.out.print("O");
				}else if(game.get(i).get(j) == 1){
					System.out.print("X");
				}
			}
			System.out.println();
		}
	}
	
	public void nextMove(){
		in = new Scanner(System.in);
		int x = in.nextInt();
		int y = in.nextInt();
		 playAt(y, x);
	}
	public boolean playAt(int i, int j){
		if(game.get(i).get(j) != -1) return false;
		game.get(i).set(j, turn);
		game = new ArrayList<ArrayList<Integer>>(game);
		verifyWinner();
		turn = 1-turn;
		return true;
	}
	
	public void verifyWinner(){
		//verify columns
		for(int i = 0 ; i < 3; i++){
			hasWinner |= (game.get(i).get(0) == game.get(i).get(1)) && (game.get(i).get(0) == game.get(i).get(2)) && (game.get(i).get(0) !=-1);
		}
		//verify rows
		for(int i = 0 ; i < 3; i++){
			hasWinner |= (game.get(0).get(i) == game.get(1).get(i)) && (game.get(0).get(i) == game.get(2).get(i)) && (game.get(0).get(i) !=-1);
		}
		hasWinner |= (game.get(0).get(0) == game.get(1).get(1)) && (game.get(0).get(0) == game.get(2).get(2)) && (game.get(0).get(0) !=-1);
		hasWinner |= (game.get(0).get(2) == game.get(1).get(1)) && (game.get(0).get(2) == game.get(2).get(0))&& (game.get(0).get(2) !=-1);
		if(hasWinner) this.setWinner(this.turn);
	}
	
	public int getTurn(){
		return this.turn;
	}
	
	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	
	
}
