package com.example.yangyuanhao.myapplication;

import android.graphics.Bitmap;

public class Hero {
    String name;
    String title;
    String profession;
    int health;
    int attack;
    int skill;
    int difficulty;
    String skill1;
    String skill2;
    String skill3;
    String skill4;
    Bitmap icon;
    //构造函数，依次为名称，称号，职业，生命，攻击，技能，难度，技能1，技能2，技能3，技能4，介绍
    public Hero(String nm, String tt, String pro, int hp, int atk, int sk, int diff, String sk1, String sk2, String sk3, String sk4, Bitmap d) {
        this.name = nm;
        this.title = tt;
        this.profession = pro;
        this.health = hp;
        this.attack = atk;
        this.skill = sk;
        this.difficulty = diff;
        this.skill1 = sk1;
        this.skill2 = sk2;
        this.skill3 = sk3;
        this.skill4 = sk4;
        this.icon = d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getSkill1() {
        return skill1;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }

    public String getSkill4() {
        return skill4;
    }

    public void setSkill4(String skill4) {
        this.skill4 = skill4;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Bitmap getIcon() {
        return icon;
    }
}
