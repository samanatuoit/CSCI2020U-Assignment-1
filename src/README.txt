Welcome to Spam Master 3000

Instructions ---- Download
------------------------------------------------------------------------------------------------------------------------
*******Make sure IntelliJ IDEA 15.0.4 is installed.
      Note: Make sure Java JDK 1.8 is installed

Go to: https://github.com/samanatuoit/CSCI2020U-Assignment-1

Clone the GIT Repository into a new folder of your choice

Download the data files to be read by the program here:
http://csundergrad.science.uoit.ca/csci2020u/assignments/data.zip
Extract the data folder.

Run Intellij IDEA 15.0.4.
Go to menu settings: File > New > Project from existing sources
    1) Select The Directory with existing source files, in this case go to where the cloned GIT Repository is located.
        Choose the CSCI2020U-Assignment1 folder.  Click OK.

    2)  The window will indicate the chosen option to create project from existing sources.  Click next.

    3) Enter new name for the project.  Click next.  If it asks you to overwrite any files, click yes.

    4)  A window will pop up asking you where to open the project.  Open the project in a new window.

    5)  Go to the cloned repository folder CSCI2020U-Assignment-1/.  Copy all the files and paste it in the
            newly created project location path.

    6)  Go back to IntelliJ project window.  On the left hand side there should be a window displaying the structure
            of this project.  Double click your project folder, a new src folder should appear.

------------------------------------------------------------------------------------------------------------------------
Compile/Run the Project
------------------------------------------------------------------------------------------------------------------------
    1) Specify the the main class.  Go to the file menu.  Run > Edit Configurations.
        Under the main class field:
         -->make sure Assign1 is the main class.
         -->Use classpath module is the project folder directory
         --> JRE field Default (1.8 - SDK of 'CSCI2020U-Assignment' module

    2) Compile the program by going to Build > Make Project
        OR
        using the build keyboard shortcut: Ctrl + F9

    3) Run the program by going to Run > Run 'yourProjectName'
        OR using the run keyboard shortcut: shift + F10

    The application should execute.

------------------------------------------------------------------------------------------------------------------------
USING the Program
------------------------------------------------------------------------------------------------------------------------
For this you will need to know the path of your extracted data folder.


Immediately an empty window Spam Master 3000 should pop up.

1) The program will ask for 4 directory chooser popups in a row:


    Popup #1 = Ham training folder selection

    Popup #2 = Spam training folder selection

    Popup #3 = Ham testing folder selection

    Popup #4 = Spam testing folder selection

Note: For each popup make sure you select the correct folder.
        E.g  For the first pop up:    ../data/train/ham      Click select folder.

2)After a few seconds the program should display the results of the filtered spam in a table format.


