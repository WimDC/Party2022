package be.thomasmore.party.model;

public class Artist {
    private int id;
    private String artistName;
    private String linkMoreInfo;
    private String genre;
    private String bio;
    private String portfolio;

    public int getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getLinkMoreInfo() {
        return linkMoreInfo;
    }

    public String getGenre() {
        return genre;
    }

    public String getBio() {
        return bio;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setLinkMoreInfo(String linkMoreInfo) {
        this.linkMoreInfo = linkMoreInfo;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public Artist() {
    }
}
