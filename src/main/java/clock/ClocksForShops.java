package clock;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ToString
@Table(name = "clocks_for_shops")
public class ClocksForShops {
    @Getter
    @Setter
    @EmbeddedId
    private ClocksForShopsKey key;

    @ToString
    @EqualsAndHashCode
    @Embeddable
    public static class ClocksForShopsKey implements Serializable {
        static final long serialVersionUID = 1L;

        @Getter
        @Setter
        @Column(name = "clock_shop_id")
        private int clockShopId;

        @Getter
        @Setter
        @Column(name = "clock_id")
        private int clockId;

        ClocksForShopsKey(int clockShopId, int clockId) {
            this.clockId = clockId;
            this.clockShopId = clockShopId;
        }

        public ClocksForShopsKey() {}
    }

    public ClocksForShops(int clockShopId, int clockId) {
        key = new ClocksForShopsKey(clockShopId, clockId);
    }

    public ClocksForShops() {}
}
