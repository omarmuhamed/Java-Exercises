import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        ArrayList<Student> Students = read_students();
        ArrayList<Course> Courses = read_courses();

        while (true) {
            System.out.print("""
                    1. Add course
                    2. List courses
                    3. Add a student
                    4. Search for a student
                    5. Delete a student
                    6. List all students
                    7. List all students with courses
                    8. Calculate fees
                    9. Exit
                    Selection:\s""");
            int selection = scan.nextInt();
            scan.nextLine();
            switch (selection) {
                case 1 -> AddCourse(Courses);
                case 2 -> ListCourses(Courses);
                case 3 -> AddStudent(Students, Courses);
                case 4 -> SearchStudent(Students);
                case 5 -> DeleteStudent(Students);
                case 6 -> ListStudents(Students);
                case 7 -> ListStudentsDetailed(Students);
                case 8 -> CalculateFees(Students);
                case 9 -> Exit(Students, Courses);
                default -> System.out.println("Wrong selection, try again.\n");
            }
        }
    }
    private static void Exit(ArrayList<Student> students, ArrayList<Course> courses) throws IOException {
        write_students(students);
        write_courses(courses);
        System.exit(1);
    }
    private static void CalculateFees(ArrayList<Student> students) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter student ID: ");
        int id = scan.nextInt();
        scan.nextLine();
        for (Student student: students) {
            if (student.getID() == id) {
                double sum = 0.;
                int unit = 100;
                if (student.courses.size() == 1) {
                    System.out.println("Total courses: 1");
                    System.out.println("No discount");
                    sum = unit;
                } else if (student.courses.size() == 2) {
                    System.out.println("Total courses: 2");
                    System.out.println("Campaign 1");
                    sum = unit + unit * 0.85;
                } else if (student.courses.size() == 3) {
                    System.out.println("Total courses: 3");
                    System.out.println("Campaign 2");
                    sum = 2 * unit + unit * 0.75;
                } else if (student.courses.size() > 3){
                    System.out.println("Total courses: " + student.courses.size());
                    System.out.println("Campaign 3");
                    sum = student.courses.size() * unit * 0.9;
                }
                sum *= 4;
                System.out.println("Total: " + sum);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void ListStudentsDetailed(ArrayList<Student> students) {
        for (Student student: students) {
            print_student_detailed(student);
        }
    }

    private static void ListStudents(ArrayList<Student> students) {
        for (Student student: students){
            System.out.println("ID: " + student.getID() + "\tName: " + student.getName() + "\tAge: " + student.getAge());
        }
    }

    private static void DeleteStudent(ArrayList<Student> students) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter student ID: ");
        int id = scan.nextInt();
        scan.nextLine();
        for (Student student: students) {
            if (student.getID() == id) {
                students.remove(student);
                System.out.println("Student Deleted.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void SearchStudent(ArrayList<Student> students) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter student name: ");
        String name = scan.nextLine();
        for (Student student: students) {
            if (student.getName().equalsIgnoreCase(name)) {
                print_student_detailed(student);
                return;
            }
        }
        System.out.println("Student not found.");
    }
    private static void print_student_detailed(Student student) {
        System.out.println("ID: " + student.getID() + "\tName: " + student.getName() + "\tAge: " + student.getAge() + "\n\tCourses: ");
        for (Course course: student.courses) {
            System.out.println("\t\tID: " + course.getID() + "\tName: " + course.getName());
        }
    }
    private static void AddStudent(ArrayList<Student> students, ArrayList<Course> courses) {
        Scanner scan = new Scanner(System.in);
        int id;
        int found;
        while (true) {
            found = 0;
            System.out.println("Enter student ID: ");
            id = scan.nextInt();
            scan.nextLine();
            for (Student student: students) {
                if (student.getID() == id){
                    found = 1;
                    break;
                }
            }
            if (found != 1) {
                String name;
                int age, courses_count, added, c_id;
                System.out.println("Enter student name: ");
                name = scan.nextLine();
                System.out.println("Enter student age: ");
                age = scan.nextInt();
                scan.nextLine();
                Student student = new Student(id, name, age);
                System.out.println("Enter courses count: ");
                courses_count = scan.nextInt();
                scan.nextLine();
                for (int i = 0; i < courses_count; i++) {
                    added = 0;
                    System.out.println("Enter " + (i + 1) + ". course ID: ");
                    c_id = scan.nextInt();
                    scan.nextLine();
                    for (Course course: courses) {
                        if (course.getID() == c_id) {
                            student.courses.add(course);
                            added = 1;
                            break;
                        }
                    }
                    if (added == 0) {
                        System.out.println("Course not found.");
                        i--;
                    }
                }
                students.add(student);
                break;
            } else {
                System.out.println("This ID is already used.");
            }
        }
        System.out.println("Student added successfully");
    }

    private static void ListCourses(ArrayList<Course> courses) {
        for (Course course: courses) {
            System.out.println("ID: " + course.getID() + "\tName: " + course.getName());
        }
    }

    private static void AddCourse(ArrayList<Course> courses) {
        Scanner scan = new Scanner(System.in);
        int id;
        int found;
        while (true) {
            found = 0;
            System.out.println("Enter course ID: ");
            id = scan.nextInt();
            scan.nextLine();
            for (Course course: courses){
                if (course.getID() == id) {
                    found = 1;
                    break;
                }
            }
            if (found != 1) {
                System.out.println("Enter course name: ");
                courses.add(new Course(id, scan.nextLine()));
                break;
            } else {
                System.out.println("This ID is already used.");
            }
        }
        System.out.println("Course added successfully");
    }

    private static FileWriter file_writer(String name) throws IOException {
        File file = new File(name);
        return new FileWriter(file, false);
    }

   private static void write_courses(ArrayList<Course> courses) throws IOException {
        FileWriter fr = file_writer("courses.txt");
        StringBuilder content = new StringBuilder();
        for (Course course: courses) {
            content.append(course.getID()).append("-").append(course.getName()).append("\n");
        }
        fr.write(content.toString());
        fr.close();
   }
    private static void write_students(ArrayList<Student> students) throws IOException {
        FileWriter fr = file_writer("students.txt");
        StringBuilder content = new StringBuilder();
        for (Student student: students) {
            content.append("#").append(student.getID()).append("-").append(student.getName()).append("-").append(student.getAge()).append("\n");
            for (Course course: student.courses) {
                content.append("*").append(course.getID()).append("-").append(course.getName()).append("\n");
            }
        }
        fr.write(content.toString());
        fr.close();
    }
    private static ArrayList<Course> read_courses() throws IOException {
        ArrayList<Course> courses = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("courses.txt"));
        String temp;
        String[] Temp;
        while ((temp = reader.readLine()) != null) {
            Temp = temp.split("-");
            courses.add(new Course(Integer.parseInt(Temp[0]), Temp[1]));
        }
        reader.close();
        return courses;
    }
    private static ArrayList<Student> read_students() throws IOException {
        ArrayList<Student> Students = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
        Student student;
        String temp;
        String[] Temp;
        while ((temp = reader.readLine()) != null) {
            if (temp.contains("#")) {
                temp = temp.substring(1);
                Temp = temp.split("-");
                student = new Student(Integer.parseInt(Temp[0]), Temp[1], Integer.parseInt(Temp[2]));
                Students.add(student);
            } else {
                temp = temp.substring(1);
                Temp = temp.split("-");
                Students.get(Students.size() - 1).courses.add(new Course(Integer.parseInt(Temp[0]), Temp[1]));
            }
        }
        reader.close();
        return Students;
    }
}
