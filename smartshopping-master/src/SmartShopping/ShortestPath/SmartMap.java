package SmartShopping.ShortestPath;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

/**
 * Classe représentant notre carte de magasin. Elle est statique pour le moment. 
 * @author Django
 *
 */
public class SmartMap {
	
	
	// Nos sommets (voir image GraphePlan.png sur Dropbox)
    private List<Vertex> vertexArr = new ArrayList<Vertex>(); 
    private Vertex v1;
	private Vertex v2;
	private Vertex v3;
	private Vertex v4;
	private Vertex v5;
	private Vertex v6;
    private Vertex v7;
	private Vertex v8;
	private Vertex v9;
	private Vertex v10;
	private Vertex v11;
	private Vertex v12;
    private Vertex v13;
	private Vertex v14;
	private Vertex v15;
	private Vertex v16;
	private Vertex v17;
	private Vertex v18;
    private Vertex v19;
	private Vertex v20;
	private Vertex v21;
	private Vertex v22;
	private Vertex v23;
	private Vertex v24;
    private Vertex v25;
	private Vertex v26;
	private Vertex v27;
	
	private final int DISTANCE = 1;
	
	// constructeur
	public SmartMap(){
		/*
		 * Map size: 5 * 7
		 * 
		 * 
		 */
		
	    v1 = new Vertex("1",1);
		v2 = new Vertex("2",2);
		v3 = new Vertex("3",3);
		v4 = new Vertex("4",4);
		v5 = new Vertex("5",5);
		v6 = new Vertex("6",6);
		v7 = new Vertex("7",8);
		v8 = new Vertex("8",10);
		v9 = new Vertex("9",11);
		v10 = new Vertex("10",12);
		v11 = new Vertex("11",13);
		v12 = new Vertex("12",15);
		v13 = new Vertex("13",16);
		v14 = new Vertex("14",18);
		v15 = new Vertex("15",20);
		v16 = new Vertex("16",21);
		v17 = new Vertex("17",22);
		v18 = new Vertex("18",23);
		v19 = new Vertex("19",24);
		v20 = new Vertex("20",25);
		v21 = new Vertex("21",26);
		v22 = new Vertex("22",30);
		v23 = new Vertex("23",31);
		v24 = new Vertex("24",32);
		v25 = new Vertex("25",33);
		v26 = new Vertex("26",34);
		v27 = new Vertex("27",35);
		
		
		// les liens entre les sommets (Edge[Sommet, Distance]) // (voir image GraphePlan.png sur Dropbox)
		// attention, si 1 est relié vers 2, 2 ne va as forcément vers 1 avec cette structure
		// il faut donc préciser que 1 va vers 2 et 2 va vers 1

		// chaque distance est la même pour l'instant (1)
		
		// exemple : V1 est lié à v2 et v6
		v1.adjacencies = new Edge[]{ new Edge(v2, DISTANCE), new Edge(v6, DISTANCE)};
		v2.adjacencies = new Edge[]{ new Edge(v1, DISTANCE), new Edge(v3, DISTANCE)};
		v3.adjacencies = new Edge[]{ new Edge(v2, DISTANCE), new Edge(v4, DISTANCE), new Edge(v7, DISTANCE)};
		v4.adjacencies = new Edge[]{ new Edge(v3, DISTANCE), new Edge(v5, DISTANCE)};
		v5.adjacencies = new Edge[]{ new Edge(v4, DISTANCE), new Edge(v8, DISTANCE)};
		v6.adjacencies = new Edge[]{ new Edge(v1, DISTANCE), new Edge(v9, DISTANCE)};
		v7.adjacencies = new Edge[]{ new Edge(v3, DISTANCE), new Edge(v11, DISTANCE)};
		v8.adjacencies = new Edge[]{ new Edge(v5, DISTANCE), new Edge(v12, DISTANCE)};
		v9.adjacencies = new Edge[]{ new Edge(v6, DISTANCE), new Edge(v10, DISTANCE), new Edge(v13, DISTANCE)};
		v10.adjacencies = new Edge[]{ new Edge(v9, DISTANCE), new Edge(v11, DISTANCE)};
		v11.adjacencies = new Edge[]{ new Edge(v7, DISTANCE), new Edge(v10, DISTANCE), new Edge(v14, DISTANCE)};
		v12.adjacencies = new Edge[]{ new Edge(v8, DISTANCE), new Edge(v15, DISTANCE)};
		v13.adjacencies = new Edge[]{ new Edge(v9, DISTANCE), new Edge(v16, DISTANCE)};
		v14.adjacencies = new Edge[]{ new Edge(v11, DISTANCE), new Edge(v18, DISTANCE)};
		v15.adjacencies = new Edge[]{ new Edge(v12, DISTANCE), new Edge(v20, DISTANCE)};
		v16.adjacencies = new Edge[]{ new Edge(v13, DISTANCE), new Edge(v17, DISTANCE), new Edge(v21, DISTANCE)};
		v17.adjacencies = new Edge[]{ new Edge(v16, DISTANCE), new Edge(v18, DISTANCE)};
		v18.adjacencies = new Edge[]{ new Edge(v14, DISTANCE), new Edge(v17, DISTANCE), new Edge(v19, DISTANCE)};
		v19.adjacencies = new Edge[]{ new Edge(v18, DISTANCE), new Edge(v20, DISTANCE)};
		v20.adjacencies = new Edge[]{ new Edge(v15, DISTANCE), new Edge(v19, DISTANCE), new Edge(v22, DISTANCE)};
		v21.adjacencies = new Edge[]{ new Edge(v16, DISTANCE), new Edge(v23, DISTANCE)};
		v22.adjacencies = new Edge[]{ new Edge(v20, DISTANCE), new Edge(v27, DISTANCE)};
		v23.adjacencies = new Edge[]{ new Edge(v21, DISTANCE), new Edge(v24, DISTANCE)};
		v24.adjacencies = new Edge[]{ new Edge(v23, DISTANCE), new Edge(v25, DISTANCE)};
		v25.adjacencies = new Edge[]{ new Edge(v24, DISTANCE), new Edge(v26, DISTANCE)};
		v26.adjacencies = new Edge[]{ new Edge(v25, DISTANCE), new Edge(v27, DISTANCE)};
		v27.adjacencies = new Edge[]{ new Edge(v22, DISTANCE), new Edge(v26, DISTANCE)};
		
		this.vertexArr.add(v1);
		this.vertexArr.add(v2);
		this.vertexArr.add(v3);
		this.vertexArr.add(v4);
		this.vertexArr.add(v5);
		this.vertexArr.add(v6);
		this.vertexArr.add(v7);
		this.vertexArr.add(v8);
		this.vertexArr.add(v9);
		this.vertexArr.add(v10);
		this.vertexArr.add(v11);
		this.vertexArr.add(v12);
		this.vertexArr.add(v13);
		this.vertexArr.add(v14);
		this.vertexArr.add(v15);
		this.vertexArr.add(v16);
		this.vertexArr.add(v18);
		this.vertexArr.add(v19);
		this.vertexArr.add(v20);
		this.vertexArr.add(v21);
		this.vertexArr.add(v22);
		this.vertexArr.add(v23);
		this.vertexArr.add(v24);
		this.vertexArr.add(v25);
		this.vertexArr.add(v26);
		this.vertexArr.add(v27);
	}
	
	// pour le moment, user postion = v1
	public Vertex getUserPosition(){
		return v1;
	}

	// getters
	public Vertex getV1() {
		return v1;
	}

	public Vertex getV2() {
		return v2;
	}

	public Vertex getV3() {
		return v3;
	}

	public Vertex getV4() {
		return v4;
	}

	public Vertex getV5() {
		return v5;
	}

	public Vertex getV6() {
		return v6;
	}

	public Vertex getV7() {
		return v7;
	}

	public Vertex getV8() {
		return v8;
	}

	public Vertex getV9() {
		return v9;
	}

	public Vertex getV10() {
		return v10;
	}

	public Vertex getV11() {
		return v11;
	}

	public Vertex getV12() {
		return v12;
	}

	public Vertex getV13() {
		return v13;
	}

	public Vertex getV14() {
		return v14;
	}

	public Vertex getV15() {
		return v15;
	}

	public Vertex getV16() {
		return v16;
	}

	public Vertex getV17() {
		return v17;
	}

	public Vertex getV18() {
		return v18;
	}

	public Vertex getV19() {
		return v19;
	}

	public Vertex getV20() {
		return v20;
	}

	public Vertex getV21() {
		return v21;
	}

	public Vertex getV22() {
		return v22;
	}

	public Vertex getV23() {
		return v23;
	}

	public Vertex getV24() {
		return v24;
	}

	public Vertex getV25() {
		return v25;
	}

	public Vertex getV26() {
		return v26;
	}

	public Vertex getV27() {
		return v27;
	}
	
	//5 * 7
	public Point getMapSize(){
		return new Point(5, 7);
		
	}
	
	public Vertex getVertexByPosition(int position){
		for(Vertex v : this.vertexArr){
			if(v.getMapPosition() == position){
				return v;
			}
			
		}
		return null;
		
	}

	
	

}
