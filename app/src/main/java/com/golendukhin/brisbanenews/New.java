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

    String getWebPublicationDate() {
        return webPublicationDate;
    }

    String getWebTitle() {
        return webTitle;
    }

    String getApiUrl() {
        return apiUrl;
    }

    String getAuthor() {
        return author;
    }
}