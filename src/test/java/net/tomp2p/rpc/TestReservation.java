package net.tomp2p.rpc;

import net.tomp2p.connection.ChannelCreator;
import net.tomp2p.futures.FutureChannelCreator;
import net.tomp2p.futures.FutureResponse;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerMaker;
import net.tomp2p.peers.Number160;

import org.junit.Assert;
import org.junit.Test;

public class TestReservation {

    /*
     * @Test public void testReservationTCPL() throws Exception { for(int
     * i=0;i<100;i++) testReservationTCP(); }
     */

    @Test
    public void testReservationTCP() throws Exception {
        Peer sender = null;
        Peer recv1 = null;
        try {
            sender = new PeerMaker(new Number160("0x9876")).setP2PId(55).setPorts(2424).makeAndListen();
            recv1 = new PeerMaker(new Number160("0x1234")).setP2PId(55).setPorts(8088).makeAndListen();
            FutureChannelCreator fcc = recv1.getConnectionBean().getConnectionReservation().reserve(3);
            fcc.awaitUninterruptibly();
            ChannelCreator cc = fcc.getChannelCreator();
            for (int i = 0; i < 1000; i++) {
                FutureResponse fr1 = sender.getHandshakeRPC().pingTCP(recv1.getPeerAddress(), cc);
                FutureResponse fr2 = sender.getHandshakeRPC().pingTCP(recv1.getPeerAddress(), cc);
                FutureResponse fr3 = sender.getHandshakeRPC().pingTCP(recv1.getPeerAddress(), cc);
                fr1.awaitUninterruptibly();
                fr2.awaitUninterruptibly();
                fr3.awaitUninterruptibly();
                System.err.println(fr1.getFailedReason() + " / " + fr2.getFailedReason() + " / "
                        + fr3.getFailedReason());
                Assert.assertEquals(true, fr1.isSuccess());
                Assert.assertEquals(true, fr2.isSuccess());
                Assert.assertEquals(true, fr3.isSuccess());
            }
            recv1.getConnectionBean().getConnectionReservation().release(cc);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (sender != null)
                sender.halt();
            if (recv1 != null)
                recv1.halt();
        }
    }

    /*
     * @Test public void testReservationUDPL() throws Exception { for(int
     * i=0;i<100;i++) testReservationUDP(); }
     */

    @Test
    public void testReservationUDP() throws Exception {
        Peer sender = null;
        Peer recv1 = null;
        try {
            sender = new PeerMaker(new Number160("0x9876")).setP2PId(55).setPorts(2424).makeAndListen();
            recv1 = new PeerMaker(new Number160("0x1234")).setP2PId(55).setPorts(8088).makeAndListen();
            FutureChannelCreator fcc = recv1.getConnectionBean().getConnectionReservation().reserve(3);
            fcc.awaitUninterruptibly();
            ChannelCreator cc = fcc.getChannelCreator();
            for (int i = 0; i < 1000; i++) {
                FutureResponse fr1 = sender.getHandshakeRPC().pingUDP(recv1.getPeerAddress(), cc);
                FutureResponse fr2 = sender.getHandshakeRPC().pingUDP(recv1.getPeerAddress(), cc);
                FutureResponse fr3 = sender.getHandshakeRPC().pingUDP(recv1.getPeerAddress(), cc);
                fr1.awaitUninterruptibly();
                fr2.awaitUninterruptibly();
                fr3.awaitUninterruptibly();
                System.err.println(fr1.getFailedReason() + " / " + fr2.getFailedReason() + " / "
                        + fr3.getFailedReason());
                Assert.assertEquals(true, fr1.isSuccess());
                Assert.assertEquals(true, fr2.isSuccess());
                Assert.assertEquals(true, fr3.isSuccess());
            }
            recv1.getConnectionBean().getConnectionReservation().release(cc);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (sender != null)
                sender.halt();
            if (recv1 != null)
                recv1.halt();
        }
    }
}
