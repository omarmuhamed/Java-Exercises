import java.util.ArrayList;

public class Student {
    private int ID;
    private String Name;
    private int Age;
    public ArrayList<Course> courses;

    public Student(int ID, String name, int age) {
        this.ID = ID;
        Name = name;
        Age = age;
        this.courses = new ArrayList<Course>();
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }




}
