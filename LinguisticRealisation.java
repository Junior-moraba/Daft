import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

public class LinguisticRealisation {
    
    static String[] months = {"Januwari","Februwari","Mashi","Apreli","Meyi","Juni","Julayi","Agasti","Septemba","Oktoba","Novemba","Disemba"};
    public LinguisticRealisation(){ }

    public String getGeneratedText(SentencePlan sentencePlan){
        Map<String,String> grammaticallyCorrectSlots = applyGrammarRules(sentencePlan);
        String generatedText = fillSlots(sentencePlan.getTemplate(), grammaticallyCorrectSlots);
        return generatedText;

    }
    
    
    /**
     * This function is responsible applying grammar rules to the template slot fillers
     * returns a map with grammatically correct template slots fillers
    */
    private static  Map<String, String> applyGrammarRules(SentencePlan sentencePlan){

        //divide the sentence plan into its two sub components. template and template slot fillers
        String template = sentencePlan.getTemplate();
        Map<String,String> slotFillers = sentencePlan.getSlotFillers();

        String[] templateText = template.split(" "); //use this to iterate through each template word
        
        Map<String, String> templateSlots = new HashMap<String, String>(); //Used to store grammatically correct template slot fillers

        //Miscellenaous variables used to within for loop below
        String word, slotInformation, day, month;
        int indexOfClosinBracket;
        
        Map<String,String> slotDataMap = new HashMap<String,String>();
        //loops through every word in the template
        for (int position=0;position<templateText.length;position++){
            word = templateText[position];
            if(word.startsWith("${")){ //stops at slots, ignoring ordinary words
                indexOfClosinBracket = word.lastIndexOf("}");                
                slotInformation = word.substring(2, indexOfClosinBracket);
                
                slotDataMap = cleanTemplateSlot(slotInformation); //Determine type of slot

                //fills type 1:Slot which looks like, ${word}
                if (slotDataMap.get("Type").equals("1")){
                    String slotDataString = slotDataMap.get("slotData");
                    if (slotDataString.startsWith("subCategory")){
                        templateSlots.put(slotDataString, slotFillers.get(slotInformation));
                    }
                    else if( position> 2 && templateText[position-1].contains("Category") && !templateText[position-1].contains("subCategory")){
                        //the case where there is an adjective qualifies category
                        String categoryPrev = templateText[position-1].substring( templateText[position-1].lastIndexOf("{")+1, templateText[position-1].lastIndexOf("}")).toLowerCase();
                        String result = Rules.Rule3(slotFillers.get(categoryPrev+"Name") , slotInformation);
                        templateSlots.put(slotDataString, result);
                    }
                    else{
                        templateSlots.put(slotDataString, slotFillers.get(slotDataString.toLowerCase()+"Name"));
                    }
                }
                //fills type 2:Slot which looks like, ${word:Qualifier}
                else if (slotDataMap.get("Type").equals("2")){
                    String result;
                    String noun = slotDataMap.get("Noun");
                    String qualifier = slotDataMap.get("Qualifier");
                    String qualifierNo = qualifier.substring(qualifier.length()-1);
                    if (qualifier.startsWith("Amount")){                        
                        if (slotFillers.containsKey("value")){
                            result = Rules.rule1( Integer.parseInt(slotFillers.get("value")), noun, "e");
                        }
                        else{
                            try{
                                result = Rules.rule1(Integer.parseInt(slotFillers.get("category"+qualifierNo+"Value")), noun, "e");
                            }
                            catch (NumberFormatException e){
                                continue;
                            }
                            
                        }
                    }
                    else if (qualifier.startsWith("subCatNo")){
                        result = Rules.rule1( Integer.parseInt(slotFillers.get("numberSubCategories")), noun, "e");
                    }
                    else if (qualifier.startsWith("Day")){
                        //get day from the date OR/AND the month
                        day = slotFillers.get("Date"+qualifierNo).split("-")[0];
                        result = Rules.rule1(Integer.parseInt(day), noun, "o");  
                    }
                    else{
                        result = "";
                    }
                    templateSlots.put(qualifier+":"+noun, result);    
                }
                //fills type 3.1:Slot which looks like, ${prefix}${word:Qualifier}
                else if (slotDataMap.get("Type").equals("3.1")){
                    ArrayList<String> result;
                    String prefix = slotDataMap.get("prefix");
                    String category = slotDataMap.get("category");
                    //if(slotFillers.containsKey(category.toLowerCase()+"Name")){ 
                    if(category.startsWith("Month")){
                        String dateNo = category.substring(category.length()-1);
                        month = slotFillers.get("Date"+dateNo).split("-")[1];
                        month = months[Integer.parseInt(month)-1];
                        result = new ArrayList<String>();
                        result.add(prefix.substring(0,prefix.length()-1));
                        result.add(month);   
                    }
                    else{
                        if (category.startsWith("Category")){
                            //find a way of ignoring case sensitivity
                            try{
                                result = Rules.rule2(prefix.substring(0,prefix.length()-1),  slotFillers.get(category.toLowerCase()+"Name"));
                            }
                            catch (NullPointerException e){
                                continue;
                            }  
                        }
                        else{
                            //startswith subcategory
                            try{
                                result = Rules.rule2(prefix.substring(0,prefix.length()-1),  slotFillers.get(category));
                            }
                            catch (NullPointerException e){
                                continue;
                            }
                        }
                    }
                    templateSlots.put(prefix, result.get(0));
                    templateSlots.put(category, result.get(1));          
                }
                //fills type 3.2:Slot which looks like, ${prefix}${word:Qualifier}
                else if (slotDataMap.get("Type").equals("3.2")){
                    String valueAsString;
                    String prefix = slotDataMap.get("prefix");
                    String noun = slotDataMap.get("Noun");
                    String qualifier = slotDataMap.get("Qualifier");
                    String qualifierNo = qualifier.substring(qualifier.length()-1);
                    ArrayList<String>  result;
                    
                    if (slotFillers.containsKey("value")){
                        valueAsString = Rules.rule1( Integer.parseInt(slotFillers.get("value")), noun, "e");
                    }
                    else{
                        valueAsString = Rules.rule1(Integer.parseInt(slotFillers.get("category"+qualifierNo+"Value")), noun, "e");
                    }
                    result = Rules.rule2(prefix.substring(0,prefix.length()-1), valueAsString);
                    templateSlots.put(prefix, result.get(0));
                    templateSlots.put(qualifier+":"+noun, result.get(1)); 
                }
            }
            
        }
        return templateSlots;
    }

    /**
     * This function is responsible for identifying the type of slot and the information within that slot
     */
    private static Map<String,String>  cleanTemplateSlot(String templateSlot){
        boolean scenario3_2 = false;
        String templateSlot2 = "";
        Map<String, String> slotInformation = new HashMap<String,String>();

        if(StringUtils.countMatches(templateSlot, "$") ==1){
            /**
             * Scenario3.1 is when a category has a prefix attached to it, therefore templateSlot2 ==category
             * Scenario3.2 is when a number has a prefix attached to it, therefore templateSlot2 == number
             * return the Prefix
            */ 
            String prefix = templateSlot.substring(0, templateSlot.indexOf("}"));
            templateSlot2 = templateSlot.substring(templateSlot.indexOf("{")+1,templateSlot.length());

            if (StringUtils.countMatches(templateSlot2, ":") ==1){
                scenario3_2 = true;
                slotInformation.put("Type","3.2");
                slotInformation.put("prefix",prefix);
            }
            else{
                slotInformation.put("Type","3.1");
                slotInformation.put("prefix",prefix);
                slotInformation.put("category",templateSlot2);
            }
        }
        if (StringUtils.countMatches(templateSlot, ":") == 1 || scenario3_2){
            /**
             * scenario 2
             * here a slot has a number and noun being qualified by that number
             * Qualifier : Object being Qualified
             * return the Qualifier and the object being qualified
             */
            String[] AmountText;
            if(scenario3_2){
                AmountText = templateSlot2.split(":");
                slotInformation.put("Qualifier",AmountText[0]);
                slotInformation.put("Noun",AmountText[1]);
                
            }
            else{
                AmountText = templateSlot.split(":");
                slotInformation.put("Type","2");
                slotInformation.put("Qualifier",AmountText[0]);
                slotInformation.put("Noun",AmountText[1]);
            }   
        }
        if (slotInformation.size()==0){
            slotInformation.put("Type","1");
            slotInformation.put("slotData",templateSlot);
        }
        return slotInformation;
    }
    
    /**
     * This function is responsible for filling the template with the grammatically correct slots
     */
    private static String fillSlots(String template, Map<String,String>grammaticallyCorrectSlots){
        StringSubstitutor stringSub = new  StringSubstitutor(grammaticallyCorrectSlots);
        String filledTemplate = stringSub.replace(template); //filling of slots.
        

        //check if any empty/or unfilled slots still exist in the template.
        String[] templateAsArray = filledTemplate.split(Pattern.quote("$"));

        /** Here we will be removing empty or unfilled slots before returning
         * This occurs when number of slots and number of arguments do not match
         * occurs for overbudget and categories-where-most-spent templates
         * This occurs at conjunctions such as futhi and na
         */
        if (templateAsArray.length==1){
            //no unfilled slots therefore just return filledTemplate
            return filledTemplate;
        }
        else{
            if (filledTemplate.split("futhi").length==2){
                return filledTemplate.split("futhi")[0];
            }
            else if(filledTemplate.split("nangamarandi").length==2){
                return filledTemplate.split("nangamarandi")[0];
            }
            else{
                //when categories most spent
                //remove unfilled and coalesce na with last filled 
                String filled;
                filled = templateAsArray[0];
                templateAsArray = filled.split(" ");
                String lastWord = templateAsArray[templateAsArray.length-1];
                RelativePronoun relativePronoun = new RelativePronoun(lastWord);
                lastWord = "n"+relativePronoun.getRelativePronoun()+lastWord.substring(1);
                templateAsArray = Arrays.copyOf(templateAsArray, templateAsArray.length-1);
                StringBuffer stringBuffer = new StringBuffer();
                for (String word: templateAsArray){
                    stringBuffer.append(word);
                    stringBuffer.append(" ");
                }
                stringBuffer.append(lastWord);
                stringBuffer.append(".");
                filled = stringBuffer.toString();
                return filled;
            }
    
        }
    }


}
