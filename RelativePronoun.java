public class RelativePronoun {
    //The purpose of this class is to determine the relative pronoun

    //the default isiZulu relative pronoun == a
    //this a must be made to coalesce with the initial vowel of the noun to which it refers

    String userNoun;
    public RelativePronoun(String noun){
        userNoun = noun;
    }
    
    public char getRelativePronoun(){
        char firstLetter = userNoun.charAt(0);
        if (firstLetter == 'a'){
            return 'a';
        }
        else if (firstLetter == 'i'){
            return 'e';
        }
        else if (firstLetter == 'u') {
            return 'o';
        }
        else{
            //return the default relative pronoun
            return 'a';
        }
    }
}
