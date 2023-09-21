package com.tuling.Test;

import lombok.Data;

@Data
public class FieldDto {

    private String descr;

    private String name;

    private Integer seq;

    public FieldDto(String descr, String name, Integer seq) {
        this.descr = descr;
        this.name = name;
        this.seq = seq;
    }

    @Override
    public String toString(){
        return "name："+this.name+"-descr:"+this.descr+"-seq"+this.seq;
    }
}
