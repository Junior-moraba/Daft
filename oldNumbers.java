public class oldNumbers {
    //take input of number from user
    //convert the number into text
    static String numbers[] = {"nye","bili","tatu","ne","hlanu","thupha","khombisa","shiyagalombili","shiyagalolunye","shumi","khulu","kulungwane"};
    public static int inputNumber;
    public static boolean context;

    //some context could be added as a parameter to better construct the word
    public oldNumbers (int no, boolean ctxt){
        inputNumber = no;
        context = ctxt;
    }

    public static String constructNumber(){
        String numAsText = Integer.toString(inputNumber);
        int lengthOfNumber = numAsText.length();
        String numberText = "";

        //Singles
        if(lengthOfNumber==1){
            String word = individualNumbers(inputNumber, context);
            return word;
            //System.out.println(word);
        }
        //Tens
        else if(lengthOfNumber==2){
            //3 cases: exactly 10, end with a 0, end with any other number
            String word1,word2,word3;
            word1 = word2 = word3 = "";
            int firstDigit = Integer.parseInt(Character.toString(numAsText.charAt(0)));
            int secondDigit = Integer.parseInt(Character.toString(numAsText.charAt(1)));


            word1 = numClass(firstDigit, "tens",true);
            word2 = multiplierDigit(firstDigit, "tens");

            if(secondDigit!=0){ //numbers not ending with a zero
                word3 = compound(secondDigit);
            }

            numberText = String.join(" ", word1, word2, word3);
            return numberText;
            //System.out.println((word1+" "+word2+" "+word3).strip());
            
            
        }
        //Hundreds
        else if (lengthOfNumber==3){
            String word1,word2,word3,word4,word5;
            word1 = word2 = word3 = word4 = word5 = "";
            int firstDigit = Integer.parseInt(Character.toString(numAsText.charAt(0)));
            int secondDigit = Integer.parseInt(Character.toString(numAsText.charAt(1)));
            int thirdDigit = Integer.parseInt(Character.toString(numAsText.charAt(2)));

            word1 = numClass(firstDigit, "hundreds",true);
            word2 = multiplierDigit(firstDigit, "hundreds");
            word3 = numClass(secondDigit, "tens",false);
            word4 = multiplierDigit(secondDigit, "tens");

            if(thirdDigit!=0){ //numbers not ending with a zero
                word5 = compound(thirdDigit);
            }

            numberText = String.join(" ", word1, word2, word3,word4,word5);
            return numberText;
            //System.out.println((word1+" "+word2+" "+word3+" "+word4+" "+ word5).strip());
        }
        //Thousands
        else if (lengthOfNumber==4){
            String word1,word2,word3,word4,word5,word6,word7;
            word1 = word2 = word3 = word4 = word5 = word6 = word7 = "";
            int firstDigit = Integer.parseInt(Character.toString(numAsText.charAt(0)));
            int secondDigit = Integer.parseInt(Character.toString(numAsText.charAt(1)));
            int thirdDigit = Integer.parseInt(Character.toString(numAsText.charAt(2)));
            int fourthDigit = Integer.parseInt(Character.toString(numAsText.charAt(3)));

            word1 = numClass(firstDigit, "thousands",true);
            word2 = multiplierDigit(firstDigit, "thousands");
            word3 = numClass(secondDigit, "hundreds",false);
            word4 = multiplierDigit(secondDigit, "hundreds");
            word5 = numClass(thirdDigit, "tens",false);
            word6 = multiplierDigit(thirdDigit, "tens");

            if(fourthDigit!=0){ //numbers not ending with a zero
                word7 = compound(fourthDigit);
            }

            numberText = String.join(" ", word1, word2, word3,word4,word5,word6,word7);
            return numberText;

            //System.out.println((word1+" "+word2+" "+word3+" "+word4+" "+ word5+" "+word6+" "+word7).strip());
        }
        else{
            return "Numbers must be within range 1-9999";
            //System.out.println("Numbers must be within range 1-9999");
        }
    }
   
    //single digits, verbalised differently from compounded numbers hence separated.
    private static String individualNumbers(int number,boolean ctxt){
        String singlePrefixes[] = {"ku","isi"};
        String prefix = "";
        String root = numbers[number-1];
        if (ctxt){
            return root;
        }
        else{
            
            if(number <=5){
                prefix = singlePrefixes[0];
            }
            else{
                prefix = singlePrefixes[1];
            }
    
            return prefix+root;
        }
        
    }

    //For numbers not ending in zero, Not divisible by 10
    private static String compound(int number){
        String singleCompoundPrefixes[] = {"na","nam","nan","nesi"};
        String prefix = ""; 
        String root = numbers[number-1];
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

    //for tens, hundreds, thousands not starting with a one, tempted to say plural numbers
    private static String multiplierDigit(int number, String group){
        if(number==1 || number==0){ //no need for multiplier
            return "";
        }
        else{
            String tensAndHundredsPrefixes[] = {"ama","ayisi"};
            String thousandsPrefixes[] = {"ezim","ezin","ezi","eziyisi"};
            String prefix = ""; 
            String root = numbers[number-1];
            if (group=="tens" || group=="hundreds"){
                if(number>1 && number <=5){
                    prefix = tensAndHundredsPrefixes[0];
                }
                else{
                    prefix = tensAndHundredsPrefixes[1];
                }
            }
            else if(group=="thousands"){
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
    private static String numClass(int number, String group, Boolean first){
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
            if (group=="tens" || group =="hundreds"){
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

                if(group=="tens"){
                    root = numbers[9]; //shumi
                }
                //hundreds
                else{
                    root=numbers[10]; //khulu
                }

                return prefix+root;
            }
        
            else if (group=="thousands"){
                root = numbers[11]; //kulungwane
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