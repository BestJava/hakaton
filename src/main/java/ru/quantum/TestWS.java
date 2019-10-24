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
        wsc.connect("ws://localhost:8080/race");
        wsc.sendMessage("{ \"team\": \"Имя команды1\"}");


        Thread.sleep(10000);
        wsc.Disconnect();
    }


    public static void main(String[] args) throws InterruptedException, IOException
    {
        TestWS t = new TestWS();
        t.start();
        Thread.sleep(1000);
    }

}
