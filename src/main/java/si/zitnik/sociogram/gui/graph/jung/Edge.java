package si.zitnik.sociogram.gui.graph.jung;

import java.awt.Color;
import java.awt.Paint;

import si.zitnik.sociogram.enums.LikingType;

public class Edge {
	private LikingType edgeType;
	private int rank; //between 1-3
	private int fromRealPersonId;
	private int toRealPersonId;
	
	private Color color;
	
	public Edge(LikingType edgeType, int rank, int fromRealPersonId, int toRealPersonId) {
		super();
		this.edgeType = edgeType;
		this.rank = rank;
		this.fromRealPersonId = fromRealPersonId;
		this.toRealPersonId = toRealPersonId;
		if (edgeType.equals(LikingType.POSITIVE)){
			this.color = Color.GREEN;
		} else {
			this.color = Color.RED;
		}
	}
	
	@Override
	public int hashCode() {
		int code = 7;

        code = 31 * code + edgeType.hashCode();
        code = 31 * code + rank;
        code = 31 * code + fromRealPersonId;
        code = 31 * code + toRealPersonId;

        return code;
	}

	public int getToRealId() {
		return this.toRealPersonId;
	}

	public int getFromRealId() {
		return this.fromRealPersonId;
	}

	public Paint getColor() {
		return this.color;
	}

	public int getRank() {
		return this.rank;
	}

	public LikingType getLikingType() {
		return this.edgeType;
	}
	
	
}
