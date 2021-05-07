package bluemarble;


import java.util.List;

import bluemarble.card.Card;
import bluemarble.gui.GUI;
import bluemarble.player.*;
import bluemarble.tiles.CityTile;
import bluemarble.tiles.Tile;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class BlueMarble {
	private final GUI gui;
	
	
	private List<Tile> tiles;
	private List<Card> cards;
	private List<Player> players;
	private Dice dice;
	private int currentTurn;
	private int nextTurn;
	
	public int processTurn() {
		return -1;
	}
	
	public int initialize() {
		return -1;
	}
	
	
	public Player getCurrentPlayer() {
		return players.get(currentTurn);
	}
	
	
	public int sellBuilding(Long target) {
		Player player = getCurrentPlayer();
		while(player.getMoney() < target) {
			int tile = player.selectSellingTile();
			if(tile == -1) {
				bankruptCurrentPlayer();
				return -1;
			}
			((CityTile)this.tiles.get(tile)).initialize();
			player.addMoney(((CityTile)this.tiles.get(tile)).getCost());
		}
		return 0;
	}
	
	
	public void bankruptCurrentPlayer() {
		players.remove(currentTurn);
	}
	
	public static void main(String args[]) {
		System.out.println("hello!");
	}
}
