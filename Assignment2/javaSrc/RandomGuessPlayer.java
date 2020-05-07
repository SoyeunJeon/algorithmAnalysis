import java.io.*;
import java.util.*;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player
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
    private String chosenName = null;
    private ArrayList<String> values = null; // Always going to be 1 value.
    private ArrayList<String> attributes_list;
    private HashMap<String, String> people_attributes = null;
    private HashMap<String, String> chosen_attributes = null; //
    private Map<String, ArrayList<String>> attributes = new HashMap<String, ArrayList<String>>();
    private Map<String, HashMap<String, String>> people = new HashMap<String, HashMap<String, String>>();
    
    
    public RandomGuessPlayer(String gameFilename, String chosenName) throws IOException {
    
        FileInputStream fstream = new FileInputStream(gameFilename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine = null;
        String[] tokens = null;
        String name = null;
        
        while((strLine = br.readLine()) != null) {
            if(strLine.length() > 0) {
                values = new ArrayList<String>();
                tokens = strLine.split(" ");
                for(int i = 1; i < tokens.length; i++) {
                    values.add(tokens[i]);
                }
                attributes.put(tokens[0], values);
            }
            else {
                break;
            }
        }
        attributes_list = new ArrayList<String>(attributes.keySet());

        while((strLine = br.readLine()) != null) {
            people_attributes = new HashMap<String, String>();
            
            while(strLine!=null && !strLine.trim().equals("")) {
                tokens = strLine.split(" ");
                if(tokens.length == 1) {
                    name = tokens[0];
                }
                else if(tokens.length > 1) {
                    people_attributes.put(tokens[0], tokens[1]);
                }
                strLine = br.readLine();
            }
           people.put(name, people_attributes);
        }    
        this.chosenName = chosenName;
        chosen_attributes = people.get(chosenName);
/**
        for(String p_name : people.keySet()) {
            System.out.println(p_name);
            for(String p_values : people.get(p_name).keySet()) {         
                String pi_values = people.get(p_name).get(p_values);
                System.out.println(p_values + " " + pi_values);
            }  
        }
        for(String Lname : chosen.keySet()) {
            System.out.println(Lname);
            for(String Oname : chosen.get(Lname).keySet()) {
                String Pvalue = chosen.get(Lname).get(Oname);
                System.out.println(Oname + " " + Pvalue);
            }
        }
        for(String kname : attributes.keySet()) {
            String key = kname.toString();
            System.out.print(key + " ");
            for(String value : attributes.get(kname)) {
                System.out.print(value + " ");
            }
            System.out.println();  
        }
    **/
    } // end of RandomGuessPlayer()
    

    // Guessing algorithms.
    public Guess guess() { // Elimate people from people Map 
        Random random = new Random();
        String guess_val = null;
        String guess_name = null;
        String guess_att = null;
        int guess_number_value = 0;
        Guess guess = null;
        
        if(people.size() > 1) {
            guess_att = attributes_list.get(random.nextInt(attributes_list.size())); // get random attribute.
            guess_number_value = random.nextInt(attributes.get(guess_att).size()); // random value number
            guess_val = attributes.get(guess_att).get(guess_number_value); // random value
            guess = new Guess(Guess.GuessType.Attribute, guess_att, guess_val); // guess attribute and value
        }
        else if(people.size() == 1){
            for(String key : people.keySet()) {
                guess_name = key;
            }
            guess = new Guess(Guess.GuessType.Person, "", guess_name); // guess the person
        }
        
        return guess;
    } // end of guess()

    // Responds if guess from other player is correct or not
    public boolean answer(Guess currGuess) {
        // placeholder, replace
        String value;
        String opponent_att = currGuess.getAttribute();
        String opponent_val = currGuess.getValue();
        boolean answer = false;
        if(opponent_val.equals(this.chosenName)) { // if opponent guesses person correct
            answer = true;
        }
        else if(!opponent_att.equals("")){ // if opponent guessing value
            for(String att : chosen_attributes.keySet()) {
                if(opponent_att.equals(att)) {
                    value = chosen_attributes.get(att);
                    if(opponent_val.equals(value)) { // if value is correct
                        answer = true;
                    }
                    else { 
                        answer = false;
                    }
                    break;
                }
            }
        }
        
        return answer;

    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
        String g_att = currGuess.getAttribute(); 
        String g_value = currGuess.getValue();
        String p_value = null;
        boolean status = false;
        ArrayList<String> remove_name = new ArrayList<String>();
        if(answer == false) {       
            if(!g_att.equals("")) {
                attributes.get(g_att).remove(g_value); //remove value from map
                for(String p_name : people.keySet()) { //search through people
                    for(String p_values : people.get(p_name).keySet()) { //search for people's attributes        
                        if(p_values.equals(g_value)) {
                            people.remove(p_name); //remove people with the wrong features
                        }
                    }  
                }
            }
            else {
                people.remove(currGuess.getType()); // remove people list by name.
            }
            status = false;
        }
        else if(answer == true){ 
            if(!g_att.equals("")) { //remove all people that doesn't g_val
                for(String p_name : people.keySet()) { // search through people with attribute
                    for(String p_attributes : people.get(p_name).keySet()) { // search through attributes map
                        if(p_attributes.equals(g_att)) { // when current attributes equal to guess attribute
                            p_value = people.get(p_name).get(p_attributes);
                            if(!p_value.equals(g_value)) { // if current value doesn't equal to guess value
                                remove_name.add(p_name);
                            }
                        }
                        //System.out.print(p_name + ": " + people.get(p_name).get(p_values) + " ");
                    }
                    //System.out.println();
                }
                for(String r_name : remove_name) {
                    people.remove(r_name);
                }
                status = false;
            } else { // when answer is person
                status = true;
            }
            
        }
        return status;
    } // end of receiveAnswer()

} // end of class RandomGuessPlayer
