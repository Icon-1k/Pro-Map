import java.util.*;

public class CriticalPath{
    static class Task {
        String name;
        double duration;
        List<Task> dependencies;
        List<Task> dependents;
        double earliestStart;
        double earliestFinish;
        double latestStart;
        double latestFinish;
        double slack;
        boolean isCritical;

        public Task(String name, double duration) {
            this.name = name;
            this.duration = duration;
            this.dependencies = new ArrayList<>();
            this.dependents = new ArrayList<>();
            this.earliestStart = 0;
            this.earliestFinish = 0;
            this.latestStart = Double.MAX_VALUE;
            this.latestFinish = Double.MAX_VALUE;
            this.slack = 0;
            this.isCritical = false;
        }

        public void addDependency(Task task) {
            dependencies.add(task);
            task.dependents.add(this);
        }
    }

    public static List<Task> analyze(Graph graph, List<Nodes> waypoints) {
        // Convert graph nodes to tasks
        List<Task> tasks = createTasks(graph, waypoints);
        
        if (tasks.isEmpty()) {
            return tasks;
        }

        // Forward pass - Calculate earliest start/finish times
        forwardPass(tasks.get(0));
        
        // Backward pass - Calculate latest start/finish times
        backwardPass(tasks.get(tasks.size() - 1));
        
        // Calculate slack and identify critical path
        calculateSlackAndCriticalPath(tasks);
        
        return tasks;
    }

    private static List<Task> createTasks(Graph graph, List<Nodes> waypoints) {
        Map<String, Task> taskMap = new HashMap<>();
        List<Task> tasks = new ArrayList<>();
        
        if (waypoints == null || waypoints.isEmpty()) {
            return tasks;
        }
        
        // Create tasks for each waypoint
        for (Nodes node : waypoints) {
            Task task = new Task(node.name, 0);
            taskMap.put(node.name, task);
            tasks.add(task);
        }
        
        // Set up dependencies and durations
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Nodes current = waypoints.get(i);
            Nodes next = waypoints.get(i + 1);
            Task currentTask = taskMap.get(current.name);
            Task nextTask = taskMap.get(next.name);
            
            // Find edge between current and next nodes
            Edge edge = findEdge(current, next);
            if (edge != null) {
                currentTask.duration = edge.weight;
                nextTask.addDependency(currentTask);
            }
        }
        
        return tasks;
    }

    private static Edge findEdge(Nodes source, Nodes destination) {
        if (source == null || destination == null || source.edges == null) {
            return null;
        }
        
        for (Edge edge : source.edges) {
            if (edge.destination == destination) {
                return edge;
            }
        }
        return null;
    }

    private static void forwardPass(Task startTask) {
        if (startTask == null) return;
        
        startTask.earliestStart = 0;
        Queue<Task> queue = new LinkedList<>();
        queue.offer(startTask);
        Set<Task> visited = new HashSet<>();
        
        while (!queue.isEmpty()) {
            Task task = queue.poll();
            if (visited.contains(task)) continue;
            visited.add(task);
            
            // Calculate earliest finish time
            task.earliestFinish = task.earliestStart + task.duration;
            
            // Update dependent tasks
            for (Task dependent : task.dependents) {
                dependent.earliestStart = Math.max(
                    dependent.earliestStart,
                    task.earliestFinish
                );
                queue.offer(dependent);
            }
        }
    }

    private static void backwardPass(Task endTask) {
        if (endTask == null) return;
        
        endTask.latestFinish = endTask.earliestFinish;
        Queue<Task> queue = new LinkedList<>();
        queue.offer(endTask);
        Set<Task> visited = new HashSet<>();
        
        while (!queue.isEmpty()) {
            Task task = queue.poll();
            if (visited.contains(task)) continue;
            visited.add(task);
            
            task.latestStart = task.latestFinish - task.duration;
            
            // Update dependencies
            for (Task dependency : task.dependencies) {
                dependency.latestFinish = Math.min(
                    dependency.latestFinish,
                    task.latestStart
                );
                queue.offer(dependency);
            }
        }
    }

    private static void calculateSlackAndCriticalPath(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return;
        
        double minSlack = Double.MAX_VALUE;
        
        // Calculate slack for all tasks
        for (Task task : tasks) {
            task.slack = task.latestStart - task.earliestStart;
            minSlack = Math.min(minSlack, task.slack);
        }
        
        // Mark critical path (tasks with minimum slack)
        for (Task task : tasks) {
            task.isCritical = (Math.abs(task.slack - minSlack) < 0.0001);
        }
    }

    public static String formatResults(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return "No tasks to analyze.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("Critical Path Analysis:\n\n");
        
        // Table header
        result.append(String.format("%-20s %-10s %-10s %-10s %-10s %-10s %-10s %s\n",
            "Task", "Duration", "E.Start", "E.Finish", "L.Start", "L.Finish", "Slack", "Critical"));
        result.append("-".repeat(100)).append("\n");
        
        // Task details
        for (Task task : tasks) {
            result.append(String.format("%-20s %-10.2f %-10.2f %-10.2f %-10.2f %-10.2f %-10.2f %s\n",
                task.name,
                task.duration,
                task.earliestStart,
                task.earliestFinish,
                task.latestStart,
                task.latestFinish,
                task.slack,
                task.isCritical ? "Yes" : "No"));
        }
        
        // Critical path
        result.append("\nCritical Path: ");
        boolean first = true;
        for (Task task : tasks) {
            if (task.isCritical) {
                if (!first) result.append(" → ");
                result.append(task.name);
                first = false;
            }
        }
        
        // Project duration
        double projectDuration = tasks.stream()
            .mapToDouble(t -> t.earliestFinish)
            .max()
            .orElse(0.0);
        result.append(String.format("\n\nTotal Route Duration: %.2f minutes", projectDuration));
        
        return result.toString();
    }
}
