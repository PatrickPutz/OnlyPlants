package at.campus02.bp2.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Need")
public class Need {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "entry_text")
    private String needTitle;

    @Column(name = "reminder")
    private LocalDateTime reminder;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNeedTitle() {
		return needTitle;
	}

	public void setNeedTitle(String needTitle) {
		this.needTitle = needTitle;
	}

	public LocalDateTime getReminder() {
        return reminder;
    }

    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
    }
}