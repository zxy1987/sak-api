package controllers;


import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import persistence.models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.libs.ws.WSClient;
import play.mvc.Result;
import services.UserService;

public class UserController extends BaseController {

    private Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Inject
    private FormFactory formFactory;

    @Inject
    private UserService userService;

    @Inject
    private WSClient wsClient;

    @AddCSRFToken
    public Result register() {
        Form<User> regForm = formFactory.form(User.class).bindFromRequest();
        try {
            if (regForm.hasErrors()) {
                JsonNode result = buildFailtureJsonNode(regForm, null, "error occur!");
                return badRequest(result);
            } else {
                User regUser = regForm.get();

                //password must be encrypted
                Long userId = userService.addUser(regUser);
                JsonNode result = buildSuccessJsonNode(regUser, "User Object!");
//                System.out.println(CSRF.getToken(request()).map(CSRF.Token::value).orElse("no-token"));
                return ok(result);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            JsonNode result = buildFailtureJsonNode(null, e, e.toString());
            return internalServerError(result);
        }
    }

    public Result login() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String email = requestData.get("email");
        String password = requestData.get("password");
        User user = userService.authenticate(email, password);
        try {
            if (user != null && user.email.equals(email)) {
                return ok(buildSuccessJsonNode(user, "登录成功啦！"));
            } else {
                return ok(buildFailtureJsonNode(null, null, "用户或密码不正确，请重新输入！"));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            JsonNode result = buildFailtureJsonNode(null, e, e.toString());
            return internalServerError(result);
        }
    }

    public Result who(Long id) {
        User user = userService.getUser(id);

        try {
            JsonNode result = buildSuccessJsonNode(user, "User Object!");
            return ok(result);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            JsonNode result = buildFailtureJsonNode(null, e, e.toString());
            return internalServerError(result);
        }
    }
}
