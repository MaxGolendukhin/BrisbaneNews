package com.golendukhin.brisbanenews;

class New {
    private String webPublicationDate;
    private String webTitle;
    private String apiUrl;
    private String author;

    New(String webPublicationDate, String webTitle, String apiUrl, String author) {
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.apiUrl = apiUrl;
        this.author = author;
    }

    /**
     * @return date of new, being published
     */
    String getWebPublicationDate() {
        return webPublicationDate;
    }

    /**
     * @return title of every new, it is main block of every row
     */
    String getWebTitle() {
        return webTitle;
    }

    /**
     * @return url to launch new details in browser
     */
    String getApiUrl() {
        return apiUrl;
    }

    /**
     * @return new author
     */
    String getAuthor() {
        return author;
    }
}