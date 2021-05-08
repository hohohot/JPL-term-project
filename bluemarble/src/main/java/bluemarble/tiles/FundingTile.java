package bluemarble.tiles;

import java.awt.geom.IllegalPathStateException;

import bluemarble.BlueMarble;
import bluemarble.player.Player;
import bluemarble.type.EventType;

public class FundingTile extends Tile {
	private final BenefitTile benefitTile;
	private final Long fundingFee;

	

	public FundingTile(String name, BlueMarble blueMarble, BenefitTile benefitTile, Long fundingFee) {
		super(name, blueMarble);
		this.benefitTile = benefitTile;
		this.fundingFee = fundingFee;
	}

	@Override
	public EventType getStartEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}

	@Override
	public EventType getPassByEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}

	@Override
	public EventType getStopEvent() {
		// TODO Auto-generated method stub
		Player currentPlayer = blueMarble.getCurrentPlayer();
		
		int sellingResult = blueMarble.sellProperty(this.fundingFee);
		
		if(sellingResult == -1) {
			this.benefitTile.setFundedMoneyLong(benefitTile.getFundedMoneyLong() + currentPlayer.getMoney());			
		}else {
			currentPlayer.addMoney(-this.fundingFee);
			this.benefitTile.setFundedMoneyLong(benefitTile.getFundedMoneyLong() + fundingFee);
		}
		return EventType.NONE;
	}

}
