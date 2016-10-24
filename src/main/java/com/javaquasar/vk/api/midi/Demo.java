package com.javaquasar.vk.api.midi;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Demo extends JFrame{
    int bassVoice=Note.i35_Fretless_Bass;
    Drum hat=new Drum(4, Drum.d42_Closed_Hi_Hat,127);
    Drum snare=new Drum(4, Drum.d38_Acoustic_Snare);
    Drum bass=new Drum(4, Drum.d35_Acoustic_Bass_Drum);
    int guitarVoice=Note.i30_Distortion_Guitar;
    Note bullet=new Note(4,Note.p96_8_Do,Note.i127_Gunshot);
    Phrase p1=new Phrase()                    
                    .chord(new Chord(8).drum(hat).drum(bass)	.note(8, Note.p28_2_Mi, bassVoice))
                    .chord(new Chord(8).drum(hat)               .note(8, Note.p28_2_Mi, bassVoice))
                    .chord(new Chord(8).drum(hat).drum(snare)	.note(8, Note.p28_2_Mi, bassVoice))
                    .chord(new Chord(8).drum(hat)               .note(8, Note.p28_2_Mi, bassVoice))
                    .chord(new Chord(8).drum(hat).drum(bass)	.note(8, Note.p28_2_Mi, bassVoice))
                    .chord(new Chord(8).drum(hat).drum(bass)	.note(8, Note.p28_2_Mi, bassVoice))
                    .chord(new Chord(8).drum(hat).drum(snare)	.note(8, Note.p34_2_La_Diese, bassVoice))
                    .chord(new Chord(8).drum(hat)		.note(8, Note.p35_2_Si, bassVoice));
    Ticker ti = new Ticker(120, p1){
	public void onTick(long count) {
	    if(count==0){
		System.out.println("begin "+new Date());
		}
	    }
	};    
    Ticker gunSerie=new Ticker(180,new Phrase()
            .chord(new Chord(8).note(bullet))
            .chord(new Chord(8).note(bullet))
            .chord(new Chord(8).note(bullet))
            .chord(new Chord(8).note(bullet))
            .chord(new Chord(8).note(bullet))
            ){
        @Override public void onFinish() {
            stop();
            }
        };
    JButton play=new JButton("Play");
    JButton stop=new JButton("Stop");
    JButton gun=new JButton("Gun");
    JButton tweet=new JButton("Tweet");
    JButton mgun=new JButton("Machine Gun");
    JButton clap=new JButton("Clap");
    
    public Demo(){
		Tools.initSynthesizer();
		ti.restart();
		this.setSize(500,150);
		this.setTitle("EasyMIDI test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.add(stop);
		this.add(play);
		this.add(gun);
		this.add(mgun);
		this.add(tweet);
		this.add(clap);
		stop.addActionListener(new ActionListener(){
				@Override public void actionPerformed(ActionEvent e) {
				ti.stop();
				}
			});
		play.addActionListener(new ActionListener(){
				@Override public void actionPerformed(ActionEvent e) {
				ti.restart();
				}
			});
		gun.addActionListener(new ActionListener(){
				@Override public void actionPerformed(ActionEvent e) {
				Tools.playNote( Note.p93_7_La, Note.i127_Gunshot,127, 2000);
				}
			});
		mgun.addActionListener(new ActionListener(){
				@Override public void actionPerformed(ActionEvent e) {
				gunSerie.restart();
				}
			});
		tweet.addActionListener(new ActionListener(){
				@Override public void actionPerformed(ActionEvent e) {
				Tools.playNote( Note.p69_5_La,Note.i123_Bird_Tweet, 99, 3000);
				}
			});
		clap.addActionListener(new ActionListener(){
				@Override public void actionPerformed(ActionEvent e) {
				Tools.playDrum(Drum.d39_Hand_Clap, 127, 2000);
				}
			});	
		this.setVisible(true);
		}
    public static void main(String[] args) {
		new Demo();
    }
}
