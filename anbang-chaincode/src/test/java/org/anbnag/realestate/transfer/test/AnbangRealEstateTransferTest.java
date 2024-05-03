package org.anbnag.realestate.transfer.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.anbang.realestate.transfer.AnbangRealEstate;
import org.anbang.realestate.transfer.AnbangRealEstateTransfer;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
final class AnbangRealEstateTransferTest {
    private final class MockKeyValue implements KeyValue {

        private final String key;
        private final String value;

        MockKeyValue(final String key, final String value) {
            super();
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public String getStringValue() {
            return this.value;
        }

        @Override
        public byte[] getValue() {
            return this.value.getBytes();
        }
    }

    private final class MockAssetResultsIterator implements QueryResultsIterator<KeyValue> {
        private final List<KeyValue> realtyList;

        MockAssetResultsIterator(final List<KeyValue> results) {

            super();

            realtyList = new ArrayList<KeyValue>();

            realtyList.add(new MockKeyValue("home1",
                    "{\"homeID\":\"home1\",\"owner\":\"owner1\",\"address\":\"address1\",\"price\":\"1000\"}" ));
            realtyList.add(new MockKeyValue("home2",
                    "{\"homeID\":\"home2\",\"owner\":\"owner2\",\"address\":\"address2\",\"price\":\"2000\"}" ));
            realtyList.add(new MockKeyValue("home3",
                    "{\"homeID\":\"home3\",\"owner\":\"owner3\",\"address\":\"address3\",\"price\":\"3000\"}" ));
            realtyList.add(new MockKeyValue("home4",
                    "{\"homeID\":\"home4\",\"owner\":\"owner4\",\"address\":\"address4\",\"price\":\"4000\"}" ));

        }

        @Override
        public Iterator<KeyValue> iterator() {
            return realtyList.iterator();
        }

        @Override
        public void close() throws Exception {
            // do nothing
        }
    }

    @Test
    void invokeUnknownTransaction() {
        AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
        Context ctx = mock(Context.class);

        Throwable thrown = catchThrowable(() -> {
            contract.unknownTransaction(ctx);
        });
        assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause().hasMessage("Undefined contract method called" );
        assertThat(((ChaincodeException) thrown).getPayload()).isNull();

        verifyZeroInteractions(ctx);
    }

    @Nested
    class InvokeCreateAssetTransaction {
        @Test
        void whenAssetExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("home1" ))
                    .thenReturn("{\"homeID\":\"home1\",\"owner\":\"owner1\",\"address\":\"address1\",\"price\":\"1000\"}" );

            Throwable thrown = catchThrowable(() -> {
                contract.createAnbangRealEstate(ctx, "home1", "owner2", "address2", "2000");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause().hasMessage("home1은 이미 존재하는 매물입니다." );
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("ASSET_ALREADY_EXISTS".getBytes());
        }

        @Test
        void whenAssetNotExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("home1" )).thenReturn("" );

            AnbangRealEstate anbangRealEstate = contract.createAnbangRealEstate(ctx, "home1", "owner1", "address1", "1000");

            assertThat(anbangRealEstate).isEqualTo(new AnbangRealEstate("home1", "owner1", "address1", "1000"));
        }
    }
    @Nested
    class InvokeReadAssetTransaction {
        @Test
        void whenAssetExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("home1" ))
                    .thenReturn("{\"homeID\":\"home1\",\"owner\":\"owner1\",\"address\":\"address1\",\"price\":\"1000\"}" );

            AnbangRealEstate anbangRealEstate = contract.readAnbangRealEstate(ctx, "home1");

            assertThat(anbangRealEstate).isEqualTo(new AnbangRealEstate("home1", "owner1", "address1", "1000"));
        }

        @Test
        void whenAssetNotExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("home1" )).thenReturn("" );

            Throwable thrown = catchThrowable(() -> {
                contract.readAnbangRealEstate(ctx, "home1");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause().hasMessage("home1은 존재하지 않는 매물입니다." );
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("ASSET_NOT_FOUND".getBytes());
        }
    }
    @Nested
    class UpdateAssetTransaction {
        @Test
        void whenAssetExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("home1" ))
                    .thenReturn("{\"homeID\":\"home1\",\"owner\":\"owner1\",\"address\":\"address1\",\"price\":\"1000\"}" );

            AnbangRealEstate anbangRealEstate = contract.updateAnbangRealEstate(ctx, "home1", "owner2", "address2", "2000");

            assertThat(anbangRealEstate).isEqualTo(new AnbangRealEstate("home1", "owner2", "address2", "2000"));
        }

        @Test
        void whenAssetNotExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("home1" )).thenReturn("" );

            Throwable thrown = catchThrowable(() -> {
                contract.updateAnbangRealEstate(ctx, "home1", "owner1", "address1", "1000");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause().hasMessage("home1은 존재하지 않는 매물입니다." );
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("ASSET_NOT_FOUND".getBytes());
        }
    }
    @Nested
    class TransferAssetTransaction {
        @Test
        void whenAssetExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("home1" ))
                    .thenReturn("{\"homeID\":\"home1\",\"owner\":\"owner1\",\"address\":\"address1\",\"price\":\"1000\"}" );

            String newOwner = contract.transferAnbangRealEstate(ctx, "home1", "owner2");
            assertThat(newOwner).isEqualTo("owner2");
        }

        @Test
        void whenAssetNotExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("home1" )).thenReturn("" );

            Throwable thrown = catchThrowable(() -> {
                contract.transferAnbangRealEstate(ctx, "home1", "owner1");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause().hasMessage("home1은 존재하지 않는 매물입니다." );
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("ASSET_NOT_FOUND".getBytes());
        }
    }
    @Test
    void invokeQueryAllAssetsTransaction() {
        AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);
        when(stub.getStateByRange("", "")).thenReturn(new MockAssetResultsIterator(null));

        String realty = contract.queryAllAnbangRealEstate(ctx);

        assertThat(realty).isEqualTo("[{\"address\":\"address1\",\"homeID\":\"home1\",\"owner\":\"owner1\",\"price\":\"1000\"}," +
                "{\"address\":\"address2\",\"homeID\":\"home2\",\"owner\":\"owner2\",\"price\":\"2000\"}," +
                "{\"address\":\"address3\",\"homeID\":\"home3\",\"owner\":\"owner3\",\"price\":\"3000\"}," +
                "{\"address\":\"address4\",\"homeID\":\"home4\",\"owner\":\"owner4\",\"price\":\"4000\"}]");
    }
    @Nested
    class DeleteAssetTransaction {
        @Test
        void whenAssetNotExists() {
            AnbangRealEstateTransfer contract = new AnbangRealEstateTransfer();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getStringState("home1" )).thenReturn("" );

            Throwable thrown = catchThrowable(() -> {
                contract.deleteAnbangRealEstate(ctx, "home1");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause().hasMessage("home1은 존재하지 않는 매물입니다." );
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("ASSET_NOT_FOUND".getBytes());
        }
    }
}
