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

        // System.out.println(slotFillers.entrySet());
        String word, slotInformation,slotInformation2;
        int indexOfClosinBracket;
        int valNumber =1;

        for (int position=0;position<templateText.length;position++){
            word = templateText[position];
            if(word.startsWith("${")){
                indexOfClosinBracket = word.indexOf("}");
                slotInformation = word.substring(2, indexOfClosinBracket);

                if(StringUtils.countMatches(word, "$") ==2){
                    //Prefix + Category
                    //apply rule 2

                    slotInformation2 = word.substring(word.lastIndexOf("$")+2,word.lastIndexOf("}"));
                    ArrayList<String> result;

                    if(slotFillers.containsKey(slotInformation2.toLowerCase()+"Name")){ //find a way of ignoring case sensitivity
                        result = Rules.rule2(slotInformation,  slotFillers.get(slotInformation2.toLowerCase()+"Name"));
                    }
                    else{
                        result = Rules.rule2(slotInformation,  slotFillers.get(slotInformation2));
                    }
                    templateSlots.put(slotInformation, result.get(0));
                    templateSlots.put(slotInformation2, result.get(1));
                    
                }
                else{
                    if(StringUtils.countMatches(slotInformation, ":") ==1){
                        //The case where we have an amount and the noun it describes
                        //Apply rule 1 
                        String[] AmountText = slotInformation.split(":");
                        String result;
                        if (slotFillers.containsKey("value")){
                            result = Rules.rule1( Integer.parseInt(slotFillers.get("value")), AmountText[1], "e");
                        }
                        else{
                            result = Rules.rule1( Integer.parseInt(slotFillers.get("category"+Integer.toString(valNumber)+"Value")), AmountText[1], "e");
                        }                        
                        templateSlots.put(slotInformation, result);

                    }
                    else if( position> 2 && templateText[position-1].contains("Category") && !templateText[position-1].contains("subCategory")){
                        //the case where there is an adjective qualifies category
                        //Apply rule 3
                        String categoryPrev = templateText[position-1].substring( templateText[position-1].lastIndexOf("{")+1, templateText[position-1].lastIndexOf("}")).toLowerCase();
                        String result = Rules.Rule3(slotFillers.get(categoryPrev+"Name") , slotInformation);
                        templateSlots.put(slotInformation, result);
                    }
                    else{
                        if (slotInformation.startsWith("subCategory")){
                            templateSlots.put(slotInformation, slotFillers.get(slotInformation));
                        }
                        else{
                            templateSlots.put(slotInformation, slotFillers.get(slotInformation.toLowerCase()+"Name"));
                        }
                    }
                }       
            }
        }
        return templateSlots;

    }
}
        