package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;

public class BaseController extends Controller {

    private Logger LOG = LoggerFactory.getLogger(UserController.class);

    private static final String API_VERSION = "1.0";

    private static final String encoding_utf8 = "utf-8";

    /**
     * 生成成功结果JsonNode
     */
    protected JsonNode buildSuccessJsonNode(Object data, String message) throws Exception {
        if (data != null) {
            return Json.parse(views.html.json.success.render(API_VERSION, Json.mapper().
                    writeValueAsString(data), message).toString());
        } else {
            return Json.parse(views.html.json.success.render(API_VERSION, Json.newObject().toString(),
                    message).toString());
        }
    }


//    /**
//     * 生成Form验证失败结果JsonNode
//     */
//    protected JsonNode buildFailtureJsonNode(Form formWithErrors, String message) throws Exception {
//        return Json.parse(views.html.json.failure.render(API_VERSION, Json.mapper().
//                writeValueAsString(this.getErrors(formWithErrors)), message).toString());
//    }

    /**
     * 生成业务处理失败结果JsonNode
     */
    protected JsonNode buildFailtureJsonNode(Form formWithErrors, Exception e, String message) {

        if (formWithErrors == null && e == null && message == null) {
            throw new IllegalArgumentException();
        }

        List<Error> errors = new ArrayList<>();

        if (formWithErrors != null) {
            List<Error> _errors = this.getErrors(formWithErrors);
            if (_errors != null) {
                errors.addAll(_errors);
            }
        }

        Error error = new Error();

        if (e == null) {
            error.message = message;
            error.key = "";
            errors.add(error);
        } else {
            error.message = e.toString();
            error.key = "";
            errors.add(error);
        }

        try {
            return Json.parse(views.html.json.failure.render(API_VERSION, Json.mapper().
                    writeValueAsString(errors), message).toString());
        } catch (JsonProcessingException e1) {
            LOG.error(e1.getMessage());
            return Json.newArray();
        }
    }

    protected List<Error> getErrors(Form form) {
        List<Error> _errors = new ArrayList<>();
        if (form.hasErrors()) {
            List<ValidationError> errors = form.allErrors();
            errors.forEach((error) -> {
                Error _error = new Error();
                _error.key = error.key();
                _error.message = Http.Context.current().messages().at(error.message());
                _errors.add(_error);
            });
        }
        return _errors;
    }

    class Error {
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        String key;
        String message;
    }
}
