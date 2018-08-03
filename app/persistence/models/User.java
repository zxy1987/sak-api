package persistence.models;

import play.data.validation.Constraints;

public class User extends Model {

    @Constraints.MinLength(value = 6, message = "user.error.password")
    @Constraints.Required(message = "user.error.password")
    public String password;

    @Constraints.Required(message = "user.error.email")
    @Constraints.Email(message = "user.error.email")
    public String email;

    @Constraints.Required(message = "user.error.name")
    public String name;

//    protected Map<String,String> buildDataLinks(){
//        Map<String,String> links = new HashMap<>();
//        links.put("self",routes.UserController.who(id).url());
//        return links;
//    }
}

