import java.util.Scanner;
import java.util.ArrayList;

public class RoundRobin {

    public static void RrSchedule(ArrayList<process> p, int n, int quantum, int ct[], ArrayList<Integer> start, ArrayList<Integer> end, ArrayList<Integer> order) {
        int remainTime[] = new int[n];
        int e = 0;
        for (process i : p) {
            remainTime[e] = i.getBurst();
            e++;
        }
        int t = 0, arrival = 0, timer = 0, old = 0;
        while (true) {
            boolean finish = true;
            e = 0;
            for (process i : p) {
                if (remainTime[e] > 0) {
                    finish = false;
                    if (remainTime[e] > quantum && i.getArrive() <= arrival) {
                        t += quantum;
                        remainTime[e] -= quantum;
                        order.add(i.getID());
                        timer += quantum;
                        start.add(old);
                        end.add(timer);
                        old = timer;
                        arrival++;
                        ct[e] = t;
                    } else if (remainTime[e] <= quantum && i.getArrive() <= arrival) {
                        t += quantum;
                        remainTime[e] -= quantum;
                        old = timer;
                        order.add(i.getID());
                        timer += quantum;
                        start.add(old);
                        if (remainTime[e] <= 0) {
                            end.add(timer + remainTime[e]);
                            old = timer + remainTime[e];
                        } else {
                            end.add(timer);
                            old = timer;
                        }
                        arrival++;
                        ct[e] = t;
                    } else {
                        if (i.getArrive() <= arrival) {
                            arrival++;
                            t += remainTime[e];
                            order.add(i.getID());
                            timer += quantum;
                            start.add(old);
                            end.add(timer);
                            old = timer;
                            remainTime[e] = 0;
                            ct[e] = t;
                        }
                    }
                }
                e++;
            }
            if (finish == true) {
                break;
            }
        }
    }

    public static void TurnAround(ArrayList<process> p, int w_time[], int n, int ta_time[], int completion_time[]) {
        int e = 0;
        for (process i : p) {
            ta_time[e] = completion_time[e] - i.getArrive();
            w_time[e] = ta_time[e] - i.getBurst();
            e++;
        }
    }

    public static void printProcessOrder(ArrayList<Integer> order, ArrayList<Integer> start, ArrayList<Integer> end, int n) {
        System.out.println("--------------------------------------");
        System.out.println("|\tProcess Order by Round Robin\t|");
        System.out.println("--------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println("Process " + order.get(i) + ": starts at-> " + start.get(i) + ", ends at-> " + end.get(i));
        }
    }

    public static void printRR(ArrayList<process> p, int n, int quantum) {
        int waitingTime[] = new int[n];
        int TurnAroundTime[] = new int[n];
        int CompletionTime[] = new int[n];
        ArrayList<Integer> start = new ArrayList<Integer>();
        ArrayList<Integer> end = new ArrayList<Integer>();
        ArrayList<Integer> order = new ArrayList<Integer>();

        RrSchedule(p, n, quantum, CompletionTime, start, end, order);
        TurnAround(p, waitingTime, n, TurnAroundTime, CompletionTime);
        int size = start.size();
        printProcessOrder(order, start, end, size);

        int total_wt = 0, total_tat = 0;

        System.out.println("Processes\t" + "Name\t" + " Arrival Time\t" + "  Burst Time\t" + " Completion Time\t" + " Turn Around Time\t" + " Waiting time\t");
        int e = 0;
        for (process i : p) {
            total_wt = total_wt + waitingTime[e];
            total_tat = total_tat + TurnAroundTime[e];
            System.out.println(i.getID() + "\t\t\t" + i.getName() + "\t\t\t" + i.getArrive() + "\t\t\t" + +i.getBurst() + "\t\t\t" + CompletionTime[e] + "\t\t\t" + TurnAroundTime[e] + "\t\t\t" + waitingTime[e]);
            e++;
        }

        System.out.println("Average Waiting Time = " + (float) total_wt / (float) n);
        System.out.println("Average Turn Around Time = " + (float) total_tat / (float) n);
    }

    public static void main(String args[]) {
        ArrayList<process> Processes = new ArrayList<process>();
        /*int N, Q;
        Scanner num = new Scanner(System.in);
        Scanner nn = new Scanner(System.in);
        Scanner process = new Scanner(System.in);
        System.out.println("What is the number of processes to execute ?");
        N = num.nextInt();
        System.out.println("Enter Round robin Time Quantum: ");
        Q = num.nextInt();
        String name;
        int burstTime, arrivalTime;
        for (int i = 1; i <= N; i++) {
            System.out.println("Enter Process " + i + " Name: ");
            name = nn.nextLine();
            System.out.println("Enter Arrival Time of Process: ");
            arrivalTime = process.nextInt();
            System.out.println("Enter Burst Time of Process: ");
            burstTime = process.nextInt();
            Processes.add(new process(i, name, burstTime, arrivalTime));
        }*/

        Processes.add(new process(1, "P1", 5, 0));
        Processes.add(new process(2, "P2", 4, 1));
        Processes.add(new process(3, "P3", 2, 2));
        Processes.add(new process(4, "P4", 1, 3));
        int N = Processes.size();
        printRR(Processes, N, 2);
    }
}
