import java.util.ArrayList;
import java.util.LinkedHashMap;


public class testMessages {
 
    private static ArrayList<Message> messages = new ArrayList<>();
    public ArrayList<Message> getMessages(){
        LinkedHashMap<String, String> hashBalance = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashBudget = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashSaving = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashCharges = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashMostSpent = new LinkedHashMap<String, String>();


        //Always applicable
        hashBalance.put("CURRENT-AMOUNT-OF-MONEY","132");

        Message balanceMessage = new Message("msg1", "currentAmount", hashBalance);
        messages.add(balanceMessage);
        
        //Only one of this applicable at a time
        // hashBudget.put("CLOSE-TO-BUDGET", "izidingo:12");
        // Message budget = new Message("msg2", "statusOfBudgets", hashBudget);
        // messages.add(budget);
        
        hashBudget.put("OVER-BUDGET", "izidingo:102,ukunethezeka:33");
        Message budget = new Message("msg2", "statusOfBudgets", hashBudget);
        messages.add(budget);
        // hashBudget.put("NOT-OVER-BUDGET", "210");
        

        //Only one of this applicable at a time
        hashSaving.put("OVER-SAVING", "140");
        Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        messages.add(saving);
        
        // hashSaving.put("REACHED-SAVINGS-GOAL", "2700");
        // hashSaving.put("NOT-REACHED-SAVINGS-GOAL", "402");
        
        
        //Always applicable
        hashCharges.put("BANK-CHARGES", "67");
        Message charges = new Message("msg4", "bankCharges", hashCharges);
        //System.out.println(charges.getArguments());
        messages.add(charges);
        
        
        //Not always applicable
        hashMostSpent.put("CATEGORIES-WHERE-MOST-MONEY-SPENT", "imali yesikolo,igrosa,uphethiloli,umshuwalense");
        Message mostSpent = new Message("msg5", "mostMoney", hashMostSpent);
        messages.add(mostSpent); 
        
        return messages;
        
        //Basically upthere we are getting the messages 
        //now its time to use the messsages in the templates.

    }
    

}
