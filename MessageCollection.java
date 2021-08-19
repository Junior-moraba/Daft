import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MessageCollection {
    
    private static ArrayList<Message> messages = new ArrayList<>();

    
    public static ArrayList<Message> getMessages(){
        LinkedHashMap<String, String> hashBalance = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashBudget = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashSaving = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashCharges = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashMostSpent = new LinkedHashMap<String, String>();


        //Always applicable
        hashBalance.put("status","CURRENT-AMOUNT-OF-MONEY");
        hashBalance.put("value", "132");
        Message balanceMessage = new Message("msg1", "currentAmount", hashBalance);
        messages.add(balanceMessage);
        
        //Only one of this applicable at a time
        // Problem with close: is it over close or under close ???
        hashBudget.put("status","CLOSE-TO-BUDGET");
        hashBudget.put("category1Name","ukunethezeka");
        hashBudget.put("category1Value","70");
        hashBudget.put("category2Name","izidingo");
        hashBudget.put("category2Value","123");
        Message budget = new Message("msg2", "statusOfBudgets", hashBudget);
        messages.add(budget);
        

        // hashBudget.put("status","OVER-BUDGET");
        // hashBudget.put("category1Name","ukunethezeka");
        // hashBudget.put("category1Value","7230");
        // hashBudget.put("category2Name","izidingo");
        // hashBudget.put("category2Value","123");
        // Message budget = new Message("msg2", "statusOfBudgets", hashBudget);
        // messages.add(budget);

        // hashBudget.put("status","NOT-OVER-BUDGET");
        // Message budget = new Message("msg2", "statusOfBudgets", hashBudget);
        // messages.add(budget);

        //Only one of this applicable at a time
        hashSaving.put("status","OVER-SAVING");
        hashSaving.put("value","120");
        Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        messages.add(saving);
        
        // hashSaving.put("status","REACHED-SAVINGS-GOAL");
        // hashSaving.put("value","2700");
        // Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        // messages.add(saving);

        // hashSaving.put("status","NOT-REACHED-SAVINGS-GOAL");
        // hashSaving.put("value","402");
        // Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        // messages.add(saving);
        
        
        //Always applicable
        hashCharges.put("status","BANK-CHARGES");
        hashCharges.put("value","67");
        Message charges = new Message("msg4", "bankCharges", hashCharges);
        //System.out.println(charges.getArguments());
        messages.add(charges);
        
        
        //Not always applicable
        hashMostSpent.put("status","CATEGORIES-WHERE-MOST-MONEY-SPENT");
        hashMostSpent.put("subCategory1","imali yesikolo");
        hashMostSpent.put("subCategory3","uphethiloli");
        hashMostSpent.put("subCategory4","umshuwalense");
        hashMostSpent.put("subCategory2","igrosa");
        Message mostSpent = new Message("msg5", "mostMoney", hashMostSpent);
        messages.add(mostSpent); 
        
        return messages;
        
    }
    
        
    
    

}
