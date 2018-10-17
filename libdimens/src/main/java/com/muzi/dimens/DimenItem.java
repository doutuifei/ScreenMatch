package com.muzi.dimens;


public class DimenItem {
    public String name;
    public String value;

    @Override
    public String toString() {
        return "<dimen name=\"" + name + "\">" + value + "</dimen>";
    }
}
