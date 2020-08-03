package com.skshazena.dvdlibrary;

import com.skshazena.dvdlibrary.controller.DVDLibraryController;
import com.skshazena.dvdlibrary.dao.DVDLibraryDao;
import com.skshazena.dvdlibrary.dao.DVDLibraryDaoFileImpl;
import com.skshazena.dvdlibrary.dao.DVDLibraryException;
import com.skshazena.dvdlibrary.ui.DVDLibraryView;
import com.skshazena.dvdlibrary.ui.UserIO;
import com.skshazena.dvdlibrary.ui.UserIOConsoleImpl;

/**
 * DVD Library Application (Module 2 Assessment) - This program keeps track of a DVD library and has
 * the following functions: list all DVDs, add a DVD, remove a DVD, edit a DVD,
 * show details of one DVD, and search for a DVD, all with file persistence.
 *
 * @author Shazena May 14, 2020
 */
public class App {

    public static void main(String[] args) throws DVDLibraryException {
        UserIO myIo = new UserIOConsoleImpl(); //instantiates new userIO object
        DVDLibraryView myView = new DVDLibraryView(myIo); //sends userIO object to View and instantiates View
        DVDLibraryDao myDao = new DVDLibraryDaoFileImpl(); //instantiates Dao
        DVDLibraryController controller = new DVDLibraryController(myDao, myView); //sends Dao and View to Controller and instantiates Controller

        controller.run(); //executes the run method in the controller
    }
}
