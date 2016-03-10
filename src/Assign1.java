import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.activation.DataSource;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Assign1 extends Application {


    private TableView<TestFile> table;
    //private ObservableList<TestFile> data;
    protected ArrayList<TestFile> myTestFiles;

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


        //System.out.println("ArrayList size = :" + myTestFiles.size());

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


        /*for (String entry : wordSpamChanceMap.keySet()) {
            System.out.println(wordSpamChanceMap.get(entry));
            System.out.println("Calculation : " + (Math.log(1-wordSpamChanceMap.get(entry))-Math.log(wordSpamChanceMap.get(entry))));
        }*/

        // Calculate probability if word is spam

        File hamDirectoryTest = directoryChooser.showDialog(primaryStage);
        File spamDirectoryTest = directoryChooser.showDialog(primaryStage);

        //Hard coded directories
        //File hamDirectoryTest = new File("C:\\Users\\Saman\\Desktop\\SoftDev\\data\\test\\ham");
        //File spamDirectoryTest = new File("C:\\Users\\Saman\\Desktop\\SoftDev\\data\\test\\spam");




        calcFrequency calcHamTest = new calcFrequency(hamDirectoryTest);
        calcFrequency calcSpamTest = new calcFrequency(spamDirectoryTest);


        //ArrayList<TestFile> myTestFiles;
        //chanceFileIsSpam = calcHamTest.Tester(wordSpamChanceMap);
        myTestFiles = calcHamTest.Tester(wordSpamChanceMap, hamDirectoryTest, spamDirectoryTest);
        //System.out.println(myTestFiles);
        for (TestFile entry : myTestFiles) {
            System.out.println("Filename: " + entry.getFilename() + " Actual class: " + entry.getActualClass() +
            " Spam probability: " + entry.getSpamProbRounded());
        }

        //Table Implementation


        table = new TableView<>();
        table.setItems(getTestFiles());

        //Create table columns
        TableColumn<TestFile,String> fileColumn = null;
        fileColumn = new TableColumn<>("File");
        fileColumn.setMinWidth(100);
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));

        TableColumn<TestFile,String> classColumn = null;
        classColumn = new TableColumn<>("Actual Class");
        classColumn.setMinWidth(100);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));

        TableColumn<TestFile,Double> spamColumn = null;
        spamColumn = new TableColumn<>("Spam Probability");
        spamColumn.setMinWidth(100);
        spamColumn.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));

        //Get the columns
        table = new TableView<>();
        table.getColumns().add(fileColumn);
        table.getColumns().add(classColumn);
        table.getColumns().add(spamColumn);


        //set the items in table
        table.setItems(getTestFiles());
        //table.setEditable(false);







        // Output for testing purposes
        //for (String entry : wordSpamChanceMap.keySet()) {
            //System.out.println(wordSpamChanceMap.get(entry));
        //}


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

    //get the TestFile Objects
    public ObservableList<TestFile> getTestFiles() {
        ObservableList<TestFile> file = FXCollections.observableArrayList();
        for (TestFile entry: myTestFiles)
            file.add(new TestFile(entry.getFilename(),entry.getSpamProbability(), entry.getActualClass()));
        return file;
    }

}
