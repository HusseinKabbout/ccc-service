package ch.so.agi.cccservice;

import ch.so.agi.cccservice.messages.AppConnectMessage;
import ch.so.agi.cccservice.messages.GisConnectMessage;
import ch.so.agi.cccservice.messages.ReadyMessage;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.net.SocketException;

import static org.junit.Assert.*;

public class ServiceTest {

    private SessionPool sessionPool = new SessionPool();
    private String expectedAppName = "App-Name";
    private String expectedGisName = "GIS-Name";
    private String sessionString = "{123-456-789-0}";
    private String apiVersion = "1.0";
    private SocketSender socketSender = new SocketSender();


    @Test
    public void appConnect() throws Exception {
        SessionId sessionId = new SessionId(sessionString);

        SessionState sessionState = new SessionState();
        Service service = new Service(sessionPool, socketSender);

        AppConnectMessage appConnectMessage = generateAppConnectMessage(sessionId, apiVersion);

        sessionPool.addSession(sessionId, sessionState);

        service.appConnect(appConnectMessage);

        String state = sessionState.getState();
        String appName = sessionState.getAppName();

        Assert.assertEquals(sessionState.CONNECTED_TO_APP, state);
        Assert.assertEquals(expectedAppName,appName);

    }

    private AppConnectMessage generateAppConnectMessage(SessionId sessionId, String apiVersion){
        AppConnectMessage appConnectMessage = new AppConnectMessage();
        appConnectMessage.setClientName(expectedAppName);
        appConnectMessage.setSession(sessionId);
        appConnectMessage.setApiVersion(apiVersion);

        return appConnectMessage;
    }

    @Test
    public void gisConnect() throws Exception {
        SessionId sessionId = new SessionId(sessionString);

        SessionState sessionState = new SessionState();
        Service service = new Service(sessionPool, socketSender);

        AppConnectMessage appConnectMessage = generateAppConnectMessage(sessionId, apiVersion);

        GisConnectMessage gisConnectMessage = generateGisConnectMessage(sessionId, apiVersion);

        sessionPool.addSession(sessionId, sessionState);

        service.appConnect(appConnectMessage);
        service.gisConnect(gisConnectMessage);

        String state = sessionState.getState();
        String appName = sessionState.getAppName();
        String gisName = sessionState.getGisName();

        Assert.assertEquals(sessionState.CONNECTED_TO_GIS, state);
        Assert.assertEquals(expectedAppName,appName);
        Assert.assertEquals(expectedGisName, gisName);
    }

    private GisConnectMessage generateGisConnectMessage(SessionId sessionId, String apiVersion){
        GisConnectMessage gisConnectMessage = new GisConnectMessage();
        gisConnectMessage.setClientName(expectedGisName);
        gisConnectMessage.setSession(sessionId);
        gisConnectMessage.setApiVersion(apiVersion);

        return gisConnectMessage;
    }


    @Ignore
    @Test
    public void ready() throws Exception {
        SessionId sessionId = new SessionId(sessionString);

        SessionState sessionState = new SessionState();
        Service service = new Service(sessionPool, socketSender);
        //SocketSender erstellen, der Nachricht empfängt und wieder ausgeben kann.

        AppConnectMessage appConnectMessage = generateAppConnectMessage(sessionId, apiVersion);

        GisConnectMessage gisConnectMessage = generateGisConnectMessage(sessionId, apiVersion);

        ReadyMessage readyMessage = new ReadyMessage();
        readyMessage.setApiVersion(apiVersion);

        sessionPool.addSession(sessionId, sessionState);

        service.appConnect(appConnectMessage);
        service.gisConnect(gisConnectMessage);
        service.ready(sessionId,readyMessage);

        String state = sessionState.getState();
        String appName = sessionState.getAppName();
        String gisName = sessionState.getGisName();

        Assert.assertEquals(sessionState.READY, state);
        Assert.assertEquals(expectedAppName,appName);
        Assert.assertEquals(expectedGisName, gisName);
    }

    @Test
    public void create() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void show() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void changed() {
    }

    @Test
    public void selected() {
    }

    @Test
    public void dataWritten() {
    }

    @Test (expected=IllegalArgumentException.class)
    public void failsWithWrongApiVersionOnAppConnect() throws Exception{
        SessionId sessionId = new SessionId(sessionString);

        String apiVersion = "2.0";

        SessionState sessionState = new SessionState();
        Service service = new Service(sessionPool, socketSender);

        AppConnectMessage appConnectMessage = generateAppConnectMessage(sessionId, apiVersion);
        sessionPool.addSession(sessionId, sessionState);

        service.appConnect(appConnectMessage);


    }

    @Test (expected=IllegalArgumentException.class)
    public void failsWithWrongApiVersionOnGisConnect() throws Exception{
        SessionId sessionId = new SessionId(sessionString);

        String wrongApiVersion= "2.0";

        SessionState sessionState = new SessionState();
        Service service = new Service(sessionPool, socketSender);
        AppConnectMessage appConnectMessage = generateAppConnectMessage(sessionId, apiVersion);

        sessionPool.addSession(sessionId, sessionState);

        service.appConnect(appConnectMessage);

        GisConnectMessage gisConnectMessage = generateGisConnectMessage(sessionId, wrongApiVersion);

        service.gisConnect(gisConnectMessage);


    }
}