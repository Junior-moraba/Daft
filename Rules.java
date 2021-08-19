import java.util.ArrayList;

public class Rules {
    
    static NumberContextControl noContextControl;
    static NounClassifier nounclassifier;
    public static String rule1(int value, String noun, String nounType){
        if (value!=0){
            noContextControl = new NumberContextControl(value,noun,nounType);
            String inContextNumber = noContextControl.verbalise(); 
            return inContextNumber;
        }
        else{
            return "";
        }
    }

    public static String rule2(String prefix,String category){
        if (!category.equals("")){
            char categoryRP = new RelativePronoun(category).getRelativePronoun();
            String inContextPrefix = prefix.substring(0,prefix.length()-1) + categoryRP;

            return inContextPrefix;
        }
        else{
            return "";
        }
        //this function could return an arraylist, items being prefix and the concatinated category
    }
    public static String Rule3(String category,String immediateAdjective){
        if (!category.equals("")){
            nounclassifier = new NounClassifier(category);
            NounData nounData = nounclassifier.getNounData();
            
            String inContextAdjective = nounData.getPersonalNoun()+immediateAdjective;
            return inContextAdjective;
        }
        else{
            return "";
        }
    }
    public static String Rule4(String category){
        return category.substring(1);
    }


}
