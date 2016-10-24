package com.javaquasar.vk.api.midi;

import java.util.*;

public class Chord {

    Vector<Drum> drums = new Vector<Drum>();
    Vector< Note> notes = new Vector< Note>();
    int part = 4;
    int counter16 = 0;
    public Chord(int p) {
	part = p;
    }
    public Chord drum(Drum d) {
	drums.add(d);
	return this;
    }
    public Chord drum(int p, int i, int v) {
	return drum(new Drum(p, i, v));
    }
    public Chord drum(int p, int i) {
	return drum(new Drum(p, i));
    }
    public Chord note(Note n) {
	notes.add(n);
	return this;
    }
    public Chord note(int prt, int ptch, int i, int v) {
	return note(new Note(prt, ptch, i, v));
    }
    public Chord note(int prt, int ptch, int i) {
	return note(new Note(prt, ptch, i));
    }
    void tick() {
	for (int i = 0; i < drums.size(); i++) {
	    drums.get(i).tick();
	}
	for (int i = 0; i < notes.size(); i++) {
	    notes.get(i).tick();
	}
    }
    void stop() {
	counter16 = 0;
	for (int i = 0; i < drums.size(); i++) {
	    drums.get(i).stop();
	}
	for (int i = 0; i < notes.size(); i++) {
	    notes.get(i).stop();
	}
    }
    void play() {
	counter16 = 0;
	for (int i = 0; i < drums.size(); i++) {
	    drums.get(i).play();
	}
	for (int i = 0; i < notes.size(); i++) {
	    notes.get(i).play();
	}
    }
}
