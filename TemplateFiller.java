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
        Map<String, String> templateSlots = new HashMap<String, String>(); 

        String word, slotInformation;
        int indexOfClosinBracket;
        int valNumber =1;
        ArrayList<String> cleanSlotData = new ArrayList<String>(); //use a map

        for (int position=0;position<templateText.length;position++){
            word = templateText[position];
            if(word.startsWith("${")){
                indexOfClosinBracket = word.lastIndexOf("}");                
                slotInformation = word.substring(2, indexOfClosinBracket);
                cleanSlotData = cleanTemplateSlot(slotInformation);

                if(cleanSlotData.get(0).equals("1")){
                    if (cleanSlotData.get(1).startsWith("subCategory")){
                        templateSlots.put(cleanSlotData.get(1), slotFillers.get(slotInformation));
                    }
                    else if( position> 2 && templateText[position-1].contains("Category") && !templateText[position-1].contains("subCategory")){
                        //the case where there is an adjective qualifies category
                        String categoryPrev = templateText[position-1].substring( templateText[position-1].lastIndexOf("{")+1, templateText[position-1].lastIndexOf("}")).toLowerCase();
                        String result = Rules.Rule3(slotFillers.get(categoryPrev+"Name") , slotInformation);
                        templateSlots.put(cleanSlotData.get(1), result);
                    }
                    else{
                        templateSlots.put(cleanSlotData.get(1), slotFillers.get(cleanSlotData.get(1).toLowerCase()+"Name"));
                    }
                }
                else if(cleanSlotData.get(0).equals("2")){
                    
                    String result;
                    if (slotFillers.containsKey("value")){
                        result = Rules.rule1( Integer.parseInt(slotFillers.get("value")), cleanSlotData.get(2), "e");
                    }
                    else{
                        result = Rules.rule1(Integer.parseInt(slotFillers.get("category"+Integer.toString(valNumber)+"Value")), cleanSlotData.get(2), "e");
                    }                        
                    templateSlots.put(cleanSlotData.get(1)+":"+cleanSlotData.get(2), result); //deal with slotInformation
                }
                else if(cleanSlotData.get(0).equals("3.1") ){
                    ArrayList<String> result;
                    if(slotFillers.containsKey(cleanSlotData.get(2).toLowerCase()+"Name")){ 
                        //find a way of ignoring case sensitivity
                        result = Rules.rule2(cleanSlotData.get(1).substring(0,cleanSlotData.get(1).length()-1),  slotFillers.get(cleanSlotData.get(2).toLowerCase()+"Name"));
                    }
                    else{
                        result = Rules.rule2(cleanSlotData.get(1).substring(0,cleanSlotData.get(1).length()-1),  slotFillers.get(cleanSlotData.get(2)));
                    }                   
                    templateSlots.put(cleanSlotData.get(1), result.get(0));
                    templateSlots.put(cleanSlotData.get(2), result.get(1));
                }
                else if(cleanSlotData.get(0).equals("3.2")){
                    String valueAsString;
                    ArrayList<String>  result2;
                    
                    if (slotFillers.containsKey("value")){
                        valueAsString = Rules.rule1( Integer.parseInt(slotFillers.get("value")), cleanSlotData.get(3), "e");
                    }
                    else{
                        valueAsString = Rules.rule1(Integer.parseInt(slotFillers.get("category"+Integer.toString(valNumber)+"Value")), cleanSlotData.get(3), "e");
                    }
                    result2 = Rules.rule2(cleanSlotData.get(1).substring(0,cleanSlotData.get(1).length()-1), valueAsString);
                    templateSlots.put(cleanSlotData.get(1), result2.get(0));
                    templateSlots.put(cleanSlotData.get(2), result2.get(1));
                }
            }
        }
        return templateSlots;
    }

    public static ArrayList<String>  cleanTemplateSlot(String templateSlot){
        boolean scenario3_2 = false;
        String templateSlot2 = "";
        ArrayList<String> slotData = new ArrayList<String>();

        if(StringUtils.countMatches(templateSlot, "$") ==1){
            /**
             * Scenario3.1 is when a category has a prefix attached to it, therefore templateSlot2 ==category
             * Scenario3.2 is when a number has a prefix attached to it, therefore templateSlot2 == number
             * return the Prefix
            */ 
            String prefix = templateSlot.substring(0, templateSlot.indexOf("}"));
            templateSlot2 = templateSlot.substring(templateSlot.indexOf("{")+1,templateSlot.length());
            System.out.println(templateSlot2+"======");

            if (StringUtils.countMatches(templateSlot2, ":") ==1){
                scenario3_2 = true;
                slotData.add("3.2");
                slotData.add(prefix);
            }
            else{
                slotData.add("3.1");
                slotData.add(prefix);
                slotData.add(templateSlot2);       
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
                slotData.add(AmountText[0]);
                slotData.add(AmountText[1]);   
                // slotData.add(e)
                
                //No need to add that its scenario 3.2
            }
            else{
                AmountText = templateSlot.split(":");
                slotData.add("2");
                slotData.add(AmountText[0]);
                slotData.add(AmountText[1]);    
            }   
        }
        if (slotData.size()==0){
            slotData.add("1");
            slotData.add(templateSlot);
        }
        return slotData;
    }
}
        