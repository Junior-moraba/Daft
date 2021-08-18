import java.io.File;  
import java.io.FileInputStream;
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.util.ArrayList;

public class Templates {
    static ArrayList<String> balanceTemplates = new ArrayList<String>();
    static ArrayList<String> budgetMetTemplates = new ArrayList<String>();
    static ArrayList<String> budgetOverTemplates = new ArrayList<String>();
    static ArrayList<String> budgetCloseTemplates = new ArrayList<String>();
    static ArrayList<String> savingsMetTemplates = new ArrayList<String>();
    static ArrayList<String> savingsOverTemplates = new ArrayList<String>();
    static ArrayList<String> savingsBelowTemplates = new ArrayList<String>();
    static ArrayList<String> chargesTemplates = new ArrayList<String>();
    static ArrayList<String> spentTemplates = new ArrayList<String>();

    public Templates(){
        getTemplates();
    }

    
    public static void getTemplates(){
        try{

            //Open excel file
            FileInputStream fileInputStream =new FileInputStream(new File(".//templates.xlsx"));
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
                                savingsMetTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==5){
                                savingsOverTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==6){
                                savingsBelowTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==7){
                                chargesTemplates.add(cell.getStringCellValue());
                            }
                            else if(cellIndex==8){
                                spentTemplates.add(cell.getStringCellValue());
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
    // public static  selectAppropriateTemplates(ArrayList<String> messages){

    // }
    
}
