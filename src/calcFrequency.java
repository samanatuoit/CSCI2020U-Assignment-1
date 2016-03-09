import java.util.*;
import java.io.*;

public class calcFrequency {

   // protected TreeMap<String, Integer> tm = new TreeMap<>();
    protected File mainDirectory;
    //private ArrayList<String> fNames = new ArrayList<>();
    protected int numOfFiles = 0;

    protected int getNumOfFiles() {
        return numOfFiles;
    }


    public calcFrequency(File mainDirectory) {
        this.mainDirectory = mainDirectory;
    }
    private void getFileNames(File mainDirectory) {
        // Probably won't need this method
        // Puts list of all filenames into ArrayList fNames
        File[] listFiles = mainDirectory.listFiles();
        /*for (File fileEntry : listFiles) {
            fNames.add(fileEntry.getName());
        }*/
    }
    protected TreeMap Trainer() {
        /*

        Read all the contents of a file into tokens of words that are put into the array 'tokens'.
        Iterate through the array and check the TreeMap 'tm' to see if the word (the key) does not exist
        in the TreeMap. If it doesn't exist,  make a new entry and assign the key the word and it's value to be 1.

        */
        TreeMap<String, Integer> tm = new TreeMap<>();
        //getFileNames(inFile);
        File[] listFiles = mainDirectory.listFiles();
        numOfFiles += listFiles.length;

        for (File fileName : listFiles) {
        //for (String fileName : fNames) {


            // Temporarily set to a fixed file
            //File inFile = new File("C:\\Users\\Saman\\Desktop\\SoftDev\\Assign1\\out\\production\\Assign1\\00001.7c53336b37003a9286aba55d2945844c");
            //TreeMap<String, Integer> tm = new TreeMap<>();


            try {
                BufferedReader in = new BufferedReader(new FileReader(fileName));
                String line;

                while ((line = in.readLine()) != null) {

                    // Split up all words in the file into tokens
                    String[] tokens = line.split("\\W+");


                    for (String token : tokens) {

                        // Check if word already exists: If it does, increment its value by 1.
                        // If it doesn't exist, create a new entry with a value of 1.
                        if (tm.containsKey(token)) {
                            tm.put(token, tm.get(token) + 1);
                        } else {
                            tm.put(token, 1);
                        }
                    }

                }



            } catch (IOException e) {
                e.printStackTrace();

            }

        }
        return tm;
    }
    protected TreeMap Tester(TreeMap<String, Double> wordSpamChanceMap) {
        TreeMap<String, Double> spamChance = new TreeMap<>();
        File[] listFiles = mainDirectory.listFiles();
        ArrayList<Double> etaList = new ArrayList<>();
        Double eta = 0.0;

        for (File fileName : listFiles) {

            try {
                BufferedReader in = new BufferedReader(new FileReader(fileName));
                String line;

                while ((line= in.readLine()) != null) {
                    String[] tokens = line.split("\\W+");
                    for (String token : tokens) {
                           etaList.add((Math.log(1-wordSpamChanceMap.get(tokens))-Math.log(wordSpamChanceMap.get(tokens ))));

                    }






                    //spamChance.put(spamProbabilityMap.get(tokens), 5.0);
                }
                for (int i = 0; i < etaList.size(); i++) {
                    eta += etaList.get(i);
                }

                Double chanceWordIsSpam = 1 / (1 + Math.pow(Math.E, eta));

                String outFile = fileName.getName();
                spamChance.put(outFile,chanceWordIsSpam);
                return spamChance;







            } catch (IOException e) {
                e.printStackTrace();
            }


        }



        return spamChance;

    }
    protected TreeMap getChanceWordIsSpam(TreeMap<String, Double> hamProbabilityMap, TreeMap<String, Double> spamProbabilityMap) {
        // Spam word probability map- Pr(S|Wi)

        TreeMap<String, Double> wordSpamChanceMap = new TreeMap<>();

        for (String entry : spamProbabilityMap.keySet()) {
            // Check to see if the word in spamProbabilityMap also exists in hamProbabilityMap so we can do the calculation.
            if (hamProbabilityMap.containsKey(entry)) {
                wordSpamChanceMap.put(entry, (spamProbabilityMap.get(entry) / (spamProbabilityMap.get(entry) + hamProbabilityMap.get(entry))));
            }
            // If the word does not have a corresponding entry in hamProbabilityMap then the chance the word is spam is
            // 1.0 (100%)
            else {
                wordSpamChanceMap.put(entry, 1.0);
            }
        }

        // For words that only exist in hamProbabilityMap and not spamProbabilityMap, then the chance the message is spam
        // is 0.0 (0%)
        for (String entry : hamProbabilityMap.keySet()) {
            if (!spamProbabilityMap.containsKey(entry)) {
                wordSpamChanceMap.put(entry, 0.0);
            }
        }

        return wordSpamChanceMap;

    }





}
