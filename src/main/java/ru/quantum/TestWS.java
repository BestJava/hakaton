package ru.quantum;

import ru.quantum.client.WebSocketClient;

import java.io.IOException;

public class TestWS {

    public WebSocketClient wsc;

    public TestWS() {
    }

    public void start() throws InterruptedException, IOException {

        wsc = new WebSocketClient();
        //    wsc.callback = this;
      //  wsc.connect("ws://172.30.9.50:8080/race");
        wsc.connect("ws://172.30.9.50:3000/race");
        wsc.sendMessage("{ \"team\": \"Кванты\"}");

        while (true) {
            Thread.sleep(10000);
        }
        //wsc.Disconnect();
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        TestWS t = new TestWS();
        t.start();
        Thread.sleep(1000);
    }

}
