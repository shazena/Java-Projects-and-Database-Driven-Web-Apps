package com.skshazena.dvdlibrary.dao;

import com.skshazena.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * DVD Library File Implementation of the DVD Library DAO interface - Here is
 * the implementation of the interface specified along with methods to marshall
 * and unmarshall the data and the save and load the libraries.
 *
 * @author Shazena May 14, 2020
 */
public class DVDLibraryDaoFileImpl implements DVDLibraryDao {

    private Map<String, DVD> library = new HashMap<>();

    private static final String DVDLIBRARY_FILE = "dvdlibrary.txt";
    private static final String DELIMETER = "::";

    @Override
    public List<DVD> getAllDVDs() throws DVDLibraryException {
        loadLibrary();
        return new ArrayList<DVD>(library.values());
    }

    @Override
    public Set<String> getDVDTitles() throws DVDLibraryException {
        loadLibrary();
        //create a set of just the titles and return that set
        Set<String> titleList = library.keySet();
        return titleList;
    }

    @Override
    public DVD addDVD(DVD newDVD) throws DVDLibraryException {
        loadLibrary();
        //put the new DVD in the library
        DVD aNewDVD = library.put(newDVD.getTitle(), newDVD);
        saveLibrary();
        return aNewDVD;
    }

    @Override
    public DVD removeDVD(String title) throws DVDLibraryException {
        loadLibrary();
        //remove library, return result to user
        //result will be DVD title or null
        DVD removedDVD = library.remove(title);
        saveLibrary();
        return removedDVD;
    }

    @Override
    public DVD updateDVD(DVD theEditedDVD) throws DVDLibraryException {
        loadLibrary();
        //we can put the updated DVD back into the Map because its details will 
        //overwrite the item that is currently there, provided the titles match
        DVD editedDVD = library.put(theEditedDVD.getTitle(), theEditedDVD);
        saveLibrary();
        return editedDVD;
    }

    @Override
    public DVD getOneDVD(String title) throws DVDLibraryException {
        loadLibrary();
        //use the string of the title that I'm passing to you and return the DVD object
        DVD aDVD = library.get(title);
        return aDVD;
    }

    private DVD unmarshallDVD(String DVDAsText) {
        //split the string of text by the delimeter, store in an array.
        String[] DVDTokens = DVDAsText.split(DELIMETER);

        //assign the array items to the different object fields.
        String title = DVDTokens[0];
        int month = Integer.parseInt(DVDTokens[1]);
        int day = Integer.parseInt(DVDTokens[2]);
        int year = Integer.parseInt(DVDTokens[3]);
        String rating = DVDTokens[4];
        String director = DVDTokens[5];
        String studio = DVDTokens[6];
        String notes = DVDTokens[7];

        DVD DVDFromFile = new DVD(title, month, day, year, rating, director, studio, notes);

        return DVDFromFile;
    }

    private String marshallDVD(DVD aDVD) {
        //Turn the fields of the DVD object into a String by appending them to their key
        String DVDAsText = aDVD.getTitle() + DELIMETER;
        DVDAsText += aDVD.getReleaseDateMonth() + DELIMETER;
        DVDAsText += aDVD.getReleaseDateDay() + DELIMETER;
        DVDAsText += aDVD.getReleaseDateYear() + DELIMETER;
        DVDAsText += aDVD.getMpaaRating() + DELIMETER;
        DVDAsText += aDVD.getDirectorsName() + DELIMETER;
        DVDAsText += aDVD.getStudio() + DELIMETER;
        DVDAsText += aDVD.getNotes() + DELIMETER;
        return DVDAsText;
    }

    /**
     * Load Library Method - Loads the text file that stores the library, reads
     * each line, unmarshalls it into a DVD object and puts those objects in the
     * HashMap. The end.
     *
     * @throws DVDLibraryException
     */
    private void loadLibrary() throws DVDLibraryException {

        Scanner scanner = null;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DVDLIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryException("Uh-oh! The Library could not be loaded", e);
        }

        String currentLine;
        DVD currentDVD;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            currentDVD = unmarshallDVD(currentLine);
            library.put(currentDVD.getTitle(), currentDVD);
        }
        scanner.close();

    }

    /**
     * Save Library Method - Bring in the list of DVD objects, marshall each DVD
     * into a string, write it to the file, and close the PrintWriter
     *
     * @throws DVDLibraryException
     */
    private void saveLibrary() throws DVDLibraryException {
        PrintWriter out = null;

        try {
            out = new PrintWriter(new FileWriter(DVDLIBRARY_FILE));
        } catch (IOException e) {
            throw new DVDLibraryException("Could not save the library", e);
        }

        String DVDAsText;
        List<DVD> DVDList = new ArrayList(library.values());

        for (DVD dvd : DVDList) {
            DVDAsText = marshallDVD(dvd);
            out.println(DVDAsText);
            out.flush();
        }
        out.close();

    }

}
