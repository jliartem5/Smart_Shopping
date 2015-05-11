package SmartShopping.ShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.sshopping.MainActivity;

import SmartShopping.OV.OVSommet;
import android.graphics.Point;
import android.util.Log;

/**
 * Classe representant notre carte de magasin. Elle est statique pour le moment.
 * @author Django
 *
 */
public class SmartMap {
	
	
	// Nos sommets (voir image GraphePlan.png sur Dropbox)
	
    private List<Vertex> vertexArr = new ArrayList<Vertex>(); 
	
	public List<Vertex> getVertexArr() {
		return vertexArr;
	}

	private final int DISTANCE = 1;
	
	// constructeur
	public SmartMap(){
		
		List<OVSommet> sommets = MainActivity.getMapSommets();
		if(sommets != null){
			for(OVSommet ovs : sommets){
				Vertex v = new Vertex("Ne"+ovs.getNumSommet(), ovs.getNumSommet(), ovs.getIdCategorie());
				this.vertexArr.add(v);
			}
		}else{
			//Alert?
			Log.e("SmartShopping", "Missing map node");
			
		}
		
		// les liens entre les sommets (Edge[Sommet, Distance]) // (voir image GraphePlan.png sur Dropbox)
		// attention, si 1 est reli� vers 2, 2 ne va as forc�ment vers 1 avec cette structure
		// il faut donc pr�ciser que 1 va vers 2 et 2 va vers 1

		// chaque distance est la m�me pour l'instant (1)
		
		// exemple : V1 est li� � v2 et v6
		List<int[]> edges = new ArrayList<int[]>();
		edges.add(new int[]{2,6});
		edges.add(new int[]{1,3});
		edges.add(new int[]{2,4,7});
		edges.add(new int[]{3,5});
		edges.add(new int[]{4,8});
		edges.add(new int[]{1,9});
		edges.add(new int[]{3,11});
		edges.add(new int[]{5,12});
		edges.add(new int[]{6,10,13});
		edges.add(new int[]{9,11});
		edges.add(new int[]{7,10,14});
		edges.add(new int[]{8,15});
		edges.add(new int[]{9,16});
		edges.add(new int[]{11,18});
		edges.add(new int[]{12,20});
		edges.add(new int[]{13,17,21});
		edges.add(new int[]{16,18});
		edges.add(new int[]{14,17,19});
		edges.add(new int[]{18,20});
		edges.add(new int[]{15,19,22});
		edges.add(new int[]{16,23});
		edges.add(new int[]{20,27});
		edges.add(new int[]{21,24});
		edges.add(new int[]{23,25});
		edges.add(new int[]{24,26});
		edges.add(new int[]{25,27});
		edges.add(new int[]{22,26});
		//De 0 � 26
		for(int i=0;i<this.vertexArr.size();++i){
			Vertex v = this.vertexArr.get(i);
			v.adjacencies = new Edge[edges.get(i).length];//Distribuer l'espace m�moire pr X nombre de Edge
			for(int j=0;j<edges.get(i).length;++j){//Trouver Vertex et construire Edge pour remplir v.adjacencies
				Vertex targetVertex = this.vertexArr.get(edges.get(i)[j]-1);//-1 pck on compte � partir de 1
				v.adjacencies[j] = new Edge(targetVertex, DISTANCE);
			}
		}
		
	}
	
	// pour le moment, user postion = v1
	public Vertex getUserPosition(){
		//	if(vertexArr != null) il y a eu un bug d'index � un moment...
		return this.vertexArr.get(0);
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
