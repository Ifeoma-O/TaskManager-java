import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ManageTasks taskmanager = new ManageTasks();
        while (true){
            System.out.println("\n******To do List******");
            System.out.println("Enter a number to perform a task");
            System.out.println("1. Add a task");
            System.out.println("2. View tasks");
            System.out.println("3. Mark task as completed");

            int choice = input.nextInt();

            switch (choice) {
                case 1 -> taskmanager.addTask();
                case 2 -> taskmanager.viewTask();
                case 3 -> taskmanager.markAsCompleted();
                default -> System.out.println("Invalid input. Try again");
            }
        }

    }
}

class Task{
    public String title;
    public String description;
    boolean completed;

    public Task(String title, String description, boolean completed){
        this.title = title;
        this.description = description;
        this.completed =  false;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return title;
    }

    public boolean isCompleted (){
        return completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

}

class ManageTasks{
    static List<Task> tasks = new ArrayList<>();

    Scanner input = new Scanner(System.in);
    List<Task>[] completed;

    public void addTask(){
        System.out.println("Enter the task title");
        String title = input.nextLine();
        System.out.println("Enter the task description");
        String description = input.nextLine();
        Task newTask = new Task(title, description, false);
        tasks.add(newTask);
        System.out.println("Tasks added successfully");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt", true))){
            writer.write("Title: " + title + "|" + "Description: " + description + "|" + "Completed status: " + completed +  System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error writing task to file: " + e.getMessage());
        }
    }

    public void viewTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
                System.out.println("Tasks: ");
                String line;
                int i = 1;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    String title = parts[0];
                    String description = parts[1];
                    boolean completed = Boolean.parseBoolean(parts[2]);

                    System.out.println("Task " + i);
                    System.out.println("Title: " + title);
                    System.out.println("Description: " + description);
                    System.out.println("Completed: " + completed);
                    i++;
                }
            } catch (IOException e) {
                System.err.println("Error reading tasks from file: " + e.getMessage());
            }
        }
    }

    public void markAsCompleted(){
        System.out.println("Enter the index of the task you want to mark as completed");
        int index = input.nextInt();
        if(tasks.isEmpty()){
            System.out.println("No tasks available");
        }
        else if(index >= 0 && index < tasks.size()){
            tasks.get(index).setCompleted(true);
            updateFile();
            System.out.println("Task status updated successfully");
        }
        else {
            System.out.println("Invalid index. No task found at index " + index);
        }
    }

    private void updateFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks){
                writer.write(task.getTitle() + "|" + task.getDescription() + "|" + task.isCompleted());
            }
        } catch (IOException e){
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


}