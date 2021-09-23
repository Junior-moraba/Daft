import java.io.File;  
import java.io.FileInputStream;
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.util.ArrayList;
import java.util.Random;
/**
 * Responsible for getting templates from external source(excel file)
 * Stores the templates and makes them ready to be called by sentence planner
 */

public class Templates {
    /**
     * To avoid opening the excel file numerous times, all the templates will be stored in arraylist and retrievvd when needed
     * An array for each type of relation. Budget repeating as different templates exist for the two screens which convey
     * information about the message
     */
    static ArrayList<String> balanceTemplates = new ArrayList<String>();
    static ArrayList<String> budgetMetTemplates = new ArrayList<String>();
    static ArrayList<String> budgetOverTemplates = new ArrayList<String>();
    static ArrayList<String> budgetCloseTemplates = new ArrayList<String>();
    static ArrayList<String> savingsMetTemplates = new ArrayList<String>();
    static ArrayList<String> savingsOverTemplates = new ArrayList<String>();
    static ArrayList<String> savingsBelowTemplates = new ArrayList<String>();
    static ArrayList<String> chargesTemplates = new ArrayList<String>();
    static ArrayList<String> spentTemplates = new ArrayList<String>();
    static ArrayList<String> periodTemplates = new ArrayList<String>();
    static ArrayList<String> percentagesTemplates = new ArrayList<String>();
    static ArrayList<String> percentValuesTemplates = new ArrayList<String>();
    static ArrayList<String> noTransactionsTemplates = new ArrayList<String>();
    static ArrayList<String> categoryBudgetOver1Templates = new ArrayList<String>();
    static ArrayList<String> categoryBudgetOver2Templates = new ArrayList<String>();
    static ArrayList<String> categoryBudgetNotOverTemplates = new ArrayList<String>();


    public Templates(){
    }

    
    public static void getTemplates(){
        try{

            //Open excel file
            FileInputStream fileInputStream =new FileInputStream(new File(".//Templates.xlsx"));
            //creating Workbook
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);     //creating a Sheet object to retrieve object  
            
            
            
            //iterating over excel file by row
            Iterator<Row> rowIterator = sheet.rowIterator();    
            
            while (rowIterator.hasNext()){  
                Row row = rowIterator.next();  
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
                if (row.equals(null) || row.getRowNum()==0){ //ignore first row, contains headings
                    continue;
                }
                else{
                    //column by column in the current row
                    while (cellIterator.hasNext()){  
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();
                        
                        if(!cell.getStringCellValue().toString().isEmpty()){
                            if (cellIndex==0){
                                balanceTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==1){
                                budgetCloseTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==2){
                                budgetOverTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==3){
                                budgetMetTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==4){
                                savingsBelowTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==5){
                                savingsOverTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==6){
                                savingsMetTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==7){
                                chargesTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==8){
                                spentTemplates.add(cell.getStringCellValue());
                            }
                            else if (cellIndex==9){
                                periodTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==10){
                                percentagesTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==11){
                                percentValuesTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==12){
                                noTransactionsTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==13){
                                categoryBudgetOver1Templates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==14){
                                categoryBudgetOver2Templates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==15){
                                categoryBudgetNotOverTemplates.add(cell.getStringCellValue());
                            }
                            else{
                                continue;
                            }
                                    
                        }else{
                            continue;
                        }   
                    }
                }
            }            
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }
    }
    /**
     * Randomly selects an appropriate template
     * an appropriate template is a template that meets criteria such as message status, relatio
     * Random selection done to increase variety in messages produced
     */ 
    public static String selectAppropriateTemplate( String message){

        Random random = new Random();
        int randomInt = 0;
        String template="";
        
        if  (message.equals("currentAmount")){
            
            randomInt = random.nextInt(Templates.balanceTemplates.size());
            template = Templates.balanceTemplates.get(randomInt);
        }
        else if  (message.equals("bankCharges")){
            randomInt = random.nextInt(Templates.chargesTemplates.size());
            template = Templates.chargesTemplates.get(randomInt);
        }
        else if  (message.equals("mostMoney")){
            randomInt = random.nextInt(Templates.spentTemplates.size());
            template = Templates.spentTemplates.get(randomInt);
        }   
        else if  (message.equals("CLOSE-TO-BUDGET")){ //status
            randomInt = random.nextInt(Templates.budgetCloseTemplates.size());
            template = Templates.budgetCloseTemplates.get(randomInt);  
        }   
        else if  (message.equals("NOT-OVER-BUDGET")){//status
            randomInt = random.nextInt(Templates.budgetMetTemplates.size());
            template = Templates.budgetMetTemplates.get(randomInt);
        }   
        else if  (message.equals("OVER-BUDGET")){//status
            randomInt = random.nextInt(Templates.budgetOverTemplates.size());
            template = Templates.budgetOverTemplates.get(randomInt);
        }    
        else if  (message.equals("NOT-REACHED-SAVINGS-GOAL")){//status
            randomInt = random.nextInt(Templates.savingsBelowTemplates.size());
            template = Templates.savingsBelowTemplates.get(randomInt); 
        }    
        else if  (message.equals("OVER-SAVING")){//status
            randomInt = random.nextInt(Templates.savingsOverTemplates.size());
            template = Templates.savingsOverTemplates.get(randomInt); 
        }
        else if  (message.equals("REACHED-SAVINGS-GOAL")){//status
            randomInt = random.nextInt(Templates.savingsMetTemplates.size());
            template = Templates.savingsMetTemplates.get(randomInt);
        }
        else if (message.equals("budgetPeriod")){
            randomInt = random.nextInt(Templates.periodTemplates.size());
            template = Templates.periodTemplates.get(randomInt);
        }
        else if (message.equals("budgetPercentages")){
            randomInt = random.nextInt(Templates.percentagesTemplates.size());
            template = Templates.percentagesTemplates.get(randomInt);
        }
        else if (message.equals("budgetValues")){
            randomInt = random.nextInt(Templates.percentValuesTemplates.size());
            template = Templates.percentValuesTemplates.get(randomInt);
        }
        else if(message.equals("numTransactions")){
            randomInt = random.nextInt(Templates.noTransactionsTemplates.size());
            template = Templates.noTransactionsTemplates.get(randomInt);
        }
        else if(message.equals("OVER-BUDGET-CATEGORY-1")){ //status
            randomInt = random.nextInt(Templates.categoryBudgetOver1Templates.size());
            template = Templates.categoryBudgetOver1Templates.get(randomInt);
        }
        else if(message.equals("OVER-BUDGET-CATEGORY-2")){ //status
            randomInt = random.nextInt(Templates.categoryBudgetOver2Templates.size());
            template = Templates.categoryBudgetOver2Templates.get(randomInt);
        }
        else if(message.equals("NOT-OVER-BUDGET-CATEGORY")){ //status
            randomInt = random.nextInt(Templates.categoryBudgetNotOverTemplates.size());
            template = Templates.categoryBudgetNotOverTemplates.get(randomInt);
        }
        else 
        {
            template = ""; 
        }
        return template;
    }
    
    
}
