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
        // writeToFile(generatedText);

    }

    public static ArrayList<String> selectAppropriateTemplate( String message){


        Random random = new Random();
        int randomInt = 0;
        String templateNumber="";
        String template="";
        

        ArrayList<String> selectedTemplate = new ArrayList<String>();
        
        
        if  (message.equals("CURRENT-AMOUNT-OF-MONEY")){
            
            randomInt = random.nextInt(Templates.balanceTemplates.size());
            template = Templates.balanceTemplates.get(randomInt);
            templateNumber = "0."+Integer.toString(randomInt);
        }
        else if  (message.equals("BANK-CHARGES")){
            randomInt = random.nextInt(Templates.chargesTemplates.size());
            template = Templates.chargesTemplates.get(randomInt);
            templateNumber = "7."+Integer.toString(randomInt);
        }
        else if  (message.equals("CATEGORIES-WHERE-MOST-MONEY-SPENT")){
            randomInt = random.nextInt(Templates.spentTemplates.size());
            template = Templates.spentTemplates.get(randomInt);
            templateNumber = "8."+Integer.toString(randomInt);
        }   
        else if  (message.equals("CLOSE-TO-BUDGET")){
            randomInt = random.nextInt(Templates.budgetCloseTemplates.size());
            template = Templates.budgetCloseTemplates.get(randomInt);
            templateNumber = "1."+Integer.toString(randomInt);
        }   
        else if  (message.equals("NOT-OVER-BUDGET")){
            randomInt = random.nextInt(Templates.budgetMetTemplates.size());
            template = Templates.budgetMetTemplates.get(randomInt);
            templateNumber = "3."+Integer.toString(randomInt);
        }   
        else if  (message.equals("OVER-BUDGET")){
            randomInt = random.nextInt(Templates.budgetOverTemplates.size());
            template = Templates.budgetOverTemplates.get(randomInt);
            templateNumber = "2."+Integer.toString(randomInt);
        }    
        else if  (message.equals("NOT-REACHED-SAVINGS-GOAL")){
            randomInt = random.nextInt(Templates.savingsBelowTemplates.size());
            template = Templates.savingsBelowTemplates.get(randomInt);
            templateNumber = "4."+Integer.toString(randomInt);
        }    
        else if  (message.equals("OVER-SAVING")){
            randomInt = random.nextInt(Templates.savingsOverTemplates.size());
            template = Templates.savingsOverTemplates.get(randomInt);
            templateNumber = "5."+Integer.toString(randomInt);
        }
        else if  (message.equals("REACHED-SAVINGS-GOAL")){
            randomInt = random.nextInt(Templates.savingsMetTemplates.size());
            template = Templates.savingsMetTemplates.get(randomInt);
            templateNumber = "6."+Integer.toString(randomInt);
        }
        else 
        {
            template = "";
            templateNumber ="";
         
        }
        
        selectedTemplate.add(template);
        selectedTemplate.add(templateNumber);

        return selectedTemplate;
    }

    public static String fillTemplates(ArrayList<Message> messages){
        ArrayList<String> filledTemplates = new ArrayList<String>();
        
        for (int i=0;i<messages.size();i++){
            ArrayList<String> template = new ArrayList<String>();
            Map<String,String> slotFillers = new HashMap<String,String>();
            Message message = messages.get(i);
            LinkedHashMap<String,String> argumentsHashMap = message.getArguments();
            
            //get template and appropriate slot fillers
            for (Map.Entry<String,String> argument : argumentsHashMap.entrySet()){
                if (argument.getKey().equals("status")){//this represents a new set of messages, status always first argument
                    template = selectAppropriateTemplate(argument.getValue()); //selecting the template
                    template.add(message.getRelation());
                }
                else{
                    slotFillers.put(argument.getKey(), argument.getValue());
                }
            }
            //apply grammar rules to the template and slot fillers
            // Map<String, String> grammaticallyCorrectTemplateSlots = applyGrammarRules(template,slotFillers);
            Map<String, String> grammaticallyCorrectTemplateSlots = loopTemplate(template,slotFillers);
            //fill the template with the slot fillers
            StringSubstitutor stringSub = new  StringSubstitutor(grammaticallyCorrectTemplateSlots);
            String filledTemplate = stringSub.replace(template.get(0));
             
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
    public static  Map<String, String> loopTemplate(ArrayList<String> template,Map<String,String> slotFillers){

        String[] templateText = template.get(0).split(" "); 
        Map<String, String> templateSlots = new HashMap<String, String>(); 

        System.out.println(slotFillers.entrySet());
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


    // public static Map<String, String> applyGrammarRules(ArrayList<String> template,Map<String,String> slotFillers){
        
    //     String templateNo = template.get(1) ;        
    //     Map<String, String> templateSlots = new HashMap<String, String>(); 
    //     int amount, cat1Value ,  cat2Value;
    //     amount = cat1Value = cat2Value = 0;
    //     String  cat1Name, cat2Name;
    //     cat1Name  = cat2Name ="";
    //     ArrayList<String> subCategories = new ArrayList<String>();
    //     for (Map.Entry<String,String> slotFiller : slotFillers.entrySet()){
            
    //         if(slotFiller.getKey().equals("value")){
    //             amount = Integer.parseInt(slotFiller.getValue());
    //         }
    //         else if(slotFiller.getKey().equals("category1Value")){
    //             cat1Value = Integer.parseInt(slotFiller.getValue());
    //         }
    //         else if(slotFiller.getKey().equals("category2Value")){
    //             cat2Value = Integer.parseInt(slotFiller.getValue());
    //         }
    //         //might need to use this if templates was to be modified
    //         else if (slotFiller.getKey().equals("category1Name")){
    //             cat1Name = slotFiller.getValue(); 
    //         }
    //         else if (slotFiller.getKey().equals("category2Name")){
    //             cat2Name = slotFiller.getValue();
    //         }
    //         else if (slotFiller.getKey().startsWith("subCategory")) {
    //             subCategories.add(slotFiller.getValue());
    //         }
    //     }
        
        
    //     int subCatlength = subCategories.size()-1;
    //     if (subCatlength!=-1){
    //         String[] slotList = {"subCategory1","subCategory2","subCategory3","subCategory4"};
    //         for (int cat=0; cat<subCatlength;cat++){
    //             templateSlots.put(slotList[cat],subCategories.get(cat));    
    //         }
    //         String lastSubcategory = subCategories.get(subCatlength);

    //         templateSlots.put("most",Rules.rule1(subCatlength,"izindleko" , "e"));
    //         templateSlots.put("na",Rules.rule2("na", lastSubcategory));
    //         templateSlots.put(slotList[subCatlength],Rules.Rule4(lastSubcategory));
    //     }
        

    //     //the template will only entertain slots which it has and ignore rest, this limits if statements 
    //     templateSlots.put("Amount", Rules.rule1(amount, "Amarandi", "e"));  //not always
    //     templateSlots.put("Amount1",Rules.rule1(cat1Value, "Amarandi", "e"));
    //     templateSlots.put("Amount2",Rules.rule1(cat2Value, "Amarandi", "e"));

    //     if(templateNo.equals("7.1")){
    //         templateSlots.put("Amount", Rules.rule1(amount, "imali", "e"));
    //     }

    //     templateSlots.put("Category", cat1Name);
    //     templateSlots.put("za",Rules.rule2("za", cat1Name)); //1.1

        
    //     templateSlots.put("za1",Rules.rule2("za", cat1Name)); //2.0
    //     templateSlots.put("za2",Rules.rule2("za", cat2Name)); //2.0
    //     templateSlots.put("nga",Rules.rule2("nga", cat1Name)); //2.1
    //     templateSlots.put("kwa",Rules.rule2("kwa", cat2Name)); //2.1
        
    //     if (templateNo.equals("1.1") || templateNo.equals("2.0")|| templateNo.equals("2.1")){
    //         templateSlots.put("Category", Rules.Rule4(cat1Name));
    //         templateSlots.put("category2",Rules.Rule4(cat2Name)); 
    //     }
    //     templateSlots.put("dlule", Rules.Rule3(cat1Name, "dlule")); //1.0

    //     return templateSlots;
    // }    
    
}
        