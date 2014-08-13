package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import uk.bl.Const;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class allows archivist to manage nominations.
 */
@Entity
@Table(name = "nomination")
public class Nomination extends Model
{

	/**
	 * file id
	 */
	private static final long serialVersionUID = -2357699575463702989L;

	@Id 
    public Long id;
   
    /**
     * This field with prefix "act-" builds an unique identifier in W3ACT database.
     */
    @Column(columnDefinition = "TEXT")
    public String url;
	
    /**
     * The name of the nomination. Derived from UKWA Nomination Form > Full name
     */
    @Required
    @Column(columnDefinition = "TEXT")
    public String name;
    
    /**
     * The title of the nomination. Derived from UKWA Nomination Form > Title of website
     */
    @Required
    @Column(columnDefinition = "TEXT")
    public String title;
    
    /**
     * The URL of the nomination. Derived from UKWA Nomination Form > URL of website
     */
    @Required
    @Column(columnDefinition = "TEXT")
    public String website_url;
    
    /**
     * The email of the nomination. Derived from UKWA Nomination Form > Email address
     */
    @Required
    @Column(columnDefinition = "TEXT")
    public String email;
    
    /**
     * The telephone number of the nomination. Derived from UKWA Nomination Form > Telephone number
     */
    @Column(columnDefinition = "TEXT")
    public String tel;
    
    /**
     * The address of the nomination. Derived from UKWA Nomination Form > Address
     */
    @Column(columnDefinition = "TEXT")
    public String address;
    
    /**
     * The nominated website owner of the nomination. Indicates whether nominator is also site
     * owner or not. Derived from UKWA Nomination Form > 
     * Are you the copyright holder or owner of the website?
     */
    public Boolean nominated_website_owner;
    
    /**
     * The justification of the nomination. Derived from UKWA Nomination Form > Your justification...
     */
    @Column(columnDefinition = "TEXT")
    public String justification;
    
    /**
     * Allows the notes regarding nomination description. 
     * Derived from UKWA Nomination Form > Notes about your...
     */
    @Column(columnDefinition = "TEXT")
    public String notes;

    /**
     * The date of the nomination. System generated.
     */
    public String nomination_date;
    
    /**
     * Indicates that the new nomination has been inspected by Archivist.
     */
    @JsonIgnore
    public Boolean nomination_checked;
    
    @Version
    @JsonIgnore
    public Timestamp lastUpdate;

    public static final Model.Finder<Long, Nomination> find = new Model.Finder<Long, Nomination>(Long.class, Nomination.class);

    public Nomination() {
    	super();
    }
    
    public String getName()
    {
        return name;
    }

    public static Nomination findByName(String name)
    {
        return find.where()
                   .eq("name",
                       name)
                   .findUnique();
    }

    /**
     * Retrieve an object by Id (id).
     * @param nid
     * @return object 
     */
    public static Nomination findById(Long id) {
    	Nomination res = find.where().eq(Const.ID, id).findUnique();
    	return res;
    }          
        
    /**
     * Retrieve a permission refusal by URL.
     * @param url
     * @return permission refusal name
     */
    public static Nomination findByUrl(String url) {
    	Nomination res = new Nomination();
    	if (url != null && url.length() > 0 && !url.equals(Const.NONE)) {
    		res = find.where().eq(Const.URL, url).findUnique();
    	} else {
    		res.name = Const.NONE;
    	}
    	return res;
    }
    
	/**
	 * This method filters permission refusals by name and returns a list 
	 * of filtered Nomination objects.
	 * @param name
	 * @return
	 */
	public static List<Nomination> filterByName(String name) {
		List<Nomination> res = new ArrayList<Nomination>();
        ExpressionList<Nomination> ll = find.where().icontains(Const.NAME, name);
    	res = ll.findList();
		return res;
	}
        
    /**
     * Retrieve all nominations.
     */
    public static List<Nomination> findAll() {
        return find.all();
    }
    
    public String toString() {
        return "Nomination(" + name + ")" + " id:" + id;
    }
    
    /**
     * Return a page of User
     *
     * @param page Page to display
     * @param pageSize Number of Nominations per page
     * @param sortBy User property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Nomination> page(int page, int pageSize, String sortBy, String order, String filter) {

        return find.where().icontains("name", filter)
        		.orderBy(sortBy + " " + order)
        		.findPagingList(pageSize)
        		.setFetchAhead(false)
        		.getPage(page);
    }    

}