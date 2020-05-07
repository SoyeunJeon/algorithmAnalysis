import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.lang.String;

public class Test {
 
  static ArrayList<String> values;
  static HashMap<String, String> characters;
  static ArrayList<String> attrList = new ArrayList<String>();
  static String attrString;
  static String candString;
  static HashMap<String, ArrayList<String>> attributes = new HashMap<String, ArrayList<String>>();
  static HashMap<String, HashMap<String, String>> candidates = new HashMap<String, HashMap<String, String>>();
  static HashMap<String, String> chosenPerson = new HashMap<String, String>();
  static ArrayList<String> persons = new ArrayList<String>();

  static HashMap<String, HashMap<String, Integer>> candidatesAttribution = new HashMap<String, HashMap<String, Integer>>();
  static HashMap<String, Integer> innerAttribution;


	public static void main(String[] args){
		try {
      // FileInputStream fstream = new FileInputStream("game1.config");
      // DataInputStream in = new DataInputStream(fstream);
      // BufferedReader br = new BufferedReader(new InputStreamReader(in));
      // String strLine;
			
      // strLine = br.readLine();  
      // while (strLine!=null && !strLine.trim().equals("")) {
      //   // System.out.println(strLine);
      //   // strLine = br.readLine();
      //   String[] token = strLine.split(" ");
      //   attrString=token[0];
      //   attrList.add(attrString);

      //   System.out.println(token.length);
      //   values=new ArrayList<String>();
      //   for (int i=1;i<token.length ;i++ ) {
      //     values.add(token[i]);
      //   }
      //   attributes.put(attrString, values);
      //   strLine=br.readLine();
      // }

      // while ((strLine=br.readLine())!=null) {
      //   characters = new HashMap<String, String>();

      //   while (strLine!=null && !strLine.trim().equals("")){
      //     String[] token = strLine.split(" ");
      //     if (token.length==1) {
      //       candString=strLine;
      //       persons.add(strLine);
      //       System.out.println(candString);
      //     } 
      //     else {
      //       if (!candidatesAttribution.containsKey(token[0])) {
      //         innerAttribution = new HashMap<String, Integer>();
      //         innerAttribution.put(token[1], 1);
      //         candidatesAttribution.put(token[0], innerAttribution);
      //       } else if (candidatesAttribution.containsKey(token[0]) && !candidatesAttribution.get(token[0]).containsKey(token[1])) {
      //         candidatesAttribution.get(token[0]).put(token[1], 1);
      //       } else {
      //         Integer oldValue = candidatesAttribution.get(token[0]).get(token[1]);
      //         oldValue++;
      //         candidatesAttribution.get(token[0]).replace(token[1], oldValue++);
      //       }
      //     }
      //     strLine=br.readLine();
      //   }
      // }
       
       FileInputStream fstream = new FileInputStream("game1.config");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine = null;
        String name = null;

        while((strLine = br.readLine()) != null) {
            if(strLine.length() <= 0) {
                break;
            }
        }
        persons = new ArrayList<String>();
        while ((strLine=br.readLine())!=null) {
            characters = new HashMap<String, String>();
            while (strLine!=null && !strLine.trim().equals("")){
                String[] token = strLine.split(" ");
                if (token.length==1) {
                    // if (strLine.trim().equals(chosenName)) {
                    //     this.chosenName = chosenName;
                    // }
                    name = token[0];
                    persons.add(name);
                    
                } else {
                    characters.put(token[0], token[1]);

                    if (!candidatesAttribution.containsKey(token[0])) {
                        innerAttribution = new HashMap<String, Integer>();
                        innerAttribution.put(token[1], 1);
                        candidatesAttribution.put(token[0], innerAttribution);
                    } else if (candidatesAttribution.containsKey(token[0]) 
                        && !candidatesAttribution.get(token[0]).containsKey(token[1])) {
                        candidatesAttribution.get(token[0]).put(token[1], 1);
                    } else {
                        Integer oldValue = candidatesAttribution.get(token[0]).get(token[1]);
                        oldValue++;
                        candidatesAttribution.get(token[0]).replace(token[1], oldValue++);
                    }
                }
                strLine=br.readLine();
            }
            candidates.put(name, characters);
        }

		} catch(IOException ex){
			System.err.println("Error reading files.");
		}

    System.out.println(candidatesAttribution.get("hairLength"));



    // System.out.println(candidates.get("P1"));
    // chosenPerson = candidates.get("P3");
    // System.out.println(chosenPerson);
    // System.out.println(persons);
    // System.out.println(attributes.get("hairColor"));
    // System.out.println(attributes.get("hairColor").size());


    // for (String attr:candidates.keySet()) {
    //   System.out.println(attr.toString());
    // }
		// System.out.println(attrList);
  //   System.out.println(attrList.get(2));
  //   System.out.println(attrList.size());

    // HashMap<String, Integer> candtemp = new HashMap<String, Integer>();

    // String compare = attrList.get(0);
    // Integer most = attributes.get(compare).size();
    // candtemp.put(compare, most);
    // for (int i=1;i<attrList.size();i++) {
    //     Integer temp = attributes.get(attrList.get(i)).size();
    //     candtemp.put(attrList.get(i), temp);
    //     if (temp > most) {
    //         most = temp;
    //         compare = attrList.get(i);
    //     }
    // }
    // System.out.println(most);
    // System.out.println(compare);
    // System.out.println(candtemp);

    // String compare = attrList.get(0);
    // int least = attributes.get(compare).size();
    // for (int i=1;i<attrList.size();i++) {
    //     int temp = attributes.get(attrList.get(i)).size();
    //     if (temp < least) {
    //         least = temp;
    //         compare = attrList.get(i);
    //     }
    // }  
    // System.out.println(compare);
    // System.out.println(least);

        String guessAttribute = "";
        String guessColor= "";
        Integer half = (persons.size()-1)/2+1;
        // for (String key : candidatesAttribution.keySet()) {
            // if (candidatesAttribution.get(key).size() < candidatesAttribution.get(guessString).size()) {
            //     guessString = key;
            // }
        //     for (String innerKey : candidatesAttribution.get(key).keySet()) {
        //         if (candidatesAttribution.get(key).get(innerKey) ==)
        //     }
        // }

        // for (String key : candidatesAttribution.keySet()) {
        //   System.out.println(candidatesAttribution.get(key));
        // }

        // Iterator<String> it = candidatesAttribution.keySet().iterator();

        // OUTER_LOOP:
        // while(half > 0) {
        //     while(it.hasNext()) {
        //         String key = it.next();
        //         for (String innerKey : candidatesAttribution.get(key).keySet()) {
        //             if (candidatesAttribution.get(key).get(innerKey) == half) {
        //                 guessAttribute = key;
        //                 guessColor = innerKey;

        //                 break OUTER_LOOP;

        //             }
        //         }

        //     }
        //     half--;
        // }
        // candidatesAttribution.get(guessAttribute).remove(guessColor);
        // System.out.println(guessAttribute + " " + guessColor);
        // for (String key : candidatesAttribution.keySet()) {
        //   System.out.println(candidatesAttribution.get(key));
        // }

        // Iterator<String> it2 = candidatesAttribution.keySet().iterator();

        // OUTER_LOOP:
        // while(half > 0) {
        //     while(it2.hasNext()) {
        //         String key = it.next();
        //         for (String innerKey : candidatesAttribution.get(key).keySet()) {
        //             if (candidatesAttribution.get(key).get(innerKey) == half) {
        //                 guessAttribute = key;
        //                 guessColor = innerKey;

        //                 break OUTER_LOOP;

        //             }
        //         }

        //     }
        //     half--;
        // }
        // candidatesAttribution.get(guessAttribute).remove(guessColor);
        // System.out.println(guessAttribute + " " + guessColor);
        // for (String key : candidatesAttribution.keySet()) {
        //   System.out.println(candidatesAttribution.get(key));
        // }

        // ArrayList<String> toRemove = new ArrayList<String>();
        // for (String key : candidatesAttribution.keySet()) {
        //     if (candidatesAttribution.get(key).size() == 1) {
        //         toRemove.add(key);
        //         System.out.println("there is something to remove cause has only one value: " + key);
        //     }
        // }
        // for (int i = 0; i<toRemove.size();i++) {
        //     candidatesAttribution.remove(toRemove.get(i));
        // }

        guessAttribute = "hairColor";
        guessColor = "red";
        System.out.println(candidates.get("P3"));

        ArrayList<String> temp = new ArrayList<String>();
        for (String ppl : candidates.keySet()) { // need to remove the person that has guess value
            for (String att : candidates.get(ppl).keySet()) {
              System.out.println(att);
                if (att.equals(guessAttribute)) {
                  System.out.println("-------");
                    if (candidates.get(ppl).get(att).equals(guessColor)) {
                        // candidates.remove(ppl);
                      temp.add(ppl);
                        System.out.println("remove success");
                    }
                }
            }
        }
        System.out.println(temp);
        System.out.println(persons);
                for (int i=0;i<temp.size();i++) {
                    persons.remove(temp.get(i));
                }        
        System.out.println(persons);


        

	}
}
