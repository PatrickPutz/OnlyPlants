import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.event.NamedEvent;

@NamedEvent
@ViewScoped
public class ClockView implements Serializable {

	private static final long serialVersionUID = -9042194771439383891L;
	private LocalDateTime dateTime;

    @PostConstruct
    public void init() {
        dateTime = LocalDateTime.now().plusYears(37).plusMonths(3).plusHours(4);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}