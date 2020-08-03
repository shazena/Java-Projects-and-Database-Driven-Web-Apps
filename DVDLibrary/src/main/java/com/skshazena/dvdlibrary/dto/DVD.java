package com.skshazena.dvdlibrary.dto;

/**
 * DVD Object Class
 *
 * @author Shazena May 14, 2020
 */
public class DVD {

    private String title;
    private int releaseDateMonth;
    private int releaseDateDay;
    private int releaseDateYear;
    private String mpaaRating;
    private String directorsName;
    private String studio;
    private String notes;

    //constructor version 1 - simple
    public DVD(String title) {
        this.title = title;
    }

    //constructor version 2 - detailed
    public DVD(String title, int releaseDateMonth, int releaseDateDay,
            int releaseDateYear, String mpaaRating, String directorsName,
            String studio, String notes) {
        this.title = title;
        this.releaseDateMonth = releaseDateMonth;
        this.releaseDateDay = releaseDateDay;
        this.releaseDateYear = releaseDateYear;
        this.mpaaRating = mpaaRating;
        this.directorsName = directorsName;
        this.studio = studio;
        this.notes = notes;
    }

    //title is read-only field, so there is no setter.
    public String getTitle() {
        return title;
    }

    public int getReleaseDateMonth() {
        return releaseDateMonth;
    }

    public void setReleaseDateMonth(int releaseDateMonth) {
        this.releaseDateMonth = releaseDateMonth;
    }

    public int getReleaseDateDay() {
        return releaseDateDay;
    }

    public void setReleaseDateDay(int releaseDateDay) {
        this.releaseDateDay = releaseDateDay;
    }

    public int getReleaseDateYear() {
        return releaseDateYear;
    }

    public void setReleaseDateYear(int releaseDateYear) {
        this.releaseDateYear = releaseDateYear;
    }

    /**
     * getReleaseDateFull Method - This method takes the individual numbers
     * stored for the month, date, and year and combines them so that they can
     * form a single string that is returned to the caller. If these values are
     * not set and are left at the default values of 0, then this method will
     * return "not set".
     *
     * @return {String} "not set" or fully formatted date
     */
    public String getRelaseDateFull() {
        String date;
        if (releaseDateMonth == 0 && releaseDateDay == 0
                && releaseDateYear == 0) {
            date = "not set "; //extra spaces to help keep things aligned
            //a full date would take up 10 spaces, and so does this with the extra
            //spaces added in.
        } else {
            date = releaseDateMonth + "/" + releaseDateDay
                    + "/" + releaseDateYear;
        }
        return date;
    }

    /**
     * getMpaaRating Method - this has been modified so that the user will get
     * the string "not set" if the variable value is null or empty. Otherwise,
     * it will send the actual value of the variable.
     *
     * @return {String} actual rating or "not set"
     */
    public String getMpaaRating() {
        String rating;
        if (mpaaRating == null || mpaaRating.equals("")) {
            rating = "not set";
        } else {
            rating = mpaaRating;
        }
        return rating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     *
     * getDirectorsName Method - this has been modified so that the user will
     * get the string "not set" if the variable value is null or empty.
     * Otherwise, it will send the actual value of the variable.
     *
     * @return {String} actual value or string "not set"
     */
    public String getDirectorsName() {
        String director;
        if (directorsName == null || directorsName.equals("")) {
            director = "not set";
        } else {
            director = directorsName;
        }
        return director;
    }

    public void setDirectorsName(String directorsName) {
        this.directorsName = directorsName;
    }

    /**
     * getStudio Method - this has been modified so that the user will get the
     * string "not set" if the variable value is null or empty. Otherwise, it
     * will send the actual value of the variable.
     *
     *
     * @return actual value or string "not set"
     */
    public String getStudio() {
        String studioName;
        if (studio == null || studio.equals("")) {
            studioName = "not set";
        } else {
            studioName = studio;
        }
        return studioName;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    /**
     * getNotes Method - this has been modified so that the user will get the
     * string "not set" if the variable value is null or empty. Otherwise, it
     * will send the actual value of the variable.
     *
     *
     * @return actual value or string "not set"
     *
     */
    public String getNotes() {
        String userNotes;
        if (notes == null || notes.equals("")) {
            userNotes = "not set";
        } else {
            userNotes = notes;
        }
        return userNotes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * toString Method - since this method previously just printed the location
     * on the heap of the object, I overrode it so that it could be used to
     * print out all the details of a specific DVD object.
     *
     * @return {String} all details of a DVD object
     */
    @Override
    public String toString() {
        String DVDInfo = String.format("Movie: %s\n"
                + "Release Date: %s\tMPAA Rating: %s\n"
                + "Director: %s\nStudio: %s\n"
                + "Notes: %s \n",
                title, getRelaseDateFull(), getMpaaRating(),
                getDirectorsName(), getStudio(), getNotes());
        return DVDInfo;
    }

    /**
     * toStringSomeDetails method - This method was made to be a shortened form
     * of the method above. It is used in the first main menu option to list all
     * DVDs
     *
     * @return {String} some details of the DVD
     */
    public String toStringSomeDetails() {
        String DVDInfo = String.format("Movie: %s\n"
                + "Release Date: %s\tMPAA Rating: %s\n",
                title, getRelaseDateFull(), getMpaaRating());
        return DVDInfo;
    }
}
