package clock;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
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

    @NotNull
    @Getter
    @Setter
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Time time;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "clocks_for_shops",
            joinColumns = {@JoinColumn(name = "clock_id")},
            inverseJoinColumns = {@JoinColumn(name = "clock_shop_id")}
    )
    private List<ClockShop> shop;

    public Clock() {}

    public Clock(String name) {
        this.name = name;
    }
}
