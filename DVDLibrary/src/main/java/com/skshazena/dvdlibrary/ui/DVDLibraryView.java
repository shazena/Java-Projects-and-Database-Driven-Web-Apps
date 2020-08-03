package com.skshazena.dvdlibrary.ui;

import com.skshazena.dvdlibrary.dto.DVD;
import java.util.List;

/**
 * DVD Library View - responsible for collecting user input and showing the user
 * output.
 *
 * @author Shazena May 14, 2020
 */
public class DVDLibraryView {

    private UserIO io;

    //constructor so that it can pull in the correct implementaion of the userIO
    public DVDLibraryView(UserIO io) {
        this.io = io;
    }

    public void displayWelcomeMessage() {
        io.print("Welcome to your DVD Library! \nWhat would you like to do?");
    }

    /*
    START - MAIN MENU SECTION
     */
    public void displayMenuBanner() {
        io.print("\n----- MAIN MENU -----");
    }

    public int printMenuAndGetChoice() {

        io.print("1. List All DVDs");
        io.print("2. Add DVDs");
        io.print("3. Remove DVDs");
        io.print("4. Edit DVDs");
        io.print("5. Show details of a DVD");
        io.print("6. Search for DVD by title");
        io.print("7. Exit");
        int result = io.readInt("Enter the number corresponding to the menu option.", 1, 7);

        return result;
    }

    /*
    END - MAIN MENU SECTION
     */
    ////////////////////////////////////////////////////////////////////////////
    /*
    START - MULTI-USE METHOD SECTION
     */
    public String getTitleFromUser() {
        //used by the remove DVD, edit DVD, display one DVD and search for DVD
        //methods in the controller.
        String DVDTitle = io.readString("Enter the name of the DVD.");
        return DVDTitle;
    }

    /**
     * listAllDVDTitles method - used by the remove mode and the edit mode as
     * option 2. It produces a list of DVD titles only.
     *
     * @param dvdList {List<DVD>} List of DVD titles
     */
    public void listAllDVDTitles(List<DVD> dvdList) {
        io.print("\n--- Movie List ---");
        for (DVD dvd : dvdList) {
            io.print(dvd.getTitle());
        }
        io.print("");
    }

    //general error message I wrote for when the user enters a title that is 
    //not found in the library or other things. It is used by the add DVD method, 
    //the edit DVD method, the view one DVD's details method, and the search for
    //DVD method that are in the controller
    public void displayCouldNotCompleteMessage() {
        io.print("ERROR: That operation could not be completed.");
        io.readString("Press enter to continue.");
    }

    /*
    END - MULTI-USE METHOD SECTION
     */
    ////////////////////////////////////////////////////////////////////////////
    /*
    START - SHOW ALL DVDs SECTION
     */
    public void displayAllDVDsBanner() {
        io.print("\n----- LIST OF DVDS -----");
    }

    public void displayAllDVDsList(List<DVD> DVDList) {
        for (DVD dvd : DVDList) {
            String DVDInfo = dvd.toStringSomeDetails();
            //String DVDInfo = dvd.toString();//testing line
            io.print(DVDInfo);
        }
        io.readString("Please hit enter to continue.");
    }

    /*
    END - SHOW ALL DVDs SECTION
     */
    ////////////////////////////////////////////////////////////////////////////
    /*
    START - ADD DVD MODE SECTION
     */
    public void displayAddDVDModeBanner() {
        io.print("\n----- ADD DVD MODE -----");
    }

    public int displayAddDVDMenuAndGetChoice() {
        io.print("Press 1 to enter a new DVD by title only.");
        io.print("Press 2 to enter a new DVD by title, date and other optional details.");
        io.print("Press 3 to exit to the main menu.");

        int result = io.readInt("Enter your choice.", 1, 3);

        return result;
    }

    public void displayAddDVDBanner() {
        io.print("\n--Enter details of new DVD.--");
    }

    /**
     * getNewDVDInfoSimple method - This method collects only the title of the
     * DVD and then adds it to the Library.
     *
     * @return {DVD} the new DVD
     */
    public DVD getNewDVDInfoSimple() {
        String DVDTitle = io.readString("\nPlease enter the DVD title.");
        DVD newDVD = new DVD(DVDTitle);
        return newDVD;
    }

    /**
     * getNewDVDInfoDetailed - This method collects all fields for the DVD and
     * then adds it to the library. The date field is required because of the
     * min, max range, but the other fields are optional. The user does not have
     * to enter all fields to create the object. There is a switch to make sure
     * that the user enters a correct day after entering their month.
     *
     * @return {DVD} the new DVD
     */
    public DVD getNewDVDInfoDetailed() {
        int dayRestriction;

        io.readString("The title and release date are required. All other fields "
                + "are optional.\nHit enter to skip a field.\n\nPress enter to start"
                + " typing the info.");

        String DVDTitle = io.readString("Please enter DVD title.");
        int DVDReleaseDateMonth = io.readInt("Please enter the numeric month of release.", 1, 12);

        //this switch checks what month you entered and makes sure you do not 
        //enter a date for the day that is not in that month
        switch (DVDReleaseDateMonth) {
            case 9: //September
            case 4: //April
            case 6: //June
            case 11://November
                dayRestriction = 30; //fell through the switch to this value
                break;
            case 2: //February
                dayRestriction = 29;
                break;
            default: //rest of Months
                dayRestriction = 31;
                break;
        }

        int DVDReleaseDateDay = io.readInt("Please enter the numeric day of release.", 1, dayRestriction);
        int DVDReleaseDateYear = io.readInt("Please enter the numeric year of release.", 1888, 3000);
        String DVDMpaaRating = io.readString("Please enter the MPAA Rating.");
        String DVDDirectorsName = io.readString("Please enter the director's name.");
        String DVDStudio = io.readString("Please enter the Studio name.");
        String DVDNotes = io.readString("Please enter your personal rating or notes on the DVD.");

        DVD aNewDVD = new DVD(DVDTitle, DVDReleaseDateMonth, DVDReleaseDateDay,
                DVDReleaseDateYear, DVDMpaaRating, DVDDirectorsName, DVDStudio, DVDNotes);

        return aNewDVD;
    }

    public void displayAddSuccessBanner() {
        io.readString("\n--New DVD Added-- \nPress enter to continue.");
    }

    /*
    END - ADD DVD MODE SECTION
     */
    ////////////////////////////////////////////////////////////////////////////
    /*
    START - REMOVE DVD MODE SECTION
     */
    public void displayRemoveDVDModeBanner() {
        io.print("\n----- REMOVE DVD MODE -----");
    }

    public int displayRemoveDVDMenuAndGetChoice() {
        io.print("Press 1 to remove a DVD by title.");
        io.print("Press 2 to list all DVDs by title.");
        io.print("Press 3 to exit to the main menu.");

        int result = io.readInt("Enter your choice.", 1, 3);

        return result;
    }

    public void displayRemovalBanner(DVD DVDToRemove) {
        if (DVDToRemove != null) {
            //if the operation has been completed, by not giving us a null object, then...
            io.print("-- DVD has been removed --");
        } else { //if we get a null object in return...
            io.print("There is no DVD with that title.");
        }
        io.readString("Press enter to continue.");
    }

    /*
    END - REMOVE DVD MODE SECTION
     */
    ////////////////////////////////////////////////////////////////////////////
    /*
    START - EDIT DVD MODE SECTION
     */
    public void displayEditDVDModeBanner() {
        io.print("\n----- EDIT DVD MODE -----");
    }

    public int displayEditDVDMenuAndGetChoice() {
        io.print("Press 1 to edit a DVD.");
        io.print("Press 2 to list all DVDs by title.");
        io.print("Press 3 to exit to the main menu.");

        int result = io.readInt("Enter your choice.", 1, 3);

        return result;
    }

    /**
     * Get Edited DVD Info - After the user enters the title of the DVD they
     * want to enter, this method gets sent the DVD object they wanted to edit.
     * This method takes in the new date from the user, validates it to see if
     * it is real, except for Feb 29ths, and the other details. Then it checks
     * to see if the user left anything blank. If a field is left blank by the
     * user here, the method says to just keep the original value of the field.
     *
     * @param editedDVD
     * @return {DVD} editedDVD, the object from the input, but with edits
     */
    public DVD getEditedDVDInfo(DVD editedDVD) {
        int dayRestriction; //used to control the day entered based on the month

        //Instructions that hopefully the user read...
        io.readString("\nTo edit a field, type your new information. \n"
                + "The date field is mandatory.\n"
                + "If you wish to leave the original info for any other field, \n"
                + "just press enter.\nPress enter to start editing.");

        //Print original details because they must enter the date. If there is a date 
        //already entered, they will need to re-enter that. 
        //TODO use readInt without a min-max on date and then run validation after if they don't leave it blank.
        io.print("For Reference, here are the original details: \n" + editedDVD.toString());
        int DVDReleaseDateMonth = io.readInt("Please enter the numeric month of release.", 1, 12);

        //this switch checks what month you entered and makes sure you do not 
        //enter a date for the day that is not in that month
        switch (DVDReleaseDateMonth) {
            case 9: //September
            case 4: //April
            case 6: //June 
            case 11://November
                dayRestriction = 30;
                break;
            case 2: //February
                dayRestriction = 29;
                break;
            default://Other Months
                dayRestriction = 31;
                break;
        }

        //the switch, depending on the month, will control whether or not the user
        //can enter a proper date, given the month they chose. ex. you cannot enter
        //April 31st, as it is not a real date.
        int DVDReleaseDateDay = io.readInt("Please enter the numeric day of release.", 1, dayRestriction);
        int DVDReleaseDateYear = io.readInt("Please enter the numeric year of release.", 1888, 3000);
        String DVDMpaaRating = io.readString("Please enter the MPAA Rating.");
        String DVDDirectorsName = io.readString("Please enter the director's name.");
        String DVDStudio = io.readString("Please enter the Studio name.");
        String DVDNotes = io.readString("Please enter your personal rating or notes on the DVD.");

        //set the date according to what the user entered.
        editedDVD.setReleaseDateMonth(DVDReleaseDateMonth);
        editedDVD.setReleaseDateDay(DVDReleaseDateDay);
        editedDVD.setReleaseDateYear(DVDReleaseDateYear);

        //perform checks to make sure fields aren't empty.
        //if the fields are empty, then the old values should remain.
        if (!DVDMpaaRating.equals("")) {
            editedDVD.setMpaaRating(DVDMpaaRating);
        }
        if (!DVDDirectorsName.equals("")) {
            editedDVD.setDirectorsName(DVDDirectorsName);
        }
        if (!DVDStudio.equals("")) {
            editedDVD.setStudio(DVDStudio);
        }
        if (!DVDNotes.equals("")) {
            editedDVD.setNotes(DVDNotes);
        }

        //show the user the new details
        io.print("\nHere are the updated details: \n" + editedDVD.toString());

        return editedDVD;
    }

    public void displayEditCompleteBanner() {
        io.print("\n-- DVD has been edited --");
    }

    /*
    END - EDIT DVD MODE SECTION
     */
    ////////////////////////////////////////////////////////////////////////////
    /*
    START - DISPLAY ONE DVD's DETAILS SECTION
     */
    public void displayOneDVDBanner() {
        io.print("\n----- DVD DETAILS -----");
    }

    public void displayDVDDetails(DVD aDVD) {
        io.print("Here are the details of " + aDVD.getTitle());
        io.print(aDVD.toString());
        io.readString("\nPress enter to continue.");
    }

    /*
    END - DISPLAY ONE DVD's DETAILS SECTION
     */
    ////////////////////////////////////////////////////////////////////////////
    /*
    START - SEARCH FOR DVD SECTION
     */
    public void displaySearchBanner() {
        io.print("----- SEARCH FOR DVD -----");
    }

    /**
     * displaySearchResult method - takes in the boolean from the controller and
     * shows the user the appropriate message.
     *
     * @param inLib {boolean} from search method in controller.
     */
    public void displaySearchResult(boolean inLib) {
        if (inLib) {
            io.print("That DVD is in your library!");
        } else {
            io.print("That DVD could not be found.");
        }
        io.readString("Press enter to continue.");
    }

    /*
    END - DISPLAY ONE DVD's DETAILS SECTION
     */
    ////////////////////////////////////////////////////////////////////////////
    /*
    START - SEARCH FOR DVD SECTION
     */
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("----- ERROR -----");
        io.print(errorMsg);

    }
    /*
    END - SEARCH FOR DVD SECTION
     */

}
