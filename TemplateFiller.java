import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import org.apache.commons.text.StringSubstitutor;

public class TemplateFiller {
    
    static ArrayList<String> messageStatus = new ArrayList<String>();
    static ArrayList<String> messageValues = new ArrayList<String>();
    static Templates templates = new Templates();
    public static void main(String[] args){
        
        
        ArrayList<Message> messages = MessageCollection.getMessages();
        String generatedText = fillTemplates(messages);
        System.out.println(generatedText);
        writeToFile(generatedText);

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
                }
                else{
                    slotFillers.put(argument.getKey(), argument.getValue());
                }
            }
            //apply grammar rules to the template and slot fillers
            Map<String, String> grammaticallyCorrectTemplateSlots = applyGrammarRules(template,slotFillers);

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

    public static Map<String, String> applyGrammarRules(ArrayList<String> template,Map<String,String> slotFillers){
        
        String templateNo = template.get(1) ;        
        Map<String, String> templateSlots = new HashMap<String, String>();

        if(templateNo.equals("0.0") ||templateNo.equals("0.1") ||templateNo.equals("0.2") 
        || templateNo.equals("5.0") ||templateNo.equals("6.0") || templateNo.equals("7.0") ){
            int amount = 0;
            
            for (Map.Entry<String,String> slotFiller : slotFillers.entrySet()){
                if(slotFiller.getKey().equals("value")){
                    amount = Integer.parseInt(slotFiller.getValue());
                }
            }
            templateSlots.put("Amount", Rules.rule1(amount, "Amarandi"));
        }
       
        //grammar rules that only apply to templates 1.0 and 2.0
        else if (templateNo.equals("2.0") || templateNo.equals("1.0")){
            String category = "";
            int cat1Amount=0;
            int cat2Amount=0;
            String cat1Value, cat1Name, cat2Value, cat2Name;
            cat1Value = cat1Name = cat2Value = cat2Name ="";
            
            for (Map.Entry<String,String> slotFiller : slotFillers.entrySet()){
                if(slotFiller.getKey().equals("category1Value")){
                    cat1Value = slotFiller.getValue();
                }
                else if(slotFiller.getKey().equals("category2Value")){
                    cat2Value = slotFiller.getValue();
                }
                //might need to use this if templates was to be modified
                else if (slotFiller.getKey().equals("category1Name")){
                    cat1Name = slotFiller.getValue(); 
                }
                else if (slotFiller.getKey().equals("category2Name")){
                    cat2Name = slotFiller.getValue();
                }
                else{
                    continue;
                }
               
            }
            
            cat1Amount = Integer.parseInt(cat1Value);
            cat2Amount = Integer.parseInt(cat2Value);
            String noun = "Amarandi";

            templateSlots.put("Amount1",Rules.rule1(cat1Amount, noun));
            templateSlots.put("Amount2",Rules.rule1(cat2Amount, noun));
            
            if ( templateNo.equals("1.0")){
                if (cat1Name.equals("luxury")){
                    category = "ukunethezeka";
                }
                else{
                    category = "izidingo";
                }
            
                templateSlots.put("Category", category);
                templateSlots.put("dlule", Rules.Rule3(category, "dlule"));
            }
            
        }
        else if (templateNo.equals("8.0")){
            ArrayList<String> subCategories = new ArrayList<String>();
            for (Map.Entry<String,String> slotFiller : slotFillers.entrySet()){
                subCategories.add(slotFiller.getValue());
            }
            int subCatlength = subCategories.size()-1;
            String[] slotList = {"subCategory1","subCategory2","subCategory3","subCategory4"};
            for (int category=0; category<subCatlength;category++){
                templateSlots.put(slotList[category],subCategories.get(category));    
            }
            String lastSubcategory = subCategories.get(subCatlength);
    
            templateSlots.put("na",Rules.rule2("na", lastSubcategory));
            templateSlots.put(slotList[subCatlength],lastSubcategory.substring(1));
            
        }

        return templateSlots;
    }    
    
}
