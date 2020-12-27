package clock;

import clock.Clock;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clock_shops")
@ToString
public class ClockShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clock_shop_id")
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
            joinColumns = {@JoinColumn(name = "clock_shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "clock_id")}
    )
    private List<Clock> clocks;

    public ClockShop() {
        clocks = new ArrayList<>();
    }

    public ClockShop(String name) {
       this();
       this.name = name;
    }
}