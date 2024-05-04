package org.anbnag.realestate.transfer.test;

import org.anbang.realestate.transfer.AnbangRealEstate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class AnbangRealEstateTest {
    @Nested
    class Equlity {
        @Test
        void isReflexive() {
            AnbangRealEstate anbangRealEstate = new AnbangRealEstate("homeID", "owner", "address", "1000");
            assertThat(anbangRealEstate).isEqualTo(anbangRealEstate);
        }
        @Test
        void isSymmetric() {
            AnbangRealEstate anbangRealEstate1 = new AnbangRealEstate("homeID", "owner", "address", "1000");
            AnbangRealEstate anbangRealEstate2 = new AnbangRealEstate("homeID", "owner", "address", "1000");
            assertThat(anbangRealEstate1).isEqualTo(anbangRealEstate2);
            assertThat(anbangRealEstate2).isEqualTo(anbangRealEstate1);
        }
        @Test
        void isTransitive() {
            AnbangRealEstate anbangRealEstate1 = new AnbangRealEstate("homeID", "owner", "address", "1000");
            AnbangRealEstate anbangRealEstate2 = new AnbangRealEstate("homeID", "owner", "address", "1000");
            AnbangRealEstate anbangRealEstate3 = new AnbangRealEstate("homeID", "owner", "address", "1000");
            assertThat(anbangRealEstate1).isEqualTo(anbangRealEstate2);
            assertThat(anbangRealEstate2).isEqualTo(anbangRealEstate3);
            assertThat(anbangRealEstate1).isEqualTo(anbangRealEstate3);
        }

        @Test
        void handlesInequality() {
            AnbangRealEstate anbangRealEstate1 = new AnbangRealEstate("homeID", "owner", "address", "1000");
            AnbangRealEstate anbangRealEstate2 = new AnbangRealEstate("homeID", "owner", "address", "2000");
            assertThat(anbangRealEstate1).isNotEqualTo(anbangRealEstate2);
        }

        @Test
        void handlesOtherObjects() {
            AnbangRealEstate anbangRealEstate1 = new AnbangRealEstate("homeID", "owner", "address", "1000");
            String anbangRealEstate2 = "homeIDowneraddress1000";
            assertThat(anbangRealEstate1).isNotEqualTo(anbangRealEstate2);
        }

        @Test
        void handlesNull() {
            AnbangRealEstate anbangRealEstate = new AnbangRealEstate("homeID", "owner", "address", "1000");
            assertThat(anbangRealEstate).isNotNull();
        }

        @Test
        void toStringWorks() {
            AnbangRealEstate anbangRealEstate = new AnbangRealEstate("homeID", "owner", "address", "1000");
            assertThat(anbangRealEstate.toString()).isEqualTo("AnbangRealEstate@f596e7e5 [homeID=homeID, owner=owner, address=address, price=1000]");
        }
    }
}
