package com.skshazena.dvdlibrary.controller;

import com.skshazena.dvdlibrary.dao.DVDLibraryDao;
import com.skshazena.dvdlibrary.dao.DVDLibraryException;
import com.skshazena.dvdlibrary.dto.DVD;
import com.skshazena.dvdlibrary.ui.DVDLibraryView;
import java.util.List;
import java.util.Set;

/**
 * DVD Library Controller Class - Handles interactions between the DAO and the
 * view, while keeping them separate from each other. Processes info from DAO
 * and to DAO and sends it to the user via the View.
 *
 * @author Shazena May 14, 2020
 */
public class DVDLibraryController {

    private DVDLibraryView view;
    private DVDLibraryDao dao;

    //constructor to pull in the dao and the view from their instantiation in the main method
    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    /**
     * Main Running Method - Most items related to how the program menu will
     * look and how the user interacts with the main menu gets processed here.
     *
     * @throws DVDLibraryException
     */
    public void run() throws DVDLibraryException {
        displayWelcome();

        boolean keepGoing = true;

        try {
            while (keepGoing) {
                int menuChoice = 0; //reset menu choice each time loop runs

                displayMenuBanner();

                menuChoice = getMenuChoice();
                //prints the menu and gets the user's choice, stores it in variable

                switch (menuChoice) {
                    case 1:
                        getAllDVDsMO(); //shows a list of all DVDs with some details
                        break;

                    case 2:
                        enterAddDVDModeMO();
                        break;

                    case 3:
                        enterRemoveDVDModeMO();
                        break;

                    case 4:
                        enterEditDVDModeMO();
                        break;

                    case 5:
                        displayOneDVDMO();
                        break;

                    case 6:
                        searchForDVDMO();
                        break;

                    case 7:
                        keepGoing = false;
                        //breaks the loop, ends program
                        break;

                    default:
                        unknownCommand(); //prints error message and asks for choice again
                }
            }//end of while loop
            exitMessage();
        } catch (DVDLibraryException e) {
            view.displayErrorMessage(e.getMessage());//TODO should this be in a private method here?
            //It was not in a private method in the Class Roster, but it feels like it should be...?
            //On account of it being the only thing in this method referencing the view
        }

    }//end of run method

    private void displayWelcome() {
        view.displayWelcomeMessage();
    }

    /*
    Items related to Main menu display and collection of menu choice
     */
    private void displayMenuBanner() {
        view.displayMenuBanner();
    }

    private int getMenuChoice() {
        return view.printMenuAndGetChoice();
    }

    /**
     * Get All DVDs MO (Menu Option) - shows a list of all the DVDs in the
     * library with some details, the date and the rating.
     *
     * @throws DVDLibraryException
     */
    private void getAllDVDsMO() throws DVDLibraryException {
        view.displayAllDVDsBanner();

        List<DVD> DVDList = dao.getAllDVDs(); //get all DVDs from Dao, store in this list

        view.displayAllDVDsList(DVDList);//show this list
        //different from method "listAllDVDsByTitle" because this shows a few more details. 
        //This one calls in the "toStringSomeDetails" method in the DVD Object while
        //that one just lists titles
    }

    /**
     * Enter Add DVD Mode MO (Menu Option) - This starts a loop where the user
     * is given three options to add a DVD or to exit this mode. This allows the
     * user to enter multiple DVDs right after one another without going back to
     * the main menu
     *
     * @throws DVDLibraryException
     */
    private void enterAddDVDModeMO() throws DVDLibraryException {

        view.displayAddDVDModeBanner();

        OUTER: //label for loop so that the break inside will end this loop
        //Netbeans created this for me
        while (true) {
            int subMenuChoice = view.displayAddDVDMenuAndGetChoice();

            boolean searchResult; //will store if title is in collection or not

            switch (subMenuChoice) {
                case 1:

                    view.displayAddDVDBanner();

                    DVD aNewDVDSimple = view.getNewDVDInfoSimple();
                    //collect info from user about new DVD

                    searchResult = performSearch(aNewDVDSimple.getTitle());
                    //run search using new DVD title

                    if (!searchResult) { //if it's not in the library yet...
                        //add it to the library and display success message
                        dao.addDVD(aNewDVDSimple);
                        view.displayAddSuccessBanner();
                    } else { //if it's in the library, display error message
                        //this is done to prevent the user from over-writing a
                        //previously added movie. They should use the edit feature
                        //if they would like to make changes to an already
                        //existing title. 
                        view.displayCouldNotCompleteMessage();
                    }
                    //then go back to the add DVD Mode Menu
                    break;
                case 2: //Add DVD - detailed version

                    view.displayAddDVDBanner();

                    DVD aNewDVDDetailed = view.getNewDVDInfoDetailed(); //get info from user.

                    searchResult = performSearch(aNewDVDDetailed.getTitle()); 

                    if (!searchResult) { 
                        //if it is not already in the library, then let them add it
                        dao.addDVD(aNewDVDDetailed);
                        view.displayAddSuccessBanner();
                    } else {
                        //else, do not let them add the DVD
                        view.displayCouldNotCompleteMessage();
                    }

                    break;
                default:
                    break OUTER;
            }

        }

    }

    /**
     * Enter Remove DVD Mode MO (Menu Option) - This starts a loop where the
     * user is given three options to Remove a DVD, show a list of all DVD
     * titles, or to exit this mode. This allows the user to remove multiple
     * DVDs right after one another without going back to the main menu. I
     * included the option to list all titles because the user needs to enter
     * the title correctly for the DVD to be removed. By doing this, the user
     * can get a quick glance at what all of the titles are so they can enter
     * them correctly.
     *
     * @throws DVDLibraryException
     */
    private void enterRemoveDVDModeMO() throws DVDLibraryException {
        view.displayRemoveDVDModeBanner();

        OUTER_1:
        while (true) {

            int subMenuChoice = view.displayRemoveDVDMenuAndGetChoice();

            switch (subMenuChoice) {
                case 1:

                    String DVDTitle = view.getTitleFromUser();

                    //store info about the DVD the user wants to delete
                    DVD DVDToRemove = dao.removeDVD(DVDTitle);
                    
                    //send it to the view to see if it was really deleted or if there was no title with that name. 
                    view.displayRemovalBanner(DVDToRemove);

                    break;
                case 2:
                    
                    //show the list of all titles in case they want to copy and paste from it.
                    //TODO possible update, list 3 titles in one line, then move to next line. 
                    listAllDVDsByTitle();

                    break;
                default:
                    break OUTER_1;
            }
        }
    }

    /**
     * List All DVDs by Title - Method to help out other methods where the user
     * has to enter the DVD title accurately to help prevent misspellings. This
     * method is referenced in the remove DVD mode and in the edit DVD mode.
     *
     * @throws DVDLibraryException
     */
    private void listAllDVDsByTitle() throws DVDLibraryException {
        List<DVD> DVDList = dao.getAllDVDs(); //get list from DAO
        view.listAllDVDTitles(DVDList); //show the list on the screen
    }

    /**
     * Enter Edit DVD Mode MO (Menu Option) - This starts a loop where the user
     * is given three options to Edit a DVD, show a list of all DVD titles, or
     * to exit this mode. This allows the user to edit multiple DVDs right after
     * one another without going back to the main menu. I included the option to
     * list all titles because the user needs to enter the title correctly for
     * the DVD to be edited. By doing this, the user can get a quick glance at
     * what all of the titles are so they can enter them correctly.
     *
     * @throws DVDLibraryException
     */
    private void enterEditDVDModeMO() throws DVDLibraryException {
        boolean searchResult;
        view.displayEditDVDModeBanner();

        OUTER_2:
        while (true) {

            int subMenuChoice = view.displayEditDVDMenuAndGetChoice();

            switch (subMenuChoice) {
                case 1:

                    String titleOfDVDToEdit = view.getTitleFromUser();

                    searchResult = performSearch(titleOfDVDToEdit);//perform search

                    if (searchResult) {//if title IS in the collection, make the edit.
                        DVD DVDToEdit = dao.getOneDVD(titleOfDVDToEdit);
                        DVD updatedDVD = view.getEditedDVDInfo(DVDToEdit);
                        dao.updateDVD(updatedDVD);

                        view.displayEditCompleteBanner();
                    } else { //if it is not in the collection, print an error message. 
                        view.displayCouldNotCompleteMessage();
                    }

                    break;
                case 2:
                    
                    //show the list of all titles in case they want to copy and paste from it.
                    listAllDVDsByTitle();
                    break;
                default:
                    break OUTER_2;
            }
        }
    }

    /**
     * Display One DVD method MO (Menu Option) - This method shows the user all
     * the details of a single movie.
     *
     * @throws DVDLibraryException
     */
    private void displayOneDVDMO() throws DVDLibraryException {
        boolean searchResult;
        view.displayOneDVDBanner();
        String DVDTitle = view.getTitleFromUser();

        searchResult = performSearch(DVDTitle); //perform search

        if (searchResult) { //if it is in the collection...
            //display the info
            DVD aDVD = dao.getOneDVD(DVDTitle);
            view.displayDVDDetails(aDVD);
        } else { //if not, show error message.
            view.displayCouldNotCompleteMessage();
        }
    }

    /**
     * Search for DVD method MO (Menu Option) - This is the last main menu
     * method where the user can enter a DVD title and it will tell the user if
     * it is in their library or not. It calls on the method in this controller
     * to run the search and give a boolean as the result which this then sends
     * to the view for translation
     *
     * @throws DVDLibraryException
     */
    private void searchForDVDMO() throws DVDLibraryException {

        String titleToSearchFor = view.getTitleFromUser();

        boolean inLibrary = performSearch(titleToSearchFor);

        view.displaySearchResult(inLibrary);
    }

    /**
     * This method performs a search for a given title of DVD within the set of
     * DVD titles. This is the method that I'm not sure is in the right
     * location. It feels like it should not really go here, but it doesn't
     * belong in the view either.
     *
     * @param title {String}
     * @return {boolean} whether or not title is in library
     */
    private boolean performSearch(String title) throws DVDLibraryException {
        Set<String> DVDTitleList = dao.getDVDTitles();
        boolean inLibrary;
        inLibrary = DVDTitleList.contains(title);
        return inLibrary;
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
