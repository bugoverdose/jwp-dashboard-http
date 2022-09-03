package nextstep.jwp.servlet;

import nextstep.jwp.controller.ErrorController;
import nextstep.jwp.controller.GetController;
import nextstep.jwp.controller.PostController;
import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.service.UserService;
import org.apache.coyote.servlet.Servlet;
import org.apache.coyote.support.HttpException;
import org.apache.coyote.servlet.request.HttpRequest;
import org.apache.coyote.servlet.response.HttpResponse;

public class CustomServlet implements Servlet {

    private final HandlerMapper handlerMapper;
    private final ErrorController errorController;

    public CustomServlet() {
        final var resourceRegistry = new ViewResolver();
        final var handlerMappings = HandlerMappings.of(
                new GetController(resourceRegistry),
                new PostController(new UserService(new InMemoryUserRepository())));
        this.handlerMapper = new HandlerMapper(handlerMappings, resourceRegistry);
        this.errorController = new ErrorController(resourceRegistry);
    }

    public HttpResponse service(HttpRequest request) {
        try {
            return handlerMapper.handle(request);
        } catch (HttpException exception) {
            return errorController.handle(exception);
        }
    }
}
