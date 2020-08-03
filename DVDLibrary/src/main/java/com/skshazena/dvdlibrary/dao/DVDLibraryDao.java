package com.skshazena.dvdlibrary.dao;

import com.skshazena.dvdlibrary.dto.DVD;
import java.util.List;
import java.util.Set;

/**
 * DVD Library DAO Interface - all the methods that the File Implementation must
 * include.
 *
 * @author Shazena May 14, 2020
 */
public interface DVDLibraryDao {

    public List<DVD> getAllDVDs() throws DVDLibraryException;

    public DVD addDVD(DVD newDVD) throws DVDLibraryException;

    public Set<String> getDVDTitles() throws DVDLibraryException;

    public DVD removeDVD(String title) throws DVDLibraryException;

    public DVD updateDVD(DVD theEditedDVD) throws DVDLibraryException;

    public DVD getOneDVD(String title) throws DVDLibraryException;
}
