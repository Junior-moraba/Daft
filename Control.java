import java.util.Scanner;

public class Control {
    
    /**
     * 1. Include no context option in parameters,
     * 2. maybe if number not equal to 1 don't allow for ordinals
     * 3. maybe if number equal to 1 dont allow for plurals or convert plural to singular
     * 4. if number not equal to 1, convert noun to plural if necessary and possible
     * But is this part of the scope(3,4) or possible scope creep
     * 5. If Zola happy, Begin phase 2 officially on Monday (2 August)
     */
    
    public static void main(String[] args){
        /**
         * Remove this during refactoring, let this class just take this inputs as parameters instead
         * remove main class
         * Rename classes (numbers to numbersVerbaliser) 
         */
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int numberInput = userInput.nextInt();
        userInput.nextLine();
        System.out.println("Enter a noun: ");
        String nounInput = userInput.nextLine();
        System.out.println("Enter Texttype (C or O): ");
        String textType = userInput.nextLine().toLowerCase();
        String cardinalType = "e";
        if(textType.equals("c")){
            System.out.println("Enter CardinalType (E or P): ");
            cardinalType = userInput.nextLine().toLowerCase();
        }

        userInput.close();

        //noun information
        Nouns noun = new Nouns(nounInput);
        NounData nounData = noun.getNounData();
        char relativeNoun = noun.getRelativePronoun();


        //number formation
        Numbers number = new Numbers(numberInput);
        String numberAString = number.constructNumber();

        //Refactor this
        String finalSentence;    
        if(textType.equals("c")){
            String constructedNumber = constructCardinalNumber(relativeNoun,nounData,number.inputNumber,numberAString);
            if(cardinalType.equals("e")){
                finalSentence = constructSentence(nounInput,constructedNumber,"epithet");

            }
            else{
                finalSentence = constructSentence(nounInput,constructedNumber,"predicate");
            }
        }
        else{
            String constructedOrdinal = constructOrdinalNumber(nounData,number.inputNumber,numberAString);
            finalSentence = constructSentence(nounInput,constructedOrdinal,"ordinal");
        }

        System.out.println(finalSentence);
    }

    /** 
     * Cardinal Rules, exceptions application to form number
     * applies the last set of rules to the relative, pronoun,number
     * and constructs  the final string of that number
    */ 
    private static String constructCardinalNumber(char relNoun, NounData nounData,int number,String numberAsString){
        
        String pNoun = nounData.personalNoun;
        String prefix = nounData.prefix;
        int nclass = nounData.nClass;

        String specialPrefix = "";
        String finalnumber;

        //individuals numbers less than and equal to five, remove the "ku" prefix
        if  (number>=1 && number <=5){
            numberAsString = numberAsString.substring(2);
        }
        
        /**
          * Rule 1
          Bili, tatu and hlanu prefix "ma" and "mi" when they qualify "ama" and "imi" nouns
          */
        if ((prefix.equals("ama") || prefix.equals("imi")) && (numberAsString.equals("bili") || numberAsString.equals("tatu") || numberAsString.equals("hlanu"))){
            specialPrefix = prefix.substring(1); //remove the first letter
        }
        /**
         * Rule 2
         * Bili prefixOptions “m” after the pronoun “zi” and the conjunction “na”
         */
        else if (numberAsString.equals("bili") && pNoun.equals("zi")){
            specialPrefix = "m";
        }
        /**
        * Rule 3
        * Tatu and hlanu prefix “n” after the pronoun “zi” and the conjunction “na”
        */
        else if((numberAsString.equals("tatu") || numberAsString.equals("hlanu")) && pNoun.equals("zi")){
            specialPrefix = "n";
        }
        /**
         * Rule 4
         * From observation
         * If numberAsString starts with i, prefix y after the pronoun
         */
        else if(numberAsString.charAt(0)=='i'){
            specialPrefix = "y";
        }
        /**
         * Rule 5
        * Also from observation
        * If numberAsString starts with a, prefix ng after the pronoun
        */
        else if (numberAsString.charAt(0)=='a'){
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
        String[] prefixOptions = {"y","l","y","s","w","l","b","k"};
        if (number==1 && nounData.singularity){
            if(nclass==1){
                //class 1, uses an e instead of o to combine the innerprefix and the 'dwa'
                numberAsString = prefixOptions[nclass-1] + "e" + "dwa";
            }
            else{
                numberAsString = prefixOptions[nclass-1] + "o" + "dwa";
            }
        }


        /**
         * rule 7 if two vowels(relative and pronoun) ,discard the pronoun vowel
         */ 
        if ( pNoun!="a" && pNoun!="u" && pNoun!="i"){
            //Do not discard pronoun
            finalnumber = String.join("",Character.toString(relNoun),pNoun,specialPrefix,numberAsString);
        }else{
            //discard pronoun
            finalnumber = String.join("",Character.toString(relNoun),specialPrefix,numberAsString);
        }

        return finalnumber;

    }

    /**
     * Ordinal number construction
     * Confirm the rules with linguist or isiZulu speakers, worked of assumptions as textbook very vague
    */ 

    private static String constructOrdinalNumber(NounData nounData,int number,String numberAsString){

        //assumptions, (not based on any literature) ordinals only work for singular words. CONFIRM
        
        String prefix;
        String finalNumber;
        int nclass = nounData.nClass;

        String[] prefixOptions = {"w","l","y","s","w","l","b","k"};
        

        /**
         * Rule 1
         * remove the ku prefix and replace the isi prefix for numbers 1-5
        */ 
        if(number>=1 && number<=5){
            numberAsString = "isi"+numberAsString.substring(2);
        }
        /**
         * Rule 2
         * If number =1 change the root to ukuqala
         */
        if (number==1){
            numberAsString = "ukuqala";
        } 
        /**
         * Rule 3
         * get the relative pronoun using the number as a noun ***weird***
        */
        RelativePronoun rp = new RelativePronoun(numberAsString);
        /**
         * Rule 4
         * remove the first letter of the number, isiZulu rule to never have consecutive vowels
         * all numbers will start with vowels, isi prefix added to individual numbers except 1, and 
         * by default numbers greater than or equal to 10 start with either i or a
        */ 
        numberAsString = numberAsString.substring(1);
        /**
         * Rule 5
         * Noun classs 6 and 8 add a special prefix 'w' between relative and personal pronoun
         * No special rules for the rest, just choose correct prefix option, and add the relative pronoun... 
         * to that prefixOption to form the prefix of the overall number
         */
        if (nclass==6 || nclass==8){
        prefix = prefixOptions[nclass-1] + "w" + rp.getRelativePronoun();
        }
        else{
        prefix = prefixOptions[nclass-1] + rp.getRelativePronoun();
        }

        finalNumber = String.join("", prefix,numberAsString);
        return finalNumber; 

    }
    private static String constructSentence(String noun,String number,String type){
        /**
         * remove the first letter == Relative pronoun of the number for the predicate
         * leave the relative pronoun in position for the epithet
         * 
         * This method can accept a parameter specifying whether to construct predicate or epithet and rturn according
         * Print out both atm // change void to String and return
         * 
         * Although found in textbook, material not covered in depth and this rules may not hold for all cases
        */ 
        
        String finalSentence;
        if(type.equals("epithet") || type.equals("ordinal")){
            finalSentence = noun+" "+number;
        }
        else{
            //as a predicate
            finalSentence = noun+" "+number.substring(1);
        }
       return finalSentence;
    }
}
