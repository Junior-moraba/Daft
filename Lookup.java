import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.util.ArrayList; // import the ArrayList class
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.text.StringSubstitutor;

public class Lookup {
    
    
    public static void main(String args[]){
        ArrayList<String> legend = new ArrayList<String>();
        ArrayList<String> templates = new ArrayList<String>();
        try{
            FileInputStream fileInputStream =new FileInputStream(new File("/Users/vhulendamoraba/Desktop/Course/Project/DAFT/code/templates.xlsx"));
            //creating Workbook instanumContextControle that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file 
            int rowNumber = 0;
            while (itr.hasNext()){  
                Row row = itr.next();  
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
                while (cellIterator.hasNext()){  
                    Cell cell = cellIterator.next();
                    if (rowNumber==0){
                        legend.add(cell.getStringCellValue());
                    }
                    else{
                        templates.add(cell.getStringCellValue());
                    }                     
                }  
                rowNumber++;
            }  
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }
        testMessages tmessages = new testMessages();
        ArrayList<Message> messages = tmessages.getMessages();

        String current, budget, savings,charges,spent = "";
        for (int i=0;i<messages.size();i++){
            Message message = messages.get(i);
            LinkedHashMap<String,String> hashMap = message.getArguments();
            String status = "";
            String statusValue ="";
           

            /**There must be a better way of doing this */
            Set set = hashMap.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()){
                Map.Entry me = (Map.Entry)iterator.next();
                
                status = me.getKey().toString();
                statusValue = me.getValue().toString();
            }    
            
            System.out.println(message.getRelation());
            if (message.getRelation().equals("currentAmount")){
                String noun = "amarandi"; //un, ignored (explain why in paper)
                current = replaceText(templates.get(0),noun,statusValue);
                System.out.println(current);
            }
            else if (message.getRelation().equals("statusOfBudgets")){
                if (status.equals("CLOSE-TO-BUDGET")){
                    String noun = "amarandi";
                    budget = replaceText2(templates.get(1),noun,statusValue);


                }
                else if (status.equals("OVER-BUDGET")){
                    String noun = "amarandi";
                    budget = replaceText3(templates.get(2),noun,statusValue);
                }
                else if (status.equals("NOT-OVER-BUDGET")){
                    budget = templates.get(3);
                    //nothing special required here, can print "template" as is
                }
                else{
                    budget = "unknown";
                }
                System.out.println(budget);
            }
            else if (message.getRelation().equals("statusOfSavingsBudgets")){
                if (status.equals("REACHED-SAVINGS-GOAL")){
                    savings = templates.get(4);
                    //nothing special required here, can print "template" as is
                }
                else if (status.equals("OVER-SAVING")){
                    String noun = "Amarandi";
                    savings = replaceText(templates.get(5),noun,statusValue);
                }
                else if (status.equals("NOT-REACHED-SAVINGS-GOAL")){
                    String noun = "Amarandi";
                    savings = replaceText(templates.get(6),noun,statusValue);
                }
                else{
                    savings = "unknown";
                }
                System.out.println(savings);
            }
            else if (message.getRelation().equals("bankCharges")){
                String noun = "Amarandi";
                charges = replaceText(templates.get(7),noun,statusValue);
                System.out.println(charges);
            }
            else if (message.getRelation().equals("mostMoney")){
                spent = templates.get(8);
                System.out.println(spent);
            }
            else{

            }
            
        }
    }
    public static String replaceText(String template, String noun, String statusValue){
        NumberContextControl numContextControl;
        Map<String, String> valuesMap = new HashMap<String, String>();

        
        //scenario where statusvalue = category:value, category:value
        int currentAmount = Integer.parseInt(statusValue);
        numContextControl = new NumberContextControl(currentAmount,noun,"e");

        valuesMap.put("Amount", numContextControl.verbalise());
        StringSubstitutor sub = new  StringSubstitutor(valuesMap);
        String resolvedString = sub.replace(template);
        
        return resolvedString;
    } 
    public static String replaceText2 (String template,String noun ,String statusValue){
        NumberContextControl numContextControl;
        Map<String, String> valuesMap = new HashMap<String, String>();
        
        //scenario where statusvalue = category:value, category:value
        String category = statusValue.split(":")[0];
        NounClassifier nounclassifier = new NounClassifier(category);
        NounData nounData = nounclassifier.getNounData();

        int currentAmount = Integer.parseInt(statusValue.split(":")[1]);
        numContextControl = new NumberContextControl(currentAmount,noun,"e");

        valuesMap.put("Amount",numContextControl.verbalise());
        valuesMap.put("Category", category);
        valuesMap.put("dlule", nounData.getPersonalNoun()+"dlule");
        StringSubstitutor sub = new  StringSubstitutor(valuesMap);
        String resolvedString = sub.replace(template);
        
        return resolvedString;
    } 
    public static String replaceText3 (String template,String noun ,String statusValue){
        NumberContextControl numContextControl;
        Map<String, String> valuesMap = new HashMap<String, String>();
        
        //scenario where statusvalue = category:value, category:value
        String needs = statusValue.split(",")[0];
        String luxuries = statusValue.split(",")[1];


        int needsCurrentAmount = Integer.parseInt(needs.split(":")[1]);
        int luxuriesCurrentAmount = Integer.parseInt(luxuries.split(":")[1]);
        numContextControl = new NumberContextControl(needsCurrentAmount,noun,"e");
        valuesMap.put("Amount",numContextControl.verbalise());
        numContextControl = new NumberContextControl(luxuriesCurrentAmount,noun,"e");
        valuesMap.put("Amount2",numContextControl.verbalise());
        StringSubstitutor sub = new  StringSubstitutor(valuesMap);
        String resolvedString = sub.replace(template);
        
        return resolvedString;
    } 
}  