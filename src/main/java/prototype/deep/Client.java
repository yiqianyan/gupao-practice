package prototype.deep;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjun
 * @date 2019/3/22 0:07
 */
public class Client{
    public  static void main(String args[]){
        Student stu = new Student();  //创建原型对象
        stu.setName("张三");
        stu.setAge(23);
        List<String> list = new ArrayList<String>();
        list.add("打篮球");
        list.add("看电影");
        stu.setHobbies(list);

        Student stu2;
        //浅克隆，此时stu的hobbies引用和stu2的hobbies引用完全相同，对其中某一个的hobbies具体数据做修改会影响另一个
        stu2  = stu.clone();

        System.out.println(stu.getHobbies()==stu2.getHobbies());//false
    }
}

