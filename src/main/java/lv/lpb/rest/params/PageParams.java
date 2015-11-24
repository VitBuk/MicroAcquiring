package lv.lpb.rest.params;

import javax.ws.rs.QueryParam;

public class PageParams {
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String SORT = "sort";
    public static final String ORDER = "order";
    
    public @QueryParam("offset") Integer offset; 
    public @QueryParam("limit") Integer limit;            
    public @QueryParam("sort") String sortParams;
    public @QueryParam("order") String order;
}
