import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

public class TemplateFiller {
    
    static ArrayList<String> messageStatus = new ArrayList<String>();
    static ArrayList<String> messageValues = new ArrayList<String>();    
    static Templates templates = new Templates();
    static String[] months = {"Januwari","Februwari","Mashi","Apreli","Meyi","Juni","Julayi","Agasti","Septemba","Oktoba","Novemba","Disemba"};
    public static void main(String[] args){
        
        
        ArrayList<Message> messages = MessageCollection.getMessages();
        String generatedText = fillTemplates(messages);
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println(generatedText);
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        writeToFile(generatedText);

    }

    public static String selectAppropriateTemplate( String message){

        Random random = new Random();
        int randomInt = 0;
        String template="";
        
        if  (message.equals("CURRENT-AMOUNT-OF-MONEY")){
            
            randomInt = random.nextInt(Templates.balanceTemplates.size());
            template = Templates.balanceTemplates.get(randomInt);
        }
        else if  (message.equals("BANK-CHARGES")){
            randomInt = random.nextInt(Templates.chargesTemplates.size());
            template = Templates.chargesTemplates.get(randomInt);
        }
        else if  (message.equals("CATEGORIES-WHERE-MOST-MONEY-SPENT")){
            randomInt = random.nextInt(Templates.spentTemplates.size());
            template = Templates.spentTemplates.get(randomInt);
        }   
        else if  (message.equals("CLOSE-TO-BUDGET")){
            randomInt = random.nextInt(Templates.budgetCloseTemplates.size());
            template = Templates.budgetCloseTemplates.get(randomInt);  
        }   
        else if  (message.equals("NOT-OVER-BUDGET")){
            randomInt = random.nextInt(Templates.budgetMetTemplates.size());
            template = Templates.budgetMetTemplates.get(randomInt);
        }   
        else if  (message.equals("OVER-BUDGET")){
            randomInt = random.nextInt(Templates.budgetOverTemplates.size());
            template = Templates.budgetOverTemplates.get(randomInt);
        }    
        else if  (message.equals("NOT-REACHED-SAVINGS-GOAL")){
            randomInt = random.nextInt(Templates.savingsBelowTemplates.size());
            template = Templates.savingsBelowTemplates.get(randomInt); 
        }    
        else if  (message.equals("OVER-SAVING")){
            randomInt = random.nextInt(Templates.savingsOverTemplates.size());
            template = Templates.savingsOverTemplates.get(randomInt); 
        }
        else if  (message.equals("REACHED-SAVINGS-GOAL")){
            randomInt = random.nextInt(Templates.savingsMetTemplates.size());
            template = Templates.savingsMetTemplates.get(randomInt);
        }
        else if (message.equals("BUDGET-PERIOD")){
            randomInt = random.nextInt(Templates.periodTemplates.size());
            template = Templates.periodTemplates.get(randomInt);
        }
        else if (message.equals("BUDGET-PERCENTAGE")){
            randomInt = random.nextInt(Templates.percentagesTemplates.size());
            template = Templates.percentagesTemplates.get(randomInt);
        }
        else if (message.equals("BUDGET-PERCENTAGE-VALUES")){
            randomInt = random.nextInt(Templates.percentValuesTemplates.size());
            template = Templates.percentValuesTemplates.get(randomInt);
        }
        else if(message.equals("NUMBER-OF-TRANSACTIONS")){
            randomInt = random.nextInt(Templates.noTransactionsTemplates.size());
            template = Templates.noTransactionsTemplates.get(randomInt);
        }
        else if(message.equals("OVER-BUDGET-CATEGORY-1")){
            randomInt = random.nextInt(Templates.categoryBudgetOver1Templates.size());
            template = Templates.categoryBudgetOver1Templates.get(randomInt);
        }
        else if(message.equals("OVER-BUDGET-CATEGORY-2")){
            randomInt = random.nextInt(Templates.categoryBudgetOver2Templates.size());
            template = Templates.categoryBudgetOver2Templates.get(randomInt);
        }
        else if(message.equals("NOT-OVER-BUDGET-CATEGORY")){
            randomInt = random.nextInt(Templates.categoryBudgetNotOverTemplates.size());
            template = Templates.categoryBudgetNotOverTemplates.get(randomInt);
        }
        else 
        {
            template = ""; 
        }
        
        return template;
    }

    public static String fillTemplates(ArrayList<Message> messages){
        ArrayList<String> filledTemplates = new ArrayList<String>();
        
        for (int i=0;i<messages.size();i++){
            String template = "";
            Map<String,String> slotFillers = new HashMap<String,String>();
            Message message = messages.get(i);
            LinkedHashMap<String,String> argumentsHashMap = message.getArguments();
            
            //get template and appropriate slot fillers
            for (Map.Entry<String,String> argument : argumentsHashMap.entrySet()){
                if (argument.getKey().equals("status")){//this represents a new set of messages, status always first argument
                    template = selectAppropriateTemplate(argument.getValue()); //selecting the template   
                }
                else{
                    slotFillers.put(argument.getKey(), argument.getValue());
                }
            }
            //apply grammar rules to the template and slot fillers
            Map<String, String> grammaticallyCorrectTemplateSlots = applyGrammarRules(template,slotFillers);
            //fill the template with the slot fillers
            StringSubstitutor stringSub = new  StringSubstitutor(grammaticallyCorrectTemplateSlots);
            String filledTemplate = stringSub.replace(template);
             
            filledTemplates.add(filledTemplate);
            
        }
        String generatedText = combineTemplates(filledTemplates);
        return generatedText;
    }
    public static String combineTemplates(ArrayList<String> filledTemplates){
        //Additional grammar rules can be applied here, rules pertaining to how sentences are combined ??
        StringBuffer stringBuffer = new StringBuffer();
        for (String template : filledTemplates){
            stringBuffer.append(template);
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }
    private static void writeToFile(String sentence){
        try{ 
            FileWriter fileWriter = new FileWriter("generatedText.txt");
            fileWriter.write(sentence);
            fileWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    
    public static  Map<String, String> applyGrammarRules(String template,Map<String,String> slotFillers){

        String[] templateText = template.split(" "); 
        System.out.println(template);
        Map<String, String> templateSlots = new HashMap<String, String>(); 

        String word, slotInformation, day, month;
        int indexOfClosinBracket;
        
        Map<String,String> slotDataMap = new HashMap<String,String>();

        for (int position=0;position<templateText.length;position++){
            word = templateText[position];
            if(word.startsWith("${")){
                indexOfClosinBracket = word.lastIndexOf("}");                
                slotInformation = word.substring(2, indexOfClosinBracket);
                
                slotDataMap = cleanTemplateSlot(slotInformation);

                if (slotDataMap.get("Type").equals("1")){
                    String slotDataString = slotDataMap.get("slotData");
                    if (slotDataString.startsWith("subCategory")){
                        templateSlots.put(slotDataString, slotFillers.get(slotInformation));
                    }
                    else if(slotDataString.startsWith("Month")){
                        //choose the right month la
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
                            result = Rules.rule1(Integer.parseInt(slotFillers.get("category"+qualifierNo+"Value")), noun, "e");
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
                            result = Rules.rule2(prefix.substring(0,prefix.length()-1),  slotFillers.get(category.toLowerCase()+"Name"));
                        }
                        else{
                            //startswith subcategory
                            result = Rules.rule2(prefix.substring(0,prefix.length()-1),  slotFillers.get(category));
                        }
                    }
                    templateSlots.put(prefix, result.get(0));
                    templateSlots.put(category, result.get(1));          
                }
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

    public static Map<String,String>  cleanTemplateSlot(String templateSlot){
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
     * Three possible scenarios
     * ${S} == S
     * ${S:N} ==  S:N 
     * ${S}${S} = S,S
     * ${S}${S:N} = S, S:N
     */ 
}
        
