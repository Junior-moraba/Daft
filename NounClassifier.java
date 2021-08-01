import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * This class classifies nouns according to noun class
 * Given a noun as input will determine noun class, prefix, personal noun and whether singular or plural
 */
public class NounClassifier {
    
    public static String userInput;
    public static String[] nounsOfPeople = new String[]{"umuntu","ubaba","ubhuti","umfana","umntwana","umngane"};
    public static List<String> nameList = new ArrayList<>(Arrays.asList(nounsOfPeople));
    
    
    
    public NounClassifier(String noun){
        userInput = noun;        
    }

    public static NounData getNounData(){
        char firstLetter = userInput.toLowerCase().charAt(0);

        NounData nounData = null;
        
        if ( firstLetter == 'a'){
           nounData = aPrefixes(userInput);
        }
        else if ( firstLetter == 'o'){
            nounData = oPrefixes(userInput);
        }
        else if ( firstLetter == 'i'){
            nounData = iPrefixes(userInput);
        }
        else if (firstLetter == 'u'){
            nounData = uPrefixes(userInput);
        }
        return nounData;
    }
    //getting the relative pronoun used to qualify this noun
    public static char getRelativePronoun(){
        RelativePronoun relativePronoun = new RelativePronoun(userInput);
        return relativePronoun.getRelativePronoun();
    }

    private static NounData aPrefixes(String userNoun){
        NounData resultNoun;
        if (userNoun.substring(0, 3).equals("aba")){
            //class 1
            resultNoun = new NounData(1, "aba","ba",false);
            return resultNoun;
        }
        else if(userNoun.substring(0, 3).equals("ama")){
            //class 2
            resultNoun = new NounData(2, "ama","a",false);
            return resultNoun;
        }
        else{
            //special case
            resultNoun = new NounData(0);
            return resultNoun;
        }
    }

    private static NounData oPrefixes(String userNoun){
        //Beginning with o can only fall into class 1
        NounData resultNoun; 
        resultNoun = new NounData(1, "aba","ba",false);
        return resultNoun;
    }

    private static NounData iPrefixes(String userNoun){
        NounData resultNoun;
        if (userNoun.substring(0, 4).equals("izim") || userNoun.substring(0, 4).equals("izin")){
            //classes 3 or 6, indeterminant : Plural
            resultNoun = new NounData(36, userNoun.substring(0, 4),"zi",false);
        }
        else if(userNoun.substring(0, 3).equals("imi")){
            //class 5 : Plural
            resultNoun = new NounData(5, "imi","i",false);
        }
        else if(userNoun.substring(0, 3).equals("izi") && (!userNoun.substring(0, 4).equals("izim") || !userNoun.substring(0, 4).equals("izin") )){
            //class 4 : Plural
            resultNoun = new NounData(4, "izi","zi",false);
        }
        else if(userNoun.substring(0, 2).equals("im") || userNoun.substring(0, 2).equals("in")){
            // class 3 : Singular
            resultNoun = new NounData(3, userNoun.substring(0, 2),"i",true);
        }
        else if(userNoun.substring(0, 3).equals("isi")){
            // class 4 : Singular
            resultNoun = new NounData(4, userNoun.substring(0, 2),"i",true);
        }
        else {
            /**
             * If singular and does not start with either im,in or isi
             * Class 2: Singular
             * Problem with determining prefix, textbook implies it will either be i or ili
             */
            if (userNoun.substring(0, 3).equals("ili")){
                resultNoun = new NounData(2,"ili","li",true);
            }
            else{
                resultNoun = new NounData(2,"i","li",true);
            }
            
        }
        return resultNoun;
    }
    //All nouns with u as a prefix are singular
    private static NounData uPrefixes(String userNoun){
        NounData resultNoun;
        /**
         * class 1;
         * consists of people, words of foreign origin and words beginning with the prefix uno (first else if statement)
         * Cant possibly know all nouns for people, so create array of few which will be sufficient for DAFT
         * foreign words will be ignored, lot of unnecessary work determining origin of word
        */
        if((userNoun.substring(0, 3).equals("umu") || userNoun.substring(0, 2).equals("um") || userNoun.substring(0, 2).equals("u")) && inPeople(userNoun)){
            if(userNoun.substring(0, 3).equals("umu")){
                resultNoun = new NounData(1, "umu","u",true);
            }
            else if(userNoun.substring(0, 2).equals("um")){
                resultNoun = new NounData(1, "um","u",true);
            }
            else{
                resultNoun = new NounData(1, "u","u",true);
            }
            return resultNoun;
        }
        else if( userNoun.substring(0, 3).equals("uno")){
            resultNoun = new NounData(1, "uno","u",true);
            return resultNoun;
        }
        /**
         * class 5;
         * if u is followed by m and it is not a person or a foreign word
        */
        if(userNoun.substring(0, 2).equals("um")  && !inPeople(userNoun)){
            resultNoun = new NounData(5, "um","u",true);
            return resultNoun;
        }
        /**
         * class 7;
        */
        else if(userNoun.substring(0, 3).equals("ubu")){
            resultNoun = new NounData(7, "ubu","bu",true);
            return resultNoun;
            
        }
        /**
         * class 8;
        */
        else if(userNoun.substring(0, 3).equals("uku")){
            resultNoun = new NounData(8, "uku","ku",true);
            return resultNoun;
            
        }
        /**
         * class 6
         * if a word begins with a u and it fails to meet any of the conditions from above, its a class 6 noun
         * This is problematic for our case where not all nouns eg(human nouns) are considered therefore
         * a significant portion of nouns will be classified as class 6 nouns incorrectly
         * another problem is determining where the prefix ends, assuming 2 possible prefixes ulu and u, based on textbook
         */
        else{
            if (userInput.substring(0,3).equals("ulu")){
                resultNoun = new NounData(6, "ulu","lu",true);
            }
            else{
                resultNoun = new NounData(6, "u","lu",true);
            }
            return resultNoun;
        }
    }
    private static boolean inPeople(String userNoun){
        //checkif there exists a tool for determining noun class, for isiZulu
        if (nameList.contains(userNoun)){
            return true;
        }
        else{
            return false;
        }
    }
    
}

