package com.lds.accounting.inputParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="row")
public class Row {

    @DatabaseField(generatedId=true)
    private long id;

    @DatabaseField
    private String summary;
    
    @DatabaseField
    private String content; // orig input
    
    @DatabaseField
    private String note;
    
    @DatabaseField
    private double price;
    
    @DatabaseField
    private Date date;
    
    /**
     * 借贷方向(true为进，false为出)
     */
    @DatabaseField
    private boolean direction;  

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Category> categories;
    
    @ForeignCollectionField(eager = false)
    private ForeignCollection<Tag> tags;
    
    @ForeignCollectionField(eager = false)
    private ForeignCollection<Contact> contacts;
    
    private List<Part> parts = new ArrayList<Part>();
  
    public Row() {}

    public void addPart(Part part) {
        parts.add(part);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    //

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public ForeignCollection<Category> getCategories() {
        return categories;
    }

    public void setCategories(ForeignCollection<Category> categories) {
        this.categories = categories;
    }

    public ForeignCollection<Tag> getTags() {
        return tags;
    }

    public void setTags(ForeignCollection<Tag> tags) {
        this.tags = tags;
    }

    public ForeignCollection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ForeignCollection<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return "Row [id=" + id + ", parts=" + parts + ", summary=" + summary
                + ", content=" + content + ", note=" + note + ", categories="
                + categories + ", tags=" + tags + ", contacts=" + contacts
                + ", price=" + price + ", date=" + date + ", direction="
                + direction + "]";
    }
    
}
