package com.lc.springcloud.controller;


import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;

import java.io.File;

public class Test {

    public static void main(String[] args) throws Exception {
        String sourcePath = "E:\\music\\昭和ロマンス.m4a";
        String targetPath = "E:\\music\\mp3\\昭和aaa.mp3";
        changeToMp3(sourcePath, targetPath);
    }

    public static void changeToMp3(String sourcePath, String targetPath) {
        Long startTime = System.currentTimeMillis();
        File source = new File(sourcePath);
        File target = new File(targetPath);
        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();
        audio.setBitRate(new Integer(128000)); //设置比特率
        audio.setSamplingRate(new Integer(8000)); //设置采样率
        audio.setChannels(new Integer(1)); //设置音频通道数
        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        MultimediaObject mediaobject = new MultimediaObject(source);

        try {
            encoder.encode(mediaobject, target, attrs);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }

        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

}
