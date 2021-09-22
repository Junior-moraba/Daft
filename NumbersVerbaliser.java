public class NumbersVerbaliser{

    /**
     * Accepets a numerical number and returns the number as a word
     */
    static String roots[] = {"nye","bili","tatu","ne","hlanu","thupha","khombisa","shiyagalombili","shiyagalolunye","shumi","khulu","kulungwane"};
    public static int inputNumber;
    

    public NumbersVerbaliser (int input_number){
        inputNumber = input_number;
    }

    public static String constructNumber(){
        String intAsString = Integer.toString(inputNumber);
        int lengthOfNumber = intAsString.length();
        String verbalisedNumber = "";

        //Singles 1 - 9
        if(lengthOfNumber==1){
            verbalisedNumber = individualNumbers(inputNumber);
        }
        else {
            Boolean firstDigit = true;
            for (int digit=0;digit<lengthOfNumber;digit++){
                int currentDigit = Integer.parseInt(Character.toString(intAsString.charAt(0)));
                
                if (digit != lengthOfNumber-1){
                    verbalisedNumber = verbalisedNumber + " "+ numClass(currentDigit, intAsString.length() ,firstDigit);
                    verbalisedNumber =  verbalisedNumber + " "+ multiplierDigit(currentDigit, intAsString.length());
                    intAsString = intAsString.substring(1); //might need to check something here to avoid out of bounds error
                    
                }
                else
                {
                    if (currentDigit!=0){
                        verbalisedNumber = verbalisedNumber + " "+ compound(currentDigit);
                    }
                    else{
                        continue;
                    }
                    
                }
                
                //important as first digit doesnt conjunct with "na"
                if (digit==0){
                    firstDigit= false; 
                }
            }

        }
        return verbalisedNumber.replaceAll("\\s{2,}"," ").strip();
    }
    

    //single digits, verbalised differently from compounded numbers hence separated.
    private static String individualNumbers(int number){
        String singlePrefixes[] = {"ku","isi"};
        String prefix = "";
        String root = roots[number-1];
        if(number <=5){
            prefix = singlePrefixes[0];
        }
        else{
            prefix = singlePrefixes[1];
        }

        return prefix+root;   
    }

    //For numbers not ending in zero
    private static String compound(int number){
        String singleCompoundPrefixes[] = {"na","nam","nan","nesi"};
        String prefix = ""; 
        String root = roots[number-1];
        /**grammar rules come into play when choosing the correct prefix for values less than 5, 
         * could be argued that this is also the case for values greater than 6
        */
        if(number<=5){
            if(root.charAt(0)=='n'){
                prefix = singleCompoundPrefixes[0];
            }
            else if (root.charAt(0)=='b'){
                prefix = singleCompoundPrefixes[1];
            }
            else if(root.charAt(0)=='t' || root.charAt(0)=='h'){
                prefix = singleCompoundPrefixes[2];
            }
        }
        else{
            prefix = singleCompoundPrefixes[3];
        }
        return prefix+root;   
    }

    //For plural tens, hundreds and thousands
    private static String multiplierDigit(int number, int group){
        if(number==1 || number==0){ //no need for multiplier
            return "";
        }
        else{
            String tensAndHundredsPrefixes[] = {"ama","ayisi"};
            String thousandsPrefixes[] = {"ezim","ezin","ezi","eziyisi"};
            String prefix = ""; 
            String root = roots[number-1];
            if (group==2 || group==3){
                if(number>1 && number <=5){
                    prefix = tensAndHundredsPrefixes[0];
                }
                else{
                    prefix = tensAndHundredsPrefixes[1];
                }
            }
            else if(group==4){
                /**grammar rules come into play when choosing the correct prefix for values less than 5, 
                 * could be argued that this is also the case for values greater than 6
                */
                if(root.charAt(0)=='b'){
                    prefix =thousandsPrefixes[0];
                }
                else if(root.charAt(0)=='t' && root.charAt(0)=='h'){
                    prefix = thousandsPrefixes[1];
                }
                else if(root.charAt(0)=='n'){
                    prefix = thousandsPrefixes[2];
                }
                else{
                    prefix = thousandsPrefixes[3];
                }
            }
            return prefix+root;
        }
    }
    
    //for appropriately choosing the correct prefix and root for each group (tens,hundreds,thousands) of numbers
    private static String numClass(int number, int group, Boolean first){
        //numClass prefix is affected by whether that word is the first word in the sentence or a word within the sentence
        if(number ==0){
            // No need to mention a zero, no class for zero
            return "";
        }
        else{
            String tensAndHundredsPrefixs[] = {"i","ama","ne","nama"};
            String thousandsPrefixs[] = {"in","izin"};
            String prefix = "";
            String root = "";
            if (group==2 || group ==3){
                if (number==1){ //one hundred
                    if (first){
                        prefix = tensAndHundredsPrefixs[0]; //main number
                    }
                    else{
                        prefix = tensAndHundredsPrefixs[2]; //number is part of bigger number
                    }
                        
                }else{ //multiple hundreds
                    if (first){
                        prefix = tensAndHundredsPrefixs[1]; //main number
                    }
                    else{
                        prefix = tensAndHundredsPrefixs[3]; //sub number
                    }   
                }
                if(group==2){
                    root = roots[9]; //shumi
                }
                //hundreds
                else{
                    root=roots[10]; //khulu
                }
                return prefix+root;
            }
            else if (group==4){
                root = roots[11]; //kulungwane
                if (number==1){ //one thousand
                    prefix = thousandsPrefixs[0];
                }else{ //multiple thousands
                    prefix = thousandsPrefixs[1]; 
                }
                return prefix+root;
            }
            else{
                return prefix+root;
            }
        }
    }
}