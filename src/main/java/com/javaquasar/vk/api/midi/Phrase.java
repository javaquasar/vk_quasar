package com.javaquasar.vk.api.midi;

import java.util.*;

public class Phrase {

    Vector< Chord> chords = new Vector<Chord>();

    public Phrase() {
    }

    public Phrase chord(Chord c) {
        chords.add(c);
        return this;
    }
}
