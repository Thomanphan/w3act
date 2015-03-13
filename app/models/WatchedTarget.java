package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;
import uk.bl.Const;

@Entity
public class WatchedTarget extends Model {
	@Id
	public Long id;
	@OneToOne @JsonIgnore
	@JoinColumn(name="id_target")
	public Target target;
	@OneToMany(mappedBy="watchedTarget", cascade=CascadeType.REMOVE) @JsonIgnore
	public List<Document> documents;
    @OneToMany(mappedBy="watchedTarget", cascade=CascadeType.REMOVE) @JsonIgnore
    public List<JournalTitle> journalTitles = new ArrayList<>();
	public String documentUrlScheme;
	public String waybackTimestamp;
	public String getUrl() { return ""+id; }
	public String getName() { return target.fieldUrls.get(0).url; }
	public static final String SEARCH_FIELD = "target.title";
	
	public static final Model.Finder<Long, WatchedTarget> find = new Model.Finder<>(Long.class, WatchedTarget.class);
	
    public WatchedTarget(Target target, String documentUrlScheme) {
		this.target = target;
		this.documentUrlScheme = documentUrlScheme;
	}
	public static Page<WatchedTarget> page(Long userId, boolean children,
			int page, int pageSize, String sortBy, String order, String filter) {
		
		ExpressionList<WatchedTarget> el = find.fetch("target").where();
		
		if (userId != null) {
			if (children) {
				List<Target> ownedTargets = Target.find.where().eq(Const.ACTIVE, true).eq("author_id", userId).findList();
				Expression expr = Expr.eq("target.authorUser.id", userId);
				for (Target ownedTarget : ownedTargets) {
					String[] urlParts = ownedTarget.fieldUrls.get(0).url.split("//", 2);
					String urlWithoutProtocol = urlParts.length == 2 ?
							ownedTarget.fieldUrls.get(0).url.split("//", 2)[1] :
							ownedTarget.fieldUrls.get(0).url;
					expr = Expr.or(Expr.icontains("target.fieldUrls.url", urlWithoutProtocol), expr);
				}
				el = el.add(expr);
			} else {
				el = el.eq("target.authorUser.id", userId);
			}
		}
		
        return el.icontains(SEARCH_FIELD, filter)
        		.orderBy(sortBy + " " + order)
        		.findPagingList(pageSize)
        		.setFetchAhead(false)
        		.getPage(page);
    }
}