package beynd.architecture.platform.command;

@FunctionalInterface
public interface CommandValidator<T> {
    void validate(Command<T> command);
}
