package com.mmbadi.sportsbuzz.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Article implements Serializable {

    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("Links")
    @Expose
    private Object links;
    @SerializedName("Facebook")
    @Expose
    private Boolean facebook;
    @SerializedName("AuthorUrlName")
    @Expose
    private Object authorUrlName;
    @SerializedName("Headline")
    @Expose
    private String headline;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Blurb")
    @Expose
    private String blurb;
    @SerializedName("SmallImageName")
    @Expose
    private String smallImageName;
    @SerializedName("SmallImageAlt")
    @Expose
    private String smallImageAlt;
    @SerializedName("LargeImageName")
    @Expose
    private String largeImageName;
    @SerializedName("LargeImageAlt")
    @Expose
    private String largeImageAlt;
    @SerializedName("ExtraImageName")
    @Expose
    private String extraImageName;
    @SerializedName("ExtraImageAlt")
    @Expose
    private String extraImageAlt;
    @SerializedName("ImageUrlLocal")
    @Expose
    private String imageUrlLocal;
    @SerializedName("ImageUrlRemote")
    @Expose
    private String imageUrlRemote;
    @SerializedName("DateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("CategoryDisplayName")
    @Expose
    private Object categoryDisplayName;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("SiteId")
    @Expose
    private Integer siteId;
    @SerializedName("SiteName")
    @Expose
    private Object siteName;
    @SerializedName("Author")
    @Expose
    private String author;
    @SerializedName("Credit")
    @Expose
    private String credit;
    @SerializedName("CreditImageUrl")
    @Expose
    private String creditImageUrl;
    @SerializedName("CreditUrl")
    @Expose
    private String creditUrl;
    @SerializedName("UrlName")
    @Expose
    private String urlName;
    @SerializedName("LiveChat")
    @Expose
    private Boolean liveChat;
    @SerializedName("WebOnly")
    @Expose
    private Boolean webOnly;
    @SerializedName("UrlFriendlyHeadline")
    @Expose
    private String urlFriendlyHeadline;
    @SerializedName("UrlFriendlyDate")
    @Expose
    private String urlFriendlyDate;
    @SerializedName("IsMainStory")
    @Expose
    private Boolean isMainStory;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("Keywords")
    @Expose
    private String keywords;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("ValidFrom")
    @Expose
    private String validFrom;
    @SerializedName("ValidTo")
    @Expose
    private String validTo;
    @SerializedName("relatedArticles")
    @Expose
    private Object relatedArticles;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getLinks() {
        return links;
    }

    public void setLinks(Object links) {
        this.links = links;
    }

    public Boolean getFacebook() {
        return facebook;
    }

    public void setFacebook(Boolean facebook) {
        this.facebook = facebook;
    }

    public Object getAuthorUrlName() {
        return authorUrlName;
    }

    public void setAuthorUrlName(Object authorUrlName) {
        this.authorUrlName = authorUrlName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getSmallImageName() {
        return smallImageName;
    }

    public void setSmallImageName(String smallImageName) {
        this.smallImageName = smallImageName;
    }

    public String getSmallImageAlt() {
        return smallImageAlt;
    }

    public void setSmallImageAlt(String smallImageAlt) {
        this.smallImageAlt = smallImageAlt;
    }

    public String getLargeImageName() {
        return largeImageName;
    }

    public void setLargeImageName(String largeImageName) {
        this.largeImageName = largeImageName;
    }

    public String getLargeImageAlt() {
        return largeImageAlt;
    }

    public void setLargeImageAlt(String largeImageAlt) {
        this.largeImageAlt = largeImageAlt;
    }

    public String getExtraImageName() {
        return extraImageName;
    }

    public void setExtraImageName(String extraImageName) {
        this.extraImageName = extraImageName;
    }

    public String getExtraImageAlt() {
        return extraImageAlt;
    }

    public void setExtraImageAlt(String extraImageAlt) {
        this.extraImageAlt = extraImageAlt;
    }

    public String getImageUrlLocal() {
        return imageUrlLocal;
    }

    public void setImageUrlLocal(String imageUrlLocal) {
        this.imageUrlLocal = imageUrlLocal;
    }

    public String getImageUrlRemote() {
        return imageUrlRemote;
    }

    public void setImageUrlRemote(String imageUrlRemote) {
        this.imageUrlRemote = imageUrlRemote;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getCategoryDisplayName() {
        return categoryDisplayName;
    }

    public void setCategoryDisplayName(Object categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Object getSiteName() {
        return siteName;
    }

    public void setSiteName(Object siteName) {
        this.siteName = siteName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCreditImageUrl() {
        return creditImageUrl;
    }

    public void setCreditImageUrl(String creditImageUrl) {
        this.creditImageUrl = creditImageUrl;
    }

    public String getCreditUrl() {
        return creditUrl;
    }

    public void setCreditUrl(String creditUrl) {
        this.creditUrl = creditUrl;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public Boolean getLiveChat() {
        return liveChat;
    }

    public void setLiveChat(Boolean liveChat) {
        this.liveChat = liveChat;
    }

    public Boolean getWebOnly() {
        return webOnly;
    }

    public void setWebOnly(Boolean webOnly) {
        this.webOnly = webOnly;
    }

    public String getUrlFriendlyHeadline() {
        return urlFriendlyHeadline;
    }

    public void setUrlFriendlyHeadline(String urlFriendlyHeadline) {
        this.urlFriendlyHeadline = urlFriendlyHeadline;
    }

    public String getUrlFriendlyDate() {
        return urlFriendlyDate;
    }

    public void setUrlFriendlyDate(String urlFriendlyDate) {
        this.urlFriendlyDate = urlFriendlyDate;
    }

    public Boolean getIsMainStory() {
        return isMainStory;
    }

    public void setIsMainStory(Boolean isMainStory) {
        this.isMainStory = isMainStory;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public Object getRelatedArticles() {
        return relatedArticles;
    }

    public void setRelatedArticles(Object relatedArticles) {
        this.relatedArticles = relatedArticles;
    }

}
