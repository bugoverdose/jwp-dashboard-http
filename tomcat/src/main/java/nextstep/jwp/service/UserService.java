package nextstep.jwp.service;

import nextstep.Application;
import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.model.User;
import nextstep.jwp.service.dto.LoginDto;
import nextstep.jwp.service.dto.SaveUserDto;
import org.apache.coyote.support.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final InMemoryUserRepository userRepository;

    public UserService(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(LoginDto loginDto) {
        final var user = userRepository.findByAccount(loginDto.getAccount());
        if (user.isEmpty()) {
            throw HttpException.ofUnauthenticated();
        }
        final var foundUser = user.get();
        if (!foundUser.checkPassword(loginDto.getPassword())) {
            throw HttpException.ofUnauthenticated();
        }
        log.info("로그인 성공! - {}", user);
        return foundUser;
    }

    public void saveUser(SaveUserDto saveUserDto) {
        final var account = saveUserDto.getAccount();
        userRepository.findByAccount(account)
                .orElseThrow(HttpException::ofBadRequest);
        final var savedUser = userRepository.save(new User(account, saveUserDto.getPassword(), saveUserDto.getEmail()));
        log.info("회원가입 성공! - {}", savedUser);
    }
}
