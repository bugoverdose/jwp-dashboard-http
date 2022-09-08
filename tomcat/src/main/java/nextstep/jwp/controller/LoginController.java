package nextstep.jwp.controller;

import nextstep.jwp.controller.dto.DtoAssembler;
import nextstep.jwp.service.UserService;
import org.apache.catalina.servlet.Controller;
import org.apache.catalina.servlet.RequestMapping;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.catalina.session.Session;

@RequestMapping(path = "/login")
public class LoginController extends Controller {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        final var session = request.getSession();
        if (session.hasAttribute(Session.USER_ATTRIBUTE)) {
            response.redirect("/index.html");
            return;
        }
        response.ok().setViewResource("/login.html");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        final var user = userService.login(DtoAssembler.ofLoginDto(request));
        final var session = request.getSession();
        session.setAttribute(Session.USER_ATTRIBUTE, user);
        response.redirect("/index.html");
    }
}
