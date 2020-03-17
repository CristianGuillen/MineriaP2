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
    static ArrayList<String[]> combinations = new ArrayList<>();
    static ArrayList<String[]> supportedCombs = new ArrayList<>();
    //static ArrayList<String> lettersSelectedAndAmount= new ArrayList<String>();
    //static ArrayList<String> allElementSelected= new ArrayList<String>();
    //static ArrayList<String>  valuesOfLettersThatRealateSplitted = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        System.out.println("************** ALGORITMO A PRIORI PARA HACER ASOCIACIONES **************\n");
        System.out.println("Direccion del archivo:");
        ReadFileFunc();
        System.out.println("\nSoporte minimo: ");
        Scanner minimumSupport = new Scanner(System.in);
        float mSupportValue = minimumSupport.nextFloat();
        System.out.println("\nConfianza minima: ");
        Scanner minimumConf = new Scanner(System.in);
        float mConfValue = minimumConf.nextFloat();
        countLettersIntheFile(mSupportValue);
        String[] arreg;
        arreg = listToArray(letters);
        for(String aux : arreg) {
            System.out.println(aux);
        }
        int z =1;
        while(z<=5) //Esto solo funciona si es un valor constante en las iteraciones. Tenia que ser comparado con el msupportValue
        {
            printCombination(arreg,arreg.length-1,z);
            z++;
        }
        System.out.println(" maximo soporte "+maxim(countLettersInSplittedArray));
        for (String[] combs: combinations) {
            for (String ele: combs) {
                System.out.print(ele+" ");
            }
            System.out.printf("=> %d\n",combSupport(combs));
            if(combSupport(combs)>=mSupportValue)
                supportedCombs.add(combs);
        }
        System.out.println(supportedCombs.size());

        //Sacar Reglas
        ArrayList<Regla> reglasQueCumplen= new ArrayList<>();
        for (String[] rawSet: supportedCombs) {
            String[] realRaw = new String[rawSet.length];
            System.arraycopy(rawSet,0,realRaw,0,rawSet.length);

            if(realRaw.length>1 && combSupport(realRaw)>=mSupportValue){
                for(int k=1;k<=(rawSet.length)-1;k++){
                    printCombination(rawSet,rawSet.length-1,k);
                    for (String[] antecedente: combinations) {
                        ArrayList<String> itemset = new ArrayList<>(Arrays.asList(rawSet));
                        for(int l=0;l<antecedente.length;l++){
                            if(itemset.contains(antecedente[l]))
                                itemset.remove(antecedente[l]);
                        }
                        String[] itemsetArr = listToArray(itemset);
                        if(itemset.size()>0 && combSupport(antecedente)>0){
                            System.out.printf("ANT %s=>%d, CONS %s=>%d = %f\n", Arrays.toString(antecedente),combSupport(antecedente),Arrays.toString(itemsetArr),combSupport(rawSet),(float)combSupport(realRaw)/combSupport(antecedente));
                            Regla newRegla = new Regla(antecedente,itemsetArr,((float)combSupport(realRaw)/(float)combSupport(antecedente)));
                            if (newRegla.getConfianza()>=mConfValue && newRegla.getConfianza()<=1)
                                reglasQueCumplen.add(newRegla);
                        }
                    }
                }
            }
        }
        reglasQueCumplen.sort(new antSort());
        for(int pls=0;pls<reglasQueCumplen.size();pls++){
            boolean repetido=false;
            int pos=-1;
            for(int work=pls+1;work<reglasQueCumplen.size();pls++){
                if(Arrays.toString(reglasQueCumplen.get(pls).getAntecedente()).equalsIgnoreCase(Arrays.toString(reglasQueCumplen.get(work).getAntecedente())) && Arrays.toString(reglasQueCumplen.get(pls).getConsecuente()).equalsIgnoreCase(Arrays.toString(reglasQueCumplen.get(work).getConsecuente()))){
                    repetido=true;
                    pos=work;
                    //break;
                }
                if (repetido){
                    reglasQueCumplen.remove(pos);
                    pls--;
                }
            }
        }
        System.out.println(reglasQueCumplen.size());

        for (Regla regla:reglasQueCumplen) {
            System.out.printf("Antecedente: %s\n", Arrays.toString(regla.getAntecedente()));
            System.out.printf("Consecuente: %s\n", Arrays.toString(regla.getConsecuente()));
            System.out.printf("Confianza: %f\n", regla.getConfianza());
            System.out.println("-----------------------------------------------");
        }
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

    static void combinationUtil(String[] arr, String[] data,
                                int i, int index, int r)
    {

        // Current combination is ready to be printed, print it
        if (index == r)
        {
            String[] newD = new String[r];
            System.arraycopy(data,0,newD,0,r);
            combinations.add(newD);
            return;
        }
        if(i>=arr.length)
            return;

        data[index]=arr[i];
        combinationUtil(arr,data,i+1,index+1,r);
        combinationUtil(arr,data,i+1,index,r);


    }

    static void printCombination(String arr[], int n, int r)
    {

        String data[]=new String[r];
        combinationUtil(arr, data, 0,0,r);
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

    public static void countLettersIntheFile(float mSupportValue){

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

        }
        //System.out.println("Letras que cuentan:");
        //System.out.println(lettersThatRelate);

        //allElementSelected.addAll(lettersSelectedAndAmount);


    }

    public static int combSupport(String[] child){
        int sup=0;
        ArrayList<String> childlist= new ArrayList<String>(Arrays.asList(child));
        for(int i=0;i<arrayOfTransactions.size();i++){
            ArrayList<String> father= new ArrayList<String>(Arrays.asList(arrayOfTransactions.get(i).split(",")));
            if(father.containsAll(childlist)){
                sup++;
            }
        }
        return sup;
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

}

class antSort implements Comparator<Regla>
{
    @Override
    public int compare(Regla o1, Regla o2) {
        return Arrays.toString(o2.getAntecedente()).compareToIgnoreCase(Arrays.toString(o1.getAntecedente()));
    }
}