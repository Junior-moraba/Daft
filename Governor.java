import java.util.Scanner;
public class Governor {
    //main class
    public static void main(String[] args){

        /**
         * For demonstration purposes only
         */
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int numberInput = userInput.nextInt();
        userInput.nextLine();
        System.out.println("In context or not (Y or N): ");
        String context = userInput.nextLine().toLowerCase();
        if (context.equals("y")){
            System.out.println("Enter a noun: ");
            String nounInput = userInput.nextLine();
            System.out.println("Enter Texttype (E, P or O): ");
            String textType = userInput.nextLine().toLowerCase();

            NumberContextControl nc2 = new NumberContextControl(numberInput,nounInput,textType);
            System.out.println(nc2.verbalise());
        }
        else{
            NumberContextControl nc1 = new NumberContextControl(numberInput);
            System.out.println(nc1.verbalise());
        }
        userInput.close();

    }
}
