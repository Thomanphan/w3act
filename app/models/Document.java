package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.avaje.ebean.Query;
import com.fasterxml.jackson.annotation.JsonIgnore;

import controllers.Portals;
import controllers.Users;
import play.Play;
import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.db.ebean.Model;

@Entity
public class Document extends Model {
	
    @Id
    public Long id;
	@ManyToOne @JsonIgnore
	@JoinColumn(name="id_watched_target")
	public WatchedTarget watchedTarget;
	public String waybackTimestamp;
	public Status status;
	public Date currentStatusSet;
	@OneToOne(mappedBy="document", cascade=CascadeType.REMOVE) @JsonIgnore
	public Book book;
	@OneToOne(mappedBy="document", cascade=CascadeType.REMOVE) @JsonIgnore
	public Journal journal;
	@ManyToMany(cascade=CascadeType.ALL) @JsonIgnore
	@JoinTable(name = "portal_document",
			joinColumns = { @JoinColumn(name = "id_document", referencedColumnName="id") },
			inverseJoinColumns = { @JoinColumn(name = "id_portal", referencedColumnName="id") })
	public List<Portal> portals = new ArrayList<>();
	@ManyToMany(cascade=CascadeType.REMOVE) @JsonIgnore
	@JoinTable(name = "fast_subject_document",
		joinColumns = { @JoinColumn(name = "id_document", referencedColumnName="id") },
		inverseJoinColumns = { @JoinColumn(name = "id_fast_subject", referencedColumnName="id") })
	public List<FastSubject> fastSubjects = new ArrayList<>();
    public String landingPageUrl;
    public String documentUrl;
    public String sha256Hash;
    public String ctpHash;
    @Required
    @Column(columnDefinition = "text")
	public String title;
	public String doi;
	public String ark;
	@DateTime(pattern="dd-MM-yyyy")
	public Date publicationDate;
	@Required
    public Integer publicationYear;
    @Required
	public String filename;
    public Long size;
    public boolean priorityCataloguing;
    public String type;
	public String author1Fn;
	public String author1Ln;
	public String author2Fn;
	public String author2Ln;
	public String author3Fn;
	public String author3Ln;
    public String getUrl() { return ""+id; }
    
    public static final Model.Finder<Long, Document> find = new Model.Finder<>(Long.class, Document.class);
    
    public String getHtmlFilename() {
    	return id + ".html";
    }
    
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        String required = "This field is required";
        if (isJournalArticleOrIssue()) {
        	if (journal.journalTitleId == null)
                errors.add(new ValidationError("journal.journalTitleId", required));
        	if (journal.volume.isEmpty() && journal.issue.isEmpty())
                errors.add(new ValidationError("journal.issue", "Complete Volume or Issue/Part or both"));
        } else if (isWholeBook()) {
        	if (book.publisher.isEmpty())
        		errors.add(new ValidationError("book.publisher", required));
        }
        return errors.isEmpty() ? null : errors;
    }
    
    public boolean isWholeBook() { return type != null && type.equals(Type.BOOK.toString()); }
    
    public boolean isBookChapter() { return type != null && type.equals(Type.BOOK_CHAPTER.toString()); }
    
    public boolean isJournalArticle() { return type != null && type.equals(Type.JOURNAL_ARTICLE.toString()); }
    
    public boolean isJournalIssue() { return type != null && type.equals(Type.JOURNAL_ISSUE.toString()); }
	
	public boolean isBookOrBookChapter() { return isWholeBook() || isBookChapter(); }
	
	public boolean isJournalArticleOrIssue() { return isJournalArticle() || isJournalIssue(); }
    
	public boolean hasPermissionForService(Portal portal) {
		List<License> licenses = watchedTarget.target.licenses;
		if (licenses.isEmpty())
			return false;
		else
			return licenses.get(0).portals.contains(portal);
	}
	
    public void clearImproperFields() {
    	if (isJournalIssue()) {
    		author1Fn = author1Ln = author2Fn = author2Ln = author3Fn = author3Ln = "";
    	}
    }
    
    public static Query<Document> query(DocumentFilter documentFilter, String sortBy, String order, String filter) {

    	ExpressionList<Document> el = find.where();
    	if (documentFilter.watchedtarget != null) {
    		el = el.eq("id_watched_target", documentFilter.watchedtarget);
    	} else if (documentFilter.user != null) {
    		el = el.in("id_watched_target", Users.getWatchedTargetIds(documentFilter.user));
    	}
    	if (documentFilter.service != null && !documentFilter.service.isEmpty()) {
    		el = el.in("id", Portals.getDocumentIds(documentFilter.service));
    	}
    	if (documentFilter.fastSubjects != null && !documentFilter.fastSubjects.isEmpty()) {
    		el = el.in("fastSubjects.fastId", documentFilter.fastSubjects);
    	}
    	SimpleDateFormat waybackFormat = new SimpleDateFormat("yyyyMMdd");
    	if (documentFilter.startdate != null) {
    		el = el.gt("waybackTimestamp", waybackFormat.format(documentFilter.startdate));
    	}
    	if (documentFilter.enddate != null) {
    		el = el.lt("waybackTimestamp", waybackFormat.format(documentFilter.enddate));
    	}
    	
    	return el.eq("status", documentFilter.status.ordinal())
        		.icontains("title", filter)
        		.orderBy(sortBy + " " + order);
    }
    
    public static Page<Document> page(DocumentFilter documentFilter, int page,
    		int pageSize, String sortBy, String order, String filter) {
    	return query(documentFilter, sortBy, order, filter)
        		.findPagingList(pageSize)
        		.setFetchAhead(false)
        		.getPage(page);
    }

	public void setStatus(Status status) {
		this.status = status;
		currentStatusSet = new Date();
	}
	
	public String currentStatusSetUTCString() {
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormatGmt.format(currentStatusSet);
	}
	
	public String waybackUrl() {
		return Play.application().configuration().getString("wayback_url") +
				waybackTimestamp + "/" + documentUrl;
	}

	public enum Type {
    	BOOK ("Book"),
    	BOOK_CHAPTER ("Book Chapter"),
    	JOURNAL_ARTICLE ("Journal Article"),
    	JOURNAL_ISSUE ("Journal Issue");
    	
    	private final String name;
    	
    	Type(String name) {
    		this.name = name;
    	}
    	
    	public String toString() {
    		return name;
    	}
    }
    
	public enum Status {
		NEW,
		SUBMITTED,
		IGNORED,
		DELETED;
    }
    
}