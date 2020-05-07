import java.io.*;
import java.util.*;


/**
 * Incidence matrix implementation for the FriendshipGraph interface.
 * 
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class IndMatrix <T extends Object> implements FriendshipGraph<T>
{

    ArrayList<ArrayList<Integer>> matrixCol;
    ArrayList<Integer> matrixRow;
    ArrayList<T> vert;
    /**
     * Contructs empty graph.
     */
    public IndMatrix() {
        // Implement me!
        matrixCol = new ArrayList<ArrayList<Integer>>();
        vert = new ArrayList<T>();
    } // end of IndMatrix()
    
    
    public void addVertex(T vertLabel) {
        // Implement me!
        if(vert.indexOf(vertLabel) < 0) { // add when vertLabel doesn't exist
            vert.add(vertLabel);
        }
    } // end of addVertex()
    
    
    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!     
        if(vert.indexOf(srcLabel) >= 0 && vert.indexOf(tarLabel) >= 0) {
            matrixRow = new ArrayList<Integer>(); // creating a new row
            for(int i = 0; i < vert.size(); i++) {
                if(vert.get(i).equals(srcLabel) || vert.get(i).equals(tarLabel)) {
                    matrixRow.add(1);
                }
                else {
                    matrixRow.add(0);
                }
            }
            matrixCol.add(matrixRow);
        } else {
            System.out.println("One or both incident vertices does not exist.");
        }
    } // end of addEdge()
    

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();      
        int count = 0;  
        if(vert.indexOf(vertLabel) >= 0) {
            for(int j = 0; j < matrixCol.size(); j++) {
                if(vert.indexOf(vertLabel) < matrixCol.get(j).size()) {
                    if(matrixCol.get(j).get(vert.indexOf(vertLabel)) == 1) { // find if row with vertLabel
                        for(int jj = 0; jj < matrixCol.get(j).size(); jj++) {
                            if(matrixCol.get(j).get(jj) == 1 && jj != vert.indexOf(vertLabel)) { // find which vertex it's connected
                                neighbours.add(vert.get(jj)); // add to array
                            }
                        }
                    }
                }
            }
        }
        else {
            System.out.println("Vertex does not exist.");
        }
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
        // Implement me!
        int position = vert.indexOf(vertLabel);
        if(position >= 0) {
            for(int i = 0; i < matrixCol.size(); i++) {
                if(matrixCol.get(i).get(position) == 1) { // find if current row has deleted value
                    matrixCol.remove(i); // remove row
                    i = i - 1;
                } else {
                    matrixCol.get(i).remove(position);
                }
                
            }
            vert.remove(position);     
        }
        else {
            System.out.println("Vertex does not exist.");
        }
        
    } // end of removeVertex()
    
    
    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!        
        int src_position = vert.indexOf(srcLabel);
        int tar_position = vert.indexOf(tarLabel);
                      // if(vert.indexOf(vertLabel) < matrixCol.get(j).size()) 
        if(src_position >= 0 && tar_position >= 0) {
            for(int i = 0; i < matrixCol.size(); i++) {
                if(src_position < matrixCol.get(i).size() && tar_position < matrixCol.get(i).size()) {
                    if(matrixCol.get(i).get(src_position) == 1 && matrixCol.get(i).get(tar_position) == 1) {
                        matrixCol.remove(i);
                    }
                }
            }
        } else {
            System.out.println("One or both incident vertices does not exist.");
        }
       
         
    } // end of removeEdges()
    
    
    public void printVertices(PrintWriter os) {
        // Implement me!
        System.out.println("Vertices: " + vert.size() + " Edges: " + matrixCol.size());
        for(int i = 0; i < vert.size(); i++) {
            os.print(vert.get(i) + " ");
        }
        os.println();
        os.flush();
    } // end of printVertices()
    
    
    public void printEdges(PrintWriter os) {
        int count = 0;
        for(int i = 0; i < matrixCol.size(); i++) {
            for(int j = 0; j < matrixCol.get(i).size(); j++) {
                if(matrixCol.get(i).get(j) == 1) {
                    count++;
                    os.print(vert.get(j) + " ");
                    if(count == 2) { 
                        os.println(); 
                        break; 
                    }
                }
            }
            count = 0;
        }
        for(int k = matrixCol.size() - 1; k >= 0; k--) {
            for(int l = matrixCol.get(k).size() - 1; l >= 0; l--) {
                if(matrixCol.get(k).get(l) == 1) {
                    count++;
                    os.print(vert.get(l) + " ");
                    if(count == 2) { os.println(); break; }
                }
            }
            count = 0;
        }
        os.flush();
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        // Implement me!
        Queue<T> queue = new LinkedList<T>();
        HashMap<T,T> connect = new HashMap<T,T>();
        int[] visited = new int[vert.size()];
        int distance = 0;
        int begin = vert.indexOf(vertLabel1);
        int end = vert.indexOf(vertLabel2);
        T currVert;
        T endVer = vertLabel2;
        
        if(begin >= 0 && end >= 0) {
            Arrays.fill(visited, 0); // set all value to 0
            visited[begin] = 1;
            queue.add(vertLabel1);
            while(!queue.isEmpty()) {
                currVert = queue.remove();
                int v = vert.indexOf(currVert);
                for(int i = 0; i < matrixCol.size(); i++) {
                    if(matrixCol.get(i).size() > v) {
                        if(matrixCol.get(i).get(v) == 1) {
                            for(int j = 0; j < matrixCol.get(i).size(); j++) {
                                if((matrixCol.get(i).get(j) == 1) && (j != v) && (visited[j] == 0)) {
                                    queue.add(vert.get(j));
                                    connect.put(vert.get(j), currVert);
                                    visited[j] = 1;
                                }
                            }
                        }
                    } else { continue; }
                }
            }  
            if(connect.get(vertLabel2) == null) {
                return disconnectedDist;  
            } else {
                while(endVer != null) {
                    distance++;
                    endVer = connect.get(endVer);
                }
            }
        } else {
            System.err.println("One or both incident vertices does not exist.");        
        }
        
       return distance - 1; 
    } // end of shortestPathDistance()
    
} // end of class IndMatrix
