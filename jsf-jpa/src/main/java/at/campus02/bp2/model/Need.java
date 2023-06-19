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
}