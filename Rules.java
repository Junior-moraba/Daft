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

    public static ArrayList<String> rule2(String prefix,String category){
        ArrayList<String> prefixNcategory = new ArrayList<String>();
        if (!category.equals("")){
            char categoryRP = new RelativePronoun(category).getRelativePronoun();
            String inContextPrefix = prefix.substring(0,prefix.length()-1) + categoryRP;
            
            prefixNcategory.add(inContextPrefix);
            prefixNcategory.add(category.substring(1));
            
            return prefixNcategory;
        }
        else{
            return prefixNcategory;
        }
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
    // public static String Rule4(String category){
        // this rule should be responsible for choosing whether to use 
        // izidingo or ukudingeka depending on the prefix
    // }


}
