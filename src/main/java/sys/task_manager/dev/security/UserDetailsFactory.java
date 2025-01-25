package sys.task_manager.dev.security;


import sys.task_manager.dev.models.User;

public final class UserDetailsFactory {

    public static UserDetailsImpl create(final User person) {

        return new UserDetailsImpl(
                person.getId(),
                person.getEmail(),
                person.getName(),
                person.getPassword()
        );
    }

}
