package beynd.architecture.platform.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandBaseService<T, R> implements CommandBaseService<T, R> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private CommandValidator<T> validator = command -> {}; // default no-op

    @Autowired(required = false)
    protected void setValidator(CommandValidator<T> validator) {
        if (validator != null) {
            this.validator = validator;
        }
    }

    @Override
    public CommandResult<R> handle(Command<T> command) {
        String traceId = getTraceId();
        try {
            log.info("[Command Start] {}", command);
            validator.validate(command);
            R result = execute(command);
            return CommandResult.success(result, getSource(), traceId);
        } catch (Exception ex) {
            log.error("[Command Error] {}", command, ex);
            return CommandResult.failure(ex.getMessage(), traceId);
        }
    }

    protected abstract R execute(Command<T> command);

    protected String getSource() {
        return "platform-command";
    }

    protected String getTraceId() {
        return java.util.UUID.randomUUID().toString();
    }

    public CommandResult<R> decorate(Command<T> command) {
        return new CommandDecorator<>(this).handle(command);
    }
}
