import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SentencePlanning {
    static ArrayList<String> messageStatus = new ArrayList<String>();
    static ArrayList<String> messageValues = new ArrayList<String>();    
    private static Templates templates = new Templates(); //reduce number of calls made to this class.
    
    
    
    public SentencePlanning(){
        //call method that gets templates
        templates.getTemplates();
    }

    public SentencePlan getSentencePlan(Message message){
        
        /**
         * We are going to seperate message arguments into two:
         * arguments for filling slots
         * arguments for not filling slots -- useful for identifying template
         * 
         */ 
        Map<String,String> slotFillers = new HashMap<String,String>();
        LinkedHashMap<String,String> argumentsHashMap = message.getArguments(); //message arguments used to fill slots
       
        //used to get the right template, if message contains status use that instead of relation
        String relationStatus = message.getRelation(); 
        for (Map.Entry<String,String> argument : argumentsHashMap.entrySet()){
             
            //argument for not filling slot
            if (argument.getKey().equals("status")){
                relationStatus = argument.getValue();
            }
            else{
                slotFillers.put(argument.getKey(), argument.getValue()); //arguments used to fill slots
            }
        }
        String template = templates.selectAppropriateTemplate(relationStatus); //select the appropriate template
        SentencePlan sentencePlan = new SentencePlan(template, slotFillers);  //create sentence plan. template + arguments for filling template slot
        return sentencePlan;
    }

}

