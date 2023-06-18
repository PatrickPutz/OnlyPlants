package at.campus02.bp2.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "protocol_entry")
public class ProtocolEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "entry_text")
    private String entryText;

    @Column(name = "entry_timestamp")
    private LocalDateTime timestamp;

    public int getId() {
        return id;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
