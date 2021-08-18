public class Rules {
    
    static NumberContextControl noContextControl;
    static NounClassifier nounclassifier;
    public static String rule1(int value, String noun){
        noContextControl = new NumberContextControl(value,noun,"e");
        String inContextNumber = noContextControl.verbalise(); 
        return inContextNumber;       
    }

    public static String rule2(String prefix,String category){
        char categoryRP = new RelativePronoun(category).getRelativePronoun();
        String inContextPrefix = prefix.substring(0,prefix.length()-1) + categoryRP;

        return inContextPrefix;
    }
    public static String Rule3(String category,String immediateAdjective){
        nounclassifier = new NounClassifier(category);
        NounData nounData = nounclassifier.getNounData();
        
        String inContextAdjective = nounData.getPersonalNoun()+immediateAdjective;
        return inContextAdjective;
    }

}
