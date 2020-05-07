import java.io.*;
import java.util.*;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer implements Player
{

    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName Name of the chosen person for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *    Note you can handle IOException within the constructor and remove
     *    the "throws IOException" method specification, but make sure your
     *    implementation exits gracefully if an IOException is thrown.
     */

    private HashMap<String, String> characters;
    private HashMap<String, HashMap<String, String>> candidates = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, String> chosenAttributes = null;
    private ArrayList<String> persons = null;

    private HashMap<String, HashMap<String, Integer>> candidatesAttribution = new HashMap<String, HashMap<String, Integer>>();
    private HashMap<String, Integer> innerAttribution;
    private String chosenName = null;

    public BinaryGuessPlayer(String gameFilename, String chosenName) throws IOException {

        FileInputStream fstream = new FileInputStream(gameFilename);
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
                    if (strLine.trim().equals(chosenName)) {
                        this.chosenName = chosenName;
                    }
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

        chosenAttributes = candidates.get(chosenName);
    } // end of BinaryGuessPlayer()


    public Guess guess() {

        String guessAttribute = null;
        String guessColor = null;
        
        // if there is only one person left
        if (persons.size() == 1) {
            String placeholder = persons.get(0);
            return new Guess(Guess.GuessType.Person, "", placeholder);
        }

        Integer half = persons.size()/2;

        Iterator<String> it = candidatesAttribution.keySet().iterator();
        OUTER_LOOP:
        while(half > 0) {
            while(it.hasNext()) {
                String key = it.next();
                for (String innerKey : candidatesAttribution.get(key).keySet()) {
                    if (candidatesAttribution.get(key).get(innerKey) == half) {
                        guessAttribute = key;
                        guessColor = innerKey;
                        break OUTER_LOOP;
                    }
                }

            }
            half--;
        }
        if (guessAttribute == null || guessColor == null) {
            return new Guess(Guess.GuessType.Person, "", persons.get(0));
        }

        return new Guess (Guess.GuessType.Attribute, guessAttribute, guessColor);

    } // end of guess()


	public boolean answer(Guess currGuess) {
        String guessAttribute = currGuess.getAttribute();
        String guessValue = currGuess.getValue();

        if (guessValue == null) {
            System.out.println("Error!");
        }

        if (guessAttribute.equals("") && guessValue.equals(chosenName)) { // if guess type is Person
            return true;
        } else if (!guessAttribute.equals("")){ // if guess type is Attribute
            for(String att : chosenAttributes.keySet()) {
                if(guessAttribute.equals(att)) {
                    String value = chosenAttributes.get(att);
                    if(guessValue.equals(value)) { // if value is correct
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
        // If player 1 made a person guess and it was correct, player1Finished should be true;
        // otherwise be false.
        String guessAttribute = currGuess.getAttribute();
        String guessValue = currGuess.getValue();

        ArrayList<String> toRemove = new ArrayList<String>();

        // when guess type is person
        if (guessAttribute.equals("")) {
            // remove the person that has been comared 
			String peopleToRemove = guessValue;
			HashMap<String, String> attToRemove = candidates.get(peopleToRemove);
			for (String att : attToRemove.keySet()) {
				String attColor = attToRemove.get(att);
				if (candidatesAttribution.containsKey(att) && candidatesAttribution.get(att).containsKey(attColor)) {
					candidatesAttribution.get(att).replace(attColor, candidatesAttribution.get(att).get(attColor)-1);
				}
			}
            persons.remove(guessValue); 
            return answer; // if the person guess was true, game is end
        } 

        // guess type Attribute
        else { 
            // attribute guess is false
            if (answer == false) { 
                // get person list to remove the person that has guess value
                for (String ppl : candidates.keySet()) { 
                    for (String att : candidates.get(ppl).keySet()) {
                        if (att.equals(guessAttribute)) {
                            if (candidates.get(ppl).get(att).equals(guessValue)) {
                                toRemove.add(ppl);
                            }
                        }
                    }
                }
            } 
            // attribute guess is true
            else if (answer == true) { 
                // get person list to remove the person that doesn't has guess value
                for (String ppl : candidates.keySet()) { 
                    for (String att : candidates.get(ppl).keySet()) {
                        if (att.equals(guessAttribute)) {
                            if (!candidates.get(ppl).get(att).equals(guessValue)) {
                                toRemove.add(ppl);
                            }
                        }
                    }
                }
            }
			// remove the person in the deletion list 
			// and change the number of contribution of that person in candidatesAttribution list
            for (int i=0;i<toRemove.size();i++) {
				String peopleToRemove = toRemove.get(i);
				HashMap<String, String> attToRemove = candidates.get(peopleToRemove);
				for (String att : attToRemove.keySet()) {
					String attColor = attToRemove.get(att);
					if (candidatesAttribution.containsKey(att) && candidatesAttribution.get(att).containsKey(attColor)) {
						candidatesAttribution.get(att).replace(attColor, candidatesAttribution.get(att).get(attColor)-1);
					}
				}
                persons.remove(peopleToRemove);
            }
        }

		toRemove.clear();
		for (String key : candidatesAttribution.keySet()) {
            // if all the candidates has same value, do not need to compare
            if (candidatesAttribution.get(key).size() == 1) { 
                toRemove.add(key);
            }
        }
        for (int i = 0; i<toRemove.size();i++) {
            candidatesAttribution.remove(toRemove.get(i));
        }

		toRemove.clear();
		for (String key : candidatesAttribution.keySet()) {
			// remove the values that has been compared
			if (candidatesAttribution.containsKey(guessAttribute) && guessAttribute.equals(key)) {
               toRemove.add(key);
            }
		}
        for (int i = 0; i<toRemove.size();i++) {
            candidatesAttribution.remove(toRemove.get(i));
        }
            
        return false;
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
