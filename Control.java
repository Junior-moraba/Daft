import java.util.Scanner;

public class Control {
    
    //need a number and a noun
    public static void main(String[] args){
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int numberInput = userInput.nextInt();
        userInput.nextLine();
        System.out.println("Enter a noun: ");
        String nounInput = userInput.nextLine();
        userInput.close();

        //noun information
        Nouns noun = new Nouns(nounInput);
        NounData nounData = noun.getNounData();
        char relativeNoun = noun.getRelativePronoun();


        //number formation
        Numbers number = new Numbers(numberInput,true);
        String numberAString = number.constructNumber();

        /**
         * For cardinals
         * maybe before constructing the number it may be necessary to check if the number is greater than 1 for plural nouns : see rule 6 in  constructNumber #interesting
         * and check if number equal 1 for singular nouns
         * noun class already in NounData
         * may need to include status of plurality as another parameter of NounData
        */ 

        String constructedNumber = constructNumber(relativeNoun,nounData,numberAString);
        
        //construct sentence 
        constructSentence(nounInput,constructedNumber);
        
        
    }

    /** 
     * applies the last set of rules to the relative, pronoun,number
     * and constructs  the final string of that number
     * checks if the  number is used as epithet or as predicate before constructing final number
    */ 
    private static String constructNumber(char relNoun, NounData nounData,String number){
        
        String pNoun = nounData.personalNoun;
        String prefix = nounData.prefix;
        int nclass = nounData.nClass;

        String specialPrefix = "";
        String finalnumber;
        //apply special rules
        /**
          * Rule 1
          Bili, tatu and hlanu prefix "ma" and "mi" when they qualify "ama" and "imi" nouns
          */
        if ((prefix.equals("ama") || prefix.equals("imi")) && (number.equals("bili") || number.equals("tatu") || number.equals("hlanu"))){
            specialPrefix = prefix.substring(1); //remove the first letter
        }
        /**
         * Rule 2
         * Bili prefixes “m” after the pronoun “zi” and the conjunction “na”
         */
        else if (number.equals("bili") && pNoun.equals("zi")){
            specialPrefix = "m";
        }
        /**
        * Rule 3
        * Tatu and hlanu prefix “n” after the pronoun “zi” and the conjunction “na”
        */
        else if((number.equals("tatu") || number.equals("hlanu")) && pNoun.equals("zi")){
            specialPrefix = "n";
        }
        /**
         * Rule 4
         * From observation
         * If number starts with i, prefix y after the pronoun
         */
        else if(number.charAt(0)=='i'){
            specialPrefix = "y";
        }
        /**
         * Rule 5
        * Also from observation
        * If number starts with a, prefix ng after the pronoun
        */
        else if (number.charAt(0)=='a'){
            specialPrefix = "ng";
        }

        /**
         * Rule 6
         * Indefinite adjectives
         * used to describe a noun in a non-specific sense
         * odwa vs nye, odwa often used for single objects in each class with slight variations for that class
         * Not married to the idea of implementing but quite interesting 
         * Interesting if number == nye and singularity == false, the produced text == another
         *          eg abantu abanye == other people
         *          not sure if this holds for all cases though
         */
        if (number.equals("nye") && nounData.singularity){
            if (nclass == 1){
                number = "yedwa";
            }
            else if (nclass ==2 || nclass==6){
                number = "lodwa";
            }
            else if (nclass == 3){
                number = "yodwa";
            }
            else if (nclass == 4){
                number = "sodwa";
            }
            else if (nclass == 5){
                number = "wodwa";
            }
            else if (nclass == 7){
                number = "bodwa";
            }else{
                number = "kodwa";
            }

        }

        /**
         * rule 7 if two vowels(relative and pronoun) ,discard the pronoun vowel
         */ 
        if ( pNoun!="a" && pNoun!="u" && pNoun!="i"){
            //Do not discard pronoun
            finalnumber = String.join("",Character.toString(relNoun),pNoun,specialPrefix,number);
        }else{
            //discard pronoun
            finalnumber = String.join("",Character.toString(relNoun),specialPrefix,number);
        }

        return finalnumber;

    }

    private static void constructSentence(String noun,String number){
        /**
         * remove the first letter == Relative pronoun of the number for the predicate
         * leave the relative pronoun in position for the epithet
         * 
         * This method can accept a parameter specifying whether to construct predicate or epithet and rturn according
         * Print out both atm // change void to String and return
         * 
         * Although found in textbook, material not covered in depth and this rules may not hold for all cases
        */ 
        //as a predicate
        System.out.println("Predicate: "+noun+" "+number.substring(1));
        //epithet
        System.out.println("Epithet: "+noun+" "+number); 
    }

    /**
     * Before writing code for ordinals get feedback on cardinals, fix large issues then move on to ordinals (finish friday 30 Jul)
     * The prefixes below are not listed in textbook (atleast the one i have been using). The textbook mentioned the division of numbers
     * created them through seeing other examples, noticed pattern. 
     * number 1
        * ukuqala    
        * prefixs per class
            * 1 wo
            * 2 lo
            * 3 yo
            * 4 so
            * 5 wo
            * 6 lo
            * 7 bo
            * 8 ko
     * numbers 2 - 9
        * Prefix isi before root
        * Singular prefixes per class 
            * 1 we
            * 2 le
            * 3 ye
            * 4 se
            * 5 we
            * 6 lwe
            * 7 be
            * 8 kwe
    * ...
     */

}
