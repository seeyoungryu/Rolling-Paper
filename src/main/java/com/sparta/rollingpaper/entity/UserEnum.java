package com.sparta.rollingpaper.entity;

public enum UserEnum {
    USER1("이상휴"),
    USER2("김대환"),
    USER3("홍준영"),
    USER4("박나원"),
    USER5("하다연"),
    USER6("홍동현"),
    USER7("한덕용"),
    USER8("문민희"),
    USER9("전아영"),
    USER10("박서윤"),
    USER11("원찬희"),
    USER12("류시영"),
    USER13("김지나"),
    USER14("이찬영"),
    USER15("유재현"),
    USER16("정윤영"),
    USER17("노경민"),
    USER18("강현지"),
    USER19("주철민"),
    USER20("송지우"),
    USER21("윤문열");

    private final String userName;

    UserEnum(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public static boolean contains(String userName) {
        for (UserEnum allowedUserName : UserEnum.values()) {
            if (allowedUserName.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
