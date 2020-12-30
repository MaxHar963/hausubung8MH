/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Producer implements Callable<Integer> {

    private final String name;
    private final Storage storage;
    private final int sleepTime;

    private final List<Integer> sent;
    private final int numberOfItems;

    public Producer(String name, Storage storage, int sleepTime, int numberOfItems) {
        this.name = name;
        this.storage = storage;
        this.sleepTime = sleepTime;
        this.numberOfItems = numberOfItems;
        sent = new ArrayList<>();
    }

    // implement this
    public List<Integer> getSent() {

        return sent;
    }

    @Override
    public Integer call() throws Exception {
        for (int i = 0; i < numberOfItems; i++) {

            if (storage.put(i)) {
                sent.add(i);

            } else {
                boolean x = true;
                while (x) {

                    Thread.sleep(sleepTime);
                    if (storage.put(i)) {
                        sent.add(i);
                        x = false;
                    }

                }

            }

        }

        storage.setProductionComplete();

        return 0;
    }

}
