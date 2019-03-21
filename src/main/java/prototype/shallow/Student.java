package prototype.shallow;

import java.util.List;

/**
 * @author zhangjun
 * @date 2019/3/22 0:06
 */
public class Student implements Cloneable {//需要实现Cloneable接口
    //java中默认深克隆的有8种基本类型以及它们的封装类型，另外还有String类型。其余的都是浅克隆，比如此处的List
    private String name;
    private int age;
    private List<String> hobbies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    @Override//重写
    public Student clone() {//返回Student
        Object obj = null;
        try {
            obj = super.clone();//直接使用Object的clone方法，为浅克隆
            return (Student) obj;
        } catch (CloneNotSupportedException e) {//CloneNotSupportedException
            return null;
        }
    }
}
