import java.util.ArrayList;

public class Rules {
    
    static NumberContextControl noContextControl;
    static NounClassifier nounclassifier;
    //Rule for verbalising numbers, using number verbalising algorithms
    public static String rule1(int value, String noun, String nounType){
        if (value!=0){
            noContextControl = new NumberContextControl(value,noun,nounType);
            String inContextNumber = noContextControl.verbalise("R"); 
            return inContextNumber;
        }
        else{
            return "";
        }
    }
    //Rule for insuring agreement between the prefix and the category
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
    //Rule for when a category is not immediately qualified
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
    //Rule for ensuring that the correct version of necessities (in isiZulu) is used
    public static ArrayList<String> Rule4(String prefix,String category){
        ArrayList<String> prefixNcategory = new ArrayList<String>();
        // this rule should be responsible for choosing whether to use 
        // izidingo or ukudingeka depending on the prefix

        if (prefix.equals("za")){
            //use ukudingeka
            category = "ukudingeka";
        }
        else if(prefix.equals("ka")){
            //use izidingo
            category = "izidingo";
        }
        char categoryRP = new RelativePronoun(category).getRelativePronoun();
        String inContextPrefix = prefix.substring(0,prefix.length()-1) + categoryRP;
            
        prefixNcategory.add(inContextPrefix);
        prefixNcategory.add(category.substring(1));

        return prefixNcategory;
    }



}
