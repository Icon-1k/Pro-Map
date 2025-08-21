import java.util.*;

public class NorthwestCorner {
    public static class RouteSegment {
        public String from;
        public String to;
        public double distance;
        public double allocation;

        public RouteSegment(String from, String to, double distance, double allocation) {
            this.from = from;
            this.to = to;
            this.distance = distance;
            this.allocation = allocation;
        }

        @Override
        public String toString() {
            return String.format("%s → %s (%.2f meters, %.2f flow)", from, to, distance, allocation);
        }
    }

    public static class TransportationResult {
        public List<RouteSegment> segments;
        public double totalDistance;
        public List<String> route;
        public List<String> steps;

        public TransportationResult() {
            this.segments = new ArrayList<>();
            this.totalDistance = 0;
            this.route = new ArrayList<>();
            this.steps = new ArrayList<>();
        }
    }

    public static TransportationResult findInitialRoute(Graph graph, Nodes start, Nodes end) {
        TransportationResult result = new TransportationResult();
        Map<String, Double> supply = new HashMap<>();
        Map<String, Double> demand = new HashMap<>();

        // Initialize supply/demand for all nodes
        initializeSupplyDemand(graph, start, end, supply, demand);
        
        // Create mapping of distances between nodes
        Map<String, Map<String, Double>> distances = createDistanceMap(graph);
        
        // Apply Northwest Corner Method
        applyNWCM(supply, demand, distances, result);
        
        return result;
    }

    private static void initializeSupplyDemand(Graph graph, Nodes start, Nodes end,
            Map<String, Double> supply, Map<String, Double> demand) {
        // Set initial supply at start node
        supply.put(start.name, 1.0);
        
        // Set demand at end node
        demand.put(end.name, 1.0);
        
        // Add intermediate nodes with balanced supply/demand
        for (Nodes node : graph.getNodes()) {
            if (!node.equals(start) && !node.equals(end)) {
                supply.put(node.name, 0.5);
                demand.put(node.name, 0.5);
            }
        }
    }

    private static Map<String, Map<String, Double>> createDistanceMap(Graph graph) {
        Map<String, Map<String, Double>> distances = new HashMap<>();
        
        // Initialize distance map for all nodes
        for (Nodes node : graph.getNodes()) {
            distances.put(node.name, new HashMap<>());
        }
        
        // Fill in distances from edges
        for (Nodes from : graph.getNodes()) {
            for (Edge edge : from.edges) {
                distances.get(from.name).put(edge.destination.name, edge.weight);
            }
        }
        
        return distances;
    }

    private static void applyNWCM(Map<String, Double> supply, Map<String, Double> demand,
            Map<String, Map<String, Double>> distances, TransportationResult result) {
        // Get sorted lists of supply and demand points
        List<String> supplyPoints = new ArrayList<>(supply.keySet());
        List<String> demandPoints = new ArrayList<>(demand.keySet());

        while (!supplyPoints.isEmpty() && !demandPoints.isEmpty()) {
            String from = supplyPoints.get(0);
            String to = demandPoints.get(0);
            
            double availableSupply = supply.get(from);
            double requiredDemand = demand.get(to);
            
            // Get distance between points
            Double distance = distances.get(from).getOrDefault(to, Double.MAX_VALUE);
            
            if (distance != Double.MAX_VALUE) {
                // Calculate allocation
                double allocation = Math.min(availableSupply, requiredDemand);
                
                // Record the route segment
                RouteSegment segment = new RouteSegment(from, to, distance, allocation);
                result.segments.add(segment);
                result.totalDistance += distance * allocation;
                
                // Update supply and demand
                supply.put(from, availableSupply - allocation);
                demand.put(to, requiredDemand - allocation);
                
                // Record step
                result.steps.add(String.format(
                    "Allocated %.2f units from %s to %s (Distance: %.2f meters)",
                    allocation, from, to, distance
                ));
                
                // Remove points with zero supply/demand
                if (supply.get(from) <= 0) {
                    supplyPoints.remove(0);
                }
                if (demand.get(to) <= 0) {
                    demandPoints.remove(0);
                }
            } else {
                // If no direct connection, try next destination
                demandPoints.remove(0);
            }
        }
        
        // Build final route
        buildRoute(result);
    }
    
    private static void buildRoute(TransportationResult result) {
        if (result.segments.isEmpty()) {
            return;
        }
        
        // Start with the first segment
        RouteSegment first = result.segments.get(0);
        result.route.add(first.from);
        result.route.add(first.to);
        
        // Add subsequent segments
        for (int i = 1; i < result.segments.size(); i++) {
            RouteSegment segment = result.segments.get(i);
            if (!result.route.contains(segment.from)) {
                result.route.add(segment.from);
            }
            if (!result.route.contains(segment.to)) {
                result.route.add(segment.to);
            }
        }
    }

    public static String formatResults(TransportationResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Northwest Corner Method Analysis:\n\n");
        
        // Route segments
        sb.append("Route Segments:\n");
        for (RouteSegment segment : result.segments) {
            sb.append("  ").append(segment).append("\n");
        }
        
        // Complete route
        sb.append("\nComplete Route: ");
        sb.append(String.join(" → ", result.route));
        
        // Total distance
        sb.append(String.format("\n\nTotal Distance: %.2f meters\n", result.totalDistance));
        
        // Steps taken
        sb.append("\nAllocation Steps:\n");
        for (String step : result.steps) {
            sb.append("  ").append(step).append("\n");
        }
        
        return sb.toString();
    }
}