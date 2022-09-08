package nextstep.jwp.controller;

import org.apache.catalina.servlet.Controller;
import org.apache.catalina.servlet.RequestMapping;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.HttpResponse;

@RequestMapping(path = {"/", "/index"})
public class HomeController extends Controller {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        response.ok().setViewResource("/index.html");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        throw new UnsupportedOperationException("API not implemented");
    }
}
