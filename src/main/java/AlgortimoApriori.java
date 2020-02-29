import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class AlgortimoApriori {

//C:\Users\CristianGuillen\Desktop\Archivos\MD.txt

    static ArrayList<String> arrayOfTransactions  = new ArrayList<String>();
    static ArrayList<String> arrayOfTransactionsSplitted  = new ArrayList<String>();
    static ArrayList<Integer> countLettersInSplittedArray = new ArrayList<Integer>();
    static ArrayList<String> letters = new ArrayList<String>();
    static ArrayList<String> lettersThatRelate = new ArrayList<String>();
    static ArrayList<String> lettersSelectedAndAmount= new ArrayList<String>();
    static ArrayList<String> allElementSelected= new ArrayList<String>();
    static ArrayList<String>  valuesOfLettersThatRealateSplitted = new ArrayList<>();
    int SaveValueIni=0;
    public static void main(String[] args) throws IOException {

        System.out.println("************** ALGORITMO A PRIORI PARA HACER ASOCIACIONES **************\n");
        System.out.println("Direccion del archivo:");
        ReadFileFunc();
        countLettersIntheFile();

        String[] arreg;
        arreg =  listToArray(letters);
        for(String aux : arreg) {
            System.out.println(aux);
        }
        int z =1;
        while(z<=5)
        {
            printCombination(arreg,letters.size()-1,z);// r es el numero de elementos que tendra la combinacion
            z++;
        }




        System.out.println(" maximo soporte "+maxim(countLettersInSplittedArray));
        // buscarLetra("a",arrayOfTransactionsSplitted,countLettersInSplittedArray);
    }
    public static String[] listToArray(ArrayList<String> arr)
    {
        String[] arr2= new String[arr.size()];
        for(int i =0;i<arr.size();i++)
        {
            arr2[i]=arr.get(i);
        }
        return arr2;

    }

    static void combinationUtil(String[] arr, String[] data, int start,
                                int end, int index, int r)
    {

        // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++)
                System.out.print(data[j]+" ");
            System.out.println("");
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);

            while (arr[i] == arr[i+1])
                i++;
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static void printCombination(String arr[], int n, int r)
    {
        // A temporary array to store all combination one by one
        String data[]=new String[r];

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r);
    }




    public static void ReadFileFunc() throws IOException {
        String text;
        BufferedReader bufferReadFile = null;
        Scanner reader = new Scanner(System.in);
        String result = reader.nextLine();
        FileReader readFile = new FileReader(result);
        bufferReadFile = new BufferedReader(readFile);
        int i=1;
        while ((text = bufferReadFile.readLine()) != null){

            arrayOfTransactions.add(text);

        }
        for (String splitt: arrayOfTransactions){
            for(String splitt2 : splitt.split(",")){
                arrayOfTransactionsSplitted.add(splitt2);

            }
        }


        // for (int j = 0; j < arrayOfTransactionsSplitted.size() ; j++) {
        //System.out.println("#" +i ++ +" "+ arrayOfTransactionsSplitted.get(j));
        // }


        //System.out.println(arrayOfTransactionsSplitted);


    }

    public static void buscarLetra(String buscar, ArrayList<String> letras,ArrayList<Integer>cant)
    {
        for (int i = 0; i<letras.size();i++)
        {
            if(buscar.equalsIgnoreCase(letras.get(i)))
            {
                System.out.println("La letra "+buscar+ " tiene "+cant.get(i)+ " repeticiones");
                return;
            }
         /* else {
              System.out.println("NO SE ENCONTRO LA LETRA");
              return;
          }*/
        }
    }


    public static int maxim(ArrayList<Integer> values)
    {
        int maxValue;
        int index=-1;
        maxValue= values.get(0);
        for(int i =0;i<values.size();i++)
        {

            if(values.get(i)>maxValue)
            {
                maxValue=values.get(i);
                // index=i;
            }
        }
        // return index;
        return maxValue;
    }


    public static void countLettersIntheFile(){
        System.out.println("\nSoporte minimo: ");
        Scanner minimumSupport = new Scanner(System.in);
        float mSupportValue = minimumSupport.nextFloat();
        //Scanner highConfidence = new Scanner(System.in);
        //float hcValue = highConfidence.nextFloat();
        //System.out.println("\n"+hcValue);
        String aux;
        int k=0;
        for(int i=0; i< arrayOfTransactionsSplitted.size() ;i++ ){
            int counter=0;
            for(int j = 0; j<arrayOfTransactionsSplitted.size(); j++){
                aux = arrayOfTransactionsSplitted.get(i);

                if(aux.equalsIgnoreCase(arrayOfTransactionsSplitted.get(j))){
                    counter++;
                }
                //for(int x = 0; x<arrayOfTransactionsSplitted.size();x++){

                if(letters.isEmpty() || !letters.contains(aux)){
                    letters.add(aux);
                    // countLettersInSplittedArray.add(counter);
                    // counter=0;
                }

            }

            //lettersSelectedAndAmount.add(arrayOfTransactions.get(i)+""+counter);
           // System.out.println("#"+k++ +" "+arrayOfTransactionsSplitted.get(i)+"=> "+ counter);
            countLettersInSplittedArray.add(counter);

            if(counter>= mSupportValue){
                lettersThatRelate.add(arrayOfTransactionsSplitted.get(i));

            }
          //  System.out.println("ELements of values that relate splitted: \n"+valuesOfLettersThatRealateSplitted);

            //Choose the greater of the repetitions in the letters
            /*
            for(String lettersThatRelateSplitted : lettersThatRelate) {
                for(String lettersThatRelateSplitted2 : lettersThatRelateSplitted.split(",")) {
                    valuesOfLettersThatRealteSplitted.add(lettersThatRelateSplitted2);
                }

            }
            */
        }
        System.out.println("Letras que cuentan:");
        System.out.println(lettersThatRelate);

        //allElementSelected.addAll(lettersSelectedAndAmount);


    }

}
