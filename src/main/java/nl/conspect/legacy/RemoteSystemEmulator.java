/*
 * Copyright 2000-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.conspect.legacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by marten on 17-04-15.
 */
public class RemoteSystemEmulator implements Runnable {

    private volatile boolean running = false;
    private ServerSocket server;
    private volatile String received = null;
    private Thread emulatorThread;

    public static void main(String[] args) throws Exception {
        RemoteSystemEmulator emulator = new RemoteSystemEmulator();
        emulator.start();
    }

    public void start() throws IOException {
        this.server = new ServerSocket(2345);
        this.server.setSoTimeout(500);
        this.emulatorThread = new Thread(this);
        this.running = true;
        this.emulatorThread.start();

    }

    public void stop() throws IOException {
        if (this.server != null) {
            this.running = false;
            this.server.close();
        }
    }

    public String getReceived() {
        return this.received;
    }

    public void run() {
        Socket connection = null;
        while (this.running) {
            try {
                connection = this.server.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                this.received = reader.readLine();
                System.out.println("Received: " + this.received);
            } catch (IOException e) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e1) {
                    // Empty on purpose
                }
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                // Empty on purpose
            }
        }

        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                // Empty on purpose
            }
        }
    }
}
