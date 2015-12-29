package lv.lpb.rest.params;

import java.util.List;

public class Exporter {
    private Integer count;
    private List list;
    
    public Exporter() {}
    public Exporter(List list) {
        this.list = list;
        count = list.size();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
