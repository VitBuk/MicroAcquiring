package lv.lpb.rest.params;

import javax.ws.rs.QueryParam;

public class PageParams {
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String SORT = "sort";
    public static final String ORDER = "order";
    
    public @QueryParam(OFFSET) Integer offset; 
    public @QueryParam(LIMIT) Integer limit;            
    public @QueryParam(SORT) String sort;
    public @QueryParam(ORDER) String order;
}
