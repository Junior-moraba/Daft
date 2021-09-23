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
            System.out.println("Enter number type \n(E) Cardinal as an epithet \n(P) Cardinal as a predicate \n(O) Ordinal number ");
            String textType = userInput.nextLine().toLowerCase();

            NumberContextControl nc2 = new NumberContextControl(numberInput,nounInput,textType);
            System.out.println(nc2.verbalise("G"));
        }
        else{
            NumberContextControl nc1 = new NumberContextControl(numberInput);
            System.out.println(nc1.verbalise("G"));
        }
        userInput.close();

    }
}
