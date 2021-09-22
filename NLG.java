import java.util.ArrayList;
public class NLG {
    static SentencePlanning sentencePlanning = new SentencePlanning();
    static LinguisticRealisation linguisticRealiser = new LinguisticRealisation();

    /**
     * To be deleted, only for demonstration and debugging purposes.
     */
    public static void main(String[] args){

        String generatedText ="";
        ArrayList<Message> messages = MessageCollection.getMessages();
        for (int position=0;position<messages.size();position++){ 
            SentencePlan sentencePlan = sentencePlanning.getSentencePlan(messages.get(position));
            generatedText = linguisticRealiser.getGeneratedText(sentencePlan);
        }
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println(generatedText);
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        
    }

    //message is from stage 1 of the pipeline, conducted by my partner amy solomons
    public static String generateSummary(Message message){
        
        SentencePlan sentencePlan = sentencePlanning.getSentencePlan(message); //stage 2 of the pipeline
        String generatedText = linguisticRealiser.getGeneratedText(sentencePlan); //stage 3 of the pipeline

        return generatedText;
        
    }
    
}
