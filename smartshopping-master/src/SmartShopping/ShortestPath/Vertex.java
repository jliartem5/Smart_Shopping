package SmartShopping.ShortestPath;


public class Vertex implements Comparable<Vertex>
{
    public final String name;
    public final int mapPosition;//Position relative au SmartPlan
    
    public int getMapPosition() {
		return mapPosition;
	}

	public Edge[] adjacencies;
    
    public Edge[] getAdjacencies() {
		return adjacencies;
	}

	public void setAdjacencies(Edge[] adjacencies) {
		this.adjacencies = adjacencies;
	}

	public double minDistance = Double.POSITIVE_INFINITY;
    
    public Vertex previous;
    
    public Vertex(String argName, int positionOnMap) { 
    	name = argName; 
    	this.mapPosition = positionOnMap;
    }
    
    public String toString() { return name; }
    
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}