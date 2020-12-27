package clock;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clocks")
public class Clock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @NotNull
    @Getter
    @Setter
    @Column
    private String name;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clocks_for_shops",
            joinColumns = {@JoinColumn(name = "clock_id")},
            inverseJoinColumns = {@JoinColumn(name = "clock_shop_id")}
    )
    private List<ClockShop> shops;

    public Clock() {
        shops = new ArrayList<>();
    }

    public Clock(String name) {
        this();
        this.name = name;
    }
}
