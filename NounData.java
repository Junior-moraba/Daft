
/**
 * Generic class of a noun
 * Each noun has morphemes associated to it by virtue of noun class
 * This class stores the noun and its associated information
 */
public class NounData{
    int nClass;
    String personalNoun;
    String prefix;
    boolean singularity;

    NounData(int nc, String prfx, String pNoun,boolean singular){
        nClass = nc;
        prefix = prfx;
        personalNoun = pNoun;
        singularity = singular;
    }
    NounData(int nc){
        nClass = nc;
    }

    public void setNClass(int nc){
        this.nClass = nc;
    }
    public int getNClass(){
        return nClass;
    }
    public void setSingular(boolean singular){
        this.singularity = singular;
    }
    public boolean getSingularity(){
        return singularity;
    }
    public void setPersonalNoun(String pNoun){
        this.personalNoun = pNoun;
    }
    public String getPersonalNoun(){
        return personalNoun;
    }
    public void setPrefix(String prfx){
        this.prefix = prfx;
    }
    public String getPrefix(){
        return prefix;
    }
}