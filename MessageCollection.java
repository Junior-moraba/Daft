import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MessageCollection {
    
    private static ArrayList<Message> messages = new ArrayList<>();

    
    public static ArrayList<Message> getMessages(){
        LinkedHashMap<String, String> hashBalance = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashBudgetLanding = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashSaving = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashCharges = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashMostSpent = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashPeriod = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashPercentages = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashPercentValues = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashTransactions = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> hashBudgetCategory = new LinkedHashMap<String, String>();



        //Always applicable
        // hashBalance.put("value", "6040");
        // Message balanceMessage = new Message("msg1", "currentAmount", hashBalance);
        // messages.add(balanceMessage);
        
        //Only one of this applicable at a time
        // Problem with close: is it over close or under close ???
        // hashBudgetLanding.put("status","CLOSE-TO-BUDGET");
        // hashBudgetLanding.put("category1Name","ukudingeka");
        // hashBudgetLanding.put("category1Value","402");
        // hashBudgetLanding.put("category2Name","ukunethezeka");
        // hashBudgetLanding.put("category2Value","1005");
        // Message budget = new Message("msg2", "statusOfBudgets", hashBudgetLanding);
        // messages.add(budget);
        

        // hashBudgetLanding.put("status","OVER-BUDGET");
        // hashBudgetLanding.put("category1Name","ukunethezeka");
        // hashBudgetLanding.put("category1Value","7");
        // // hashBudgetLanding.put("category2Name","izidingo");
        // // hashBudgetLanding.put("category2Value","99");
        // Message budget = new Message("msg2", "statusOfBudgets", hashBudgetLanding);
        // messages.add(budget);

        // hashBudgetLanding.put("status","NOT-OVER-BUDGET");
        // hashBudgetLanding.put("value","7230");
        // Message budget = new Message("msg2", "statusOfBudgets", hashBudgetLanding);
        // messages.add(budget);

        //Only one of this applicable at a time
        // hashSaving.put("status","OVER-SAVING");
        // hashSaving.put("value","101");
        // Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        // messages.add(saving);
        
        // hashSaving.put("status","REACHED-SAVINGS-GOAL");
        // Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        // messages.add(saving);

        // hashSaving.put("status","NOT-REACHED-SAVINGS-GOAL");
        // hashSaving.put("value","1320");
        // Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        // messages.add(saving);
        
        
        // //Always applicable
        // hashCharges.put("value","25");
        // Message charges = new Message("msg4", "bankCharges", hashCharges);
        // //System.out.println(charges.getArguments());
        // messages.add(charges);
        
        
        // Not always applicable
        // hashMostSpent.put("subCategory1","ugesi");
        // hashMostSpent.put("subCategory2","amakhonsathi-nemicimbi");
        // hashMostSpent.put("subCategory3","izingubo");
        // hashMostSpent.put("subCategory4","ukuqasha");
        // Message mostSpent = new Message("msg5", "mostMoney", hashMostSpent);
        // messages.add(mostSpent);
        
        // hashPeriod.put("Date1", "15-07-2021");
        // hashPeriod.put("Date2", "26-08-2021");
        // Message period = new Message("msg6", "budgetPeriod", hashPeriod);
        // messages.add(period);

        // hashPercentages.put("category1Name","ukunethezeka");
        // hashPercentages.put("category1Value","40");
        // hashPercentages.put("category2Name","ukudingeka");
        // hashPercentages.put("category2Value","55");
        // hashPercentages.put("category3Name","wonga");
        // hashPercentages.put("category3Value","5");
        // Message percentage = new Message("msg7", "budgetPercentages", hashPercentages);
        // messages.add(percentage);

        // hashPercentValues.put("category1Name","ukunethezeka");
        // hashPercentValues.put("category1Value","510");
        // hashPercentValues.put("category2Name","ukudingeka");
        // hashPercentValues.put("category2Value","1820");
        // hashPercentValues.put("category3Name","wonga");
        // hashPercentValues.put("category3Value","70");
        // Message percentValues = new Message("msg8", "budgetValues", hashPercentValues);
        // messages.add(percentValues);

        hashTransactions.put("value", "8");
        Message transactions = new Message("msg9", "numTransactions", hashTransactions);
        messages.add(transactions);

        
        // hashBudgetCategory.put("status","OVER-BUDGET-CATEGORY-1");
        // hashBudgetCategory.put("numberSubCategories","9");
        // hashBudgetCategory.put("value","2014");
        // Message budgetCategory = new Message("msg10", "statusOfBudgetsCategory", hashBudgetCategory);
        // messages.add(budgetCategory);

        // hashBudgetCategory.put("status","OVER-BUDGET-CATEGORY-2");
        // hashBudgetCategory.put("category1Name","igrosa");
        // hashBudgetCategory.put("category1Value","125");
        // hashBudgetCategory.put("category2Name","ugesi");
        // hashBudgetCategory.put("category2Value","190");
        // Message budgetCategory = new Message("msg10", "statusOfBudgetsCategory", hashBudgetCategory);
        // messages.add(budgetCategory);

        // hashBudgetCategory.put("status","NOT-OVER-BUDGET-CATEGORY");
        // hashBudgetCategory.put("category1Name","umshuwalense");
        // Message budgetCategory = new Message("msg11", "statusOfBudgetsCategory", hashBudgetCategory);
        // messages.add(budgetCategory);

        return messages;
        
    }
    
        
    
    

}
