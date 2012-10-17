package com.lds.accounting.inputParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Row {

    private long id;

    private String summary;
    
    private String content; // orig input
    
    private String note;
    
    private double price;
    
    private Date date;
    
    /**
     * 借贷方向(true为进，false为出)
     */
    private boolean direction;  

    private Category category;
    
    private long category_id;
    
    private List<Tag> tags;
    
    private List<Contact> contacts = new ArrayList<Contact>();
    
    public Row() {}
    

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }
    

    /* GET & SET */
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public long getCategoryId() {
        return category_id;
    }


    public void setCategoryId(long category_id) {
        this.category_id = category_id;
    }


    @Override
    public String toString() {
        return "Row [id=" + id + ", summary=" + summary + ", content="
                + content + ", note=" + note + ", price=" + price + ", date="
                + date + ", direction=" + direction + ", category=" + category
                + ", category_id=" + category_id + ", tags=" + tags
                + ", contacts=" + contacts + "]";
    }
}
