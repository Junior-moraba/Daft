import java.util.ArrayList;
import java.util.Scanner;

public class NLG {
    static SentencePlanning sentencePlanning = new SentencePlanning();
    static LinguisticRealisation linguisticRealiser = new LinguisticRealisation();

    /**
     * Main method for the purpose of demonstration/testing NLG outside of mobile application
     */
    public static void main(String[] args){
        String generatedText ="";
        SentencePlan sentencePlan;

        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter (1) for entire summary (2) individual summary");
        int numberInput = userInput.nextInt();
        userInput.nextLine();
        if (numberInput==1){
            
            ArrayList<Message> messages = MessageCollection.getAllMessages();
            for (int position=0;position<messages.size();position++){ 
                sentencePlan = sentencePlanning.getSentencePlan(messages.get(position));
                generatedText = generatedText +" "+linguisticRealiser.getGeneratedText(sentencePlan);
            }
        }
        else
        {
            String secondQuestion = "choose one of the following summaries"+ 
                                "\n(1) bank balance summary"+
                                "\n(2) budgetting period summary"+
                                "\n(3) bank charges summary"+
                                "\n(4) budget distribution by percentage summary"+
                                "\n(5) budget distribution by value summary"+
                                "\n(6) number of transactions summary"+
                                "\n(7) categories most spent summary"+
                                "\n(8) below budget summary"+
                                "\n(9) close to exceeding budget summary"+
                                "\n(10) exceeded the budget summary"+
                                "\n(11) below savings target summary"+
                                "\n(12) reached savings target summary"+
                                "\n(13) above savings target summary";
    
            System.out.println(secondQuestion);
            numberInput = userInput.nextInt();
            userInput.nextLine();
            sentencePlan = sentencePlanning.getSentencePlan(MessageCollection.getIndividualMessage(numberInput));
            generatedText = linguisticRealiser.getGeneratedText(sentencePlan);
        }        
        userInput.close();
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println(generatedText);
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        
    }
    /**
     * Not for demonstration as above
     * This function is used when the entire pipeline is working, 
     * Required paremeter message
     * message is from stage 1 of the pipeline, conducted by my partner amy solomons
     * will use the message to get a sentence plan
     * user sentence plan to get text from the lingusitic realiser
     * returns generated text
     */
    //
    public static String generateSummary(Message message){
        
        SentencePlan sentencePlan = sentencePlanning.getSentencePlan(message); //stage 2 of the pipeline
        String generatedText = linguisticRealiser.getGeneratedText(sentencePlan); //stage 3 of the pipeline

        return generatedText;
        
    }
    
}
