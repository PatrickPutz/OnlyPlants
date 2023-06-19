package at.campus02.bp2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "need_list")
public class NeedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "need_list_id")
    private List<Need> needs = new ArrayList<>();
    
    public List<Need> getNeeds(){
    	return needs;
    }
    
    public void addNeed(Need need) {
    	needs.add(need);
    }
    
    public void removeNeed(Need need) {
    	needs.remove(need);
    }
}