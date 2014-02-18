package uk.bl;

import java.util.HashMap;
import java.util.Map;

public final class Const {
  
    private Const() {}
	
	// Taxonomy
	public static final String USERS           = "users";
	public static final String ROLES           = "roles";
	public static final String PERMISSIONS     = "permissions";
	public static final String ORGANISATIONS   = "organisations";
	public static final String MAILTEMPLATES   = "mailtemplates";
	public static final String CONTACTPERSONS  = "contactpersons";
	public static final String LICENSES        = "licenses";
	public static final String LICENCE         = "license";
	public static final String COLLECTION_URL  = "collection_url";

    // Drupal connection definitions
    public static final String URI             = "uri";
    public static final String URL             = "url";
    public static final String URL_STR         = "http://www.webarchive.org.uk/act/node.json?type=";
	public static final String DRUPAL_USER     = "drupal_user";
	public static final String DRUPAL_PASSWORD = "drupal_password";
	public static final String OUT_FILE_PATH   = "res.txt"; // res files are stored in root directory of the project
	
	// Names of the JSON nodes
	public static final String FIRST_PAGE      = "first";
	public static final String LAST_PAGE       = "last";
	public static final String PAGE_IN_URL     = "page=";
	public static final String LIST_NODE       = "list";
	public static final String FIELD_URL_NODE  = "field_url";
	public static final String NODE_TYPE       = "type";
	public static final String EMPTY           = "empty";
	public static final String ITEM            = "item";
	
	// Body elements in JSON node
	public static final String VALUE              = "value";
	public static final String SUMMARY            = "summary";
	public static final String FORMAT             = "format";
	public static final String BODY               = "body";
	public static final String AUTHOR             = "author";
	public static final String FIELD_AFFILIATION  = "field_affiliation";
	public static final String FIELD_SUBJECT      = "field_subject";
	public static final String FIELD_QA_STATUS    = "field_qa_status";
	public static final String FIELD_NOMINATING_ORGANISATION = "field_nominating_organisation";
	public static final String FIELD_TARGET       = "field_target";
	public static final String FIELD_QA_ISSUE     = "field_qa_issue";
	public static final String JSON               = ".json";
	public static final String COMMA              = ",";

	public static final int STRING_LIMIT          = 50;

	// Help constants
	public static final int PAGINATION_OFFSET     = 10; // offset is a page step from current page for pagination
	public static final int ROWS_PER_PAGE         = 50;
	public static final String WEBARCHIVE_LINK    = "www.webarchive.org.uk"; 
	public static final String ACT_URL            = "act-";
	public static final String WCT_URL            = "wct-";
	public static final String EDIT_LINK          = "/edit";
	public static final String INITIAL_REVISION   = "initial revision";
	public static final String STR_FORMAT         = "UTF-8";
	public static final String CSV                = "csv";
	public static final String TARGET_DEF         = "TARGETDEF";
	public static final String OFFSET             = "offset";
	public static final String LIMIT              = "limit";
	public static final String LIST_DELIMITER     = ", ";
	public static final String SLASH_DELIMITER    = "/";
	public static final String PROJECT_PROPERTY_FILE = "conf\\w3act.properties";
	public static final String HOST               = "host";
	public static final String PORT               = "port";
	public static final String FROM               = "from";
	public static final String TAGS               = "tags";
	
	// Elements in HTML forms
	public static final String FALSE              = "false";
	public static final String TRUE               = "true";
	public static final String FILTER             = "filter";
	public static final String NONE               = "none";
	public static final String USER               = "user";

	// Target
	public static final String NID                = "nid";
	public static final String TITLE              = "title";
	public static final String FIELD_URL          = "fieldurl";
	public static final String KEYSITE            = "keysite";
	public static final String STATUS             = "status";
	public static final String LIVE_SITE_STATUS   = "livesitestatus";
	public static final String DESCRIPTION        = "description";
	public static final String SUBJECT            = "subject";
	public static final String ORGANISATION       = "organisation";
	public static final String ORIGINATING_ORGANISATION = "originating_organisation";
	public static final String FIELD_SUGGESTED_COLLECTIONS = "field_suggested_collections";
	public static final String FIELD_CRAWL_FREQUENCY = "field_crawl_frequency";
	public static final String FIELD_COLLECTION_CATEGORIES = "field_collection_categories";
	public static final String FIELD_SCOPE        = "field_scope";
	public static final String FIELD_DEPTH        = "field_depth";
	public static final String REVISION           = "revision";
	public static final String ACTIVE             = "active";
	public static final String FIELD_WCT_ID       = "wct";
	public static final String FIELD_SPT_ID       = "spt";
	public static final String FIELD_LICENSE      = "license";
	public static final String FIELD_LICENSE_NODE = "field_license";
	public static final String FIELD_UK_HOSTING   = "field_uk_hosting";
	public static final String FIELD_UK_POSTAL_ADDRESS = "field_uk_postal_address";
	public static final String FIELD_UK_POSTAL_ADDRESS_URL = "field_uk_postal_address_url";
    public static final String FIELD_VIA_CORRESPONDENCE = "field_via_correspondence";
    public static final String FIELD_NOTES        = "field_notes";
    public static final String FIELD_PROFESSIONAL_JUDGEMENT = "field_professional_judgement";
    public static final String FIELD_PROFESSIONAL_JUDGEMENT_EXP = "field_professional_judgement_exp";
    public static final String FIELD_NO_LD_CRITERIA_MET = "field_no_ld_criteria_met";
    public static final String FIELD_IGNORE_ROBOTS_TXT = "field_ignore_robots_txt";
    public static final String FIELD_CRAWL_START_DATE = "field_crawl_start_date";
    public static final String FIELD_CRAWL_END_DATE = "field_crawl_end_date";
    public static final String WHITE_LIST = "white_list";
    public static final String BLACK_LIST = "black_list";
    public static final String KEYWORDS = "keywords";
    public static final String SYNONYMS = "synonyms";
    public static final String SORTED = "sorted";
    public static final String LANGUAGE = "language";
    public static final String AUTHORS = "authors";
    public static final String FLAGS = "flags";
	public static final String DATE_OF_PUBLICATION = "date_of_publication";
	public static final String JUSTIFICATION = "justification";
	public static final String SELECTION_TYPE = "selection_type";
	public static final String SELECTOR_NOTES = "selector_notes";
	public static final String ARCHIVIST_NOTES = "archivist_notes";
	public static final String LEGACY_SITE_ID = "legacy_site_id";
    
    // Creator
	public static final String UID                = "uid";
	public static final String NAME               = "name";
	public static final String EMAIL              = "email";
	public static final String DEFAULT_ROLE       = "user";
	public static final String PASSWORD           = "password";
	public static final String USER_URL           = "user_url";

	// Taxonomy
	public static final String TYPE               = "type";
	public static final String FIELD_OWNER        = "field_owner";
	public static final String PARENT             = "parent";
	public static final String PARENTS_ALL        = "parents_all";
	
	// Organisation
	public static final String FIELD_ABBREVIATION = "field_abbreviation";
	
	// Permission
	public static final String ID                 = "id";
	public static final String PERMISSION         = "permission";
	
	// Crawl Permission
	public static final String TARGET             = "target";
	public static final String CONTACT_PERSON     = "contactPerson";
	public static final String CREATOR_USER       = "creatorUser";
	public static final String TEMPLATE           = "template";
	public static final String REQUEST_FOLLOW_UP  = "requestFollowup";
	
	public static final String DEFAULT_CRAWL_PERMISSION_STATUS = "QUEUED";
	
	// Contact Person
	public static final String POSITION          = "position";
	public static final String CONTACT_ORGANISATION = "contactOrganisation";
	public static final String PHONE             = "phone";
	public static final String POSTAL_ADDRESS    = "postalAddress";
	public static final String WEB_FORM          = "webForm";
	public static final String DEFAULT_CONTACT   = "defaultContact";
	public static final String PERMISSION_CHECKED = "permissionChecked";
	
	// Licence
	public static final String AGREE             = "agree";
	public static final String CONTENT           = "content";
	public static final String PUBLISH           = "publish";
	public static final String DATE              = "date";
	public static final String ON                = "on";
	
	// Permission Refusals
	public static final String REFUSAL_DATE      = "refusal-date";
	public static final String REASON            = "reason";

	// Communications Logging
	public static final String LOG_DATE          = "log-date";
	public static final String NOTES             = "notes";
	public static final String CURATOR           = "curator";

	// Mail Template
	public static final String TEXT              = "text";
	public static final String FROM_EMAIL        = "fromEmail";
	public static final String PLACE_HOLDERS     = "placeHolders";
	public static final String DEFAULT_EMAIL_FLAG = "defaultEmail";
	public static final String TEMPLATES_PATH    = "conf\\templates\\";
	public static final String DEFAULT_TEMPLATE  = "General";
	
	// Buttons
	public static final String SAVE               = "save";
	public static final String DELETE             = "delete";
	public static final String CLEAR              = "clear";
	public static final String EXPORT             = "export";
	public static final String EXPORT_FILE        = "export.csv";
	public static final String CSV_SEPARATOR      = ";";
	public static final String CSV_LINE_END       = "\n";
	public static final String SEARCH             = "search";
	public static final String ADDENTRY           = "addentry";
	public static final String SEND               = "send";
	public static final String SEND_ALL           = "sendall";
	public static final String SEND_SOME          = "sendsome";
	public static final String PREVIEW            = "preview";
	public static final String REJECT             = "reject";
	public static final String SUBMIT             = "submit";
	public static final String UPDATE             = "update";
	public static final String REQUEST            = "request";	

	// Sorting/Pagination
	public static final String PAGE_NO             	= "p";
	public static final String PAGE_SIZE          	= "page_size";
	public static final String SORT_BY				= "s";
	public static final String ORDER             	= "o";
	public static final String QUERY             	= "q";
	public static final String QUERY_COLLECTION    	= "query_collection";
	public static final String QUERY_QA_STATUS     	= "query_qa_status";

	// Types of the JSON nodes
	public enum NodeType {
        URL, 
		COLLECTION,
		ORGANISATION,
		USER,
		TAXONOMY,
		TAXONOMY_VOCABULARY,
		INSTANCE;
    }
	
	public enum TaxonomyType {
		COLLECTION,
		LICENSE,
		SUBJECT,
		QUALITY_ISSUE;
    }
	
	/**
	 * Records status of permission process.
	 */
	public enum CrawlPermissionStatus {
		NOT_INITIATED,
		QUEUED,
		PENDING,
		REFUSED,
		EMAIL_REJECTED,
		GRANTED;
    }
    
    /**
     * E-mail type
     */
	public enum MailTemplateType {
		PERMISSION_REQUEST,
		THANK_YOU_ONLINE_PERMISSION_FORM,
		THANK_YOU_ONLINE_NOMINATION_BY_OWNER,
		OPT_OUT;
    }
	    
	/**
	 * Types of permission refusal.
	 */
	public enum RefusalType {
		THIRD_PARTY_CONTENT,
		IMPRACTICALITY,
		INTERNAL_REASONS,
		LEGALISTIC_FORM,
		NO_REASON,
		PRIVACY,
		OTHER;
    }
    	
	/**
	 * Types of communication logs.
	 */
	public enum CommunicationLogTypes {
		EMAIL,
		PHONE,
		LETTER,
		WEB_FORM,
		CONTACT_DETAIL_REQUEST,
		OTHER;
    }
    	
	/**
	 * The predominant language of target.
	 */
	public enum TargetLanguage {
		EN,
		DE;
    }
    
	/**
	 * The flag types of target.
	 */
	public enum TargetFlags {
	    PRIORITY_PERMISSION,
	    PRIORITY_CRAWL_AND_QA, 
	    PRIORITY_QA,
	    QA_ISSUE_APPEARANCE,
	    QA_ISSUE_FUNCTIONALITY,
	    QA_ISSUE_CONTENT,
	    FOLLOW_UP_PEMISSION,
	    GENERAL_CHANGE_REQUEST;
    }
    
	public enum SelectionType {
		NOMINATION, // when created from UKWA
		SELECTION;
    }
	
	/**
	 * The QA status types.
	 */
	public enum QAStatusType {
		PASSED_PUBLISH_NO_ACTION_REQUIRED,
		FAILED_DO_NOT_PUBLISH,
		FAILED_PASS_TO_ENGINEER,
		RECRAWL_REQUESTED,
		ISSUE_NOTED;
    }
    
    /**
     * Help collections to read JSON lists like
     * "field_url":[{"url":"http:\/\/www.adoptionuk.org\/"}]
     */
    public static final Map<String, Integer> targetMap = new HashMap<String, Integer>();
    	static {
    	targetMap.put("field_url", 0);
    	targetMap.put("field_description", 1);
    	targetMap.put("field_uk_postal_address_url", 2);
    	targetMap.put("field_suggested_collections", 3);
    	targetMap.put("field_collections", 4);
    	targetMap.put("field_license", 5);
    	targetMap.put("field_collection_categories", 6);
    	targetMap.put("field_notes", 7);
    	targetMap.put("field_instances", 8);
    }
		
    public static final Map<String, Integer> collectionMap = new HashMap<String, Integer>();
    	static {
    	collectionMap.put("field_targets", 0);
    	collectionMap.put("field_sub_collections", 1);
    }
		
    public static final Map<String, Long> statusMap = new HashMap<String, Long>();
    	static {
    	statusMap.put("N/A", 1L);
    	statusMap.put("No QA issues found", 2L);
    	statusMap.put("QA issue", 3L);
    }
		
    /**
     * Help collection to read JSON sub nodes like 
     * "field_affiliation":{"uri":"http:\/\/www.webarchive.org.uk\/act\/node\/101","id":"101","resource":"node"}
     */
    public static final Map<String, String> subNodeMap = new HashMap<String, String>();
    	static {
    	subNodeMap.put(AUTHOR, URI);
    	subNodeMap.put(FIELD_AFFILIATION, URI);
    	subNodeMap.put(FIELD_NOMINATING_ORGANISATION, URI);
    	subNodeMap.put(FIELD_SUBJECT, URI);
    	subNodeMap.put(FIELD_QA_STATUS, URI);
//    	subNodeMap.put(FIELD_COLLECTION_CATEGORIES, URI);
    	subNodeMap.put(FIELD_OWNER, URI);
    	subNodeMap.put(PARENT, URI);
    	subNodeMap.put(PARENTS_ALL, URI);
    	subNodeMap.put(FIELD_TARGET, URI);
    	subNodeMap.put(FIELD_QA_ISSUE, URI);
    }
		
    /**
     * DEFINITIONS FOR TESTING  	
     */
    
    // test fields
    public static String FIELD_EMAIL = "email";
    public static String FIELD_PASSWORD = "password";
    
    // test values
    public static String DEFAULT_EMAIL = "ross.king@ait.ac.at";    	
    public static String DEFAULT_PASSWORD = "secret";  
    public static String TEST_ORGANISATIONS_URL = "http://localhost:9000/organisations";
}