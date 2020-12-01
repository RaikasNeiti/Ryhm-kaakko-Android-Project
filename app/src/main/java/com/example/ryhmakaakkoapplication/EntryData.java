package com.example.ryhmakaakkoapplication;

import java.util.ArrayList;

class EntryData {
    private ArrayList<Entry> entries;
    private static final EntryData ourInstance = new EntryData();

    static EntryData getInstance() {
        return ourInstance;
    }

    private EntryData() {
        entries = new ArrayList<Entry>();
    }

    public ArrayList<Entry> getArray() {
        return this.entries;
    }

    public void addToArray(Entry entry) {
        entries.add(entry);
    }

    public void getTime()   {



    }

}
