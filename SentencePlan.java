import java.util.Map;

/**
 * Generic class of a sentence plan 
 * Remember sentence plan == template + message arguments
 */
public class SentencePlan {
    
    private String template;
    private Map<String,String> slotFillers;
    public SentencePlan(String tem_plate, Map<String,String> slot_fillers){
        this.template = tem_plate;
        this.slotFillers =slot_fillers;
    }

    public void setTemplate(String tem_plate){
        this.template = tem_plate;
    }
    public String getTemplate(){return template;}

    public void setSlotFillers(Map<String,String> slot_fillers){
        this.slotFillers =slot_fillers;
    }
    public Map<String,String> getSlotFillers(){return slotFillers;}
    
}


