package com.example._tproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class Member {
    private String mId;
    private String mPw;
    private String mName;
    private String phoneNum;
    private String address;
}
