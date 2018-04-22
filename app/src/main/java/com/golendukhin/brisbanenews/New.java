package com.golendukhin.brisbanenews;

class New {
    private String webPublicationDate;
    private String webTitle;
    private String apiUrl;

    New(String webPublicationDate, String webTitle, String apiUrl) {
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.apiUrl = apiUrl;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}