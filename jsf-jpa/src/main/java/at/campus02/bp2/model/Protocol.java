package at.campus02.bp2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "protocol")
public class Protocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "protocol_id")
    private List<ProtocolEntry> entries = new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<ProtocolEntry> getEntries() {
        return entries;
    }

    public void addEntry(ProtocolEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(ProtocolEntry entry) {
        entries.remove(entry);
    }
}
