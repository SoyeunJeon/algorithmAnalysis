import java.io.*;
import java.util.*;


/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 * 
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix <T extends Object> implements FriendshipGraph<T>
{
    ArrayList<T> vertices;
    ArrayList<ArrayList<Integer>> matrixCol;
    ArrayList<Integer> matrixRow;

    Integer notConn = 0;
    Integer conn = 1;

	/**
	 * Contructs empty graph.
	 */
    public AdjMatrix() {

        matrixCol = new ArrayList<ArrayList<Integer>>();

        vertices = new ArrayList<T>();       

    } // end of AdjMatrix()
    

    
    public void addVertex(T vertLabel) {

        if (vertices.indexOf(vertLabel) < 0 ) {
            matrixRow = new ArrayList<Integer>();
            vertices.add(vertLabel);

            for (int i = 0; i<vertices.size();i++) { // fill the row with 0 for default
                matrixRow.add(notConn);
            }
            matrixCol.add(matrixRow);

            int newVert = vertices.indexOf(vertLabel);

            for (int i = 0; i<vertices.size()-1;i++) { // increase the size of each existing row
                matrixCol.get(i).add(newVert, notConn);
            }            
        }

    } // end of addVertex()
	
   
    public void addEdge(T srcLabel, T tarLabel) {

        int vert1 = vertices.indexOf(srcLabel);
        int vert2 = vertices.indexOf(tarLabel);

        if (vert1 >= 0 && vert2 >= 0) { //indirected -> add two 1 when adding edges
            matrixCol.get(vert2).set(vert1, conn);
            matrixCol.get(vert1).set(vert2, conn);            
        } else {
            System.out.println("One or both vertices does not exist.");
        }

    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        
        int vert = vertices.indexOf(vertLabel); 
        if (vert >=0) {
            for (int i=0;i<vertices.size();i++) {
                if (matrixCol.get(vert).get(i) == conn) { // 1 is connected with means they are a neighbor
                    neighbours.add(vertices.get(i));
                }
            }            
        }else {
            System.out.println("Vertex does not exist.");
        }

        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {

        int vert = vertices.indexOf(vertLabel);

        if (vert >=0) {
            for (int i =0;i<vertices.size();i++) {
                matrixCol.get(i).remove(vert); // remove vertex in row
            }

            vertices.remove(vert); 
            matrixCol.remove(vert); //remove vertex in column             
        } else {
            System.out.println("Vertex does not exist.");
        }

    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {

        int vert1 = vertices.indexOf(srcLabel);
        int vert2 = vertices.indexOf(tarLabel);

        if (vert1 >=0 && vert2 >=0) { //set 1 into 0
            matrixCol.get(vert1).set(vert2, notConn); 
            matrixCol.get(vert2).set(vert1, notConn);            
        } else {
            System.out.println("One or both vertices does not exist.");
        }

    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {

        for (int i=0;i<vertices.size();i++) {
            os.print(vertices.get(i)+" ");
        }   
    	os.println();
        os.flush();

        System.out.println("density" + vertices.size());
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {

    	for (int i=0;i<vertices.size();i++) {
    		for (int j=0;j<vertices.size();j++) {
    			if (matrixCol.get(i).get(j) == conn) {
    				os.print(vertices.get(i) + " ");
                    os.println(vertices.get(j));
    			}
    		}
    	}
    	os.flush();

    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	
        int src = vertices.indexOf(vertLabel1);
        int dest = vertices.indexOf(vertLabel2);
        int vSize = vertices.size();
        T curVertex;
        int distance =0;   
        T des = vertLabel2;


        if (src >= 0 && dest >=0) {
             // 1 is visited, 0 is not visited
            int[] visited = new int[vSize];
            for (int i=0;i<vSize;i++) {
                visited[i] = 0;
            }
            visited[src] = 1;

            Queue<T> q = new LinkedList<T>();
            HashMap<T, T> fam = new HashMap<T, T>();

            q.add(vertLabel1);
            fam.put(vertLabel1, null);

            while(!q.isEmpty()) {
                curVertex = q.remove();
                int v = vertices.indexOf(curVertex);
                for (int i=0;i<vSize;i++) {
                    if (visited[i]!=1 && matrixCol.get(v).get(i)==1) {
                        visited[i]=1;
                        q.add(vertices.get(i));
                        fam.put(vertices.get(i), curVertex);
                    }
                }
            }

            if (fam.get(vertLabel2) == null) {
                return disconnectedDist;
            } else {
                while (des != null) {
                    distance++;
                    des = fam.get(des);
                }
            }           
        } else {
            System.out.println("One or both vertices does not exist.");
        }

        return distance-1;
   	
    } // end of shortestPathDistance()

} // end of class AdjMatrix