package materialtest.theartistandtheengineer.co.materialtest.pojo;

/**
 * Created by mpcen-desktop on 3/26/15.
 */
public class Book {
    private String id;
    private String volumeInfo;
    private String title;
    private String authors;
    private String industryIdentifiers;
    private String type;
    private String identifier;
    private String imageLinks;
    private String urlThumbnail;
    private String selfLink;
    private String ISBN_13;

    public Book(String id,
             String volumeInfo,
             String title,
             String authors,
             String industryIdentifiers,
             String type,
             String identifier,
             String imageLinks,
             String urlThumbnail,
             String selfLink,
             String ISBN_13){

        this.id = id;
        this.volumeInfo = volumeInfo;
        this.title = title;
        this.authors = authors;
        this.industryIdentifiers = industryIdentifiers;
        this.type = type;
        this.identifier = identifier;
        this.imageLinks = imageLinks;
        this.urlThumbnail = urlThumbnail;
        this.selfLink = selfLink;
        this.ISBN_13 = ISBN_13;
    }

    public Book() {

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getVolumeInfo(){
        return volumeInfo;
    }

    public void setVolumeInfo(String volumeInfo){
        this.volumeInfo = volumeInfo;
    }

    public String getAuthors(){
        return authors;
    }

    public void setAuthors(String authors){
        this.authors = authors;
    }

    public String getIndustryIdentifiers(){
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(String industryIdentifiers){
        this.industryIdentifiers = industryIdentifiers;
    }

    public String getISBN_13(){
        return ISBN_13;
    }

    public void setISBN_13(String ISBN_13){
        this.ISBN_13 = ISBN_13;
    }

    public String getImageLinks(){
        return imageLinks;
    }

    public void setImageLinks(String imageLinks){
        this.imageLinks = imageLinks;
    }

    public String geturlThumbnail(){
        return urlThumbnail;
    }

    public void seturlThumbnail(String urlThumbnail){
        this.urlThumbnail = urlThumbnail;
    }

    public String getSelfLink(){
        return selfLink;
    }

    public void setSelfLink(String selfLink){
        this.selfLink = selfLink;
    }

    @Override
    public String toString(){
        return "Title: "+title+
                "Author: "+authors+
                "ISBN_13: "+ISBN_13+
                "urlThumbnail "+urlThumbnail+
                "imageLinks "+imageLinks;
    }
}
