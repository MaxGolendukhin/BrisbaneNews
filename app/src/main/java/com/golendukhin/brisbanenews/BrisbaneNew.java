package com.golendukhin.brisbanenews;

class BrisbaneNew {
    private String webPublicationDate;
    private String webTitle;
    private String apiUrl;
    private String author;
    private String sectionName;

    BrisbaneNew(String webPublicationDate, String webTitle, String apiUrl, String author, String sectionName) {
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.apiUrl = apiUrl;
        this.author = author;
        this.sectionName = sectionName;
    }

    /**
     * @return date of brisbaneNew, being published
     */
    String getWebPublicationDate() {
        return webPublicationDate;
    }

    /**
     * @return title of every brisbaneNew, it is main block of every row
     */
    String getWebTitle() {
        return webTitle;
    }

    /**
     * @return url to launch brisbaneNew details in browser
     */
    String getApiUrl() {
        return apiUrl;
    }

    /**
     * @return brisbaneNew author
     */
    String getAuthor() {
        return author;
    }

    /**
     * @return brisbaneNew sectionName
     */
    String getSectionName() {
        return sectionName;
    }
}