package si.zitnik.sociogram.gui.graph.jung;

public class Vertex {
	private int realPersonId;
	
	public Vertex(int realPersonId){
		this.realPersonId = realPersonId;
	}
	
	@Override
	public int hashCode() {
		return realPersonId;
	}
	
	@Override
	public boolean equals(Object obj) {
		Vertex v2 = (Vertex)obj;
		if (v2.realPersonId == this.realPersonId){
			return true;
		} else {
			return false;
		}
	}

	public int getRealId() {
		return this.realPersonId;
	}
}
