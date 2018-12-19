package com.example.daszh.myapplication;

import java.util.ArrayList;

public class FoodInfo {
    public ArrayList<Food> myfood = new ArrayList<Food>();
    public int current;
    FoodInfo() {
        myfood.add(new Food("大豆", "粮", "粮食", "蛋白质", "#BB4C3B"));
        myfood.add(new Food("十字花科蔬菜", "蔬", "蔬菜", "维生素C", "#C48D30"));
        myfood.add(new Food("牛奶", "饮", "饮品", "钙", "#4469B0"));
        myfood.add(new Food("海鱼", "肉", "肉食", "蛋白质", "#20A17B"));
        myfood.add(new Food("菌菇类", "蔬", "蔬菜", "微量元素", "#BB4C3B"));
        myfood.add(new Food("番茄", "蔬", "蔬菜", "番茄红素", "#4469B0"));
        myfood.add(new Food("胡萝卜", "蔬", "蔬菜", "胡萝卜素", "#20A17B"));
        myfood.add(new Food("荞麦", "粮", "粮食", "膳食纤维", "#BB4C3B"));
        myfood.add(new Food("鸡蛋", "杂", "杂", "几乎所有营养", "#C48D30"));
    }
    FoodInfo(ArrayList<Food> f) {
        this.myfood = f;

    }
    public int findpos(String nm) {
        for (int i = 0; i < myfood.size(); i++) {
            if (myfood.get(i).getName().equals(nm)) {
                return i;
            }
        }
        return -1;
    }
    public void Delfood(String nm) {
        myfood.remove(findpos(nm));
    }
    public void collect(String nm) {
        myfood.get(findpos(nm)).collect++;
    }
    public void uncollect(String nm) {
        myfood.get(findpos(nm)).collect--;
    }
}
