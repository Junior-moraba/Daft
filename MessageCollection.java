import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Since NLG pipeline components were divided such that project members will be independent, 
 * This class served as content selection during testing, as this was assigned to my project partner
 * It contains all types of messages that can be created in the content selection.
 * Every type of message != all types of message arguments
 * the 13 private functions == all types of messages
 * the 2 public functions == types of available message combinations
 */
public class MessageCollection {
    
    private static ArrayList<Message> messages = new ArrayList<>();

    public MessageCollection(){}

    //Message about users bank balance
    private static Message bankBalanceMessage(){
        LinkedHashMap<String, String> hashBalance = new LinkedHashMap<String, String>();
        hashBalance.put("value", "6040");
        Message balanceMessage = new Message("msg1", "currentAmount", hashBalance);
        return balanceMessage;
    }
    //Message about the period which the summary was created for
    private static Message budgetingPeriodMessage(){
        LinkedHashMap<String, String> hashPeriod = new LinkedHashMap<String, String>();
        hashPeriod.put("Date1", "15-07-2021");
        hashPeriod.put("Date2", "26-08-2021");
        Message period = new Message("msg6", "budgetPeriod", hashPeriod);
        return period;
        
    } 
    //Message about bank charges charged to user during this period
    private static Message bankChargesMessage(){
        LinkedHashMap<String, String> hashCharges = new LinkedHashMap<String, String>();
        hashCharges .put("value","25");
        Message charges = new Message("msg4", "bankCharges", hashCharges );
        return charges;
    }
    //Message about how user chose to distribute their budget in percentages
    private static Message budgetPercentageDistributionMessage(){
        LinkedHashMap<String, String> hashPercentages = new LinkedHashMap<String, String>();
        hashPercentages.put("category1Name","ukunethezeka");
        hashPercentages.put("category1Value","40");
        hashPercentages.put("category2Name","ukudingeka");
        hashPercentages.put("category2Value","55");
        hashPercentages.put("category3Name","wonga");
        hashPercentages.put("category3Value","5");
        Message percentage = new Message("msg7", "budgetPercentages", hashPercentages);
        return percentage;
    } 
    //Message about how user chose to distribute their budget in rands
    private static Message budgetValueDistributionMessage(){
        LinkedHashMap<String, String> hashPercentValues = new LinkedHashMap<String, String>();
        hashPercentValues.put("category1Name","ukunethezeka");
        hashPercentValues.put("category1Value","510");
        hashPercentValues.put("category2Name","ukudingeka");
        hashPercentValues.put("category2Value","1820");
        hashPercentValues.put("category3Name","wonga");
        hashPercentValues.put("category3Value","70");
        Message percentValues = new Message("msg8", "budgetValues", hashPercentValues);
        return percentValues;
    } 
    //Message about number of transactions in users bank statement
    private static Message numberOfTransactionsMessage(){
        LinkedHashMap<String, String> hashTransactions = new LinkedHashMap<String, String>();
        hashTransactions.put("value", "8");
        Message transactions = new Message("msg9", "numTransactions", hashTransactions);
        return transactions;
    } 
    //Message about the categories where user spent most of their funds
    private static Message mostSpentMessage(){
        LinkedHashMap<String, String> hashMostSpent = new LinkedHashMap<String, String>();
        hashMostSpent .put("subCategory1","ugesi");
        hashMostSpent .put("subCategory2","amakhonsathi-nemicimbi");
        hashMostSpent .put("subCategory3","izingubo");
        hashMostSpent .put("subCategory4","ukuqasha");
        Message mostSpent = new Message("msg5", "mostMoney", hashMostSpent );
        return mostSpent;
    }
    //Message about users adherence to spending budget: over budget
    private static Message overBudget(){
        LinkedHashMap<String, String> hashBudget = new LinkedHashMap<String, String>();
        hashBudget.put("status","OVER-BUDGET");
        hashBudget.put("category1Name","ukunethezeka");
        hashBudget.put("category1Value","7");
        hashBudget.put("category2Name","izidingo");
        hashBudget.put("category2Value","99");
        Message budget = new Message("msg2", "statusOfBudgets", hashBudget);
        return budget;
    }
    //Message about users adherence to spending budget: close to exceeding the budget
    private static Message closeToBudget(){
        LinkedHashMap<String, String> hashBudget = new LinkedHashMap<String, String>();
        hashBudget.put("status","CLOSE-TO-BUDGET");
        hashBudget.put("category1Name","ukudingeka");
        hashBudget.put("category1Value","402");
        hashBudget.put("category2Name","ukunethezeka");
        hashBudget.put("category2Value","1005");
        Message budget = new Message("msg2", "statusOfBudgets", hashBudget);
        return budget;
    }
    //Message about users adherence to spending budget: well below the budget
    private static Message NotOverBudget(){
        LinkedHashMap<String, String> hashBudget = new LinkedHashMap<String, String>();
        hashBudget.put("status","NOT-OVER-BUDGET");
        hashBudget.put("value","7230");
        Message budget = new Message("msg2", "statusOfBudgets", hashBudget);
        return budget;
    }
    //Message about users adherence to savings budget: over the savings target
    private static Message overSaving(){
        LinkedHashMap<String, String> hashSaving = new LinkedHashMap<String, String>();
        hashSaving.put("status","OVER-SAVING");
        hashSaving.put("value","101");
        Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        return saving;
    }
    //Message about users adherence to savings budget: reached savings target
    private static Message reachedSavingsGoal(){
        LinkedHashMap<String, String> hashSaving = new LinkedHashMap<String, String>();
        hashSaving.put("status","REACHED-SAVINGS-GOAL");
        Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        return saving;
    }
    //Message about users adherence to savings budget: below the savings target
    private static Message belowSavingsGoal(){
        LinkedHashMap<String, String> hashSaving = new LinkedHashMap<String, String>();
        hashSaving.put("status","NOT-REACHED-SAVINGS-GOAL");
        hashSaving.put("value","1320");
        Message saving = new Message("msg3", "statusOfSavingsBudgets", hashSaving);
        return saving;
    }

    /**
     * This function groups all of selected messages in the content selection 
     * Used to generate one summary instead of many
     */
    public static ArrayList<Message> getAllMessages(){
        messages.add(budgetingPeriodMessage());
        messages.add(bankChargesMessage());
        messages.add(numberOfTransactionsMessage());
        messages.add(mostSpentMessage());
        messages.add(budgetPercentageDistributionMessage());
        messages.add(budgetValueDistributionMessage());
        messages.add(overBudget());
        messages.add(belowSavingsGoal());
        return messages;
    }
     /**
     * This function gets a single message from the content selection 
     * used to generate an individual summary
     */
    public static Message getIndividualMessage(int number){
        if (number==1){
            return bankBalanceMessage();
        }
        else if(number==2){
            return budgetingPeriodMessage();
        }
        else if (number==3){
            return bankChargesMessage();
        }
        else if(number==4){
            return  budgetPercentageDistributionMessage();
        }
        else if (number==5){
            return budgetValueDistributionMessage();
        }
        else if(number==6){
            return numberOfTransactionsMessage();
        }
        else if (number==7){
            return mostSpentMessage();
        }
        else if (number==8){
            return NotOverBudget();   
        }
        else if (number==9){
            return closeToBudget();
        }
        else if (number==10){
            return overBudget();
        }
        else if (number==11){
            return belowSavingsGoal();   
        }
        else if (number==12){
            return reachedSavingsGoal();
        }
        else if (number==13){
            return overSaving();
        }
        else{
            //default message will be message about users bank balance
            return bankBalanceMessage();
        }
    }
}
