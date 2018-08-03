package persistence.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

 public abstract class Model {
    public Long id;
    public Date createdTime = new Date();
    public Date updatedTime = new Date();

//     protected Map<String,Object> buildDataMap(){
//         Map<String,Object> data = new HashMap<>();
//         Map<String,String> links = buildDataLinks();
//         data.put("data",this);
//         data.put("links",links);
//         return data;
//     }

//    abstract protected Map<String,String> buildDataLinks();

}
