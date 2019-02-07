package com.example.davichiar.addavichi;

public class Notice {

    String searchTitle, searchLink, searchImglink, searchContext, searchDate, searchNicname, searchAdd, searchActive, searchText;

    public Notice(String searchTitle, String searchLink, String searchImglink, String searchContext, String searchDate, String searchNicname, String searchAdd, String searchActive, String searchText) {
        this.searchTitle = searchTitle;
        this.searchLink = searchLink;
        this.searchImglink = searchImglink;
        this.searchContext = searchContext;
        this.searchDate = searchDate;
        this.searchNicname = searchNicname;
        this.searchAdd = searchAdd;
        this.searchActive = searchActive;
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getSearchLink() {
        return searchLink;
    }

    public void setSearchLink(String searchLink) {
        this.searchLink = searchLink;
    }

    public String getSearchImglink() {
        return searchImglink;
    }

    public void setSearchImglink(String searchImglink) {
        this.searchImglink = searchImglink;
    }

    public String getSearchContext() {
        return searchContext;
    }

    public void setSearchContext(String searchContext) {
        this.searchContext = searchContext;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getSearchNicname() {
        return searchNicname;
    }

    public void setSearchNicname(String searchNicname) {
        this.searchNicname = searchNicname;
    }

    public String getSearchAdd() {
        return searchAdd;
    }

    public void setSearchAdd(String searchAdd) {
        this.searchAdd = searchAdd;
    }

    public String getSearchActive() {
        return searchActive;
    }

    public void setSearchActive(String searchActive) {
        this.searchActive = searchActive;
    }
}
