package pl.inzynierka;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class Pagination { ;
    Page page;
    static final String domain = "localhost:8080/";
    String url;
    int firstPage = 0;
    int previousPage = 0;
    int actualPage = 0;
    int nextPage = 0;
    int lastPage = 0;

    public Pagination(Page page, String url) {
        this.url = url;
        this.page = page;
        previousPage = page.getNumber() - 1;
        actualPage = page.getNumber();
        nextPage = page.getNumber() + 1;
        lastPage = page.getTotalPages()-1;
    }

    public boolean isFirstPage(){
        return (page.getNumber() >= 2) ? true : false;
    }

    public Integer getFirstPage() {
        return 1;
    }

    public String getFirstPageUrl(){
        return url+"?page="+firstPage;
    }

    public boolean isPreviousPage(){
        return (page.getNumber() >= 1 ) ? true : false;
    }

    public Integer getPreviousPage(){
        return previousPage+1;
    }

    public String getPreviousPageUrl(){
        return url+"?page="+previousPage;
    }

    public boolean isActualPage(){
        return (page.getNumber() >= 0) ? true : false;
    }

    public Integer getActualPage() {
        return actualPage+1;
    }

    public String getActualPageUrl(){
        return url+"?page="+actualPage;
    }

    public boolean isNextPage(){
        return (page.getNumber() < page.getTotalPages() - 2) ? true : false;
    }

    public Integer getNextPage(){
        return nextPage+1;
    }

    public String getNextPageUrl(){
        return url+"?page="+nextPage;
    }

    public boolean isLastPage(){
        return (page.getTotalPages() >= 1 && page.getTotalPages()-1 != page.getTotalPages()) ? true : false;
    }

    public Integer getLastPage() {
        return lastPage+1;
    }

    public String getLastPageUrl(){
        return url+"?page="+lastPage;
    }

}
