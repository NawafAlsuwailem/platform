package beynd.architecture.platform.command;

/**
 * Generic contract for all command operations.
 * Enforces the use of Command<T> as input and CommandResult<R> as output.
 */
public interface CommandBaseService<T, R> {
    CommandResult<R> handle(Command<T> command);
}
