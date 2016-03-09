import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.DecimalFormat;
import java.util.TreeMap;

public class Assign1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        //File mainDirectory = directoryChooser.showDialog(primaryStage);
        //System.out.println(mainDirectory);
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(new Scene(root, 850, 500));
        primaryStage.show();

        int numOfHamFiles = 0;
        int numOfSpamFiles = 0;

        // Nice formatting for percentage values
        DecimalFormat df = new DecimalFormat("#.000000");


        // ham frequency map - Words (key) and how many ham messages (value) they appear in
        TreeMap<String, Integer> trainHamFreq;
        // spam frequency map - Words (key) and how many spam messages (value) they appear in
        TreeMap<String, Integer> trainSpamFreq;

        // ham probability map- Words (key) mapped to how many number of messages they were found in
        // divided by the number of ham messages (value)
        // Pr(Wi|H)
        TreeMap<String, Double> hamProbabilityMap = new TreeMap<>();

        // spam probability map- Words (key) mapped to how many number of messages they were found in
        // divided by the number of spam messages (value)
        // Pr(Wi|S)
        TreeMap<String, Double> spamProbabilityMap = new TreeMap<>();

        // Spam word probability map- Pr(S|Wi)
        TreeMap<String, Double> wordSpamChanceMap;

        // Pr(S|F)
        TreeMap<String, Double> chanceFileIsSpam = new TreeMap<>();

        File mainDirectory = directoryChooser.showDialog(primaryStage);
        /* List subdirectories in mainDirectory
        if it is train, go into traom
        List subdirectories
        If ham, to to ham



        */
        //ArrayList<File> = mainDirectory




        File hamDirectory = mainDirectory;
        calcFrequency calc = new calcFrequency(hamDirectory);

        trainHamFreq = calc.Trainer();
        numOfHamFiles += calc.getNumOfFiles();


        mainDirectory = directoryChooser.showDialog(primaryStage);
        File spamDirectory = mainDirectory;
        calcFrequency calcSpam = new calcFrequency(spamDirectory);
        trainSpamFreq = calcSpam.Trainer();
        numOfSpamFiles += calcSpam.getNumOfFiles();

        // Calculate ham and spam probability map
        for (String entry : trainHamFreq.keySet()) {
            hamProbabilityMap.put(entry, ((trainHamFreq.get(entry) / (double)numOfHamFiles)));
        }
        /*System.out.println("--- Ham Probability Map --- ");
        for (String entry : hamProbabilityMap.keySet()) {
            System.out.println(df.format(hamProbabilityMap.get(entry)));
        }*/

        for (String entry : trainSpamFreq.keySet()) {
            spamProbabilityMap.put(entry, ((trainSpamFreq.get(entry) / (double)numOfSpamFiles)));
        }

        wordSpamChanceMap = calcSpam.getChanceWordIsSpam(hamProbabilityMap, spamProbabilityMap);

        // Calculate probability if word is spam
        mainDirectory = directoryChooser.showDialog(primaryStage);
        File hamDirectoryTest = mainDirectory;
        mainDirectory = directoryChooser.showDialog(primaryStage);
        File spamDirectoryTest = mainDirectory;



        calcFrequency calcHamTest = new calcFrequency(hamDirectoryTest);
        calcFrequency calcSpamTest = new calcFrequency(spamDirectoryTest);


        chanceFileIsSpam = calcHamTest.Tester(wordSpamChanceMap);












        // Output for testing purposes
        for (String entry : wordSpamChanceMap.keySet()) {
            System.out.println(wordSpamChanceMap.get(entry));
        }


        /*System.out.println("--- Spam Probability Map --- ");
        for (String entry : spamProbabilityMap.keySet()) {
            //System.out.println(df.format(spamProbabilityMap.get(entry)));
            System.out.println(spamProbabilityMap.get(entry));
        }*/

        //System.out.println(trainHamFreq.values());
        //System.out.println(trainHamFreq.descendingMap());
        System.out.println("------");
        System.out.println("numOfHamFiles = " + numOfHamFiles);
        System.out.println("numOfSpamFiles = " + numOfSpamFiles);


        //System.out.println(trainHamFreq.keySet());
        //TreeMap<String, Integer> trainSpamFreq = calc.spamTrainer();




    }
}
